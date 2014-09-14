package japa.parser;

import japa.parser.ast.expr.AnnotationExpr;

import java.io.StringReader;

public class JavaParserHelper {
    public static AnnotationExpr parseAnnotation(String str) {
        try {
            return new ASTParser(new StringReader(str)).Annotation();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
