/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.scanner;

import com.buschmais.jqassistant.plugin.common.api.scanner.filesystem.FileResource;
import java.io.IOException;
import java.io.InputStream;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import org.jqassistant.contrib.plugin.php.PhpLexer;
import org.jqassistant.contrib.plugin.php.PhpParser;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import java.io.File;


/**
 *
 * @author falk
 */
public class PHPLexer {
    
    public void test (final File item) {
        try {
         
             InputStream inputStream = FileUtils.openInputStream(item);

        
            Lexer lexer = new PhpLexer(CharStreams.fromStream(inputStream));

        
            TokenStream tokenStream = new CommonTokenStream(lexer);
    //        
    //        
    //        
            PhpParser parser = new PhpParser(tokenStream);
    //         
    //            parser.setBuildParseTree(true);
    //            
            ParseTree tree = parser.htmlDocument();
            System.err.println(tree.toStringTree(parser));
            parseTree(tree, 0);
        
        } catch (IOException e) {
         e.printStackTrace();
         System.out.println(e);
      }
    }
    
    protected void parseTree(ParseTree tree, int level){
        int childCount = tree.getChildCount();
        
        String pad = "##############################".substring(0, level);
        System.err.println(pad + " " + tree.getClass().getCanonicalName());
        System.err.println(pad + " " + tree.getText());
        
        
        for (int i = 0; i < childCount; i++) {
            ParseTree childTree = tree.getChild(i);
            
            
            
            parseTree(childTree, level + 1);
        }
    }
            
    
}
