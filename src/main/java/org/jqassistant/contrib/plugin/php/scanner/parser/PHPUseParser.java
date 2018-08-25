/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.scanner.parser;

import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.php.scanner.parser.helper.PHPUse;

/**
 * parse subtree and detect used clause specification.
 * in php, it can be a namespace or a type.
 * @author falk
 */
public class PHPUseParser {
    
    protected PHPUse parentUse;
    protected Boolean start = false;
    protected Boolean nextisAlias = false; 
    protected boolean skipFirst = false;
    
    public PHPUseParser (){
        
    }
    
    /**
     * by parsing, skip first entity
     * @return this
     */
    public PHPUseParser skipFirst(){
        skipFirst = true;
        return this;
    }
    
    /**
     * parse tree and return contains 'use'
     * @param tree
     * @return PHPUse
     */
    protected PHPUse parse(ParseTree tree){
        start = false;
        parseTree(tree, 10);
        return parentUse;
    }
    
    /**
     * walk through tree 
     * @param tree
     * @param level 
     */
    protected void parseTree(ParseTree tree, int level){
        
        //String pad = "NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN".substring(0, level);
        //System.err.println(pad + " [" + tree.getClass().getSimpleName() + "]: " + tree.getText()); //getCanonicalName
        
        if( tree.getClass().getSimpleName().equals( "NamespaceNameListContext")) {
            start = true;
        }
        if(start && !nextisAlias && tree.getClass().getSimpleName().equals( "IdentifierContext")){
            if(skipFirst){
                skipFirst = false;
            } else {
            PHPUse n = new PHPUse();
              n.name = tree.getText();
              n.alias = n.name;
              n.parent = parentUse;
              parentUse = n;
            }
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
    }
}
