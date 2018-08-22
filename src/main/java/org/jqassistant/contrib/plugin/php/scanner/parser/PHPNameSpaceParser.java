/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.scanner.parser;

import com.buschmais.jqassistant.core.store.api.Store;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.php.model.PHPClass;
import org.jqassistant.contrib.plugin.php.model.PHPNamespace;

/**
 *
 * @author falk
 * - super namespaces erkennen
 */
public class PHPNameSpaceParser {
    
    protected PHPNamespace parentNamespace;
    protected Store store;
    protected Boolean start = false;
    
    public PHPNameSpaceParser (Store store){
        this.store = store;
    }
    
    protected PHPNamespace parse(ParseTree tree){
        start = false;
        parseTree(tree, 10);
        return parentNamespace;
    }
    
    
    
    protected PHPNamespace parseTree(ParseTree tree, int level){
        
        //String pad = "NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN".substring(0, level);
        //System.err.println(pad + " [" + tree.getClass().getSimpleName() + "]: " + tree.getText()); //getCanonicalName
        
        if( tree.getClass().getSimpleName().equals( "NamespaceNameListContext")) {
            start = true;
        }
        if(start && tree.getClass().getSimpleName().equals( "IdentifierContext")){
            String fullname = setFullQualifiedName(parentNamespace, tree.getText());
            PHPNamespace n = store.find(PHPNamespace.class, fullname);
            if(n == null){
                n = store.create(PHPNamespace.class);
                n.setFullQualifiedName(fullname);
                n.setName(tree.getText());
                n.setParent(parentNamespace);
                parentNamespace = n;
                System.err.println("ADD Namespace:" + fullname);
            }
        }
        
        int childCount = tree.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ParseTree childTree = tree.getChild(i);
            parseTree(childTree, level + 1);
        }
        
        return null;
    }
    
    protected String setFullQualifiedName(PHPNamespace parent, String Name){
        String namespace = Name.toLowerCase();
        
        while(parent != null){
            namespace = parent.getName().toLowerCase() + "|" + namespace;
            parent = parent.getParent();
        }
        
        return "NAMESPACE|" + namespace;
    }
}
