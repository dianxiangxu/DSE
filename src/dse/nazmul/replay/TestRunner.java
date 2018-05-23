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
public class TestRunner {
    
     InvokeManager invokeManager = null;
     
    public TestRunner()
    {
        invokeManager = new InvokeManager();
    }
    
    public void testAPass()
    {
        invokeManager.testInvoke(2);
    }
    
}
