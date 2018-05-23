/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.nazmul.replay;

import com.microsoft.z3.*;
import dse.nazmul.ConditionStatement;
import dse.nazmul.eparser.ArithmeticExpressionGenerator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author Md Nazmul Karim
 */
public class ExpressionBuilder {
    
    Context context = null;
    HashMap<String, String> cfg = new HashMap<String, String>();
    HashMap<String, IntExpr> existingSymbol = new HashMap<String, IntExpr>();
    
    ArithmeticExpressionGenerator z3ExpressionGenerator = null;
                   
    public ExpressionBuilder() {
        cfg.put("model", "true");
        context = new Context(cfg);
        existingSymbol.clear();
        
        z3ExpressionGenerator = new ArithmeticExpressionGenerator(context, existingSymbol);
    }
    
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
    
    public void parseAndBuildExpression(UniquePath uniquePath)
    {
        Solver mainSolver = context.mkSolver();        
        mainSolver.reset();
        existingSymbol.clear();
        z3ExpressionGenerator.setContext(context);
        z3ExpressionGenerator.setExistingSymbol(existingSymbol);
        
        
       // Iterator i = statements.iterator();
        Iterator it = uniquePath.path.iterator();
        while (it.hasNext()) {
            ConditionStatement stmt = (ConditionStatement)it.next();
//            if(context == null)
//            {
//                System.out.println("Main Solver is null");
//            }
            makeExpression(stmt,mainSolver);
            System.out.println(stmt.toString());
        }
        
        System.out.println("findSolution1");

        uniquePath.visited = true;
        Model model = null;
        if (Status.SATISFIABLE == mainSolver.check())
        {
            model = mainSolver.getModel();
            System.out.println(model);
            uniquePath.hasSolution = true;
           
        } 
        else
        {
            uniquePath.hasSolution = false;
            System.out.println("Not satisfiable.");
        }
        uniquePath.setModel(model);
    }
    
    
    public void parseAndBuildExpression(ObservablePath objPath)
    {
        Solver mainSolver = context.mkSolver();        
        mainSolver.reset();
        existingSymbol.clear();
        z3ExpressionGenerator.setContext(context);
        z3ExpressionGenerator.setExistingSymbol(existingSymbol);
        
        
       // Iterator i = statements.iterator();
        Iterator it = objPath.path.iterator();
        while (it.hasNext()) {
            ConditionStatement stmt = (ConditionStatement)it.next();
//            if(context == null)
//            {
//                System.out.println("Main Solver is null2");
//            }
            makeExpression(stmt,mainSolver);
            System.out.println(stmt.toString());
        }
        
        System.out.println("findSolution1");

        objPath.visited = true;
        Model model = null;
        if (Status.SATISFIABLE == mainSolver.check())
        {
            model = mainSolver.getModel();
            System.out.println(model);
            objPath.hasSolution = true;
           
        } 
        else
        {
            objPath.hasSolution = false;
            System.out.println("Not satisfiable.");
        }
        objPath.setModel(model);
    }
    
     private void makeExpression(ConditionStatement stmt, Solver mainSolver)
    {
        if(context == null || mainSolver == null || stmt ==null)
        {
            System.out.println("Main Solver is null2");
        }
        
       try {
        if(stmt.getOperand().equals("<"))
        {
            mainSolver.add(context.mkLt(getComplexExpression(stmt.getLeftHand()),getComplexExpression(stmt.getRightHand())));
        }
        else if(stmt.getOperand().equals("<="))
        {
            mainSolver.add(context.mkLe(getComplexExpression(stmt.getLeftHand()),getComplexExpression(stmt.getRightHand())));
        }
        else if(stmt.getOperand().equals(">"))
        {
            mainSolver.add(context.mkGt(getComplexExpression(stmt.getLeftHand()),getComplexExpression(stmt.getRightHand())));
        }
        else if(stmt.getOperand().equals(">="))
        {
            mainSolver.add(context.mkGe(getComplexExpression(stmt.getLeftHand()),getComplexExpression(stmt.getRightHand())));
        }
        else if(stmt.getOperand().equals("=="))
        {
            mainSolver.add(context.mkEq(getComplexExpression(stmt.getLeftHand()),getComplexExpression(stmt.getRightHand())));
        }
        else if(stmt.getOperand().equals("!="))
        {
            mainSolver.add(context.mkNot(context.mkEq(getComplexExpression(stmt.getLeftHand()),getComplexExpression(stmt.getRightHand()))));
        }
        
       }
       catch(Exception e)
       {
           System.out.println(e.getMessage());
           e.printStackTrace();
       }
        
    }
    
//    private void makeExpression(ConditionStatement stmt, Solver mainSolver)
//    {
//             
//        if(stmt.getOperand().equals("<"))
//        {
//            mainSolver.add(context.mkLt(getExpression(stmt.getLeftHand(),stmt.getLeftHandType()),getExpression(stmt.getRightHand(),stmt.getrightHandType())));
//        }
//        else if(stmt.getOperand().equals("<="))
//        {
//            mainSolver.add(context.mkLe(getExpression(stmt.getLeftHand(),stmt.getLeftHandType()),getExpression(stmt.getRightHand(),stmt.getrightHandType())));
//        }
//        else if(stmt.getOperand().equals(">"))
//        {
//            mainSolver.add(context.mkGt(getExpression(stmt.getLeftHand(),stmt.getLeftHandType()),getExpression(stmt.getRightHand(),stmt.getrightHandType())));
//        }
//        else if(stmt.getOperand().equals(">="))
//        {
//            mainSolver.add(context.mkGe(getExpression(stmt.getLeftHand(),stmt.getLeftHandType()),getExpression(stmt.getRightHand(),stmt.getrightHandType())));
//        }
//        
//        
//        
//    }
    
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
    
    private ArithExpr getComplexExpression(String literal)
    {
        return z3ExpressionGenerator.parseAndGetArithExpr(literal);    
    }
    
}
