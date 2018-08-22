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
    
    protected PHPUse parentUse;
    protected Boolean start = false;
    protected Boolean nextisAlias = false; 
    
    public PHPUseParser (){
        
    }
    
    protected PHPUse parse(ParseTree tree){
        start = false;
        parseTree(tree, 10);
        System.out.println("TEMP Use: " + parentUse.getFullQualifiedName());
        return parentUse;
    }
    
    
    
    protected PHPUse parseTree(ParseTree tree, int level){
        
        //String pad = "NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN".substring(0, level);
        //System.err.println(pad + " [" + tree.getClass().getSimpleName() + "]: " + tree.getText()); //getCanonicalName
        
        if( tree.getClass().getSimpleName().equals( "NamespaceNameListContext")) {
            start = true;
        }
        if(start && !nextisAlias && tree.getClass().getSimpleName().equals( "IdentifierContext")){
            PHPUse n = new PHPUse();
              n.name = tree.getText();
              n.alias = n.name;
              n.parent = parentUse;
              parentUse = n;
        }
        else if (start && tree.getClass().getSimpleName().equals( "TerminalNodeImpl") && tree.getText().toLowerCase().equals("as")){
            nextisAlias = true;
        }
        else if(nextisAlias && tree.getClass().getSimpleName().equals( "IdentifierContext")){
           parentUse.alias = tree.getText();
        }
        
        int childCount = tree.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ParseTree childTree = tree.getChild(i);
            parseTree(childTree, level + 1);
        }
        
        return null;
    }
}
