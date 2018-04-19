/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.replay;
import com.microsoft.z3.*;
import dse.nazmul.ConditionStatement;
import dse.nazmul.SootIntCollectorInstra;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author Md Nazmul Karim
 */
public class UniquePath {
    
    public Model model =null;
    public Vector<ConditionStatement> path =null;
    public int noOfConditions = 0;
    public String indentityString = "";
    public String descriptionString = "";
    public boolean hasSolution = false;
    public boolean validPath = false;
    public boolean visited = false;
    
    
    
    public UniquePath(){
        path = new Vector<ConditionStatement>();  
        noOfConditions = 0;
    }
    
    public void addACondition( ConditionStatement condition) {
        path.add(condition);
        noOfConditions = path.size();
        indentityString += condition.toString();
        descriptionString += condition.getDescriptionString();
    }
    
    public void setModel(Model m)
    {
        this.model = m;
    }
    
    public String getIdentityString()
    {
        return this.indentityString;
    }
    
    public String getDescriptionString()
    {
        return this.descriptionString;
    }
    
    public boolean isEqual( UniquePath anotherPath)
    {
       
        if(path.size() != anotherPath.path.size())
        {
            return false;
        }
        else
        {
            for(int i=0;i<path.size();i++)
            {
                if(!path.get(i).toString().equalsIgnoreCase(anotherPath.path.get(i).toString()))
                    return false;
            }
            
        }
        
        return true; 
        
    }
    
    public void printModel()
    {
        if(this.hasSolution == false)
            return;
        FuncDecl[] funcs = model.getConstDecls();
          for(int j=0;j<funcs.length;j++)
          {
              System.out.println(funcs[j].getName().toString()+" "+funcs[j].toString());
              Expr fi = model.getConstInterp(funcs[j]);
              System.out.println("fi="+ fi.toString());
          }
    }
    
    public void primeMe()
    {
        ConditionStatement cs;
        for(int j=0;j<path.size();j++)
        {
            System.out.println(path.get(j));
        }
    }
    
    
    
}
