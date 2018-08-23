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
    protected Boolean start = false;
    protected Helper helper;
    
    public PHPNameSpaceParser (Store store){
        helper = new Helper(store);
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
            parentNamespace = helper.getNamespace(tree.getText(), parentNamespace);
        }
        
        int childCount = tree.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ParseTree childTree = tree.getChild(i);
            parseTree(childTree, level + 1);
        }
        
        return null;
    }
}
