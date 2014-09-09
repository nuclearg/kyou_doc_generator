package net.nuclearg.kyou;

public class ExprDescription {
    /**
     * ��ǩ
     */
    String tag = "";
    /**
     * ����
     */
    String name = "";
    /**
     * ����
     */
    String desc = "";
    /**
     * ��������
     */
    String inType = "";
    /**
     * ��������
     */
    String inDesc = "";
    /**
     * �������
     */
    String outType = "";
    /**
     * �������
     */
    String outDesc = "";
    /**
     * ��׺����
     */
    String postfixType = "";
    /**
     * ��׺����
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
        builder.append("* **����** _(").append(inType).append(")_").append(LN);
        builder.append(LN);
        builder.append(inDesc).append(LN);
        builder.append(LN);
        builder.append("* **���** _(").append(outType).append(")_").append(LN);
        builder.append(LN);
        builder.append(outDesc).append(LN);
        builder.append(LN);
        builder.append("* **��׺** _(").append(postfixType).append(")_").append(LN);
        builder.append(LN);
        builder.append(postfixDesc).append(LN);
        builder.append(LN);

        return builder.toString();
    }
}
