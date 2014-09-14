package net.nuclearg.kyou.expr;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.nuclearg.kyou.LinkInfo;
import net.nuclearg.kyou.PostProcessor;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

public class ExprListProcessor implements PostProcessor {
    public static final List<LinkInfo> exprLinks = new ArrayList<>();

    @Override
    public void process(File dstDir) throws Exception {
        String content = StringUtils.join(exprLinks.toArray());

        File dstFile = new File(dstDir.getAbsolutePath() + File.separator + "全部表达式列表.md");
        FileUtils.write(dstFile, content, "utf-8");
    }

}
