package net.nuclearg.kyou;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;

public abstract class AnnotatedClassProcessor implements FileProcessor {

    @Override
    public boolean supports(File file) {
        if (!file.getName().endsWith(".java"))
            return false;

        if (!file.getParent().endsWith(this.path()))
            return false;

        try {
            ClassInfo info = new ClassInfo(file);
            if (info.className == null)
                return false;
            if (info.annotationExpr == null)
                return false;
            if (!this.annotationClassName().equals(info.annotationExpr.getName().getName()))
                return false;

            return true;
        } catch (Exception ex) {
            System.err.println("parse java file fail. file: " + file);
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public void process(File file, File dstDir) throws Exception {
        ClassInfo info = new ClassInfo(file);

        Output output = this.process(info);

        System.out.println(output.fileName);
        System.out.println(output.content);

        File dstFile = new File(dstDir.getAbsolutePath() + File.separator + output.fileName + ".md");
        FileUtils.write(dstFile, output.content, "utf-8");
    }

    protected abstract String path();

    protected abstract String annotationClassName();

    protected abstract Output process(ClassInfo info);

    public static class Output {
        public final String fileName;
        public final String content;

        public Output(String fileName, String content) {
            this.fileName = fileName;
            this.content = content;
        }
    }

    protected void buildExample(ClassInfo info, StringBuilder builder) {
        String exampleStr = info.docEntries.get("example");
        if (exampleStr == null)
            return;

        String LN = SystemUtils.LINE_SEPARATOR;

        builder.append("#### Ê¾Àý").append(LN);
        builder.append(LN);

        String[] examples = StringUtils.splitByWholeSeparator(exampleStr, ClassInfo.DOC_ITEM_DELIMITER);
        for (String example : examples) {
            String[] lines = StringUtils.split(example, LN);

            builder.append("* ").append(lines[0]).append("<br/>").append(LN);
            builder.append(LN);

            for (int i = 1; i < lines.length; i++)
                builder.append(lines[i]).append(LN).append(LN);
        }
    }
}
