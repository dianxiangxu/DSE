/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.nazmul.replay;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

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
        Utility.clear();        
        try {
            int noOfArguments = invokeManager.getNoOfArguments();
            System.out.println("NO OF ARGS:" + noOfArguments);
            invokeManager.testInvoke(noOfArguments);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        new ConditionFileReader().clearFile(Utility.outputFile);
    }
    
}
