package net.nuclearg.kyou;

import java.io.File;

public interface FileProcessor {
    public boolean supports(File file);

    public void process(File file, File dstDir) throws Exception;
}
