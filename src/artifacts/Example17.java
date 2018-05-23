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
public class Example17 {
     public int example(double x, byte y) {
        byte ret = (byte) 0;
        if (x <= 0) {
            ret = (byte) (y + ((byte) x));
        } else {
            x = ret;
        }

        if (x == 0) {
            System.out.println("assertion violated");
        }
        return (int) x;
    }
     
     public static int noOfArguments = 2;
    public static void main(String[] args)
    {
        int x = Integer.valueOf(args[0]);
        int y = Integer.valueOf(args[1]);
        //System.out.println(example(x,y));
    }
}
