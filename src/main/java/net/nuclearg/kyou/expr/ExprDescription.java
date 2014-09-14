package net.nuclearg.kyou.expr;

import java.util.Map;

class ExprDescription {
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
    /**
     * ���Ӻ�׺�ֶ�
     */
    Map<String, ExprComplexFieldDescription> complexPostfixFields;

    @Override
    public String toString() {
        String LN = System.lineSeparator();

        StringBuilder builder = new StringBuilder();

        builder.append("> **").append(tag).append("** -- _").append(name).append("_").append(LN);
        builder.append(LN);
        builder.append(desc);
        builder.append(LN);
        builder.append("#### ���� _(").append(inType).append(")_").append(LN);
        builder.append(LN);
        builder.append(inDesc).append(LN);
        builder.append(LN);
        builder.append("#### ��� _(").append(outType).append(")_").append(LN);
        builder.append(LN);
        builder.append(outDesc).append(LN);
        builder.append(LN);
        builder.append("#### ��׺ _(").append(postfixType).append(")_").append(LN);
        builder.append(LN);
        if (this.complexPostfixFields == null) {
            builder.append(postfixDesc).append(LN);
            builder.append(LN);
        } else
            for (ExprComplexFieldDescription field : this.complexPostfixFields.values()) {
                builder.append("* ").append(field.name).append(" _(").append(field.type).append(")_").append(LN);
                builder.append(LN);
                builder.append("    ").append(field.desc).append(LN);
                builder.append(LN);
            }

        return builder.toString();
    }
}
