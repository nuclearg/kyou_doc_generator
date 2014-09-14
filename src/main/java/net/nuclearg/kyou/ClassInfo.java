package net.nuclearg.kyou;

import japa.parser.JavaParserHelper;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.expr.ArrayInitializerExpr;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.FieldAccessExpr;
import japa.parser.ast.expr.IntegerLiteralExpr;
import japa.parser.ast.expr.MemberValuePair;
import japa.parser.ast.expr.NormalAnnotationExpr;
import japa.parser.ast.expr.SingleMemberAnnotationExpr;
import japa.parser.ast.expr.StringLiteralExpr;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;

public class ClassInfo {
    public static final String DOC_ITEM_DELIMITER = "<<<DOC_ITEM_DELIMITER>>>";
    private static final String LN = SystemUtils.LINE_SEPARATOR;

    public final String className;

    public final Map<String, String> docEntries;

    public final Map<String, String> annotationValues;
    public final Map<String, List<Map<String, String>>> annotationSubValues;

    public final AnnotationExpr annotationExpr;

    @SuppressWarnings("unchecked")
    public ClassInfo(File file) {
        try {
            /*
             * 读源文件
             */
            String source = FileUtils.readFileToString(file, "utf8");
            InternalClassInfo info = new InternalClassInfo(source);

            /*
             * 读取基本信息
             */
            // 类名
            this.className = info.clsName;

            /*
             * 处理javadoc
             */
            Map<String, StringBuilder> docMap = new LinkedHashMap<>();
            StringBuilder current;
            docMap.put("", current = new StringBuilder());

            for (String ln : info.javadocLines) {
                if (StringUtils.isBlank(ln))
                    continue;

                if (ln.startsWith("<"))
                    ln = "";

                if (ln.startsWith("@")) {
                    int pos = ln.indexOf(' ');
                    if (pos < 0)
                        continue;

                    String name = ln.substring(1, pos);
                    ln = ln.substring(pos + 1);

                    if (docMap.containsKey(name)) {
                        current = docMap.get(name);
                        current.append(DOC_ITEM_DELIMITER);
                    } else {
                        current = new StringBuilder();
                        docMap.put(name, current);
                    }
                }

                current.append(ln).append(LN);
            }

            Map<String, String> docEntries = new LinkedHashMap<>();
            for (Entry<String, StringBuilder> entry : docMap.entrySet())
                docEntries.put(entry.getKey(), entry.getValue().toString().trim());
            this.docEntries = Collections.unmodifiableMap(docEntries);

            /*
             * 处理annotation
             */
            if (info.annotation == null) {
                this.annotationValues = Collections.emptyMap();
                this.annotationSubValues = Collections.emptyMap();
                this.annotationExpr = null;
            } else {
                Map<String, String> annotationValues = new LinkedHashMap<>();
                Map<String, List<Map<String, String>>> annotationSubValues = new LinkedHashMap<>();
                if (info.annotation instanceof NormalAnnotationExpr)
                    for (MemberValuePair pair : ((NormalAnnotationExpr) info.annotation).getPairs()) {
                        Object value = getExprValue(pair.getValue());
                        if (value instanceof String)
                            annotationValues.put(pair.getName(), (String) getExprValue(pair.getValue()));
                        else
                            annotationSubValues.put(pair.getName(), (List<Map<String, String>>) value);
                    }
                else {
                    SingleMemberAnnotationExpr annotationExpr = (SingleMemberAnnotationExpr) info.annotation;
                    Object value = getExprValue(annotationExpr.getMemberValue());
                    if (value instanceof String)
                        annotationValues.put("value", (String) value);
                    else
                        annotationSubValues.put("value", (List<Map<String, String>>) value);
                }
                this.annotationValues = Collections.unmodifiableMap(annotationValues);
                this.annotationSubValues = Collections.unmodifiableMap(annotationSubValues);
                this.annotationExpr = info.annotation;
            }
        } catch (Exception ex) {
            throw new RuntimeException("parse file fail. file: " + file, ex);
        }
    }

    private static class InternalClassInfo {
        final String clsName;
        final List<String> javadocLines;
        final AnnotationExpr annotation;

        InternalClassInfo(String source) {
            List<String> javadocLines = new ArrayList<>();
            StringBuilder annotation = new StringBuilder();
            String clsName = null;

            String[] lines = StringUtils.split(source, "\r\n");

            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty())
                    continue;
                if (line.startsWith("package") || line.startsWith("import"))
                    continue;

                if (line.startsWith("/**") || line.startsWith("*/"))
                    continue;

                if (line.startsWith("*")) {
                    line = line.substring(1);
                    javadocLines.add(line.trim());
                    continue;
                }

                if (line.contains("class ")) {
                    clsName = line.substring(line.indexOf("class ") + "class ".length());
                    clsName = clsName.substring(0, clsName.indexOf(' '));
                    break;
                }

                annotation.append(line);
            }

            this.javadocLines = Collections.unmodifiableList(javadocLines);
            this.annotation = annotation.length() > 0 ? JavaParserHelper.parseAnnotation(annotation.toString()) : null;
            this.clsName = clsName;
        }
    }

    private Object getExprValue(Expression expr) {
        if (expr instanceof StringLiteralExpr)
            return ((StringLiteralExpr) expr).getValue();
        if (expr instanceof IntegerLiteralExpr)
            return ((IntegerLiteralExpr) expr).getValue();
        if (expr instanceof FieldAccessExpr)
            return ((FieldAccessExpr) expr).getField();
        if (expr instanceof ArrayInitializerExpr) {
            ArrayInitializerExpr arrayInitExpr = (ArrayInitializerExpr) expr;
            List<Map<String, String>> fields = new ArrayList<>();
            for (Expression subExpr : arrayInitExpr.getValues()) {
                Map<String, String> map = new LinkedHashMap<>();

                for (MemberValuePair pair : ((NormalAnnotationExpr) subExpr).getPairs())
                    map.put(pair.getName(), (String) getExprValue(pair.getValue()));

                fields.add(Collections.unmodifiableMap(map));
            }

            return Collections.unmodifiableList(fields);
        }
        return null;
    }
}
