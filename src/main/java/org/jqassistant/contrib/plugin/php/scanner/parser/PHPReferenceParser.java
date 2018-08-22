/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.scanner.parser;

import com.buschmais.jqassistant.core.store.api.Store;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.php.model.PHPCalling;
import org.jqassistant.contrib.plugin.php.model.PHPClass;
import org.jqassistant.contrib.plugin.php.model.PHPMethod;
import org.jqassistant.contrib.plugin.php.model.PHPNamespace;
import org.jqassistant.contrib.plugin.php.scanner.parser.helper.PHPUse;

/**
 *
 * @author falk
 */
public class PHPReferenceParser {
    protected PHPNamespace namespace;
    protected Store store;
    protected Map<String, PHPUse> useContext = new HashMap<>();
    protected PHPCalling caller;
    protected PHPNamespace phpNamespace;
    protected List<String> modifire; 

    public PHPReferenceParser (Store store, PHPCalling caller){
        this(store, caller, null, new  HashMap<String, PHPUse>());
    }
    
    public void setNamespace(PHPNamespace phpNamespace){
        this.phpNamespace = phpNamespace;
    }
    
    public void setUseContext(Map<String, PHPUse> useContext){
        this.useContext = useContext;
    }
    
    public PHPReferenceParser (Store store, PHPCalling caller, PHPNamespace phpNamespace){
        this(store, caller, phpNamespace, new  HashMap<String, PHPUse>());
    }
    
    public PHPReferenceParser (Store store, PHPCalling caller, PHPNamespace phpNamespace, Map<String, PHPUse> useContext){
        this.useContext = useContext;
        this.phpNamespace = phpNamespace;
        this.store = store;
        this.caller = caller;
    }
    
    public void parse(ParseTree tree){
        
        System.out.println("TODO: Ausdruck verarbeiten: " + tree.getText());
        //parseTree(tree, 5);
    }
    
    protected void parseTree(ParseTree tree, int level){
        
        String pad = "RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR".substring(0, level);
        System.err.println(pad + " [" + tree.getClass().getSimpleName() + "]: " + tree.getText()); //getCanonicalName
        
        int childCount = tree.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ParseTree childTree = tree.getChild(i);
            parseTree(childTree, level + 1);
        }
    }
    
}
