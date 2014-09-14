package net.nuclearg.kyou.matcher;

import net.nuclearg.kyou.AnnotatedClassProcessor;
import net.nuclearg.kyou.ClassInfo;

public class FilterMatcherProcessor extends AnnotatedClassProcessor {

    @Override
    protected String path() {
        return "matcher/filter";
    }

    @Override
    protected String annotationClassName() {
        return "FilterMatcherDescription";
    }

    @Override
    protected Output process(ClassInfo info) {
        String tag = info.annotationValues.get("name");
        String name = info.className;
        String desc = info.docEntries.get("");
        String paramType = info.annotationValues.get("paramType");
        String paramDesc = info.annotationValues.get("param");

        StringBuilder builder = new StringBuilder();
        String LN = System.lineSeparator();

        builder.append("> :**").append(tag).append("**");
        switch (paramType) {
            case "Integer":
                builder.append("(param)");
                break;
            case "String":
                builder.append("(\"param\")");
                break;
        }

        builder.append(" -- _").append(name).append("_").append(LN);
        builder.append(LN);
        builder.append(desc).append(LN);
        builder.append(LN);
        builder.append("* ²ÎÊı _(").append(paramType).append(")_").append(LN);
        builder.append(LN);
        if (paramDesc != null) {
            builder.append(paramDesc);
            builder.append(LN);
        }

        super.buildExample(info, builder);

        return new Output("[Filter] " + name, builder.toString());
    }

}
