/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.scanner.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.php.scanner.parser.helper.PHPUse;
import org.jqassistant.contrib.plugin.php.model.VisibilityModifier;
import org.jqassistant.contrib.plugin.php.model.PHPFunctionDescriptor;
import org.jqassistant.contrib.plugin.php.model.PHPNamespaceDescriptor;
import org.jqassistant.contrib.plugin.php.model.PHPTypeDescriptor;

/**
 * parse subtree and detect function characteristics 
 * @author falk
 */
public class PHPFunctionParser {
    protected PHPNamespaceDescriptor namespace;
    protected Helper helper;
    protected Map<String, PHPUse> useContext = new HashMap<>();
    protected PHPFunctionDescriptor phpFunction;
    protected PHPNamespaceDescriptor phpNamespace = null;
    protected PHPTypeDescriptor phpClass = null;
    protected List<String> modifire; 

    public PHPFunctionParser (Helper helper, PHPTypeDescriptor phpClass){
        this(helper, phpClass, new  HashMap<String, PHPUse>());
    }
    
    public PHPFunctionParser (Helper helper, PHPTypeDescriptor phpClass, Map<String, PHPUse> useContext){
        this.useContext = useContext;
        this.phpClass = phpClass;
        this.helper = helper;
        
        modifire = new ArrayList<String>();
    }
    
    public PHPFunctionParser (Helper helper, PHPNamespaceDescriptor phpNamespace){
        this(helper, phpNamespace, new  HashMap<String, PHPUse>());
    }
    
    public PHPFunctionParser (Helper helper, PHPNamespaceDescriptor phpNamespace, Map<String, PHPUse> useContext){
        this.useContext = useContext;
        this.phpNamespace = phpNamespace;
        this.helper = helper;
        
        modifire = new ArrayList<String>();
    }
    
    /**
     * parse tree and return the php function
     * @param tree
     * @return PHPFunctionDescriptor
     */
    public PHPFunctionDescriptor parse(ParseTree tree){
        parseTree(tree, 1);
        
        return phpFunction;
    }
    
    /**
     * walk through tree 
     * @param tree
     * @param level 
     */
    protected void parseTree(ParseTree tree, int level){
        
//        String pad = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF".substring(0, level);
//        System.err.println(pad + " [" + tree.getClass().getSimpleName() + "]: " + tree.getText()); //getCanonicalName
        
        if(phpFunction == null && tree.getClass().getSimpleName().equals("IdentifierContext")){
            if(phpClass != null){
                phpFunction = helper.getFunction(tree.getText(), phpClass);
            } else {
                phpFunction = helper.getFunction(tree.getText(), namespace);
            }
            
            phpFunction.setLinesOfCode(0);
            phpFunction.setLineNumber(helper.getLineNumberByTokenNumber(tree.getChild(0).getSourceInterval().a));
            
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
            parseParameterListTree(tree, level);
            return;
        }
        else if (tree.getClass().getSimpleName().equals("BlockStatementContext")){
            phpFunction.setLinesOfCode(countBodyLines(tree));
            return;
        }
        
        int childCount = tree.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ParseTree childTree = tree.getChild(i);
            parseTree(childTree, level + 1);
        }
    }
    
     protected void parseParameterListTree(ParseTree tree, int level){
//        String pad = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF".substring(0, level);
//        System.err.println(pad + " [" + tree.getClass().getSimpleName() + "]: " + tree.getText()); //getCanonicalName
         
        if (tree.getClass().getSimpleName().equals("VariableInitializerContext")){
            phpFunction.getParameters().add(helper.getFunctionParameter(phpFunction.getParameters().size(), tree.getChild(0).getText()));
            return;
        } 
         
        int childCount = tree.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ParseTree childTree = tree.getChild(i);
            parseParameterListTree(childTree, level + 1);
        }
     }
    
    protected Integer countBodyLines(ParseTree tree){
         Integer end = helper.getLineNumberByTokenNumber(tree.getSourceInterval().b);
         return end - phpFunction.getLineNumber() + 1;
    }
}
