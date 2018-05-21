/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.nazmul.eparser;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Model;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;
import java.io.FileInputStream;
import java.util.HashMap;
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
//        ANTLRInputStream input = new ANTLRInputStream(
//                new FileInputStream("C:\\Users\\Md Nazmul Karim\\Documents\\NetBeansProjects\\MutationTestApp\\src\\dse\\nazmul\\eparser\\t.expr")); 

//11~(2*(p2+1))>(2*p1)
//16~((2*p1)+(2*(p2+1)))<=5



        Context context = null;
        HashMap<String, String> cfg = new HashMap<String, String>();
        HashMap<String, IntExpr> existingSymbol = new HashMap<String, IntExpr>();
        cfg.put("model", "true");
        context = new Context(cfg);
        existingSymbol.clear();
        Solver mainSolver = context.mkSolver();        
        mainSolver.reset();       


        ANTLRInputStream input = new ANTLRInputStream("(2*(p2+1))\n"); 
        
        ConditionExpLexer lexer = new ConditionExpLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ConditionExpParser parser = new ConditionExpParser(tokens);
        ParseTree tree = parser.prog();
        
        EvalVisitor eval = new EvalVisitor(context,existingSymbol);
       //eval.visit(tree);
        
        ArithExpr e1 =  eval.visit(tree);
        System.out.println(e1);
        
        ANTLRInputStream input2 = new ANTLRInputStream("((2*p1))\n"); 
        
        ConditionExpLexer lexer2 = new ConditionExpLexer(input2);
        CommonTokenStream tokens2 = new CommonTokenStream(lexer2);
        ConditionExpParser parser2 = new ConditionExpParser(tokens2);
        ParseTree tree2 = parser2.prog();
        
        EvalVisitor eval2 = new EvalVisitor(context,existingSymbol);
       //eval.visit(tree);
         ArithExpr e2 =eval2.visit(tree2);
         
         System.out.println(e2);
         mainSolver.add(context.mkGt(e2, e1));
         
         
         Model model = null;
        if (Status.SATISFIABLE == mainSolver.check())
        {
            model = mainSolver.getModel();
            System.out.println(model);
           
        } 
        else
        {
           System.out.println("Not satisfiable.");
        }
        //System.out.println();
        
       // System.out.println(eval.comExpr.arithmeticExpression.simplify());
        
    }
    
}
