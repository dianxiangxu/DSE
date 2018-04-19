/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.replay;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author Md Nazmul Karim
 */
public class Invoker {

    public void invoke(String[] arr) {
        Class[] argTypes = new Class[1];
        argTypes[0] = String[].class;
        try {
            Method mainMethod = Class.forName("artifacts.Example1").getDeclaredMethod("main", argTypes);
            Object[] argListForInvokedMain = new Object[1];
            argListForInvokedMain[0] = arr;

            System.out.println("Invoke starts");
            mainMethod.invoke(null, argListForInvokedMain);
            System.out.println("Invoke ends.");

        } catch (ClassNotFoundException ex) {
            System.err.println("Class not found in classpath.");
        } catch (NoSuchMethodException ex) {
            System.err.println("Class does not define public static void main(String[])");
        } catch (InvocationTargetException ex) {
            System.err.println("Exception while executing :" + ex.getTargetException());
        } catch (IllegalAccessException ex) {
            System.err.println("main(String[]) in class is not public");
        }
    }

}
