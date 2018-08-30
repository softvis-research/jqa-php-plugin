/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.scanner.parser;

import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.php.model.PHPNamespaceDescriptor;

/**
 * parse subtree and detect namespace characteristics 
 * @author falk
 */
public class PHPNameSpaceParser {
    
    protected PHPNamespaceDescriptor parentNamespace;
    protected Boolean start = false;
    protected Helper helper;
    
    public PHPNameSpaceParser (Helper helper){
        this.helper = helper;
    }
    
    /**
     * parse tree and return contains php namespace
     * @param tree
     * @return PHPNamespaceDescriptor
     */
    protected PHPNamespaceDescriptor parse(ParseTree tree){
        start = false;
        parseTree(tree, 10);
        return parentNamespace;
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
        if(start && tree.getClass().getSimpleName().equals( "IdentifierContext")){
            parentNamespace = helper.getNamespace(tree.getText(), parentNamespace);
        }
        
        int childCount = tree.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ParseTree childTree = tree.getChild(i);
            parseTree(childTree, level + 1);
        }
    }
}
