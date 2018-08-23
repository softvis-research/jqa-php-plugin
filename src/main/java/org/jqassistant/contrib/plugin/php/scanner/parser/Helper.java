/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.scanner.parser;

import com.buschmais.jqassistant.core.store.api.Store;
import org.jqassistant.contrib.plugin.php.model.PHPClass;
import org.jqassistant.contrib.plugin.php.model.PHPInterface;
import org.jqassistant.contrib.plugin.php.model.PHPTrait;
import org.jqassistant.contrib.plugin.php.model.PHPNamespace;

/**
 *
 * @author falk
 */
public class Helper {

    protected Store store;
    
    public Helper(Store store){
        this.store = store;
    }
    
    public PHPNamespace getNamespace(String name, PHPNamespace parentNamespace){
        String fullname = namespace_setFullQualifiedName(name, parentNamespace);
        PHPNamespace n = store.find(PHPNamespace.class, fullname);
        if(n == null){
            n = store.create(PHPNamespace.class);
            n.setFullQualifiedName(fullname);
            n.setName(name);
            n.setParent(parentNamespace);
            System.err.println("ADD Namespace: " + fullname);
        }

        return n;
    }
    
    protected String namespace_setFullQualifiedName(String Name, PHPNamespace parent){
        String namespace = Name.toLowerCase();
        
        while(parent != null){
            namespace = parent.getName().toLowerCase() + "|" + namespace;
            parent = parent.getParent();
        }
        
        return "NAMESPACE|" + namespace;
    }
    
    protected PHPInterface getInterface(String name, PHPNamespace namespace){
         String fullname = class_setFullQualifiedName(name, namespace);
           
         PHPInterface phpClass = store.find(PHPInterface.class, fullname);
            if(phpClass == null){
                phpClass = store.create(PHPInterface.class);
                phpClass.setFullQualifiedName(fullname);
                phpClass.setNamespace(namespace);
                System.err.println("ADD Interface: " + fullname);
            }
            
            phpClass.setName(name);
            
            return phpClass;
    }
    
     protected PHPClass getClass(String name, PHPNamespace namespace){
        String fullname = class_setFullQualifiedName(name, namespace);
        PHPClass  phpClass = store.find(PHPClass.class, fullname);
        if(phpClass == null){
            phpClass = store.create(PHPClass.class);
            phpClass.setFullQualifiedName(fullname);
            phpClass.setNamespace(namespace);
            System.err.println("ADD Class: " + fullname);
        }
        
        phpClass.setName(name);
        
        return phpClass;
    }
     
     protected PHPTrait getTrait(String name, PHPNamespace namespace){
        String fullname = class_setFullQualifiedName(name, namespace);
        PHPTrait  phpClass = store.find(PHPTrait.class, fullname);
        if(phpClass == null){
            phpClass = store.create(PHPTrait.class);
            phpClass.setFullQualifiedName(fullname);
            phpClass.setNamespace(namespace);
            System.err.println("ADD Trait: " + fullname);
        }
        
        phpClass.setName(name);
        
        return phpClass;
    } 
    
    protected String class_setFullQualifiedName(String Name, PHPNamespace parent){
        String namespace = Name.toLowerCase();
        
        while(parent != null){
            namespace = parent.getName().toLowerCase() + "|" + namespace;
            parent = parent.getParent();
        }
        
        return "CLASS|" + namespace;
    }
    
}
