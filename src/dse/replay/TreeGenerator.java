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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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
    
    String textAreaOutput = "";
    
    public TreeGenerator()
    {
        invokeManager = new InvokeManager();
        conditionTracer = new HashMap<>();
        root = new TreeNode();
        nodeStack = new ListStack();
        textAreaOutput = "";
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
    
    int width = 500;
    private void showTree()
    {
        JFrame frame = new JFrame("Condition Tree");
        String str = ((ConditionStatement)root.getCondition()).printValue();
        DefaultMutableTreeNode mroot = new DefaultMutableTreeNode(str);
        traverseTree(root,mroot);
        
        JTree tree = new JTree(mroot);
       
        JScrollPane scrollPane = new JScrollPane(tree);
        expandAllNodes(tree);
        
        scrollPane.setPreferredSize(new Dimension(width,200));
        
        JPanel motherPanel =  new JPanel();
        motherPanel.setPreferredSize(new Dimension(width,500));
        motherPanel.setLayout(new BorderLayout(5, 10));
        motherPanel.add(scrollPane, BorderLayout.NORTH);
        motherPanel.add(getOutputPanel(),BorderLayout.SOUTH);
        frame.getContentPane().add(motherPanel);
        frame.setSize(width,600 );
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    
    private JPanel getOutputPanel()
    {
        JPanel outputPanel = new JPanel();
        outputPanel.setPreferredSize(new Dimension(width, 360));
        outputPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
        outputPanel.add(new JTextArea(textAreaOutput,27, 45));
        
        return outputPanel;
    }
    
    private void expandAllNodes(JTree tree) {
        int j = tree.getRowCount();
        int i = 0;
        while (i < j) {
            tree.expandRow(i);
            i += 1;
            j = tree.getRowCount();
        }
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
        
        System.out.println("ADDING To OUTPUT:"+uniquePath.descriptionString + "::"+ uniquePath.inputAsString + "\n");
        textAreaOutput += uniquePath.descriptionString + "::"+ uniquePath.inputAsString + "\n";
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
            else
            {
            
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
            }
        loopCounter++; 
        }

    }
    
    public void negateAndCall(TreeNode node)
    {
        createModel(node);
        
        
    }
    
    public void invokeFromModel(Model model,TreeNode currentNode)
    {
        invokeManager.invokeFromModel(model);
        UniquePath uniquePath = invokeManager.getUniquePath();
        System.out.println("After Invoke:"+uniquePath.getDescriptionString());
        System.out.println("IS NODE NULL:"+currentNode.getCondition());
        addToTree(currentNode,uniquePath);  //not first time
    }
    
    public void createModel(TreeNode node)
    {
        UniquePath up = new UniquePath();
        TreeNode currentNodeHolder = node;
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
        up.reversePath();
        System.out.println("UP for model:"+up.getDescriptionString());
        new ExpressionBuilder().parseAndBuildExpression(up);
        
        if(up.hasSolution)
        {    
           invokeFromModel(up.model, currentNodeHolder);
        }
        else
        {
            System.out.println("ADDING To OUTPUT:"+up.descriptionString + "::"+ "Not feasible" + "\n");
            textAreaOutput += up.descriptionString + "::"+ "Not feasible" + "\n";
        
        }
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