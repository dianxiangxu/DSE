/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.nazmul.replay;

import com.microsoft.z3.Model;
import dse.nazmul.ConditionStatement;
import dse.nazmul.MainDSEInstra;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
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
    int noOfPaths = 0;
    
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
        Utility.clear();
        noOfPaths = 0;
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
                String tag = "";
                if(trueChild.causeOfBirth)
                    tag = "T";
                else
                    tag = "F";
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(((ConditionStatement)trueChild.getCondition()).printValue()+tag);
                traverseTree(trueChild,childNode);
                node.add(childNode);
            }
            if(tree.getFalseChildren() != null)
            {
                TreeNode falseChild = tree.getFalseChildren();
                String tag = "";
                if(falseChild.causeOfBirth)
                    tag = "T";
                else
                    tag = "F";
                
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(((ConditionStatement)falseChild.getCondition()).printValue()+tag);
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
        }
        else
        {
            System.out.println("Start Tree comparing");
            boolean eligibleToAdd = false;
            int conditionEquivalencyValue = 2;
            System.out.println((ConditionStatement)nextNode.getCondition());
            while(it.hasNext())
            {
               
               ConditionStatement comparableCondition = (ConditionStatement) it.next();
               System.out.println(comparableCondition);
               System.out.println("Equivalency Value:1:"+conditionEquivalencyValue);
               if(conditionEquivalencyValue>=2)     // 2 means completely different condition
               {   
                   System.out.println("Equivalency Value:2:"+conditionEquivalencyValue);
                   conditionEquivalencyValue = ((ConditionStatement)nextNode.getCondition()).compareEquivalency(comparableCondition); 
                   System.out.println("Equivalency Value:3:"+conditionEquivalencyValue);
                   if(conditionEquivalencyValue ==0 || conditionEquivalencyValue ==1)
                    {
                        System.out.println("Equivalency Value:4:"+conditionEquivalencyValue);
                        eligibleToAdd = true;
                        continue;
                    }
               }
               System.out.println("Equivalency Value:5:"+conditionEquivalencyValue);
               if(eligibleToAdd)
               {
                   if(conditionEquivalencyValue == 0)    //means same condition true value
                   {
                       System.out.println("Equivalency Value:6:"+conditionEquivalencyValue);
                       nextNode = addToTrueBranch(nextNode,comparableCondition);
                   }
                   else                               // 1 means same condition negative value
                   {
                       System.out.println("Equivalency Value:7:"+conditionEquivalencyValue);
                       nextNode = addToFalseBranch(nextNode,comparableCondition);
                       conditionEquivalencyValue = 0;
                   }
               }
            }
        }
        noOfPaths++;
        System.out.println("ADDING To OUTPUT:"+uniquePath.descriptionString + "::"+ uniquePath.inputAsString + "\n");
        textAreaOutput += noOfPaths+" :"+uniquePath.descriptionString + "::"+ uniquePath.inputAsString + "\n";
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
            nodeStack.push(node);
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
            noOfPaths++;
            System.out.println("ADDING To OUTPUT:"+up.descriptionString + "::"+ "Not feasible" + "\n");
            textAreaOutput += noOfPaths+" :"+up.descriptionString + "::"+ "Not feasible" + "\n";
        
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