/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.scanner.parser;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import org.jqassistant.contrib.plugin.php.model.PHPClass;
import org.jqassistant.contrib.plugin.php.model.PHPFileDescriptor;
import org.jqassistant.contrib.plugin.php.model.PHPFunction;
import org.jqassistant.contrib.plugin.php.model.PHPNamespace;
import org.jqassistant.contrib.plugin.php.model.PHPProperty;
import java.util.List;
import org.antlr.v4.runtime.tree.ParseTree;
import com.buschmais.jqassistant.core.store.api.Store;
import java.util.HashMap;
import java.util.Map;
import org.jqassistant.contrib.plugin.php.scanner.parser.helper.PHPUse;

/**
 *  - namespace erkennen
    - use anweisungen erkennen
    - funktionen
    - calls
    - classes
 * @author falk
 */
public class PHPFileParser {
    
    protected PHPFileDescriptor fileDescriptor;
    protected Store store;
    protected PHPNamespace namespace;
    protected Map<String, PHPUse> useContext = new HashMap<>();
    PHPReferenceParser referenceParser;
    
    public PHPFileParser(Store store, PHPFileDescriptor fileDescriptor){
        this.fileDescriptor = fileDescriptor;
        this.store = store;
    }
    
    public void parse(ParseTree tree){   
        
        System.out.println("org.jqassistant.contrib.plugin.php.scanner.parser.PHPFileParser.parse()");
        referenceParser = new PHPReferenceParser(store, fileDescriptor, namespace, useContext);
       
        parseTree(tree, 0);
        
        
    }
    
     protected void parseTree(ParseTree tree, int level){
        
        switch (tree.getClass().getSimpleName()) {
            case "QualifiedNamespaceNameContext":               
                namespace = (new PHPNameSpaceParser(store)).parse(tree);
                return;
            case "UseDeclarationContentListContext":
                PHPUse u = (new PHPUseParser()).parse(tree);
                useContext.put(u.alias, u);
                return;
            case "ClassDeclarationContext":
                fileDescriptor.getClasses().add((new PHPClassParser(store, namespace, useContext)).parse(tree));
                return;
            case "FunctionDeclarationContext":
                fileDescriptor.getFunctions().add((new PHPFunctionParser(store, namespace, useContext)).parse(tree));
                return;
            case "AssignmentExpressionContext":
                referenceParser.setNamespace(namespace);
                referenceParser.setUseContext(useContext);
                referenceParser.parse(tree);
                return;
            case "ChainExpressionContext":
                referenceParser.setNamespace(namespace);
                referenceParser.setUseContext(useContext);
                referenceParser.parse(tree);
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
