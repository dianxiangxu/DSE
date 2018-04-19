/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.replay;
import java.util.*;

import com.microsoft.z3.*;
import dse.nazmul.ConditionStatement;

class InvokeManager
{
    private static String fileName = "C:/applications/output.txt";
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
        if(model == null)
        {
            vector.add(Integer.toString(6));
            vector.add(Integer.toString(21));
        }
        else
        {
            FuncDecl[] funcs = model.getConstDecls();
            if(funcs.length ==1)   //just one symbolic value
            {
                for(int j=0;j<funcs.length;j++)
                {
                    if(Integer.parseInt(funcs[j].getName().toString().substring(1)) == 1)    //first symbolic value
                    {
                        Expr fi = model.getConstInterp(funcs[j]);
                        vector.add(fi.toString());
                        vector.add(Integer.toString(rand.nextInt(100)));
                    }
                    else   //2nd sombolic value
                    {
                        Expr fi = model.getConstInterp(funcs[j]);
                        vector.add(Integer.toString(rand.nextInt(100)));
                        vector.add(fi.toString());
                    }
                   
                }
            }
            else
            {
                for(int k=0;k<funcs.length;k++)
                {
                    
                    Expr fi = model.getConstInterp(funcs[k]);
                    if(Integer.parseInt(funcs[k].getName().toString().substring(1)) == 1 && k==1)   //p1 or p2 anyone can come first
                    {
                        vector.add(0,fi.toString());
                    }
                    else
                    {
                        vector.add(fi.toString());
                    }
                }
            }
        }
        arr = vector.toArray(new String[vector.size()]);
        System.out.println("INPUT:"+vector);
        invoker.invoke(arr);                
        processFileAndCreateModel();
    }
    
   
    
   
   
}