/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.nazmul.eparser;

import java.io.FileInputStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 *
 * @author Md Nazmul Karim
 */
public class Caller {
    
    public static void main(String[] args) throws Exception {
//        String inputFile = null; 
//        if ( args.length>0 ) inputFile = args[0];
//        InputStream is = System.in;
//        if ( inputFile!=null ) is = new FileInputStream(inputFile);
//        ANTLRInputStream input = new ANTLRInputStream(is); 
        ANTLRInputStream input = new ANTLRInputStream(
            new FileInputStream("C:\\Users\\Md Nazmul Karim\\Documents\\NetBeansProjects\\MutationTestApp\\src\\dse\\nazmul\\eparser\\t.expr")); 
        
        ConditionExpLexer lexer = new ConditionExpLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ConditionExpParser parser = new ConditionExpParser(tokens);
        ParseTree tree = parser.prog();
        
        EvalVisitor eval = new EvalVisitor();
        eval.visit(tree);
        
       // System.out.println(eval.comExpr.arithmeticExpression.simplify());
        
    }
    
}
