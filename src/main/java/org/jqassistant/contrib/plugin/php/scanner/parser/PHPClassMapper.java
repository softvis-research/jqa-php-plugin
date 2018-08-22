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
import org.jqassistant.contrib.plugin.php.model.PHPType;
import org.jqassistant.contrib.plugin.php.scanner.parser.helper.PHPUse;

/**
 *
 * @author falk
 */
public class PHPClassMapper {
    
    protected PHPType phpClass;
    protected boolean mapInterfaces;
    protected Store store;
    protected Map<String, PHPUse> useContext = new HashMap<>();
    
    public PHPClassMapper(Store store, PHPType phpClass, boolean mapInterfaces, Map<String, PHPUse> useContext ){
        this.store = store;
        this.phpClass = phpClass;
        this.mapInterfaces = mapInterfaces;
        this.useContext = useContext;
        
    }
    
    public void parse(ParseTree tree){
        System.out.println("TODO: " + (mapInterfaces ? "Interfaces" : "Super Class") + " zuordnen:" + tree.getText());
        //--> NamespaceNameListContext
        parseTree(tree, 5);
    }
    
    protected void parseTree(ParseTree tree, Integer level){
        
        String pad = "IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII".substring(0, level);
        System.err.println(pad + " [" + tree.getClass().getSimpleName() + "]: " + tree.getText()); //getCanonicalName
        
        
        int childCount = tree.getChildCount();
        for (int i = 0; i < childCount; i++) {
            parseTree(tree.getChild(i), level + 1);
        }
    
    }
}
