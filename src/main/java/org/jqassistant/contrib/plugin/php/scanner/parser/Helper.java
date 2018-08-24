/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.scanner.parser;

import com.buschmais.jqassistant.core.store.api.Store;
import org.jqassistant.contrib.plugin.php.model.PHPClassDescriptor;
import org.jqassistant.contrib.plugin.php.model.PHPFunctionDescriptor;
import org.jqassistant.contrib.plugin.php.model.PHPInterfaceDescriptor;
import org.jqassistant.contrib.plugin.php.model.PHPMethodDescriptor;
import org.jqassistant.contrib.plugin.php.model.PHPNamespaceDescriptor;
import org.jqassistant.contrib.plugin.php.model.PHPPropertyDescriptor;
import org.jqassistant.contrib.plugin.php.model.PHPTraitDescriptor;
import org.jqassistant.contrib.plugin.php.model.PHPTypeDescriptor;
import org.jqassistant.contrib.plugin.php.model.VisibilityModifier;

/**
 * helper class
 * @author falk
 */
public class Helper {

    protected Store store;
    
    public Helper(Store store){
        this.store = store;
    }
    
    /**
     * create Namespace or get existing
     * @param name
     * @param parentNamespace
     * @return  PHPNamespaceDescriptor
     */
    public PHPNamespaceDescriptor getNamespace(String name, PHPNamespaceDescriptor parentNamespace){
        String fullname = namespace_getFullQualifiedName(name, parentNamespace);
        PHPNamespaceDescriptor n = store.find(PHPNamespaceDescriptor.class, fullname);
        if(n == null){
            n = store.create(PHPNamespaceDescriptor.class);
            n.setFullQualifiedName(fullname);
            n.setName(name);
            n.setParent(parentNamespace);
            System.err.println("ADD Namespace: " + fullname);
        }

        return n;
    }
    
    /**
     * get full qualified name of namespace by parents
     * @param Name
     * @param parent
     * @return String
     */
    private String namespace_getFullQualifiedName(String Name, PHPNamespaceDescriptor parent){
        String namespace = Name.toLowerCase();
        
        while(parent != null){
            namespace = parent.getName().toLowerCase() + "|" + namespace;
            parent = parent.getParent();
        }
        
        return "NAMESPACE|" + namespace;
    }
    
    /**
     * create interface object or get exists
     * @param name
     * @param namespace
     * @return PHPInterfaceDescriptor
     */
    public PHPInterfaceDescriptor getInterface(String name, PHPNamespaceDescriptor namespace){
         String fullname = type_getFullQualifiedName(name, namespace);
           
         PHPInterfaceDescriptor phpClass = store.find(PHPInterfaceDescriptor.class, fullname);
            if(phpClass == null){
                phpClass = store.create(PHPInterfaceDescriptor.class);
                phpClass.setFullQualifiedName(fullname);
                phpClass.setNamespace(namespace);
                System.err.println("ADD Interface: " + fullname);
            }
            
            phpClass.setName(name);
            
            return phpClass;
    }
    
    /**
     * create class object or get exists
     * @param name
     * @param namespace
     * @return PHPClassDescriptor
     */
    public PHPClassDescriptor getClass(String name, PHPNamespaceDescriptor namespace){
        String fullname = type_getFullQualifiedName(name, namespace);
        PHPClassDescriptor  phpClass = store.find(PHPClassDescriptor.class, fullname);
        if(phpClass == null){
            phpClass = store.create(PHPClassDescriptor.class);
            phpClass.setFullQualifiedName(fullname);
            phpClass.setNamespace(namespace);
            System.err.println("ADD Class: " + fullname);
        }
        
        phpClass.setName(name);
        
        return phpClass;
    }
     
    /**
     * create Trait object or get exists
     * @param name
     * @param namespace
     * @return PHPTraitDescriptor
     */
    public PHPTraitDescriptor getTrait(String name, PHPNamespaceDescriptor namespace){
        String fullname = type_getFullQualifiedName(name, namespace);
        PHPTraitDescriptor  phpClass = store.find(PHPTraitDescriptor.class, fullname);
        if(phpClass == null){
            phpClass = store.create(PHPTraitDescriptor.class);
            phpClass.setFullQualifiedName(fullname);
            phpClass.setNamespace(namespace);
            System.err.println("ADD Trait: " + fullname);
        }
        
        phpClass.setName(name);
        
        return phpClass;
    } 
    
    /**
     * get full qualified name of php type
     * @param Name
     * @param parent
     * @return String
     */
    private String type_getFullQualifiedName(String Name, PHPNamespaceDescriptor parent){
        String namespace = Name.toLowerCase();
        
        while(parent != null){
            namespace = parent.getName().toLowerCase() + "|" + namespace;
            parent = parent.getParent();
        }
        
        return "TYPE|" + namespace;
    }
    
    /**
     * create method object or get exists
     * @param name
     * @param phpClass
     * @return PHPMethodDescriptor
     */
    public PHPMethodDescriptor getMethod(String name, PHPTypeDescriptor phpClass){
        String fullname = method_getFullQualifiedName(name, phpClass);
        PHPMethodDescriptor phpMethod = store.find(PHPMethodDescriptor.class, fullname);
        if(phpMethod == null){
            phpMethod = store.create(PHPMethodDescriptor.class);
            phpMethod.setFullQualifiedName(fullname);
            phpMethod.setName(name);
            System.err.println("ADD Method: " + fullname);
        }
        
        return phpMethod;
    }
    
    /**
     *  get full qualified name of php method
     * @param Name
     * @param phpClass
     * @return String
     */
    private String method_getFullQualifiedName(String Name, PHPTypeDescriptor phpClass){
        String namespace = Name.toLowerCase();
        

        namespace = phpClass.getName().toLowerCase() + "|" + namespace;
        PHPNamespaceDescriptor parent = phpClass.getNamespace();
        while(parent != null){
            namespace = parent.getName().toLowerCase() + "|" + namespace;
            parent = parent.getParent();
        }
        return "METHOD|" + namespace;

    }
    
    /**
     * create function object or get exists
     * @param name
     * @param namespace
     * @return PHPFunctionDescriptor
     */
    public PHPFunctionDescriptor getFunction(String name, PHPNamespaceDescriptor namespace){
        String fullname = function_getFullQualifiedName(name, namespace);
        PHPFunctionDescriptor phpFunction = store.find(PHPFunctionDescriptor.class, fullname);
        if(phpFunction == null){
            phpFunction = store.create(PHPFunctionDescriptor.class);
            phpFunction.setFullQualifiedName(fullname);
            phpFunction.setName(name);
            System.err.println("ADD Function: " + fullname);
        }
        
        return phpFunction;
    }
    
    /**
     * get full qualified name of php function
     * @param Name
     * @param namespace
     * @return String
     */
    private String function_getFullQualifiedName(String Name, PHPNamespaceDescriptor namespace){
        String fullname = Name.toLowerCase();
        
        if (namespace != null){
            PHPNamespaceDescriptor parent = namespace;
            while(parent != null){
                fullname = parent.getName().toLowerCase() + "|" + fullname;
                parent = parent.getParent();
            }
        }
        
        return "FUNCTION|" + fullname;
    }
    
    public PHPPropertyDescriptor getProperty(){
        PHPPropertyDescriptor phpProperty = store.create(PHPPropertyDescriptor.class);
        phpProperty.setVisibility(VisibilityModifier.DEFAULT);
        phpProperty.setStatic(Boolean.FALSE);
        
        return phpProperty;
    }
}
