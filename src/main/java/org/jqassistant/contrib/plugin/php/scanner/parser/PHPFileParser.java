/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.scanner.parser;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import org.jqassistant.contrib.plugin.php.model.PHPFileDescriptor;
import java.util.List;
import org.antlr.v4.runtime.tree.ParseTree;
import com.buschmais.jqassistant.core.store.api.Store;
import java.util.HashMap;
import java.util.Map;
import org.jqassistant.contrib.plugin.php.scanner.parser.helper.PHPUse;
import org.jqassistant.contrib.plugin.php.model.PHPClassDescriptor;
import org.jqassistant.contrib.plugin.php.model.PHPFunctionDescriptor;
import org.jqassistant.contrib.plugin.php.model.PHPNamespaceDescriptor;
import org.jqassistant.contrib.plugin.php.model.PHPPropertyDescriptor;

/**
 * parse php files
 * detect namespace, uses, classes and funtions
 * @author falk
 */
public class PHPFileParser {
    
    protected PHPFileDescriptor fileDescriptor;
    protected Store store;
    protected PHPNamespaceDescriptor namespace;
    protected Map<String, PHPUse> useContext = new HashMap<>();
    
    public PHPFileParser(Store store, PHPFileDescriptor fileDescriptor){
        this.fileDescriptor = fileDescriptor;
        this.store = store;
    }
    
    public void parse(ParseTree tree){   
        
        parseTree(tree, 0);
        
    }
    
    /**
     * walk through tree 
     * @param tree
     * @param level 
     */
     protected void parseTree(ParseTree tree, int level){
        
        switch (tree.getClass().getSimpleName()) {
            case "UseDeclarationContentListContext":
                PHPUse u = (new PHPUseParser()).parse(tree);
                useContext.put(u.alias, u);
                System.out.println("TEMP Use: " + u.getFullQualifiedName());
                return;
            case "QualifiedNamespaceNameContext":
                if(tree.getText().toLowerCase().startsWith("use")){
                    //BUGFIX: Parser ident Use with relative namespace as namespace stats with 'use'
                    PHPUse u2 = (new PHPUseParser()).skipFirst().parse(tree);
                    System.out.println("TEMP Use: " + u2.getFullQualifiedName());
                    useContext.put(u2.alias, u2);
                }
                else {
                    namespace = (new PHPNameSpaceParser(store)).parse(tree);
                }
                return;
            case "ClassDeclarationContext":
                fileDescriptor.getClasses().add((new PHPTypeParser(store, namespace, useContext)).parse(tree));
                return;
            case "FunctionDeclarationContext":
                fileDescriptor.getFunctions().add((new PHPFunctionParser(store, namespace, useContext)).parse(tree));
                return;
            case "AssignmentExpressionContext":
                return;
            case "ChainExpressionContext":
                return;
        }
        
        
        //String pad = "########################################################################################################################".substring(0, level);
        //System.err.println(pad + " [" + tree.getClass().getSimpleName() + "]: " + tree.getText()); //getCanonicalName
        
        
        int childCount = tree.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ParseTree childTree = tree.getChild(i);
            parseTree(childTree, level + 1);
        }
    }
   
    
}
