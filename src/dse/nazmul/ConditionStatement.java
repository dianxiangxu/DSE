/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.nazmul;

import dse.nazmul.test.PatternDefinition;
import dse.nazmul.test.PatternFinder;
import dse.nazmul.test.StringUtil;
import java.util.HashMap;

/**
 *
 * @author Md Nazmul Karim
 */
public class ConditionStatement {
    
    //public static String [] operandLibrary = {"==","<",">","<=",">=","!="};
   // public static String [] operandNegationLibrary = {"==","<",">","<=",">=","!="};
    
    public static int IDENTIFIER = 101;
    public static int INTEGER = 102;
    
    public static HashMap<String, String> operandNegationMap = new HashMap<String, String>();
    public static HashMap<String, String> operandEquivalenceMap = new HashMap<String, String>();
    static
    {
        operandNegationMap.put("==", "!=");
        operandNegationMap.put("!=", "==");
        operandNegationMap.put("<", ">=");
        operandNegationMap.put(">=", "<");
        operandNegationMap.put("<=", ">");
        operandNegationMap.put(">", "<=");
        
        
        operandEquivalenceMap.put("==", "==");
        operandEquivalenceMap.put("!=", "!=");
        operandEquivalenceMap.put("<", ">");
        operandEquivalenceMap.put(">=", "<=");
        operandEquivalenceMap.put("<=", ">=");
        operandEquivalenceMap.put(">", "<");
        
    }
    String leftHand = "";
    String rightHand = "";
    String operand = "";
    int lineNo = 0;
    boolean booleanValue = true;
    
    public boolean hasLoop = false;
    
    int leftHandType = 0;    
    int rightHandType = 0;
    
    public ConditionStatement(int lineNo, String left, String op, String right){
        this.leftHand = left;
        this.operand = op;
        this.rightHand = right;
        this.lineNo = lineNo;
       // System.out.println("before:"+this.toString());
       // this.setConditionliteraltypes();    //COMPLEX
        //checkForLoop();
       // System.out.println("after:"+this.toString());
    }
    
    public void checkForLoop()
    {
        PatternDefinition pdL ;
        
        if(!isInt(this.leftHand))
        {
            pdL= new StringUtil().hasLoopPattern(this.leftHand);
            if( pdL.isHasLoop())
            {    
                this.leftHand = pdL.getBase();
                this.operand = operandNegationMap.get(this.operand);
            }
        }
        PatternDefinition pdR ;
        if(!isInt(this.rightHand))
        {
            pdR= new StringUtil().hasLoopPattern(this.rightHand);
            if(pdR.isHasLoop())
            {    
                this.rightHand = pdR.getBase();
                this.operand = operandNegationMap.get(this.operand);
            }
        }
    }
   
    public void setLeftHandType(int leftHandType) {
        this.leftHandType = leftHandType;
    }

    public void setRightHandType(int rightHandType) {
        this.rightHandType = rightHandType;
    }
    
    public int getLeftHandType() {
        return leftHandType;
    }

     public int getrightHandType() {
        return rightHandType;
    }
    
    public String getLeftHand() {
        return leftHand;
    }

    public void setLeftHand(String leftHand) {
        this.leftHand = leftHand;
    }

    public String getRightHand() {
        return rightHand;
    }

    public void setRightHand(String rightHand) {
        this.rightHand = rightHand;
    }

    public String getOperand() {
        return operand;
    }

    public void setOperand(String operand) {
        this.operand = operand;
    }
    
    public int getLineNo() {
        return lineNo;
    }

    public void setOperand(int ln) {
        this.lineNo = ln;
    }
    
    public boolean negateCondition()               // negate this existing object
    {
        System.out.println("Negation Called.");
        boolean negationSuccessfull = false;
        
        if(operandNegationMap.get(this.operand)!= null)
        {
            this.operand = operandNegationMap.get(this.operand);
            negationSuccessfull = true;
        }
        else
        {
        }
        return negationSuccessfull;
    }
    
    public ConditionStatement returnNegateCondition()   //means negate and send new negated condition
    {
        System.out.println("Negate.");
        boolean negationSuccessfull = false;
        ConditionStatement  cs = new ConditionStatement(this.lineNo, this.leftHand, this.operand,this.rightHand);
        if(operandNegationMap.get(this.operand)!= null)
        {
            cs.operand = operandNegationMap.get(this.operand);
            negationSuccessfull = true;
        }
        else
        {
        }
        return cs;
    }
    
    public void convertToQuivalentBooleanStatement()
    {
        //String temp = getLeftHand();
       //setLeftHand(getRightHand());
        //setRightHand(temp);
        this.operand = operandNegationMap.get(this.operand);
        //this.setConditionliteraltypes();
        this.booleanValue = false;
    }
    
     public void setConditionliteraltypes() {
        if (isInt(this.getLeftHand())) {
            this.setLeftHandType(INTEGER);
        } else {
            this.setLeftHandType(IDENTIFIER);
        }

        if (isInt(this.getRightHand())) {
            this.setRightHandType(INTEGER);
        } else {
            this.setRightHandType(IDENTIFIER);
        }
    }
    
    private static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
    
    public int compareEquivalency(ConditionStatement comparableStatement)
    {
        int returnValue = 2;  //completely diffenent condition
        
        if((this.lineNo+this.leftHand+this.operand+this.rightHand).equalsIgnoreCase((comparableStatement.lineNo+comparableStatement.leftHand+comparableStatement.operand+comparableStatement.rightHand)))
        {   
            returnValue = 0;     // same condition and same truth value
        }
        else if((this.lineNo+this.leftHand+operandNegationMap.get(this.operand)+this.rightHand).equalsIgnoreCase((comparableStatement.lineNo+comparableStatement.leftHand+comparableStatement.operand+comparableStatement.rightHand)))
        {
            returnValue = 1;     // same condition with negatetive truth value
        }
        else
        {
            returnValue = 2;     // means different condition
        }
        return returnValue;                
    }
    
    public String toString()
    {
        return this.lineNo+this.leftHand+this.operand+this.rightHand+this.booleanValue;
    }
    public String printValue()
    {
        return this.leftHand+this.operand+this.rightHand+" ("+this.lineNo+") ";
    }
    
    public String toAlternativeString()
    {
        return this.lineNo+this.leftHand+operandNegationMap.get(this.operand)+this.rightHand+!this.booleanValue;
    }
    
    public String getDescriptionString()
    {
        return "("+this.lineNo+") "+this.leftHand+this.operand+this.rightHand;
    }
    public String getAlternativeDescriptionString()
    {
        return "(line:"+this.lineNo+")"+this.leftHand+operandNegationMap.get(this.operand)+this.rightHand+!this.booleanValue;
    }
    
}
