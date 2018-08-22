/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.scanner.parser.helper;


/**
 *
 * @author falk
 */
public class PHPUse {
    
    public PHPUse parent = null;
    public String name = "";
    public String alias = "";
    
     public String getFullQualifiedName(){
        String namespace = name.toLowerCase();
        
        while(parent != null){
            namespace = parent.name.toLowerCase() + "|" + namespace;
            parent = parent.parent;
        }
        
       
        
        return "USE|" + namespace + " AS " + alias;
    }
}
