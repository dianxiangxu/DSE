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
public class Example35 {
    
    static int findMaximum(int arr[])
    {
       int max = arr[0];
       int i;
       for (i = 0; i < arr.length; i++)
       {
           if (arr[i] > max)
              max = arr[i];
       }
       return max;
    }
    
     public static int noOfArguments = 3;
    public static void main(String[] args)
    {
        int[] a = {10,20,5,1};
        int max = findMaximum(a);
        System.out.println(max);
    }
    
}
