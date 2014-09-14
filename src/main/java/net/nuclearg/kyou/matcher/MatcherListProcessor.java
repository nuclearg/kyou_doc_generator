package net.nuclearg.kyou.matcher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.nuclearg.kyou.LinkInfo;
import net.nuclearg.kyou.PostProcessor;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;

public class MatcherListProcessor implements PostProcessor {
    public static final List<LinkInfo> pipeMatchers = new ArrayList<>();
    public static final List<LinkInfo> attrMatchers = new ArrayList<>();
    public static final List<LinkInfo> filterMatchers = new ArrayList<>();

    @Override
    public void process(File dstDir) throws Exception {
        String LN = SystemUtils.LINE_SEPARATOR;

        StringBuilder builder = new StringBuilder();

        builder.append("#### ����ƥ����").append(LN);
        builder.append(LN);
        builder.append("* [[���ھ���·����ƥ����]]").append(LN);
        builder.append("* [[���ڽڵ����͵�ƥ����]]").append(LN);
        builder.append("* [[���ڽڵ����Ƶ�ƥ����]]").append(LN);

        builder.append("#### �ܵ�ƥ����").append(LN);
        builder.append(LN);
        builder.append(StringUtils.join(pipeMatchers.toArray()));
        builder.append(LN);

        builder.append("#### ����ƥ����").append(LN);
        builder.append(LN);
        builder.append(StringUtils.join(attrMatchers.toArray()));
        builder.append(LN);

        builder.append("#### ����ƥ����").append(LN);
        builder.append(LN);
        builder.append(StringUtils.join(filterMatchers.toArray()));
        builder.append(LN);

        File dstFile = new File(dstDir.getAbsolutePath() + File.separator + "ȫ��ƥ�����б�.md");
        FileUtils.write(dstFile, builder.toString(), "utf-8");
    }

}
