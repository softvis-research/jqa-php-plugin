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
import org.jqassistant.contrib.plugin.php.model.PHPMethodDescriptor;
import org.jqassistant.contrib.plugin.php.model.PHPNamespaceDescriptor;
import org.jqassistant.contrib.plugin.php.model.PHPTypeDescriptor;

/**
 * parse subtree and detect method characteristics 
 * @author falk
 */
public class PHPMethodParser {
    protected PHPNamespaceDescriptor namespace;
    protected Helper helper;
    protected Map<String, PHPUse> useContext = new HashMap<>();
    protected PHPMethodDescriptor phpMethod;
    protected PHPTypeDescriptor phpClass;
    protected List<String> modifire; 
    
      
    public PHPMethodParser (Helper helper, PHPTypeDescriptor phpClass){
        this(helper, phpClass, new  HashMap<String, PHPUse>());
    }
    
    public PHPMethodParser (Helper helper, PHPTypeDescriptor phpClass, Map<String, PHPUse> useContext){
        this.useContext = useContext;
        this.phpClass = phpClass;
        this.helper = helper;
        
        modifire = new ArrayList<String>();
    }
    
    /**
     * parse tree and return contains php method
     * @param tree
     * @return PHPMethodDescriptor
     */
    public PHPMethodDescriptor parse(ParseTree tree){
        parseTree(tree, 1);
        
        return phpMethod;
    }
    
    /**
     * walk through tree 
     * @param tree
     * @param level 
     */
    protected void parseTree(ParseTree tree, int level){
        
        //String pad = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF".substring(0, level);
        //System.err.println(pad + " [" + tree.getClass().getSimpleName() + "]: " + tree.getText()); //getCanonicalName
        
        if(phpMethod == null && tree.getClass().getSimpleName().equals("IdentifierContext")){
            phpMethod = helper.getMethod(tree.getText(), phpClass);
            phpMethod.setParametersCount(0);
            phpMethod.setLinesOfCode(0);
            phpMethod.setLineNumber(helper.getLineNumberByTokenNumber(tree.getChild(0).getSourceInterval().a));
            
            for (int i = 0; i < modifire.size(); i++) {
                switch (modifire.get(i)){
                    case "static":
                        phpMethod.setStatic(Boolean.TRUE);
                        break;
                    case "private":
                        phpMethod.setVisibility(VisibilityModifier.PRIVATE);
                        break;
                    case "protected":
                        phpMethod.setVisibility(VisibilityModifier.PROTECTED);
                        break;
                    case "public":
                        phpMethod.setVisibility(VisibilityModifier.PUBLIC);
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
            phpMethod.setParametersCount(parameterCount);
            return;
        }
        else if (tree.getClass().getSimpleName().equals("BlockStatementContext")){
            phpMethod.setLinesOfCode(countBodyLines(tree));
            return;
        }
        
        int childCount = tree.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ParseTree childTree = tree.getChild(i);
            parseTree(childTree, level + 1);
        }
    }
    
    protected Integer countBodyLines(ParseTree tree){
         Integer end = helper.getLineNumberByTokenNumber(tree.getSourceInterval().b);
         return end - phpMethod.getLineNumber() + 1;
    }
    
     
}
