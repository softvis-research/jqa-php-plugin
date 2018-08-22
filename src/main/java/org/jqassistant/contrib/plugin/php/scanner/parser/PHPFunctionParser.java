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
import org.jqassistant.contrib.plugin.php.model.PHPClass;
import org.jqassistant.contrib.plugin.php.model.PHPNamespace;
import org.jqassistant.contrib.plugin.php.scanner.parser.helper.PHPUse;
import org.jqassistant.contrib.plugin.php.model.PHPFunction;

/**
 *
 * @author falk
 * - calls erkennen
 */
public class PHPFunctionParser {
    protected PHPNamespace namespace;
    protected Store store;
    protected Map<String, PHPUse> useContext = new HashMap<>();
    protected PHPFunction phpFunction;
    protected PHPClass phpClass;
    
    public PHPFunctionParser (Store store){
        this(store, null, new  HashMap<String, PHPUse>() );
    }
    
    public PHPFunctionParser (Store store, Map<String, PHPUse> useContext){
        this(store, null, useContext);
    }
    
    public PHPFunctionParser (Store store, PHPClass phpClass, Map<String, PHPUse> useContext){
        this.useContext = useContext;
        this.phpClass = phpClass;
        this.store = store;
    }
    
    public PHPFunction parse(ParseTree tree){
        parseTree(tree, 1);
        
        phpFunction = store.create(PHPFunction.class);
        return phpFunction;
    }
    
    protected void parseTree(ParseTree tree, int level){
        
        String pad = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF".substring(0, level);
        System.err.println(pad + " [" + tree.getClass().getSimpleName() + "]: " + tree.getText()); //getCanonicalName
        
        
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
        
        return "FUNCTION|" + namespace;
    }
}
