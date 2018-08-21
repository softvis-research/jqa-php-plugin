/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.scanner.parser;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import com.buschmais.jqassistant.core.store.api.Store;
import java.util.HashMap;
import java.util.Map;
import org.jqassistant.contrib.plugin.php.model.PHPClass;

/**
 *
 * @author falk
 */
public class PHPParserHelper {
    
    protected static Store s;
    private static Map<String, PHPClass> classes = new HashMap<>();
    
    public static void setStore(Store store){
        s = store;
    }
    
    public PHPClass getClass(String FullName){
        if (classes.containsKey(FullName)){
            return classes.get(FullName);
        }
        
        PHPClass v = s.create(PHPClass.class);
        v.setFullQualifiedName(FullName);
        
        return v;
    }
    
}
