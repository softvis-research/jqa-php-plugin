/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.scanner.parser;

import com.buschmais.jqassistant.core.store.api.Store;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.php.model.PHPNamespace;
import org.jqassistant.contrib.plugin.php.model.PHPType;
import org.jqassistant.contrib.plugin.php.scanner.parser.helper.PHPUse;
import java.util.List;
import org.jqassistant.contrib.plugin.php.model.PHPInterface;
import org.jqassistant.contrib.plugin.php.model.PHPClass;

/**
 *
 * @author falk
 */
public class PHPClassMapper {
    
    protected PHPType phpClass;
    protected boolean mapInterfaces;
    protected Store store;
    protected Map<String, PHPUse> useContext = new HashMap<>();
    protected Helper helper;
    
    public PHPClassMapper(Store store, PHPType phpClass, boolean mapInterfaces, Map<String, PHPUse> useContext ){
        this.phpClass = phpClass;
        this.mapInterfaces = mapInterfaces;
        this.useContext = useContext;
        this.helper = new Helper(store);
    }
    
    public void parse(ParseTree tree){
        parseTree(tree, 5);
    }
    
    protected void parseTree(ParseTree tree, Integer level){
        
        //String pad = "IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII".substring(0, level);
        //System.err.println(pad + " [" + tree.getClass().getSimpleName() + "]: " + tree.getText()); //getCanonicalName
        
        if(tree.getClass().getSimpleName().equals("QualifiedNamespaceNameContext")){
            boolean isRelative = true;
            Integer listIdx = 0;
            
            if(tree.getChild(0).getClass().getSimpleName().equals("TerminalNodeImpl") && tree.getChild(0).getText().equals("\\")){
                isRelative = false;
                listIdx = 1;
            }
            
            List<String> namelist = new ArrayList<String>();
            
            int childCount = tree.getChild(listIdx).getChildCount();
            for (int i = 0; i < childCount; i++) {
                if(!tree.getChild(listIdx).getChild(i).getClass().getSimpleName().equals("IdentifierContext") ){
                    continue;
                }
                
                String name = tree.getChild(listIdx).getChild(i).getText();
                
                
                if (namelist.isEmpty() && isRelative){
                    
                    boolean isMapped = false;
                    for (Map.Entry<String, PHPUse> entry : useContext.entrySet()) {
                        if(name.equals(entry.getKey())){
                            PHPUse u = entry.getValue();
                            while (u != null){
                                namelist.add(0, u.name);
                                u = u.parent;
                            }
                            isMapped = true;
                            break;
                        }
                    }
                    
                    if(!isMapped){
                        //use Namespace
                        PHPNamespace n = phpClass.getNamespace();
                        while (n != null){
                            namelist.add(0, n.getName());
                            n = n.getParent();
                        }
                        namelist.add(name);
                    }
                    continue;
                }
                
                namelist.add(name);
            }
            
            addClass(namelist);
            
            return;
        }
        
        
        int childCount = tree.getChildCount();
        for (int i = 0; i < childCount; i++) {
            parseTree(tree.getChild(i), level + 1);
        }
    
    }
    
    protected void addClass(List<String> namelist){
        
        //TODO: Namespace anlegen
        //TODO: Classe oder interface anlegen
        
        PHPNamespace n = null;
        
        Integer s = namelist.size() -1;
        for (int i = 0; i < s; i++) {
            n = helper.getNamespace(namelist.get(i), n);
        }
        
        if (mapInterfaces){
            PHPInterface i = helper.getInterface(namelist.get(namelist.size() -1), n);
            phpClass.getInterfaces().add(i);
        }
        else {
            PHPClass c = helper.getClass(namelist.get(namelist.size() -1), n);
            phpClass.setSuperClass(c);
        }
    }
    
}
