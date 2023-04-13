package ca.concordia;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.io.IOException;
import java.util.Map;

class MethodFinder {

  public static String findMethodName(String filename, int lineNumber) throws IOException {

    String source = FileUtil.read(filename);

    ASTParser parser = ASTParser.newParser(AST.getJLSLatest());

    Map<String, String> options = JavaCore.getOptions();
    JavaCore.setComplianceOptions(JavaCore.VERSION_11, options);
    parser.setCompilerOptions(options);

    parser.setSource(source.toCharArray());
    CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

    FindMethodVisitor visitor = new FindMethodVisitor(compilationUnit, lineNumber);
    compilationUnit.accept(visitor);


    return visitor.getMethodName();
  }
}