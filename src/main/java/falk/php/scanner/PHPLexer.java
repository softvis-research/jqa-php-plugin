/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package falk.php.scanner;

import java.io.IOException;
import java.io.InputStream;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import falk.php.PhpLexer;
import falk.php.PhpParser;
import java.util.HashSet;
import java.util.Set;


/**
 *
 * @author falk
 */
public class PHPLexer {
    
    public void test (InputStream inputStream) {
        try {
         
        
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
        
        } catch (IOException e) {
         e.printStackTrace();
         System.out.println(e);
      }
    }
    
}
