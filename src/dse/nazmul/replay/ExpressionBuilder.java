/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.nazmul.replay;

import com.microsoft.z3.*;
import dse.nazmul.ConditionStatement;
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
                   
    public ExpressionBuilder() {
        cfg.put("model", "true");
        context = new Context(cfg);
        existingSymbol.clear();
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
        
        
       // Iterator i = statements.iterator();
        Iterator it = uniquePath.path.iterator();
        while (it.hasNext()) {
            ConditionStatement stmt = (ConditionStatement)it.next();
            //System.out.println(pair.getKey());
            //up2 = (UniquePath)pair.getValue();
           // ConditionStatement stmt = (ConditionStatement)pair.getValue();
            //System.out.println("S:" + stmt);
            makeExpression(stmt,mainSolver);
        }
        
        System.out.println("findSolution1");

        uniquePath.visited = true;
        Model model = null;
        if (Status.SATISFIABLE == mainSolver.check())
        {
            model = mainSolver.getModel();
            System.out.println(model);
            uniquePath.hasSolution = true;
         
//          FuncDecl[] funcs = model.getConstDecls();
//          for(int j=0;j<funcs.length;j++)
//          {
//              System.out.println(funcs[j].getName()+" "+funcs[j].toString());
//              Expr fi = model.getConstInterp(funcs[j]);
//              System.out.println("fi="+ fi);
//          }
           
        } else
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
        
        
       // Iterator i = statements.iterator();
        Iterator it = objPath.path.iterator();
        while (it.hasNext()) {
            ConditionStatement stmt = (ConditionStatement)it.next();
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
    
    private IntExpr getComplexExpression(String literal, int type)
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
    
}
