/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.nazmul.replay;

import dse.nazmul.ConditionStatement;

/**
 *
 * @author Md Nazmul Karim
 */
public class ConditionLiteralChecker {

    public static int IDENTIFIER = 101;
    public static int INTEGER = 102;

    public ConditionLiteralChecker() {
    }

    public ConditionStatement setConditionliteraltypes(ConditionStatement condition) {
        if (isInt(condition.getLeftHand())) {
            condition.setLeftHandType(INTEGER);
        } else {
            condition.setLeftHandType(IDENTIFIER);
        }

        if (isInt(condition.getRightHand())) {
            condition.setRightHandType(INTEGER);
        } else {
            condition.setRightHandType(IDENTIFIER);
        }

        return condition;
    }
    
    
    public static int getType(String literal)
    {
        if (isInt(literal)) {
            return INTEGER;
        } else {
            return IDENTIFIER;
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
    
}
