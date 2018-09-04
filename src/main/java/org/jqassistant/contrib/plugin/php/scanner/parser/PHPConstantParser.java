/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.scanner.parser;

import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.php.model.VisibilityModifier;
import org.jqassistant.contrib.plugin.php.model.PHPPropertyDescriptor;

/**
 * parse subtree and detect property characteristics 
 * @author falk
 */
public class PHPConstantParser {
    protected Helper helper;
    protected PHPPropertyDescriptor phpProperty;
    
    public PHPConstantParser (Helper helper){
        this.helper = helper;
    }
    
    /**
     * parse tree and return contains php property
     * @param tree
     * @return 
     */
    public PHPPropertyDescriptor parse(ParseTree tree){
        phpProperty = helper.getProperty();
        phpProperty.setLineNumber(helper.getLineNumberByTokenNumber(tree.getChild(0).getSourceInterval().a));
        phpProperty.setConstant(Boolean.TRUE);
        parseTree(tree, 1);
        
        helper.logInfo("ADD Constant: " + phpProperty.getName() + " " + phpProperty.getVisibility() + (phpProperty.isStatic() ? " STATIC" : "") + (phpProperty.isConstant()? " CONSTANT" : ""));
        return phpProperty;
    }
    
    /**
     * walk through tree 
     * @param tree
     * @param level 
     */
    protected void parseTree(ParseTree tree, int level){
        
        //String pad = "PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP".substring(0, level);
        //System.err.println(pad + " [" + tree.getClass().getSimpleName() + "]: " + tree.getText()); //getCanonicalName
        
        if(tree.getClass().getSimpleName().equals("IdentifierContext")){
            phpProperty.setName(tree.getChild(0).getText());
            return;
        }

        int childCount = tree.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ParseTree childTree = tree.getChild(i);
            parseTree(childTree, level + 1);
        }
    }
}
