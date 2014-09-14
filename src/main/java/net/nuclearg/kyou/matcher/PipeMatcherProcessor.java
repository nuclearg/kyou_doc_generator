package net.nuclearg.kyou.matcher;

import net.nuclearg.kyou.AnnotatedClassProcessor;
import net.nuclearg.kyou.ClassInfo;

public class PipeMatcherProcessor extends AnnotatedClassProcessor {

    @Override
    protected String path() {
        return "matcher/pipe";
    }

    @Override
    protected String annotationClassName() {
        return "PipeMatcherDescription";
    }

    @Override
    protected Output process(ClassInfo info) {
        String tag = info.annotationValues.get("value");
        String name = info.className;
        String desc = info.docEntries.get("");

        StringBuilder builder = new StringBuilder();
        String LN = System.lineSeparator();

        builder.append("> {left}**").append(tag).append("**{right} -- _").append(name).append("_").append(LN);
        builder.append(LN);
        builder.append(desc).append(LN);
        builder.append(LN);

        super.buildExample(info, builder);

        return new Output("[Pipe] " + name, builder.toString());
    }

}
