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
public class Example15 {
     
static int example(int x1, int y1) {

        if (x1 > 10  ) {
            y1 = y1 + 1;
            if( y1>5  )
            {
                y1 = x1+5;
                if( y1>x1)
                {
                    x1 = x1+1;
                }
            }
        } else {
            x1 = x1 + 3;
        }
        int z = x1 + y1;
        return z;
    }


     
     public static int noOfArguments = 2;
    public static void main(String[] args)
    {
        int x = Integer.valueOf(args[0]);
        int y = Integer.valueOf(args[1]);
        System.out.println(example(x,y));
    }
}
