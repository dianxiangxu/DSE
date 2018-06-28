/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.nazmul;

/**
 *
 * @author Md Nazmul Karim
 */
public class ReferenceDescriptor {
    
    public static final int INT_TYPE    = 3000;
    public static final int DOUBLE_TYPE = 3001;
    public static final int OBJECT_TYPE = 3002;
    
    private String key = "";
    private int type;

    public ReferenceDescriptor()
    {
        
    }
    
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
}
