/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.scanner.parser;

import com.buschmais.jqassistant.core.store.api.Store;
import com.sun.codemodel.internal.JExpr;
import java.util.HashMap;
import java.util.Map;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.php.model.PHPNamespace;
import org.jqassistant.contrib.plugin.php.scanner.parser.helper.PHPUse;
import org.jqassistant.contrib.plugin.php.model.PHPClass;
import org.jqassistant.contrib.plugin.php.model.PHPMethod;
import org.jqassistant.contrib.plugin.php.model.PHPProperty;
import org.jqassistant.contrib.plugin.php.model.PHPType;
import org.jqassistant.contrib.plugin.php.model.PHPInterface;

/**
 *
 * @author falk
 * - properties
    - methods
    - superclass erkennen 
 */
public class PHPClassParser {
    
    protected PHPNamespace namespace;
    protected Store store;
    protected Map<String, PHPUse> useContext = new HashMap<>();
    protected PHPType phpClass;
    protected boolean isAbstract = false;
    protected boolean isInterface = false;
    
    public PHPClassParser (Store store, PHPNamespace namespace, Map<String, PHPUse> useContext){
        this.useContext = useContext;
        this.namespace = namespace;
        this.store = store;
    }
    
    public PHPType parse(ParseTree tree){
        parseTree(tree, 1, 0);
        return phpClass;
    }
    
    protected void parseTree(ParseTree tree, int level, int idx){
        
        //String pad = "CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC".substring(0, level);
        //System.err.println(pad + " [" + tree.getClass().getSimpleName() + "]: " + tree.getText()); //getCanonicalName
        
        if(phpClass == null && tree.getClass().getSimpleName().equals("IdentifierContext")) {
            String fullname = setFullQualifiedName(namespace, tree.getText());
            if (isInterface){
                phpClass = store.find(PHPInterface.class, fullname);
                if(phpClass == null){
                    phpClass = store.create(PHPInterface.class);
                    phpClass.setFullQualifiedName(fullname);
                    phpClass.setNamespace(namespace);
                    System.err.println("ADD Interface: " + fullname);
                }
            } else {
                phpClass = store.find(PHPClass.class, fullname);
                if(phpClass == null){
                    phpClass = store.create(PHPClass.class);
                    phpClass.setFullQualifiedName(fullname);
                    phpClass.setNamespace(namespace);
                    System.err.println("ADD Class: " + fullname);
                }
                
                phpClass.as(PHPClass.class).setAbstract(isAbstract);
            }
            
            phpClass.setName(tree.getText());
        }
        else if (phpClass == null && tree.getClass().getSimpleName().equals("ModifierContext")){
            if(tree.getText().toLowerCase().equals("abstract")){
                isAbstract = true;
            }
        }
        else if (phpClass == null && tree.getClass().getSimpleName().equals("TerminalNodeImpl")){
            if(tree.getText().toLowerCase().equals("interface")){
                isInterface = true;
            }
        }
        else if (phpClass != null && tree.getClass().getSimpleName().equals("ClassStatementContext")){
            for (int i = 0; i < tree.getChildCount(); i++) {
                if(tree.getChild(i).getClass().getSimpleName().equals("VariableInitializerContext")){
                    phpClass.getProperties().add((new PHPPropertyParser(store)).parse(tree));
                    return;
                }
                else if (tree.getChild(i).getClass().getSimpleName().equals("MethodBodyContext")){
                    phpClass.getMethods().add((new PHPMethodParser(store, phpClass)).parse(tree));
                    return;
                }
            }
        }
        else if(phpClass != null && tree.getClass().getSimpleName().equals("InterfaceListContext")){
            (new PHPClassMapper(store, phpClass, true, useContext)).parse(tree);
            return;
        }
        else if(phpClass != null && level == 2 && tree.getClass().getSimpleName().equals("TerminalNodeImpl")){
            if(tree.getText().toLowerCase().equals("extends")){
                (new PHPClassMapper(store, phpClass, false, useContext)).parse(tree.getParent().getChild(idx + 1));
                return;
            }            
        }
       
        
        //level = 2 && "TerminalNodeImpl" == extends --> 
        
        int childCount = tree.getChildCount();
        for (int i = 0; i < childCount; i++) {
            parseTree(tree.getChild(i), level + 1, i);
        }
    }
    
     protected String setFullQualifiedName(PHPNamespace parent, String Name){
        String namespace = Name.toLowerCase();
        
        while(parent != null){
            namespace = parent.getName().toLowerCase() + "|" + namespace;
            parent = parent.getParent();
        }
        
        return "CLASS|" + namespace;
    }
}
