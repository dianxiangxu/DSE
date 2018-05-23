/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package artifacts;

/**
 *
 * @author Md Nazmul Karim
 */
public class Example23 {
    /*
	 * In general if SA analyzes a particular part of CFG
	 * and finds that assertion violates, it will not be
	 * able to tell if violating path is feasible or not.
	 * Need to do SE to dismiss false positives.
	 * However, if SA tells that a particular part of CFG
	 * assertion does not violates then feasibility should 
	 * not be an issue. 
	 * Non proven fix right now is to find the 
	 * state change and make sure that different values are opposites, i.e., 0 and !0
     */
    static int example(int x1, int x2) {
        int x = x1 + x2;
        boolean cond_1;
        if (x == 0) {
            cond_1 = false;
        } else {
            cond_1 = true;
        }
        //cond_1 = x1==x2 && x != 5 && cond_1;
        boolean cond_2 = false;
        if (x1 == x2) {
            if (x != 5) {
                if (cond_1) {
                    cond_2 = true;
                }
            }
        }

        //if(x1==x2 && x != 5 && cond_1){
        if (cond_2) {
            System.out.println("assert");
            System.exit(2);
        }
        return x;
    }
    
    public static int noOfArguments = 2;
    public static void main(String[] args)
    {
        int x = Integer.valueOf(args[0]);
        int y = Integer.valueOf(args[1]);
        System.out.println(example(x,y));
    }
}
