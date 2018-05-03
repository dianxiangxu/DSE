/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.nazmul.replay;

/**
 *
 * @author Md Nazmul Karim
 */
public class MainCaller {
    
    
    public static void main(String[] args)
    {
//        Z3ConditionSolution p = new Z3ConditionSolution();
//        p.doAlltask();
        TreeGenerator treeGenerator = new TreeGenerator();
        treeGenerator.invokeFirstTime();
       // new InvokeManager().testInvoke();
    }
    
}
