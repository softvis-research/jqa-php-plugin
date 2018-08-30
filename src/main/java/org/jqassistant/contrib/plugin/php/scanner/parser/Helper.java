/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.scanner.parser;

import com.buschmais.jqassistant.core.store.api.Store;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.Token;
import org.jqassistant.contrib.plugin.php.model.PHPClassDescriptor;
import org.jqassistant.contrib.plugin.php.model.PHPFunctionDescriptor;
import org.jqassistant.contrib.plugin.php.model.PHPInterfaceDescriptor;
import org.jqassistant.contrib.plugin.php.model.PHPNamespaceDescriptor;
import org.jqassistant.contrib.plugin.php.model.PHPPropertyDescriptor;
import org.jqassistant.contrib.plugin.php.model.PHPTraitDescriptor;
import org.jqassistant.contrib.plugin.php.model.PHPTypeDescriptor;
import org.jqassistant.contrib.plugin.php.model.VisibilityModifier;
import org.jqassistant.contrib.plugin.php.model.PHPFunctionParameterDescriptor;

/**
 * helper class
 * @author falk
 */
public class Helper {

    protected Store store;
    protected TokenStream tokenStream;
    
    public Helper(Store store, TokenStream tokenStream){
        this.store = store;
        this.tokenStream = tokenStream;
    }
    
    public Integer getLineNumberByTokenNumber(Integer tokenNumber){
        Token firstToken = tokenStream.get(tokenNumber);
        return firstToken.getLine();    
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
            if(namespace != null){
                namespace.getTypes().add(phpClass);
            }
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
            if(namespace != null){
                namespace.getTypes().add(phpClass);
            }
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
            if(namespace != null){
                namespace.getTypes().add(phpClass);
            }
            
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
    private String type_getFullQualifiedName(String Name, PHPNamespaceDescriptor namespace){
        String fqn = Name.toLowerCase();
        
        PHPNamespaceDescriptor parent = namespace;
        while(parent != null){
            fqn = parent.getName().toLowerCase() + "|" + fqn;
            parent = parent.getParent();
        }
        
        return "TYPE|" + fqn;
    }
    
    /**
     * create method object or get exists
     * @param name
     * @param phpClass
     * @param namespace
     * @return PHPMethodDescriptor
     */
    public PHPFunctionDescriptor getFunction(String name, PHPTypeDescriptor phpClass, PHPNamespaceDescriptor namespace){
        String fullname = function_getFullQualifiedName(name, phpClass, namespace);
        PHPFunctionDescriptor phpMethod = store.find(PHPFunctionDescriptor.class, fullname);
        if(phpMethod == null){
            phpMethod = store.create(PHPFunctionDescriptor.class);
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
    private String function_getFullQualifiedName(String Name, PHPTypeDescriptor phpClass, PHPNamespaceDescriptor namespace){
        String fqn = Name.toLowerCase();
        

        fqn = phpClass.getName().toLowerCase() + "|" + fqn;
        PHPNamespaceDescriptor parent = namespace;
        while(parent != null){
            fqn = parent.getName().toLowerCase() + "|" + fqn;
            parent = parent.getParent();
        }
        return "METHOD|" + fqn;

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
            if(namespace != null){
                namespace.getFunctions().add(phpFunction);
            }
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
    
     public PHPFunctionParameterDescriptor getFunctionParameter(Integer idx, String name){
        PHPFunctionParameterDescriptor phpFunctionParameter = store.create(PHPFunctionParameterDescriptor.class);
        phpFunctionParameter.setIndex(idx);
        phpFunctionParameter.setName(name);
        System.err.println("ADD FunctionParameter: " + idx + " " + name);
        
        return phpFunctionParameter;
    }
}
