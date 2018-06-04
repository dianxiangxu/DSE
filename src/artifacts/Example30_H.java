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
public class Example30_H {
    static int example(int x1, int y1, int z1) {
         
        int x = x1;
        int y = y1;
        int z = z1;
        
        int k = 0;
      
        if( k < 50)
        {
            while( k < 30)
            {
                k = k+10;   
                x = x +9;
            }
            k = k+20;
        }    
        if(x > k)
            z = x + y +z ;
        return z;
    }
    public static int noOfArguments = 3;
    public static void main(String[] args)
    {
        int x = Integer.valueOf(args[0]);
        int y = Integer.valueOf(args[1]);
        int z = Integer.valueOf(args[2]);
        System.out.println(example(x,y,z));
    }
}
