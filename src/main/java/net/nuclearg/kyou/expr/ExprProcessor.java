package net.nuclearg.kyou.expr;

import java.util.HashMap;
import java.util.Map;

import net.nuclearg.kyou.AnnotatedClassProcessor;
import net.nuclearg.kyou.ClassInfo;

import org.apache.commons.lang.StringUtils;

public class ExprProcessor extends AnnotatedClassProcessor {

    @Override
    protected String path() {
        return "pack/expr";
    }

    @Override
    protected String annotationClassName() {
        return "ExprDescription";
    }

    @Override
    protected Output process(ClassInfo info) {
        String tag = info.annotationValues.get("name");
        String name = info.className;
        String desc = info.docEntries.get("");
        String inType = info.annotationValues.get("typeIn");
        String inDesc = info.docEntries.get("in");
        String outType = info.annotationValues.get("typeOut");
        String outDesc = info.docEntries.get("out");
        String postfixType = info.annotationValues.get("postfix");
        String postfixDesc = info.docEntries.get("postfix");

        StringBuilder builder = new StringBuilder();
        String LN = System.lineSeparator();

        builder.append("> **").append(tag).append("** -- _").append(name).append("_").append(LN);
        builder.append(LN);
        builder.append(desc).append(LN);
        builder.append(LN);
        builder.append("#### 输入 _(").append(inType).append(")_").append(LN);
        builder.append(LN);
        builder.append(inDesc).append(LN);
        builder.append(LN);
        builder.append("#### 输出 _(").append(outType).append(")_").append(LN);
        builder.append(LN);
        builder.append(outDesc).append(LN);
        builder.append(LN);
        builder.append("#### 后缀 _(").append(postfixType).append(")_").append(LN);
        builder.append(LN);
        if (postfixDesc != null) {
            // 判断是不是有多个后缀
            if (postfixType.equals("Complex")) {
                // 解析doc
                Map<String, String> fieldDescMap = new HashMap<>();
                for (String fieldDesc : StringUtils.splitByWholeSeparator(info.docEntries.get("postfix"), ClassInfo.DOC_ITEM_DELIMITER)) {
                    int pos = fieldDesc.indexOf(' ');
                    String n = fieldDesc.substring(0, pos);
                    String v = fieldDesc.substring(pos + 1);

                    fieldDescMap.put(n, v);
                }

                // 解析annotation
                for (Map<String, String> field : info.annotationSubValues.get("complexPostfixFields")) {
                    String fieldName = field.get("name");
                    String fieldType = field.get("type");
                    String fieldDesc = fieldDescMap.get(fieldName);

                    builder.append("* ").append(fieldName).append(" _(").append(fieldType).append(")_").append(LN);
                    builder.append(LN);
                    builder.append("    ").append(fieldDesc).append(LN);
                    builder.append(LN);
                }
            }
            else {
                builder.append(postfixDesc);
                builder.append(LN);
            }
        }

        super.buildExample(info, builder);

        return new Output("[Expr] " + tag, builder.toString());
    }

}
