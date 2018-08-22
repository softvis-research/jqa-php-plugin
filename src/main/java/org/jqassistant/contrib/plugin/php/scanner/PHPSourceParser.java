/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.scanner;

import com.buschmais.jqassistant.core.store.api.Store;
import com.buschmais.jqassistant.plugin.common.api.scanner.filesystem.FileResource;
import java.io.IOException;
import java.io.InputStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jqassistant.contrib.plugin.php.PhpLexer;
import org.jqassistant.contrib.plugin.php.PhpParser;
import org.jqassistant.contrib.plugin.php.model.PHPFileDescriptor;
import org.jqassistant.contrib.plugin.php.model.PHPLineDescriptor;
import org.jqassistant.contrib.plugin.php.scanner.parser.PHPFileParser;
import org.jqassistant.contrib.plugin.php.scanner.parser.PHPParserHelper;

/**
 *
 * @author falk
 */
public class PHPSourceParser {
    private final Store store;
    private final PHPFileDescriptor fileDescriptor;

    private int lineNumber = 1;

    PHPSourceParser(final Store store, final PHPFileDescriptor fileDescriptor) {
        this.store = store;
        this.fileDescriptor = fileDescriptor;
    }

    void parseFile(final FileResource item) {
        try {
         
            InputStream inputStream = item.createStream();
            Lexer lexer = new PhpLexer(CharStreams.fromStream(inputStream));
            TokenStream tokenStream = new CommonTokenStream(lexer);
            PhpParser parser = new PhpParser(tokenStream); 
            //parser.setBuildParseTree(true);      
            ParseTree tree = parser.htmlDocument();
            //System.err.println(tree.toStringTree(parser));
            
            PHPFileParser a = new PHPFileParser(store, this.fileDescriptor);
            a.parse(tree);
        
        } catch (IOException e) {
         e.printStackTrace();
         System.out.println(e);
      }
    }
    
   
}
