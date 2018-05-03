/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.nazmul.replay;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 *
 * @author Md Nazmul Karim
 */
public class Invoker {

    public void invoke(String[] arr) throws MalformedURLException {
        Class[] argTypes = new Class[1];
        argTypes[0] = String[].class;
        try {
            String absPath = "C:/Users/Md Nazmul Karim/Documents/NetBeansProjects/MutationTestApp/sootOutput";
           
            File f = new File(absPath);
            URL[] cp = {f.toURI().toURL()};
            URLClassLoader urlcl = new URLClassLoader(cp);
            Class clazz = urlcl.loadClass("artifacts.Example1");
            
            
            Method mainMethod = clazz.getDeclaredMethod("main", argTypes);
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
