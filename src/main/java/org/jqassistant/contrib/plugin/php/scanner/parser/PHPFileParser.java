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

/**
 *  - namespace erkennen
    - use anweisungen erkennen
    - funktionen
    - calls
    - classes
 * @author falk
 */
public class PHPFileParser {
    
    protected ScannerContext sc;
    
    public void PHPFileParser(ScannerContext sc){
        this.sc = sc;
    }
    
    public PHPClass parse(ParseTree tree){   
        //JavaTypeResolver nachschauen
        //String n = "xyz";
        PHPClass returnValue;// = sc.getStore().find(PHPClass.class, n);
        //if (returnValue == null){
            returnValue = sc.getStore().create(PHPClass.class);
            
        //}
        
        return returnValue;
    }
   
    
}
