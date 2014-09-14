package net.nuclearg.kyou;

import java.io.File;

public interface Processor {
    public boolean supports(File file);

    public void process(File file, File dstDir) throws Exception;
}
