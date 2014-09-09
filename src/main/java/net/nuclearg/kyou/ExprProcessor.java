package net.nuclearg.kyou;

import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.JavadocComment;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.expr.FieldAccessExpr;
import japa.parser.ast.expr.MemberValuePair;
import japa.parser.ast.expr.NormalAnnotationExpr;
import japa.parser.ast.expr.StringLiteralExpr;

import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;

import org.apache.commons.io.FileUtils;

class ExprProcessor implements Processor {

    @Override
    public boolean supports(File file) {
        return file.getName().matches(".+?Expr.java");
    }

    @Override
    public void process(File file, File dstDir) throws Exception {
        /*
         * 读源文件
         */
        CompilationUnit unit = JavaParser.parse(file, "utf-8");
        TypeDeclaration type = unit.getTypes().get(0);
        NormalAnnotationExpr annotation = (NormalAnnotationExpr) type.getAnnotations().get(0);
        JavadocComment doc = type.getJavaDoc();

        ExprDescription desc = new ExprDescription();

        desc.name = type.getName().substring(0, type.getName().length() - "Expr".length());
        /*
         * 处理annotation
         */
        for (MemberValuePair pair : annotation.getPairs())
            switch (pair.getName()) {
                case "name":
                    desc.tag = ((StringLiteralExpr) pair.getValue()).getValue();
                    break;
                case "postfix":
                    desc.postfixType = ((FieldAccessExpr) pair.getValue()).getField();
                    break;
                case "typeIn":
                    desc.inType = ((FieldAccessExpr) pair.getValue()).getField();
                    break;
                case "typeOut":
                    desc.outType = ((FieldAccessExpr) pair.getValue()).getField();
                    break;
            }

        /*
         * 处理doc
         */
        BufferedReader r = new BufferedReader(new StringReader(doc.getContent()));
        String ln;
        while ((ln = r.readLine()) != null) {
            if (ln.startsWith(" * "))
                ln = ln.substring(" * ".length()).trim();

            if (ln.isEmpty())
                continue;

            if (ln.startsWith("<"))
                ln = "";

            if (ln.startsWith("@in")) {
                desc.inDesc = ln.substring("@in".length());
                continue;
            }

            if (ln.startsWith("@out")) {
                desc.outDesc = ln.substring("@out".length());
                continue;
            }

            if (ln.startsWith("@postfix")) {
                desc.postfixDesc = ln.substring("@postfix".length());
                continue;
            }

            if (ln.startsWith("@author"))
                break;

            desc.desc += ln + System.lineSeparator();
        }
        r.close();

        /*
         * 输出
         */
        File dstFile = new File(dstDir.getAbsolutePath() + File.separator + desc.name + ".md");
        FileUtils.write(dstFile, desc.toString(), "utf-8");
    }
}
