/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.scanner.parser;

import java.util.HashMap;
import java.util.Map;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.php.scanner.parser.helper.PHPUse;
import org.jqassistant.contrib.plugin.php.model.PHPClassDescriptor;
import org.jqassistant.contrib.plugin.php.model.PHPNamespaceDescriptor;
import org.jqassistant.contrib.plugin.php.model.PHPTypeDescriptor;

/**
 * parse subtree and detect type characteristics 
 * @author falk
 */
public class PHPTypeParser {
    
    protected PHPNamespaceDescriptor namespace;
    protected Map<String, PHPUse> useContext = new HashMap<>();
    protected PHPTypeDescriptor phpClass;
    protected boolean isAbstract = false;
    protected String type = "class";
    protected Helper helper;
    
    public PHPTypeParser (Helper helper, PHPNamespaceDescriptor namespace, Map<String, PHPUse> useContext){
        this.useContext = useContext;
        this.namespace = namespace;
        this.helper = helper;
    }
    
    /**
     * parse tree and return contains php type (class, interface or trait)
     * @param tree
     * @return PHPNamespaceDescriptor
     */
    public PHPTypeDescriptor parse(ParseTree tree){
        parseTree(tree, 1, 0);
        return phpClass;
    }
    
    /**
     * walk through tree 
     * @param tree
     * @param level 
     */
    protected void parseTree(ParseTree tree, int level, int idx){
        
//        String pad = "CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC".substring(0, level);
//        System.err.println(pad + " [" + tree.getClass().getSimpleName() + "]: " + tree.getText()); //getCanonicalName
        
        if(phpClass == null && tree.getClass().getSimpleName().equals("IdentifierContext")) {
            if (type.equals("interface")){
                phpClass = helper.getInterface(tree.getText(), namespace);
            }
            else if (type.equals("trait")){
                phpClass = helper.getTrait(tree.getText(), namespace);
            } else {
                phpClass = helper.getClass(tree.getText(), namespace);
                phpClass.as(PHPClassDescriptor.class).setAbstract(isAbstract);
            }
        }
        else if (phpClass == null && tree.getClass().getSimpleName().equals("ModifierContext")){
            if(tree.getText().toLowerCase().equals("abstract")){
                isAbstract = true;
            }
        }
        else if (phpClass == null && tree.getClass().getSimpleName().equals("TerminalNodeImpl")){
            if(tree.getText().toLowerCase().equals("interface")){
                type = "interface";
            }
            else if(tree.getText().toLowerCase().equals("trait")){
                type = "trait";
            }
        }
        else if (phpClass != null && tree.getClass().getSimpleName().equals("ClassStatementContext")){
            for (int i = 0; i < tree.getChildCount(); i++) {
                if(tree.getChild(i).getClass().getSimpleName().equals("VariableInitializerContext")){
                    phpClass.getProperties().add((new PHPPropertyParser(helper)).parse(tree));
                    return;
                }
                else if (tree.getChild(i).getClass().getSimpleName().equals("MethodBodyContext")){
                    phpClass.getMethods().add((new PHPFunctionParser(helper, phpClass, namespace)).parse(tree));
                    return;
                }
                else if (tree.getChild(i).getClass().getSimpleName().equals("TerminalNodeImpl")){
                    if(tree.getChild(i).getText().toLowerCase().equals("const")){
                        phpClass.getProperties().add((new PHPConstantParser(helper)).parse(tree));
                        return;
                    }
                }
            }
        }
        else if(phpClass != null && tree.getClass().getSimpleName().equals("InterfaceListContext")){
            (new PHPTypeMapper(helper, phpClass, namespace, "interface", useContext)).parse(tree);
            return;
        }
        else if(phpClass != null && level == 2 && tree.getClass().getSimpleName().equals("TerminalNodeImpl")){
            if(tree.getText().toLowerCase().equals("extends") && !type.equals("interface")){
                (new PHPTypeMapper(helper, phpClass, namespace, "superclass", useContext)).parse(tree.getParent().getChild(idx + 1));
                return;
            }
        }
        else if(phpClass != null && level == 3 && tree.getClass().getSimpleName().equals("TerminalNodeImpl")){
            if(tree.getText().toLowerCase().equals("use")){
                (new PHPTypeMapper(helper, phpClass, namespace, "trait", useContext)).parse(tree.getParent().getChild(idx + 1));
                return;
            }
        }
       
        int childCount = tree.getChildCount();
        for (int i = 0; i < childCount; i++) {
            parseTree(tree.getChild(i), level + 1, i);
        }
    }
}
