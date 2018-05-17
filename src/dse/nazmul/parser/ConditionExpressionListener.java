/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.nazmul.parser;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Model;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;
import dse.nazmul.ConditionStatement;
import dse.nazmul.parser.CONDEXPParser.ExprContext;
import dse.nazmul.replay.ConditionLiteralChecker;
//import dse.nazmul.parser.GYOOParser.AddContext;
//import dse.nazmul.parser.GYOOParser.AssignContext;
//import dse.nazmul.parser.GYOOParser.PrintContext;
import java.util.HashMap;
import java.util.Map;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 *
 * @author Md Nazmul Karim
 */
public class ConditionExpressionListener extends CONDEXPBaseListener {

   // private Map<String, Integer> variables;
   // public static String mathExpression = "";
    Context context = null;
    HashMap<String, String> cfg = new HashMap<String, String>();
    HashMap<String, IntExpr> existingSymbol = new HashMap<String, IntExpr>();
    Solver mainSolver = null; 
    String pre = "";
    String pos = "";
    String op = "";
    int pretype = 0;
    int postype = 0;
    int type = 0;
    int count = 0;
    ArithExpr expression = null;
    
    public ConditionExpressionListener() {
        cfg.put("model", "true");
        context = new Context(cfg);
        existingSymbol.clear();
        
        mainSolver = context.mkSolver();        
        mainSolver.reset();
        existingSymbol.clear();
        pre = "";
        op = "";
    }
    
    @Override
    public void enterExpr(ExprContext ctx) {
      
    }
    
    @Override
    public void exitExpr(ExprContext ctx) {
      
   
    }
    
   @Override public void visitTerminal(TerminalNode terminal) {
        System.err.println("terminal " + terminal.getText());   
        if(terminal.getText()!=null)
            integrate(terminal.getText());
    }
   
//    terminal (
//    terminal (
//    terminal 10
//    terminal -
//    terminal 7
//    terminal )
//    terminal +
//    terminal x
//    terminal )
   //(((10+12)+x1)+x1)
   public void print()
   {
       System.out.println(expression);
       
       System.out.println(expression.simplify());
       
       
        mainSolver.add(context.mkGt(expression, context.mkInt(Integer.parseInt("5"))));
        
        Model model = null;
        if (Status.SATISFIABLE == mainSolver.check())
        {
            model = mainSolver.getModel();
            System.out.println(model);
                      
        }
       
   }
   
   private void integrate(String lit)
   {
       if(lit.equals("(") || lit.equals(")"))
       {
           System.out.println("Nothing to do.");
       }
       else if(lit.equals("+") || lit.equals("-") || lit.equals("*"))
       {
           op = lit;
           System.out.println("OP set:"+op);
       }
       else 
       {
           int thisType = ConditionLiteralChecker.getType(lit);
           System.out.println("TYPE:"+ thisType);
           
           if(thisType == ConditionLiteralChecker.IDENTIFIER || thisType == ConditionLiteralChecker.INTEGER)
           {
               if(pre.equals(""))
               {
                   pre = lit;
                   pretype = thisType;
                   System.out.println("pre is set:"+pre);
               }
               else
               {
                   pos = lit;
                   postype = thisType;
                   System.out.println("pos is set:"+pos);
               }
           }
           else
           {
               System.out.println("Unknown type");
           }
           
       }
       
        if(!op.isEmpty() && !pre.isEmpty() && !pos.isEmpty())
        {  
            if(count>0)
                getComplexExpression("", 0,pos,postype,op);
            else
                getComplexExpression(pre, pretype,pos,postype,op);
            op="";
            pos ="";
            postype =0;
            
            count++;
        }
   }
   
   
   private void makeExpression(ConditionStatement stmt, Solver mainSolver)
    {
             
        if(stmt.getOperand().equals("<"))
        {
            mainSolver.add(context.mkLt(getExpression(stmt.getLeftHand(),stmt.getLeftHandType()),getExpression(stmt.getRightHand(),stmt.getrightHandType())));
        }
        else if(stmt.getOperand().equals("<="))
        {
            mainSolver.add(context.mkLe(getExpression(stmt.getLeftHand(),stmt.getLeftHandType()),getExpression(stmt.getRightHand(),stmt.getrightHandType())));
        }
        else if(stmt.getOperand().equals(">"))
        {
            mainSolver.add(context.mkGt(getExpression(stmt.getLeftHand(),stmt.getLeftHandType()),getExpression(stmt.getRightHand(),stmt.getrightHandType())));
        }
        else if(stmt.getOperand().equals(">="))
        {
            mainSolver.add(context.mkGe(getExpression(stmt.getLeftHand(),stmt.getLeftHandType()),getExpression(stmt.getRightHand(),stmt.getrightHandType())));
        }
        
        
        
    }
    
    private IntExpr getExpression(String literal, int type)
    {
        IntExpr expr = null;
        
        if(existingSymbol.get(literal)!= null)
        {
            expr = (IntExpr) existingSymbol.get(literal);
        }
        else
        {
            if(type == ConditionStatement.IDENTIFIER)
            {
                 expr = context.mkIntConst(literal);
            }
            else if(type == ConditionStatement.INTEGER)
            {
                 expr = context.mkInt(Integer.parseInt(literal));
            }
        }
        
        return expr;    
    }
    
    
    
    private void getComplexExpression(String pre, int preType,String pos, int posType,String op)
    {
        IntExpr preExpr = null;
        IntExpr posExpr = null;
        System.out.println("String: "+pre+"|"+preType+"|"+pos+"|"+posType+"|"+op);
        try{
        if(!pre.isEmpty() && existingSymbol.get(pre)!= null)
        {
            preExpr = (IntExpr) existingSymbol.get(pre);
        }
        else if(!pre.isEmpty())
        {
            System.out.println("new pre exp.");
            if(preType == ConditionLiteralChecker.IDENTIFIER)
            {
                 preExpr = context.mkIntConst(pre);
            }
            else if(preType == ConditionLiteralChecker.INTEGER)
            {
                 preExpr = context.mkInt(Integer.parseInt(pre));
            }
        }
        
        if(!pos.isEmpty() && existingSymbol.get(pos)!= null)
        {
            posExpr = (IntExpr) existingSymbol.get(pos);
        }
        else if(posType == ConditionLiteralChecker.IDENTIFIER)
        {
             posExpr = context.mkIntConst(pos);
        }
        else if(posType == ConditionLiteralChecker.INTEGER)
        {
             posExpr = context.mkInt(Integer.parseInt(pos));
        }
        //}
        
        if(!pre.isEmpty())
        {
            if(op.equals("+"))
                expression = context.mkAdd(preExpr, posExpr);
            else if(op.equals("-"))
                expression = context.mkSub(preExpr, posExpr);
        }
        else
        {
            if(op.equals("+"))
                expression = context.mkAdd(expression, posExpr);
            else if(op.equals("-"))
                expression = context.mkSub(expression, posExpr);
        }
        }catch(Exception e)
        {
            System.out.println(e.toString());
            e.printStackTrace();
        }
      //  return expr;    
    }

}