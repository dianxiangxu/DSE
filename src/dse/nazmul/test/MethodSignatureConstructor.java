/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.nazmul.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class MethodSignatureConstructor {
  public static void main(String args[]) throws Exception {
    Class c = Class.forName("dse.nazmul.test.MyClass2");

  //  Constructor constructors[] = c.getDeclaredConstructors();
   Method methods[] = c.getDeclaredMethods();
    Object obj = null;
    for (Method cons : methods) {
      Class[] params = cons.getParameterTypes();
      //Class[] Mehotd.getParameterTypes()
      
      
      
      if (params.length == 4 ) {
          if(params[0].isArray())
              System.out.println("is an array");
//          System.out.println(params[1]);
//          System.out.println(params[2].getTypeName());
//          System.out.println(params[3].getTypeName());
        //obj = cons.newInstance(10);
        break;
      }
    }
  }
}

class MyClass2 {
  private int count;

  public void test(int[] c, String[] b, int d, double a) {
    System.out.println("MyClass(int):" + c);
    //count = c;
  }

  MyClass2() {
    System.out.println("MyClass()");
    count = 0;
  }

  void setCount(int c) {
    System.out.println("setCount(int): " + c);
    count = c;
  }

  int getCount() {
    System.out.println("getCount():" + count);
    return count;
  }

  void showcount() {
    System.out.println("count is " + count);
  }
}
