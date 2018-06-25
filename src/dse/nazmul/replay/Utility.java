/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.nazmul.replay;

import java.io.File;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Md Nazmul Karim
 */
public class Utility {
    
    public static String className = "Example34";  //2,12,6,6_A, 27,27_C,30_G,30_A
    
    
    public static String neatbeansCompilationDir = "\\build\\classes\\artifacts\\"; 
    public static String sutPackage = "artifacts"; 
    public static String sootOutputDir = getCurrentDirectory()+"\\"+
                                                            "sootOutput"; 
    
    public static String tempFile = "temp.txt";
    public static String outputFile = "output.txt";
    
    public static String getCurrentDirectory() 
    {
        String workingDir = System.getProperty("user.dir");
        System.out.println("Current working directory : " + workingDir);
        return workingDir;
    }
    
    public static void clear()
    {
        File file = new File(Utility.getCurrentDirectory()+Utility.neatbeansCompilationDir+className+".class");
        
        System.out.println(file.toString());
        if(file.delete())
        {
            System.out.println("File deleted successfully");
        }
        else
        {
            System.out.println("Failed to delete the file");
        }
    }
    
    public static int isParameterArray(String className)
    {
        try 
        {
            Class c = Class.forName(className);            
            Method methods[] = c.getDeclaredMethods();
            Object obj = null;
            for (Method cons : methods) 
            {
                Class[] params = cons.getParameterTypes();           
                if (params.length == 1 ) 
                {
                    if(params[0].isArray())
                    System.out.println("is an array");
                    return 1;
                }

            } 
        }
        catch (ClassNotFoundException ex) 
        {
            ex.printStackTrace();
        }
        return 0;
    }
}
