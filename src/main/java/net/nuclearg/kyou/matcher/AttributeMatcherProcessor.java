package net.nuclearg.kyou.matcher;

import net.nuclearg.kyou.AnnotatedClassProcessor;
import net.nuclearg.kyou.ClassInfo;
import net.nuclearg.kyou.LinkInfo;

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

        builder.append("#### �������").append(LN);
        builder.append(LN);
        builder.append("_�ص�[[ȫ��ƥ�����б�]]_");

        String pageName = "[����ƥ����] " + name;
        MatcherListProcessor.attrMatchers.add(new LinkInfo(pageName, desc));

        return new Output(pageName, builder.toString());
    }

}
