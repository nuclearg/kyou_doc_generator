package net.nuclearg.kyou;

import java.io.File;

interface Processor {
    boolean supports(File file);

    void process(File file, File dstDir) throws Exception;
}
