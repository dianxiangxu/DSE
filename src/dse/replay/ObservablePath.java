/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.replay;

import com.microsoft.z3.Expr;
import com.microsoft.z3.FuncDecl;
import com.microsoft.z3.Model;
import dse.nazmul.ConditionStatement;
import java.util.Vector;

/**
 *
 * @author Md Nazmul Karim
 */
public class ObservablePath {
    
    public Vector<ConditionStatement> path =null;
    public int noOfConditions = 0;
    public String indentityString = "";
    public String descriptionString = "";
    public boolean pathTraversed = false;   //counter precess
    public boolean pathVisited = false;  //called for invoke
    public UniquePath  associatedUniquePath;
    public Model model =null;
    
    public boolean visited = false;   //counter precess
    public boolean hasSolution = false;  //called for invoke
    
    
    
    public ObservablePath(){
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
        
    public void primeMe()
    {
        ConditionStatement cs;
        for(int j=0;j<path.size();j++)
        {
            System.out.println(path.get(j));
        }
    }
    
    
}
