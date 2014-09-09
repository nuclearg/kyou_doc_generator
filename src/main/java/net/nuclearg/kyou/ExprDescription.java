package net.nuclearg.kyou;

public class ExprDescription {
    /**
     * 标签
     */
    String tag = "";
    /**
     * 名称
     */
    String name = "";
    /**
     * 描述
     */
    String desc = "";
    /**
     * 输入类型
     */
    String inType = "";
    /**
     * 输入描述
     */
    String inDesc = "";
    /**
     * 输出类型
     */
    String outType = "";
    /**
     * 输出描述
     */
    String outDesc = "";
    /**
     * 后缀类型
     */
    String postfixType = "";
    /**
     * 后缀描述
     */
    String postfixDesc = "";

    @Override
    public String toString() {
        String LN = System.lineSeparator();

        StringBuilder builder = new StringBuilder();

        builder.append("# ").append(tag).append(" ").append("(").append(name).append(")").append(LN);
        builder.append(LN);
        builder.append(desc);
        builder.append(LN);
        builder.append("* **输入** _(").append(inType).append(")_").append(LN);
        builder.append(LN);
        builder.append(inDesc).append(LN);
        builder.append(LN);
        builder.append("* **输出** _(").append(outType).append(")_").append(LN);
        builder.append(LN);
        builder.append(outDesc).append(LN);
        builder.append(LN);
        builder.append("* **后缀** _(").append(postfixType).append(")_").append(LN);
        builder.append(LN);
        builder.append(postfixDesc).append(LN);
        builder.append(LN);

        return builder.toString();
    }
}
