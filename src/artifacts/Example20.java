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
public class Example20 {
    /*
	 * This example shows that SA will determine that the 
	 * 3rd branch false outcome is infeasible, i.e., assertion violates.
	 * Yet, the path itself is infeasible. 
     */
    static int example(int x, int y) {
        x = x * 0;
        if (x > y) {
            if (x == y) {
                if (x == 0) {
                    System.out.println("assert");
                }
            }
        }
        return x + y;
    }
    
    public static int noOfArguments = 2;
    public static void main(String[] args)
    {
        int x = Integer.valueOf(args[0]);
        int y = Integer.valueOf(args[1]);
        System.out.println(example(x,y));
    }
}
