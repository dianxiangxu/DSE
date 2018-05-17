/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.nazmul.parser;

import java.io.FileInputStream;
import java.io.IOException;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

/**
 *
 * @author Md Nazmul Karim
 */
public class AntlrRunner {
    public static void main(String[] args) {
    try {
             //((2*p1)+(2*(p2+1)))    ,(2*(p2+1))   , loop : (((0+1)+1)+1) , (((p1+p2)+p2)+p2)
        ANTLRInputStream input = new ANTLRInputStream(
            new FileInputStream("C:\\Users\\Md Nazmul Karim\\Documents\\NetBeansProjects\\MutationTestApp\\src\\dse\\nazmul\\parser\\CONDEXP"));    

        CONDEXPLexer lexer = new CONDEXPLexer(input);
        CONDEXPParser parser = new CONDEXPParser(new CommonTokenStream(lexer));
        ConditionExpressionListener listener = new ConditionExpressionListener();
        parser.addParseListener(listener);

        parser.expr(); 
        listener.print();
        
        
        ////////////
//         ANTLRInputStream input = new ANTLRInputStream(
//            new FileInputStream("C:\\applications\\java\\FHEProg\\src\\fheprog\\CONDEXP"));    
//
//        CONDEXPLexer lexer = new CONDEXPLexer(input);
//        CONDEXPParser parser = new CONDEXPParser(new CommonTokenStream(lexer));
//        ParseTree tree = parser.expr();
//       new ParseTreeWalker().walk(new MyExpListener(), tree);
//      //  parser.addParseListener();
//
//       // Parse
//        // Start parsing
//        parser.expr(); 
        
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
