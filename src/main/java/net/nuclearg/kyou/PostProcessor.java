package net.nuclearg.kyou;

import java.io.File;

public interface PostProcessor {
    public void process(File dstDir) throws Exception;
}
