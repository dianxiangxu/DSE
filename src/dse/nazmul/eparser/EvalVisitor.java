/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.nazmul.eparser;

/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
***/
import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Solver;
import java.util.HashMap;
import java.util.Map;

public class EvalVisitor extends ConditionExpBaseVisitor<ArithExpr> {
    /** "memory" for our calculator; variable/value pairs go here */
    //Map<String, Integer> memory = new HashMap<String, Integer>();
    
    Context context = null;
   // HashMap<String, String> cfg = new HashMap<String, String>();
    HashMap<String, IntExpr> existingSymbol = null;
   // Solver mainSolver = null; 
    ArithExpr expression = null;
    
    public EvalVisitor(Context ctx, HashMap<String, IntExpr> existingSym){
    
        //cfg.put("model", "true");
        context = ctx;
        existingSymbol = existingSym;
        //expression = null;
        
        //mainSolver = context.mkSolver();        
        //mainSolver.reset();
        //existingSymbol.clear();
    }
   
    /** INT */
    @Override
    public IntExpr visitInt(ConditionExpParser.IntContext ctx) {
        String id = ctx.INT().getText();
        //System.out.println("Int:"+id);
        if(existingSymbol.get(id)!= null)
        {
            System.out.println("Symbol already exists:"+id);
            return (IntExpr) existingSymbol.get(id);
        }
        else
        {
            return context.mkInt(Integer.parseInt(id));// Integer.valueOf(ctx.INT().getText());
        }
        
    }

    /** ID */
    @Override
    public IntExpr visitId(ConditionExpParser.IdContext ctx) {
        String id = ctx.ID().getText();
       //System.out.println("ID:"+id);
        if(existingSymbol.get(id)!= null)
        {
            System.out.println("Symbol already exists:"+id);
            return (IntExpr) existingSymbol.get(id);
        }
        //else
       // {
            return context.mkIntConst(id);// Integer.valueOf(ctx.INT().getText());
       // }
    }

   

    /** expr op=('+'|'-') expr */
    @Override
    public ArithExpr visitArithOperation(ConditionExpParser.ArithOperationContext ctx) {
        boolean leftIsInt = false;
        boolean rightIsInt = false;
        if(visit(ctx.expr(0)) instanceof IntExpr)
        {
            leftIsInt = true;
           // System.out.println("left is int");
        }
        if(visit(ctx.expr(1)) instanceof IntExpr)
        {
            rightIsInt = true;
             //System.out.println("left is int");
        }
        
       // ArithExpr left = visit(ctx.expr(0)) ;  // get value of left subexpression
       // ArithExpr right = visit(ctx.expr(1)); // get value of right subexpression
        if ( ctx.op.getType() == ConditionExpParser.ADD ) 
            return context.mkAdd( leftIsInt?(IntExpr)visit(ctx.expr(0)):(ArithExpr)visit(ctx.expr(0)),rightIsInt?(IntExpr)visit(ctx.expr(1)):(ArithExpr)visit(ctx.expr(1)));
        else if ( ctx.op.getType() == ConditionExpParser.SUB ) 
            return context.mkSub( leftIsInt?(IntExpr)visit(ctx.expr(0)):(ArithExpr)visit(ctx.expr(0)),rightIsInt?(IntExpr)visit(ctx.expr(1)):(ArithExpr)visit(ctx.expr(1))); // must be SUB
        else
            return context.mkMul( leftIsInt?(IntExpr)visit(ctx.expr(0)):(ArithExpr)visit(ctx.expr(0)),rightIsInt?(IntExpr)visit(ctx.expr(1)):(ArithExpr)visit(ctx.expr(1))); // must be MUL
    }

    /** '(' expr ')' */
    @Override
    public ArithExpr visitParens(ConditionExpParser.ParensContext ctx) {
        return visit(ctx.expr()); // return child expr's value
    }
    
     @Override
    public ArithExpr visitPrintExpr(ConditionExpParser.PrintExprContext ctx) {
        ArithExpr value = visit(ctx.expr()); // evaluate the expr child
        System.out.println(value);         // print the result
        expression = value;
        //System.out.println(value.simplify().toString()); 
        //System.out.println(value.);
        
        //ArithExpr part0 = context.mkAdd();
       // ArithExpr another = context.mkMul();
        
        
        return value;                          // return dummy value
    }
    
//    public void reset()
//    {
//        //this.mainSolver.reset();
//        this.existingSymbol.clear();
//        this.expression = null;
//    }
    
    public ArithExpr getArithmeticExpression()
    {
        return this.expression;
    }
}
