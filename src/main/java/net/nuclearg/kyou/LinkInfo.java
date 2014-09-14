package net.nuclearg.kyou;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;

public class LinkInfo {
    public final String name;
    public final String url;
    public final String desc;

    public LinkInfo(String pageName, String desc) {
        this.name = name(pageName);
        this.url = url(pageName);
        this.desc = desc(desc);
    }

    @Override
    public String toString() {
        String LN = SystemUtils.LINE_SEPARATOR;
        return "* [" + this.name + "](" + this.url + ") <br/>" + LN + desc + LN + LN;
    }

    private static String name(String pageName) {
        return StringUtils.split(pageName, ' ')[1];
    }

    private static String url(String pageName) {
        pageName = StringUtils.replace(pageName, " ", "-");
        pageName = StringUtils.replace(pageName, "[", "%5B");
        pageName = StringUtils.replace(pageName, "]", "%5D");
        pageName = StringUtils.replace(pageName, "<", "-");
        pageName = StringUtils.replace(pageName, ">", "-");
        return "https://github.com/nuclearg/kyou/wiki/" + pageName;
    }

    private static String desc(String desc) {
        return StringUtils.split(desc, "\r\n")[0];
    }
}
