package net.nuclearg.kyou.matcher;

import net.nuclearg.kyou.AnnotatedClassProcessor;
import net.nuclearg.kyou.ClassInfo;

public class AttributeMatcherProcessor extends AnnotatedClassProcessor {

    @Override
    protected String path() {
        return "matcher/attribute";
    }

    @Override
    protected String annotationClassName() {
        return "OperatorDescription";
    }

    @Override
    protected Output process(ClassInfo info) {
        String tag = info.annotationValues.get("value");
        String name = info.className;
        String desc = info.docEntries.get("");

        StringBuilder builder = new StringBuilder();
        String LN = System.lineSeparator();

        builder.append("> [attr**").append(tag).append("**'value'] -- _").append(name).append("_").append(LN);
        builder.append(LN);
        builder.append(desc).append(LN);
        builder.append(LN);

        super.buildExample(info, builder);

        builder.append("#### 相关链接").append(LN);
        builder.append(LN);
        builder.append("_回到[[全部匹配器列表]]_");

        return new Output("[Attr] " + name, builder.toString());
    }

}
