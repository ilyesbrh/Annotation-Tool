/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay.Model;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 *
 * @author ilies
 */
public class CustomResourceBundle extends ResourceBundle{

    HashMap<String, Object> hashMap = new HashMap<>();
    
    @Override
    protected Object handleGetObject(String key) {
        
        return hashMap.get(key);
        
    }

    @Override
    public Enumeration<String> getKeys() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void addObject(String key, Object obj){
        
        hashMap.put(key, obj);
        
    }
    
    
}
