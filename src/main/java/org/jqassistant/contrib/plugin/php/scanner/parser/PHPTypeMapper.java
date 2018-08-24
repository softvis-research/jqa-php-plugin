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
import org.jqassistant.contrib.plugin.php.scanner.parser.helper.PHPUse;
import java.util.List;
import org.jqassistant.contrib.plugin.php.model.PHPClassDescriptor;
import org.jqassistant.contrib.plugin.php.model.PHPInterfaceDescriptor;
import org.jqassistant.contrib.plugin.php.model.PHPNamespaceDescriptor;
import org.jqassistant.contrib.plugin.php.model.PHPTraitDescriptor;
import org.jqassistant.contrib.plugin.php.model.PHPTypeDescriptor;

/**
 * detect super class, traits or interfaces an map it to class
 * @author falk
 */
public class PHPTypeMapper {
    
    protected PHPTypeDescriptor phpClass;
    protected String mapType = "superclass";
    protected Store store;
    protected Map<String, PHPUse> useContext = new HashMap<>();
    protected Helper helper;
    
    public PHPTypeMapper(Store store, PHPTypeDescriptor phpClass, String mapType, Map<String, PHPUse> useContext ){
        this.phpClass = phpClass;
        this.mapType = mapType;
        this.useContext = useContext;
        this.helper = new Helper(store);
    }
    
    /**
     * parse tree
     * @param tree
     * @return PHPNamespaceDescriptor
     */
    public void parse(ParseTree tree){
        parseTree(tree, 5);
    }
    
    /**
     * walk through tree 
     * @param tree
     * @param level 
     */
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
                        PHPNamespaceDescriptor n = phpClass.getNamespace();
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
    
    /**
     * mapp inerface, super class or trait to php type
     * @param namelist 
     */
    protected void addClass(List<String> namelist){
        
        PHPNamespaceDescriptor n = null;
        
        Integer s = namelist.size() -1;
        for (int i = 0; i < s; i++) {
            n = helper.getNamespace(namelist.get(i), n);
        }
        
        if (mapType.equals("interface")){
            PHPInterfaceDescriptor i = helper.getInterface(namelist.get(namelist.size() -1), n);
            phpClass.getInterfaces().add(i);
        }
        else if (mapType.equals("superclass")) {
            PHPClassDescriptor c = helper.getClass(namelist.get(namelist.size() -1), n);
            phpClass.setSuperClass(c);
        }
        else if (mapType.equals("trait")) {
            PHPTraitDescriptor c = helper.getTrait(namelist.get(namelist.size() -1), n);
            phpClass.as(PHPClassDescriptor.class).getTraits().add(c);
        }
    }
    
}
