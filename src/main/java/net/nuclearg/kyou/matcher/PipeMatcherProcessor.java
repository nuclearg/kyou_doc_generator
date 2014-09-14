package net.nuclearg.kyou.matcher;

import net.nuclearg.kyou.AnnotatedClassProcessor;
import net.nuclearg.kyou.ClassInfo;
import net.nuclearg.kyou.LinkInfo;

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

        builder.append("#### 相关链接").append(LN);
        builder.append(LN);
        builder.append("_回到[[全部匹配器列表]]_");

        String pageName = "[管道匹配器] " + name;
        MatcherListProcessor.pipeMatchers.add(new LinkInfo(pageName, desc));

        return new Output(pageName, builder.toString());
    }

}
