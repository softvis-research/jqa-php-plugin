/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package falk.php.scanner;

import com.buschmais.jqassistant.plugin.common.api.scanner.filesystem.FileResource;
import java.io.IOException;
import java.io.InputStream;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import falk.php.PhpLexer;
import falk.php.PhpParser;
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
        
//        Token t;
//       
//         while (( t = lexer.nextToken()).getType() != PhpLexer.EOF){
//             if (t.getText().trim().length() == 0){
//                 continue;
//             }
//             
//             System.out.println("--> " + t.getText());
//             System.out.println("1-> " + PhpLexer.tokenNames[t.getType()]);
//        }
        
        TokenStream tokenStream = new CommonTokenStream(lexer);
//        
//        
//        
        PhpParser parser = new PhpParser(tokenStream);
//        
//        
//        
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
