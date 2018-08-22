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

/**
 *
 * @author falk
 * - super namespaces erkennen
 */
public class PHPUseParser {
    
    protected Map<String, PHPUse> useContext = new HashMap<>();
    
    public PHPUseParser (){
    }
    
    protected PHPUse parse(ParseTree tree){
        parseTree(tree, 10);
        return new PHPUse();
    }
    
    protected void parseTree(ParseTree tree, int level){
        
        String pad = "UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU".substring(0, level);
        System.err.println(pad + " [" + tree.getClass().getSimpleName() + "]: " + tree.getText()); //getCanonicalName
        
        if( tree.getClass().getSimpleName() == "NamespaceNameListContext") {
            
        }
        //"IdentifierContext"
        //        "TerminalNodeImpl"
        
        int childCount = tree.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ParseTree childTree = tree.getChild(i);
            parseTree(childTree, level + 1);
        }
        
    }
}
