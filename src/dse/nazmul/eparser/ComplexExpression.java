/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.nazmul.eparser;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.IntExpr;

/**
 *
 * @author Md Nazmul Karim
 */
public class ComplexExpression {
    
    public static int EXP_INT = 1001;
    public static int EXP_ARITHMETIC = 1002;
   
    private int expressionType;
    IntExpr integerExpression = null;
    ArithExpr arithmeticExpression = null;

    public ComplexExpression()
    {
       
    }
    
    public void setIntExpression(IntExpr intExpr)
    {
         this.integerExpression = intExpr;
         this.expressionType = EXP_INT;
    }
    
    public void setArithExpression(ArithExpr arithExpr)
    {
         this.arithmeticExpression = arithExpr;
         this.expressionType = EXP_ARITHMETIC;
    }
    
    public IntExpr getIntegerExpression() {
        return integerExpression;
    }

    public ArithExpr getArithmeticExpression() {
        return arithmeticExpression;
    }
    
    
    public int getExpressionType() {
        return expressionType;
    }

    public void setExpressionType(int expressionType) {
        this.expressionType = expressionType;
    }
    
}
