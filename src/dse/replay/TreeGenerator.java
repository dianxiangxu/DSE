/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.replay;

import com.microsoft.z3.Model;
import dse.nazmul.ConditionStatement;
import dse.replay.structure.ListStack;
import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

class TreeGenerator
{
    InvokeManager invokeManager = null;
    TreeNode tree = null;
    HashMap<String,TreeNode> conditionTracer = null;
    TreeNode root =null;
    TreeNode currentTreeNode =null;
    
    ListStack nodeStack = null;
    
    public TreeGenerator()
    {
        invokeManager = new InvokeManager();
        conditionTracer = new HashMap<>();
        //tree = new TreeNode();
        root = new TreeNode();
        nodeStack = new ListStack();
        //currentTreeNode = root;
    }
    
   
    public void invokeFirstTime()
    {
        invokeManager.invokeFromModel(null);
        UniquePath uniquePath = invokeManager.getUniquePath();
        
        addToTree(root,uniquePath);
        preOrderRecursivePrint(root);
        System.out.println("####################");
        
        stepAndExplore();
        
        preOrderRecursivePrint(root);
        
        showTree();
    }
    
    private void showTree()
    {
        JFrame frame = new JFrame("Condition Tree");
        String str = ((ConditionStatement)root.getCondition()).printValue();
        DefaultMutableTreeNode mroot = new DefaultMutableTreeNode(str);
        traverseTree(root,mroot);
        
        JTree tree = new JTree(mroot);
        JScrollPane scrollPane = new JScrollPane(tree);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.setSize(300, 150);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void traverseTree(TreeNode tree,DefaultMutableTreeNode node) {
            
           String str = ((ConditionStatement)tree.getCondition()).printValue();
	   if(node ==null)
            {
                node = new DefaultMutableTreeNode(str);
             
            }  
            if(tree.getTrueChildren() != null)
            {
                TreeNode trueChild = tree.getTrueChildren();
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(((ConditionStatement)trueChild.getCondition()).printValue());
                traverseTree(trueChild,childNode);
                node.add(childNode);
            }
            if(tree.getFalseChildren() != null)
            {
                TreeNode falseChild = tree.getFalseChildren();
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(((ConditionStatement)falseChild.getCondition()).printValue());
                traverseTree(falseChild,childNode);
                node.add(childNode);
            }
	}
    
    private TreeNode addToTree(TreeNode nextNode ,UniquePath uniquePath)
    {
        Iterator it = uniquePath.path.iterator();
        System.out.println(uniquePath.path.size());
        //TreeNode nextNode = root;
        if(nextNode.data == null)//if(conditionTracer.size()==0)   //very first time
        {
            System.out.println("Start Tree generation");
            while(it.hasNext())
            {
               ConditionStatement comparableCondition = (ConditionStatement) it.next();
               System.out.println(comparableCondition);
               nextNode = addToTrueBranch(nextNode,comparableCondition);
               
               nextNode.isLeaf = false;
               nextNode.invoked = true;
               nextNode.reachFeasible = true;
               nextNode.trueChecked = true;
            }
            
            nextNode.isLeaf = true;
            nextNode.invoked = true;
            nextNode.reachFeasible = true;
            nextNode.model = uniquePath.model;
            //nextNode.getParent().trueChecked = true;
        }
        else
        {
            System.out.println("Start Tree comparing");
            boolean eligibleToAdd = false;
            int value = 2;
            System.out.println((ConditionStatement)nextNode.getCondition());
            while(it.hasNext())
            {
               
               ConditionStatement comparableCondition = (ConditionStatement) it.next();
               System.out.println(comparableCondition);
               System.out.println("VAL:1:"+value);
               if(value>=2)
               {   
                   System.out.println("VAL:2:"+value);
                   value = ((ConditionStatement)nextNode.getCondition()).compareEquivalency(comparableCondition); 
                   System.out.println("VAL:3:"+value);
                   if(value ==0 || value ==1)
                    {
                        System.out.println("VAL:4:"+value);
                        eligibleToAdd = true;
                        continue;
                    }
               }
               System.out.println("VAL:5:"+value);
               if(eligibleToAdd)
               {
                   if(value == 0)
                   {
                       System.out.println("VAL:6:"+value);
                       nextNode = addToTrueBranch(nextNode,comparableCondition);
                   }
                   else
                   {
                       System.out.println("VAL:7:"+value);
                       nextNode = addToFalseBranch(nextNode,comparableCondition);
                       value = 0;
                   }
               }
            }
        }
        return nextNode;

    }
    
    private TreeNode addToTrueBranch(TreeNode node, ConditionStatement cs)
    {
        TreeNode temp = null;
        if(node.data == null)
        {
            System.out.println("Node: "+cs);
            node.setCondition(cs);
            conditionTracer.put(cs.toString(), node);
            System.out.println("Added to tracer :"+cs.toString());
            conditionTracer.put(cs.toAlternativeString(), node);
            System.out.println("Added to tracer :"+cs.toAlternativeString());
            node.trueChecked = true;
            //node.addTrueBranch(new TreeNode());
            nodeStack.push(node);
            //return node.getTrueChildren();
            temp = node;
        }
        else
        {
            
           TreeNode child = new TreeNode();
           child.setCondition(cs);
           child.trueChecked = true;
           node.addTrueBranch(child);
           
           conditionTracer.put(cs.toString(), child);
           System.out.println("Added to tracer :"+cs.toString());
           conditionTracer.put(cs.toAlternativeString(), child);
           System.out.println("Added to tracer :"+cs.toAlternativeString());
           nodeStack.push(child);
           temp = child;
        }
        
        return temp;
    }
    
    private TreeNode addToFalseBranch(TreeNode node, ConditionStatement cs)
    {
        TreeNode temp = null;
        if(node.data == null)
        {
            System.out.println("This should not happen");
        }
        else
        {
            
           TreeNode child = new TreeNode();
           child.setCondition(cs);
           child.trueChecked = true;
           node.falseChecked = true;
           node.addFalseChild(child);
           
           conditionTracer.put(cs.toString(), child);
           System.out.println("Added to tracer :"+cs.toString());
           conditionTracer.put(cs.toAlternativeString(), child);
           System.out.println("Added to tracer :"+cs.toAlternativeString());
           nodeStack.push(child);
           temp = child;
        }
        
        return temp;
    }
    
    
    public void stepAndExplore()
    {
        int loopCounter = 0;
        
        while(!nodeStack.isEmpty()  && loopCounter <40)
        {
            TreeNode node = (TreeNode) nodeStack.top();
            System.out.println("WORKING with node: "+node.getCondition().toString());
            if(node.trueChecked && node.falseChecked)
            {
                nodeStack.pop();
            }
            
            if(!node.falseChecked)
            {
                // check false
                System.out.println("FALSE not checked");
                negateAndCall(node);                
                node.falseChecked = true;
            }
            
            if(!node.trueChecked)
            {
                // check true
                System.out.println("TRUE not checked");
                negateAndCall(node);
                node.trueChecked = true;
            }
        loopCounter++; 
        }

    }
    
    public void negateAndCall(TreeNode node)
    {
        Model model = createModel(node);
        invokeFromModel(model, node);
        
    }
    
    public void invokeFromModel(Model model,TreeNode currentNode)
    {
        invokeManager.invokeFromModel(model);
        UniquePath uniquePath = invokeManager.getUniquePath();
        System.out.println("After Invoke:"+uniquePath.getDescriptionString());
        addToTree(currentNode,uniquePath);  //not first time
    }
    
    public Model createModel(TreeNode node)
    {
        UniquePath up = new UniquePath();
        ConditionStatement negationCondition = ((ConditionStatement)(node.getCondition())).returnNegateCondition();
        up.addACondition(negationCondition);
        boolean causeOfBirth = node.causeOfBirth;
        node = node.getParent();
        while(node != null)
        {
            ConditionStatement tempCond = (ConditionStatement)(node.getCondition());
            if(!causeOfBirth)
                up.addACondition(tempCond.returnNegateCondition());
            else
                up.addACondition(tempCond);
            causeOfBirth = node.causeOfBirth;
            node = node.getParent();
        }
        System.out.println("UP for model:"+up.getDescriptionString());
        new ExpressionBuilder().parseAndBuildExpression(up);
        
        return up.model;
    }
    
    public void preOrderRecursivePrint(TreeNode root) {
        System.out.println("Printing the tree...");
        if (root != null) {
            if(root.getCondition()!=null)
                System.out.println(((ConditionStatement)root.getCondition()).getDescriptionString() + " ");
            if(root.getFalseChildren()!=null)
                preOrderRecursivePrint(root.getFalseChildren());
            if(root.getTrueChildren()!=null)
                preOrderRecursivePrint(root.getTrueChildren());
        }
    }
}