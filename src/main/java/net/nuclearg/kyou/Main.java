package net.nuclearg.kyou;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.nuclearg.kyou.expr.ExprProcessor;
import net.nuclearg.kyou.matcher.AttributeMatcherProcessor;
import net.nuclearg.kyou.matcher.FilterMatcherProcessor;
import net.nuclearg.kyou.matcher.PipeMatcherProcessor;

public class Main {
    public static void main(String[] args) throws Exception {
        File srcDir;
        if (args.length == 0)
            srcDir = new File(".");
        else
            srcDir = new File(args[0]);

        File dstDir;
        if (args.length <= 1)
            dstDir = new File(".");
        else
            dstDir = new File(args[1]);

        Processor[] processors = {
                new ExprProcessor(),
                new AttributeMatcherProcessor(),
                new FilterMatcherProcessor(),
                new PipeMatcherProcessor() };

        for (File file : getFiles(srcDir))
            for (Processor processor : processors)
                if (processor.supports(file))
                    processor.process(file, dstDir);
    }

    private static List<File> getFiles(File file) {
        if (file.isFile())
            return Arrays.asList(file);

        List<File> files = new ArrayList<>();
        for (File f : file.listFiles())
            files.addAll(getFiles(f));
        return files;
    }
}
