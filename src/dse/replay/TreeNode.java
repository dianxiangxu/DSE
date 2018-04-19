package dse.replay;


import com.microsoft.z3.Expr;
import com.microsoft.z3.FuncDecl;
import com.microsoft.z3.Model;
import dse.nazmul.ConditionStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class TreeNode<ConditionStatement>{
	 
    ConditionStatement data = null;    
    public TreeNode parent = null;
    public TreeNode trueBranch = null;
    public boolean trueChecked = false;   
    public TreeNode falseBranch = null;
    public boolean falseChecked = false;
    public boolean isLeaf = false;          //when has no trueChild and no falseChild
    public boolean reachFeasible = false;   // condition satisfiable
    public boolean invoked = false;  // called the method based on this condition
    public boolean fullyExplored = false;  // true when all child is done 
    public Model model =null;
    public boolean causeOfBirth = true; 
            
    public TreeNode()
    {
    }
    public TreeNode(ConditionStatement data) {
        this.setCondition(data);
    }
    
    public void setData(ConditionStatement data)
    {
         this.setCondition(data);
    }

    public void addTrueBranch(TreeNode branch) {
        branch.setParent(this);
        branch.causeOfBirth = true;
        this.trueBranch =branch;
    }
    
    public void addFalseChild(TreeNode branch) {
        branch.setParent(this);
        branch.causeOfBirth = false;
        this.falseBranch =branch;
    }

    public TreeNode getTrueChildren() {
        return this.trueBranch;
    }
    
    public TreeNode getFalseChildren() {
        return this.falseBranch;
    }

    public ConditionStatement getCondition() {
        return data;
    }

    public void setCondition(ConditionStatement data) {
        this.data = data;
    }
    
    private void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public TreeNode getParent() {
        return parent;
    }
    
     public String getInput(Model model)
    {
        String toReturn = "";
        if(model != null)
        {
            String[] arr = {};
            Random rand = new Random(100);
            Vector<String> vector = new Vector<String>();
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
            toReturn = "INPUT:"+vector.toString();
        }
        else
        {
            toReturn = "";
        }
        return toReturn;
    }
    
}