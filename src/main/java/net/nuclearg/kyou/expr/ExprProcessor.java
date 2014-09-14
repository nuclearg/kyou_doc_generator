package net.nuclearg.kyou.expr;

import net.nuclearg.kyou.AnnotatedClassProcessor;
import net.nuclearg.kyou.ClassInfo;

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
        builder.append("#### ÊäÈë _(").append(inType).append(")_").append(LN);
        builder.append(LN);
        builder.append(inDesc).append(LN);
        builder.append(LN);
        builder.append("#### Êä³ö _(").append(outType).append(")_").append(LN);
        builder.append(LN);
        builder.append(outDesc).append(LN);
        builder.append(LN);
        builder.append("#### ºó×º _(").append(postfixType).append(")_").append(LN);
        builder.append(LN);
        if (postfixDesc != null) {
            builder.append(postfixDesc);
            builder.append(LN);
        }

        // if (this.complexPostfixFields == null) {
        // builder.append(postfixDesc).append(LN);
        // builder.append(LN);
        // } else
        // for (ExprComplexFieldDescription field : this.complexPostfixFields.values()) {
        // builder.append("* ").append(field.name).append(" _(").append(field.type).append(")_").append(LN);
        // builder.append(LN);
        // builder.append("    ").append(field.desc).append(LN);
        // builder.append(LN);
        // }

        super.buildExample(info, builder);

        return new Output("[Expr] " + tag, builder.toString());
    }

}
