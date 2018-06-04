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
import com.microsoft.z3.BitVecExpr;
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
            return context.mkInt(Integer.parseInt(id));
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
        return context.mkIntConst(id);
       
    }

   

    /** expr op=('+'|'-') expr */
    @Override
    public ArithExpr visitArithOperation(ConditionExpParser.ArithOperationContext ctx) {
        boolean leftIsInt = false;
        boolean rightIsInt = false;
        if(visit(ctx.expr(0)) instanceof IntExpr)
        {
            leftIsInt = true;
            //System.out.println("left is int");
        }
        if(visit(ctx.expr(1)) instanceof IntExpr)
        {
            rightIsInt = true;
            //System.out.println("right is int");
        }
       
       
        
//         ArithExpr left = visit(ctx.expr(0)) ;  // get value of left subexpression
//         ArithExpr right = visit(ctx.expr(1)); // get value of right subexpression
//         System.out.println(left.toString());
        switch (ctx.op.getType()) {
            case ConditionExpParser.ADD:
                return context.mkAdd( leftIsInt?(IntExpr)visit(ctx.expr(0)):(ArithExpr)visit(ctx.expr(0)),rightIsInt?(IntExpr)visit(ctx.expr(1)):(ArithExpr)visit(ctx.expr(1)));
            case ConditionExpParser.SUB:
                return context.mkSub( leftIsInt?(IntExpr)visit(ctx.expr(0)):(ArithExpr)visit(ctx.expr(0)),rightIsInt?(IntExpr)visit(ctx.expr(1)):(ArithExpr)visit(ctx.expr(1))); // must be SUB
            case ConditionExpParser.MUL:
                return context.mkMul( leftIsInt?(IntExpr)visit(ctx.expr(0)):(ArithExpr)visit(ctx.expr(0)),rightIsInt?(IntExpr)visit(ctx.expr(1)):(ArithExpr)visit(ctx.expr(1))); // must be MUL
            case ConditionExpParser.DIV:
                return context.mkDiv( leftIsInt?(IntExpr)visit(ctx.expr(0)):(ArithExpr)visit(ctx.expr(0)),rightIsInt?(IntExpr)visit(ctx.expr(1)):(ArithExpr)visit(ctx.expr(1))); // DIV
            default:
                return context.mkMod((IntExpr)visit(ctx.expr(0)),(IntExpr)visit(ctx.expr(1))); // MOD
        }
    }
    
    @Override
    public ArithExpr visitUnaryArithOperation(ConditionExpParser.UnaryArithOperationContext ctx)
    {
        return context.mkUnaryMinus((ArithExpr) visit(ctx.expr()));
    }
    
    @Override    
    public ArithExpr visitBitWiseOperation(ConditionExpParser.BitWiseOperationContext ctx)
    {
        BitVecExpr bitVecExpr = null;
        
        BitVecExpr lhs = context.mkInt2BV(32, (IntExpr)visit(ctx.expr(0)));
        BitVecExpr rhs = context.mkInt2BV(32, (IntExpr)visit(ctx.expr(1)));
        
        switch (ctx.op.getType()) {
            case ConditionExpParser.AND:
                bitVecExpr = context.mkBVAND(lhs, rhs);
                break;
            case ConditionExpParser.OR:
                bitVecExpr = context.mkBVOR(lhs, rhs);
                break;
            case ConditionExpParser.XOR:
                bitVecExpr = context.mkBVXOR(lhs, rhs);
                break;
            case ConditionExpParser.RSH:
                bitVecExpr = context.mkBVASHR(lhs, rhs);
                break;
            case ConditionExpParser.LSH:
                bitVecExpr = context.mkBVSHL(lhs, rhs);
                break;
            case ConditionExpParser.RSH0F:
                bitVecExpr = context.mkBVLSHR(lhs, rhs);
                break;
            default:
                break;
        }
        
        return context.mkBV2Int(bitVecExpr, false);
    }
    
//    @Override    
//    public ArithExpr visitUnaryBitwise(ConditionExpParser.UnaryBitwiseContext ctx)
//    {
//        BitVecExpr bitVecExpr = null;
//        BitVecExpr operand = context.mkInt2BV(32, (IntExpr)visit(ctx.expr()));
//        bitVecExpr = context.mkBV(operand);
//        
//        return context.mkBV2Int(bitVecExpr, false);
//    }

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
        return value;                          
    }
  
    public ArithExpr getArithmeticExpression()
    {
        return this.expression;
    }
}
