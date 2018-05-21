/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.nazmul.eparser;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;
import java.util.HashMap;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 *
 * @author Md Nazmul Karim
 */
public class ArithmeticExpressionGenerator {
    
    private String input = "";
    private Context context = null;
    private HashMap<String, IntExpr> existingSymbol = null;

   
    
    public ArithmeticExpressionGenerator(Context ctx, HashMap<String, IntExpr> existingSymbol)
    {
        setContext(ctx);
        setExistingSymbol(existingSymbol);
    }
    
    public ArithExpr parseAndGetArithExpr(String in)
    {
        EvalVisitor eval =null;
        try{
        this.input = in;
        System.out.println("Parser Input:"+in);
        ANTLRInputStream inputStream = new ANTLRInputStream(input+"\n"); 
        ConditionExpLexer lexer = new ConditionExpLexer(inputStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ConditionExpParser parser = new ConditionExpParser(tokens);
        ParseTree tree = parser.prog();
        
        eval = new EvalVisitor(context,existingSymbol);
        eval.visit(tree);    
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        return eval.getArithmeticExpression();
    }
    
    public void clear()
    {
        existingSymbol.clear();
    }
    
     public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public HashMap<String, IntExpr> getExistingSymbol() {
        return existingSymbol;
    }

    public void setExistingSymbol(HashMap<String, IntExpr> existingSymbol) {
        this.existingSymbol = existingSymbol;
    }
}
