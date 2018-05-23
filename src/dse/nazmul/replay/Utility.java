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
}
