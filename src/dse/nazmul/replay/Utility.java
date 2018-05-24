/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.nazmul.replay;

import java.io.File;

/**
 *
 * @author Md Nazmul Karim
 */
public class Utility {
    
    public static String className = "Example10";
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
        File file = new File(Utility.getCurrentDirectory()+Utility.neatbeansCompilationDir+Utility.className+".class");
        
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
}
