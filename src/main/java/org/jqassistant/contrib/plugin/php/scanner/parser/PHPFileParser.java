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
    
    
    
    public void parse(ParseTree tree, PHPFileDescriptor fileDescriptor){   
        
        System.out.println("org.jqassistant.contrib.plugin.php.scanner.parser.PHPFileParser.parse()");
        
    }
   
    
}
