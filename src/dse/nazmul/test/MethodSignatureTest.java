/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.nazmul.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MethodSignatureTest{
  public static void main(String args[]) throws Exception {
    Class c = Class.forName("dse.nazmul.test.MyClass");
    System.out.println("\nMethods:");
    Method methods[] = c.getDeclaredMethods();
    for (Method meth : methods)
      System.out.println(" " + meth);
  }
}
class MyClass {
  private int count;

  MyClass(int c) {
    count = c;
  }

  MyClass() {
    count = 0;
  }

  void setCount(int c) {
    count = c;
  }

  int getCount() {
    return count;
  }

  void showcount() {
    System.out.println("count is " + count);
  }
}

   
