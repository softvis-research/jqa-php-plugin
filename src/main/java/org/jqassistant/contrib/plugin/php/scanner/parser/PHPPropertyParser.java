/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.scanner.parser;

import com.buschmais.jqassistant.core.store.api.Store;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.php.model.PHPClass;
import org.jqassistant.contrib.plugin.php.model.PHPProperty;
import org.jqassistant.contrib.plugin.php.model.VisibilityModifier;

/**
 *
 * @author falk
 */
public class PHPPropertyParser {
    protected Store store;
    protected PHPProperty phpProperty;
    
    public PHPPropertyParser (Store store){
        this.store = store;
    }
    
    public PHPProperty parse(ParseTree tree){
        phpProperty = store.create(PHPProperty.class);
        phpProperty.setVisibility(VisibilityModifier.DEFAULT);
         phpProperty.setStatic(Boolean.FALSE);
        parseTree(tree, 1);
        
        System.err.println("ADD Property: " + phpProperty.getName() + " " + phpProperty.getVisibility() + (phpProperty.isStatic() ? " STATIC" : ""));
        return phpProperty;
    }
    
    protected void parseTree(ParseTree tree, int level){
        
        //String pad = "PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP".substring(0, level);
        //System.err.println(pad + " [" + tree.getClass().getSimpleName() + "]: " + tree.getText()); //getCanonicalName
        
        if(tree.getClass().getSimpleName().equals("VariableInitializerContext")){
            phpProperty.setName(tree.getChild(0).getText());
        }
        else if (tree.getClass().getSimpleName().equals("MemberModifierContext")){
           switch (tree.getText().toLowerCase()){
                case "static":
                    phpProperty.setStatic(Boolean.TRUE);
                    break;
                case "private":
                    phpProperty.setVisibility(VisibilityModifier.PRIVATE);
                    break;
                case "protected":
                    phpProperty.setVisibility(VisibilityModifier.PROTECTED);
                    break;
                case "public":
                    phpProperty.setVisibility(VisibilityModifier.PUBLIC);
                    break;
            }
        }

        int childCount = tree.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ParseTree childTree = tree.getChild(i);
            parseTree(childTree, level + 1);
        }
    }
}
