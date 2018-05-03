/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.nazmul.replay;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Vector;

/**
 *
 * @author Md Nazmul Karim
 */
class ConditionFileReader {
    
      public String fileName;
    public File file;

    public ConditionFileReader() {
        this.fileName = "";
    }

    public void setFile(String file) {
        this.fileName = file;
    }
    
      public void readConditionFile(String afile, Vector entries) {
        try {
            FileInputStream fstream = new FileInputStream(afile);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String str = "";
            while ((str = br.readLine()) != null  )
            {
                System.out.println(str);
                if(!str.trim().isEmpty())
                    entries.add(str);
            }
            in.close();
        } catch (Exception e) {
            System.out.println("Exception at file read:" + e.toString());
        }

    }
      
      
      public void clearFile(String fileName) {
        FileWriter fw;
        try {
            fw = new FileWriter(fileName);

            PrintWriter pw = new PrintWriter(fw);
            pw.write("");
            pw.flush();
            pw.close();
        } catch (IOException ex) {
        }

    }
}