/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.scanner.parser;

import com.buschmais.jqassistant.core.store.api.Store;
import java.util.HashMap;
import java.util.Map;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.php.model.PHPNamespace;
import org.jqassistant.contrib.plugin.php.scanner.parser.helper.PHPUse;
import org.jqassistant.contrib.plugin.php.model.PHPClass;
import org.jqassistant.contrib.plugin.php.model.PHPProperty;

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
    protected PHPClass phpClass;
    
    public PHPClassParser (Store store, PHPNamespace namespace, Map<String, PHPUse> useContext){
        this.useContext = useContext;
        this.namespace = namespace;
        this.store = store;
    }
    
    public PHPClass parse(ParseTree tree){
        parseTree(tree, 1);
        return phpClass;
    }
    
    protected void parseTree(ParseTree tree, int level){
        
        //String pad = "CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC".substring(0, level);
        //System.err.println(pad + " [" + tree.getClass().getSimpleName() + "]: " + tree.getText()); //getCanonicalName
        
        if(phpClass == null && tree.getClass().getSimpleName().equals("IdentifierContext")) {
            String fullname = setFullQualifiedName(namespace, tree.getText());
            phpClass = store.find(PHPClass.class, fullname);
            if(phpClass == null){
                phpClass = store.create(PHPClass.class);
                phpClass.setFullQualifiedName(fullname);
                phpClass.setName(tree.getText());
                phpClass.setNamespace(namespace);
                System.err.println("ADD Class: " + fullname);
            }
        }
        else if (phpClass != null && tree.getClass().getSimpleName().equals("ClassStatementContext")){
            for (int i = 0; i < tree.getChildCount(); i++) {
                if(tree.getChild(i).getClass().getSimpleName().equals("VariableInitializerContext")){
                    phpClass.getProperties().add((new PHPPropertyParser(store)).parse(tree));
                    break;
                }
                else if (tree.getChild(i).getClass().getSimpleName().equals("MethodBodyContext")){
                    phpClass.getMethods().add((new PHPFunctionParser(store)).parse(tree));
                    break;
                }
            }
        }
        
        int childCount = tree.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ParseTree childTree = tree.getChild(i);
            parseTree(childTree, level + 1);
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
