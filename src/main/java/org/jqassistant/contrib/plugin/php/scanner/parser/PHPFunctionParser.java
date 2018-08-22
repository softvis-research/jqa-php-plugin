/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.scanner.parser;

import com.buschmais.jqassistant.core.store.api.Store;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.php.model.PHPClass;
import org.jqassistant.contrib.plugin.php.model.PHPNamespace;
import org.jqassistant.contrib.plugin.php.scanner.parser.helper.PHPUse;
import org.jqassistant.contrib.plugin.php.model.PHPFunction;
import org.jqassistant.contrib.plugin.php.model.PHPFileDescriptor;
import org.jqassistant.contrib.plugin.php.model.VisibilityModifier;

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
    protected PHPFileDescriptor phpFile;
    protected List<String> modifire; 
    
    public PHPFunctionParser (Store store, PHPFileDescriptor phpFile){
        this(store, null, new  HashMap<String, PHPUse>() );
        this.phpFile = phpFile;
    }
    
    public PHPFunctionParser (Store store, Map<String, PHPUse> useContext, PHPFileDescriptor phpFile){
        this(store, null, useContext);
        this.phpFile = phpFile;
    }
    
    public PHPFunctionParser (Store store, PHPClass phpClass){
        this(store, phpClass, new  HashMap<String, PHPUse>());
    }
    
    public PHPFunctionParser (Store store, PHPClass phpClass, Map<String, PHPUse> useContext){
        this.useContext = useContext;
        this.phpClass = phpClass;
        this.store = store;
        
        modifire = new ArrayList<String>();
    }
    
    public PHPFunction parse(ParseTree tree){
        parseTree(tree, 1);
        
        phpFunction = store.create(PHPFunction.class);
        return phpFunction;
    }
    
    protected void parseTree(ParseTree tree, int level){
        
        //String pad = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF".substring(0, level);
        //System.err.println(pad + " [" + tree.getClass().getSimpleName() + "]: " + tree.getText()); //getCanonicalName
        
        if(phpFunction == null && tree.getClass().getSimpleName().equals("IdentifierContext")){
            String fullname = getFullQualifiedName(tree.getText());
            phpFunction = store.find(PHPFunction.class, fullname);
            if(phpFunction == null){
                phpFunction = store.create(PHPFunction.class);
                phpFunction.setFullQualifiedName(fullname);
                phpFunction.setName(tree.getText());
                System.err.println("ADD Function: " + fullname);
            }
            
            phpFunction.setParametersCount(0);
            phpFunction.setLinesOfCode(0);
            
            for (int i = 0; i < modifire.size(); i++) {
                switch (modifire.get(i)){
                    case "static":
                        phpFunction.setStatic(Boolean.TRUE);
                        break;
                    case "private":
                        phpFunction.setVisibility(VisibilityModifier.PRIVATE);
                        break;
                    case "protected":
                        phpFunction.setVisibility(VisibilityModifier.PROTECTED);
                        break;
                    case "public":
                        phpFunction.setVisibility(VisibilityModifier.PUBLIC);
                        break;
                }
            }
            
        }
        else if (tree.getClass().getSimpleName().equals("MemberModifierContext")){
            modifire.add(tree.getText().toLowerCase());
        }
        else if (tree.getClass().getSimpleName().equals("FormalParameterListContext")){
            Integer parameterCount = 0;
            for (int i = 0; i < tree.getChildCount(); i++) {
                 if (tree.getChild(i).getClass().getSimpleName().equals("FormalParameterContext")){
                    parameterCount++;
                 }
            }
            phpFunction.setParametersCount(parameterCount);
            return;
        }
        else if (tree.getClass().getSimpleName().equals("BlockStatementContext")){
            phpFunction.setLinesOfCode(parseBodyTree(tree, 0));
            return;
        }
        
        int childCount = tree.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ParseTree childTree = tree.getChild(i);
            parseTree(childTree, level + 1);
        }
    }
    
    protected Integer parseBodyTree(ParseTree tree, int linesOfCode){
        if (tree.getClass().getSimpleName().equals("InnerStatementContext")){
            linesOfCode++;
        }
        
//        String pad = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB".substring(0, level);
//        System.err.println(pad + " [" + tree.getClass().getSimpleName() + "]: " + tree.getText()); //getCanonicalName
        
        int childCount = tree.getChildCount();
        for (int i = 0; i < childCount; i++) {
            linesOfCode += parseBodyTree(tree.getChild(i), 0);
        }
        
        return linesOfCode;
    }
    
     protected String getFullQualifiedName(String Name){
        String namespace = Name.toLowerCase();
        
        if (phpClass != null){
            namespace = phpClass.getName().toLowerCase() + "|" + namespace;
            PHPNamespace parent = phpClass.getNamespace();
            while(parent != null){
                namespace = parent.getName().toLowerCase() + "|" + namespace;
                parent = parent.getParent();
            }
            return "CLASS_FUNCTION|" + namespace;
        }
        
//        if (phpFile != null){
//            namespace = phpFile.getFileName().toLowerCase() + "|" + namespace;
//            return "FILE_FUNCTION|" + namespace;
//        }
        
        return "FUNCTION|" + namespace;
    }
}
