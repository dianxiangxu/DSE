/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.replay;
import artifacts.Example1;
import java.util.*;

import com.microsoft.z3.*;
import dse.nazmul.ConditionStatement;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

class InvokeManager
{
    private static String fileName = "output.txt";
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
        fileReader.readConditionFile(fileName, fileEntry);
        fileReader.clearFile(fileName);
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
                statement = new ConditionStatement(Integer.parseInt(tokens[0]), tokens[1], tokens[2], tokens[3]);
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
        
        for(int i = 0; i< 3;i++)
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
    
   
    
     public void testInvoke()
     {
         Random rand = new Random(100);
        String[] arr = {};
        Vector<String> vector = new Vector<String>();
        
        for(int i = 0; i< 3;i++)
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