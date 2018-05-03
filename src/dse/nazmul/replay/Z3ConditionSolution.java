/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.nazmul.replay;
import java.util.*;

import com.microsoft.z3.*;
import dse.nazmul.ConditionStatement;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

class Z3ConditionSolution
{
    private static String fileName = "C:/applications/output.txt";
    ConditionFileReader fileReader = null;
    Vector fileEntry = null;
    //Vector conditionStatements = null;
    ExpressionBuilder expressionBuilder = null;
    Invoker invoker = null;
    Vector<ObservablePath> allObservablePaths = null;
    Vector<UniquePath> allUniquePaths = null;
    HashMap<String,String> pathTracer = null;
    int observablePathVisitCounter = 0;
    
    public Z3ConditionSolution()
    {
        invoker = new Invoker();
        fileReader = new ConditionFileReader();
        fileEntry = new Vector();
        allObservablePaths = new Vector();
        allUniquePaths = new Vector();
        expressionBuilder =  new ExpressionBuilder();
        pathTracer = new HashMap<>();
        observablePathVisitCounter = 0;
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
    
    private void parseConditionLines(ObservablePath objPath)
    {
         try {
            int size = this.fileEntry.size();
            //System.out.println("SIZE:"+size);

            String innerDelim = "[~]";

            Iterator i = this.fileEntry.iterator();
            UniquePath uniquePath = new UniquePath();
            while (i.hasNext()) {
                String line = (String) i.next();
                //System.out.println("S:" + line);
                  
                String[] tokens = line.split(innerDelim);
              
                ConditionStatement statement = null;
                statement =  new ConditionStatement(Integer.parseInt(tokens[0]), tokens[1], tokens[2],tokens[3]);
                //conditionStatements.add(statement);
                uniquePath.addACondition(statement);
                //System.out.println(statement);
            }
            
            expressionBuilder.parseAndBuildExpression(uniquePath);
            uniquePath.printModel();
            if(objPath != null)
            {
                objPath.associatedUniquePath = uniquePath;
                objPath.pathVisited = true;
                allObservablePaths.set(observablePathVisitCounter, objPath);
            }
            else
            {
                checkAndAdd(uniquePath);
            }
           // doAllNegate(uniquePath);
            
          
            

        } catch (Exception e) {
            System.out.println("EX :" + e.toString());
            e.printStackTrace();
        }
    }
    
    public void descModel(Model model)
    {
        System.out.println("######Show Model#######");
        FuncDecl[] funcs = model.getConstDecls();
          for(int j=0;j<funcs.length;j++)
          {
              System.out.println(funcs[j].getName().toString()+" "+funcs[j].toString());
              Expr fu = model.getConstInterp(funcs[j]);
              System.out.println("param="+ fu.toString());
          }
        System.out.println("######End Show Model#######");  
    }
    
    public void doAllNegate(UniquePath uniquePath)
    {
        Iterator it = uniquePath.path.iterator();  //HashMap
            
       // UniquePath up;
        Vector<ConditionStatement> pathConditionVector = new Vector<ConditionStatement>();
        while(it.hasNext()){
            //Map.Entry pair = (Map.Entry)it.next();
            //System.out.println(pair.getKey());
            pathConditionVector.add((ConditionStatement)it.next());
        }
        
        for(int i=0;i<pathConditionVector.size();i++)
        {
            UniquePath up = new UniquePath();
            
            for(int j=0;j<pathConditionVector.size();j++)
            {
                ConditionStatement cond;
                if(i==j)
                {
                    cond = pathConditionVector.elementAt(j);
                    cond = cond.returnNegateCondition();
                }
                else
                {
                    cond = pathConditionVector.elementAt(j);
                }
                up.addACondition(cond);
            }
            checkAndAdd(up);
        }
    }
    
    public void checkAndAdd(UniquePath up)
    {
         String tracer = "";
         if(up.path.size()>0)
         {    
             tracer = up.path.get(0).toString();
             System.out.println("Tracer : "+tracer);
         }
         else
         {    
             System.out.println("Path size less length 0.");
         }
         if(pathTracer.get(tracer) == null)   ///also have define else condition
         {    
             pathTracer.put(tracer, "1");
             ObservablePath obPath = new ObservablePath();
             obPath.addACondition( up.path.get(0));
             obPath.associatedUniquePath = up;
             obPath.pathVisited = true;
             allObservablePaths.add(obPath);
             //allUniquePaths.add(up);
                         
             System.out.println("Added:"+tracer+"|"+up.getIdentityString());
             
             //put the negation also
             System.out.println("Putting the negation");
             
             //
             ObservablePath obPathNegate = new ObservablePath();
             obPathNegate.addACondition( up.path.get(0).returnNegateCondition());
             obPathNegate.associatedUniquePath = new UniquePath();
             obPathNegate.pathVisited = false;
             allObservablePaths.add(obPathNegate);
             //allUniquePaths.add(obPathNegate.associatedUniquePath);
             
             pathTracer.put(obPathNegate.getIdentityString(), "1");
                         
             System.out.println("Added The negate:tracer:"+obPathNegate.getIdentityString());
             
             
         }
             
    }
    
     public void checkAndAdd(UniquePath up, ObservablePath obPath)
     {
         String tracer = "";
         tracer = obPath.getIdentityString();
         if(pathTracer.get(tracer) == null)
         {
             pathTracer.put(tracer, "1");
             obPath.associatedUniquePath = up;
             allObservablePaths.add(obPath);
             //allUniquePaths.add(up);
             System.out.println("Added2:"+tracer);
         }
         else
         {
             System.out.println("Already existing..."+tracer);
         }
     }
    
  
    
    public void processFileAndCreateModel(ObservablePath objPath)
    {
        processFileReading();
        parseConditionLines(objPath);               
        clearStructures();
    }
    public void invokeFromModel(Model model,ObservablePath op)
    {
        if(model  != null && op !=null)
        {
            System.out.println("INVOKING NEW OBJPATH");
            System.out.println(op.getDescriptionString());
            descModel(model);
            
        }
        Random rand = new Random(100);
        String[] arr = {};
        Vector<String> vector = new Vector<String>();
        if(model == null)
        {
            vector.add(Integer.toString(rand.nextInt(100)));
            vector.add(Integer.toString(rand.nextInt(100)));
            
        }
        else
        {
            FuncDecl[] funcs = model.getConstDecls();
            if(funcs.length ==1)
            {
                for(int j=0;j<funcs.length;j++)
                {
                    if(Integer.parseInt(funcs[j].getName().toString().substring(1)) == 1)
                    {
                        Expr fi = model.getConstInterp(funcs[j]);
                        vector.add(fi.toString());
                        vector.add(Integer.toString(rand.nextInt(100)));
                    }
                    else   //2
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
                    if(Integer.parseInt(funcs[k].getName().toString().substring(1)) == 1 && k==1)
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
        try {                
            invoker.invoke(arr);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        processFileAndCreateModel(op);
    }
    
    @SuppressWarnings("serial")
    class TestFailedException extends Exception
    {
        public TestFailedException()
        {
            super("Check FAILED");
        }
    };



    static boolean hasChange = false;
    static boolean hasNonVisitedPath = true;
    
    public void doAlltask()
    {
       // Z3ConditionSolution p = new Z3ConditionSolution();

        try
        {
            com.microsoft.z3.Global.ToggleWarningMessages(true);
            Log.open("test.log");

            {
                HashMap<String, String> cfg = new HashMap<String, String>();
                cfg.put("model", "true");
                Context ctx = new Context(cfg);    
               

                invokeFromModel(null,null);
                
                while(observablePathVisitCounter< allObservablePaths.size())
                {
                    System.out.println("observable Path Visit Counter : "+observablePathVisitCounter);
                    ObservablePath objPath = allObservablePaths.get(observablePathVisitCounter);
                    UniquePath uniquePath = objPath.associatedUniquePath;
                    
                    //checkAndAdd(uniquePath, objPath);
                    System.out.println(objPath.getDescriptionString()+ "::"+objPath.associatedUniquePath.getDescriptionString()+"invoke status:"+objPath.pathVisited);
                    if(objPath.pathVisited)  //invoked
                    {
                        System.out.println("Visited this path.");
                        int j;
                        if(uniquePath.path.size() > objPath.path.size())
                        {
                            System.out.println("Unique Path size greater than obj path");
                            j = objPath.path.size()+1;
                        }
                        else
                        {
                            System.out.println("Unique Path size smaller or equal to obj path");
                            j = 0;   // has no extra conditions
                        }
                        System.out.println("j="+j);
                        
                        if(j!=0)
                        {
                            ObservablePath newObservablePath = new ObservablePath();
                            ObservablePath newObservablePathNegate = new ObservablePath();
                            UniquePath newUniquePath = new UniquePath();
                            UniquePath newUniquePathNegate = new UniquePath();
                            int i=0;
                            for(i=0 ; i<j-1 ; i++)
                            {
                                System.out.println("i="+i);
                                
                                newObservablePath.addACondition(uniquePath.path.elementAt(i));
                                newObservablePathNegate.addACondition(uniquePath.path.elementAt(i));
                              
                            }
                            newObservablePath.addACondition(uniquePath.path.elementAt(i));
                            newObservablePathNegate.addACondition(uniquePath.path.elementAt(i).returnNegateCondition());
                               //checkAndAdd(newUniquePath, newObservablePath); 
                            
                            newObservablePath.associatedUniquePath = newUniquePath;
                            newObservablePathNegate.associatedUniquePath = newUniquePathNegate;
                            System.out.println("New positive path:"+newObservablePath.descriptionString);
                            checkAndAdd(newUniquePath,newObservablePath);
                            System.out.println("New negative path:"+newObservablePathNegate.descriptionString);
                            checkAndAdd(newUniquePathNegate,newObservablePathNegate);
                            
                        }
                        else   //invoked but size is equal
                        {
                            
                        }
                        observablePathVisitCounter++;
                    }
                    else  //invoke based on observable path
                    {
                        System.out.println("Path not visited.");
                        new ExpressionBuilder().parseAndBuildExpression(objPath);
                        invokeFromModel(objPath.model,objPath);
                    }
                }
                //
                System.out.println("Size:"+allObservablePaths.size());
                for(int i=0;i<allObservablePaths.size();i++)
                {
                    System.out.println("*********************************************");
                    //System.out.println(allObservablePaths.get(i).getIdentityString()+ "||"+allObservablePaths.get(i).associatedUniquePath.getIdentityString()+"||:"+allObservablePaths.get(i).associatedUniquePath.hasSolution);
                    //allObservablePaths.get(i).associatedUniquePath.printModel();
                    
                    System.out.println(allObservablePaths.get(i).getDescriptionString()+ "||"+allObservablePaths.get(i).associatedUniquePath.getDescriptionString()+"||:"+allObservablePaths.get(i).associatedUniquePath.hasSolution);
                    allObservablePaths.get(i).associatedUniquePath.printModel();
                }
                
            }


            Log.close();
            if (Log.isOpen())
                System.out.println("Log is still open!");
        } catch (Z3Exception ex)
        {
            System.out.println("Z3 Managed Exception: " + ex.getMessage());
            System.out.println("Stack trace: ");
            ex.printStackTrace(System.out);
        } 
        catch (Exception ex)
        {
            System.out.println("Unknown Exception: " + ex.getMessage());
            System.out.println("Stack trace: ");
            ex.printStackTrace(System.out);
        }
    }
}