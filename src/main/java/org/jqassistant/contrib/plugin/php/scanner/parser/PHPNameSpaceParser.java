/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.scanner.parser;

import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.php.model.PHPNamespace;

/**
 *
 * @author falk
 * - super namespaces erkennen
 */
public class PHPNameSpaceParser {
    
    PHPNamespace parentNamespace;
    
    protected PHPNamespace parse(ParseTree tree){
        return parse(tree, null);
    }
    
    protected PHPNamespace parse(ParseTree tree, PHPNamespace parentNamespace){
        this.parentNamespace = parentNamespace;
        return parseTree(tree, 10);
        
    }
    
    protected PHPNamespace parseTree(ParseTree tree, int level){
        
        String pad = "NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN".substring(0, level);
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
        
        return null;
    }
}
