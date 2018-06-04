/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.nazmul.replay;
import artifacts.Example1;
import java.util.*;

import com.microsoft.z3.*;
import dse.nazmul.ConditionStatement;
import java.io.File;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

class InvokeManager
{
    ConditionFileReader fileReader = null;
    Vector fileEntry = null;
    ExpressionBuilder expressionBuilder = null;
    Invoker invoker = null;
    private UniquePath uniquePath = null;
    
    
    public InvokeManager()
    {
        invoker = new Invoker();
        fileReader = new ConditionFileReader();
        fileEntry = new Vector();
        uniquePath = new UniquePath();
        
    }
    
    public UniquePath getUniquePath()
    {
        return this.uniquePath;
    }
    
    private void processFileReading()
    {
        fileReader.readConditionFile(Utility.outputFile, fileEntry);
        fileReader.clearFile(Utility.outputFile);
    }
    
    private void clearStructures()
    {
        fileEntry.clear();
       // conditionStatements.clear();
    }
    
    private void parseConditionLines() 
    {
        try 
        {
            int size = this.fileEntry.size();
            String innerDelim = "[~]";
            Iterator i = this.fileEntry.iterator();

            while (i.hasNext()) 
            {
                String line = (String) i.next();
                String[] tokens = line.split(innerDelim);
                
                ConditionStatement statement = null;
                System.out.println("Condition:"+tokens[0] +","+ tokens[1] + ","+ tokens[2] +","+ tokens[3]);
                statement = new ConditionStatement(Integer.parseInt(tokens[0]), tokens[1], tokens[2], tokens[3]);
                statement.checkForLoop();
                uniquePath.addACondition(statement);
            }

        } catch (Exception e) 
        {
            System.out.println("EX :" + e.toString());
            e.printStackTrace();
        }
    }

    
    public void processFileAndCreateModel()
    {
        processFileReading();
        parseConditionLines();               
        clearStructures();
    }
    public void invokeFromModel(Model model)
    {
        this.uniquePath = new UniquePath();
        Random rand = new Random(100);
        String[] arr = {};
        Vector<String> vector = new Vector<String>();
        
        for(int i = 0; i< getNoOfArguments();i++)
        {
            vector.add(Integer.toString(rand.nextInt(100)));
        }
        
        if(model != null)
        {
            FuncDecl[] funcs = model.getConstDecls();

            int pos = 0;
            for(int j=0;j<funcs.length;j++)
            {
                pos = Integer.parseInt(funcs[j].getName().toString().substring(1))-1;

                Expr fi = model.getConstInterp(funcs[j]);
                vector.set(pos,fi.toString());
            }
        }
        //vector.add(0,Integer.toString(Example1.noOfArguments));
        arr = vector.toArray(new String[vector.size()]);
        System.out.println("INPUT:"+vector);
        try {                
            invoker.invoke(arr);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        processFileAndCreateModel();
        this.uniquePath.storeInputAsString(vector.toString());
    }
    
    
    public int getNoOfArguments()
    {
        String absPath = Utility.sootOutputDir;
        int noOfArguments = -1;
        File f = new File(absPath);
        try{
            URL[] cp = {f.toURI().toURL()};
            URLClassLoader urlcl = new URLClassLoader(cp);
            String className = Utility.sutPackage+"."+Utility.className;
            System.out.println("Class Name : "+absPath+"\\"+className);
            Class clazz = urlcl.loadClass(className);  
            Object boo = clazz.newInstance();
            Field field1 = clazz.getDeclaredField("noOfArguments");
            noOfArguments = field1.getInt(boo);
            System.out.println("NO OF ARGS:"+noOfArguments);
      
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return noOfArguments;
    }
   
    
     public void testInvoke(int noOfParameters)
     {
         Random rand = new Random(100);
        String[] arr = {};
        Vector<String> vector = new Vector<String>();
        
        for(int i = 0; i< noOfParameters;i++)
        {
            vector.add(Integer.toString(rand.nextInt(100)));
        }
        
         arr = vector.toArray(new String[vector.size()]);
        System.out.println("INPUT:"+vector);
        try {                
            invoker.invoke(arr);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
     }
   
   
}