package falk.php;

import com.buschmais.jqassistant.core.scanner.api.DefaultScope;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.jqassistant.plugin.common.test.AbstractPluginIT;
import java.io.File;
import java.util.List;
import java.util.Set;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import falk.php.model.PHPDirectoryDescriptor;
import falk.php.model.PHPFileDescriptor;
import falk.php.model.PHPLineDescriptor;
import falk.php.scanner.PHPLexer;
import java.io.InputStream;

/**
 * Unit test for simple App.
 */
public class PHPScannerTest extends AbstractPluginIT
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
    
    
    @Test
    public void scanPHPFileTest() {
        System.out.println("falk.PHPScannerPluginTest.scanPHPFileTest()");
         store.beginTransaction();
         
         ClassLoader classLoader = new PHPScannerTest().getClass().getClassLoader();
         File testFile = new File(classLoader.getResource("testfiles/index.php").getFile());
          System.out.println(testFile.getAbsolutePath());
          assertTrue (testFile.exists());
          
        //getScanner().scan(testFile, "/index.php", DefaultScope.NONE);
         assertThat(getScanner().scan(testFile, "/index.php", DefaultScope.NONE), CoreMatchers.<Descriptor>instanceOf(PHPFileDescriptor.class));
         
        AbstractPluginIT.TestResult testResult = query("MATCH (PHPFile:PHP:File) RETURN PHPFile");
        List<PHPFileDescriptor> phpFiles = testResult.getColumn("PHPFile");
        assertThat(phpFiles.size(), equalTo(1));
        
        PHPFileDescriptor phpFile = phpFiles.get(0);
        assertThat(phpFile.getFileName(), equalTo("/index.php"));
        
        Set<PHPLineDescriptor> lines = phpFile.getLines();
        assertThat(lines.size(), equalTo(9));
        
        PHPLineDescriptor row0 = lines.iterator().next();
        assertThat(row0.getLineNumber(), equalTo(1));
        assertThat(row0.getText(), equalTo("<?php"));
         
        store.commitTransaction();
    }
    
    @Test
    public void scanPHPLexerTest() {
        ClassLoader classLoader = new PHPLexer().getClass().getClassLoader();
        //File testFile = new File(classLoader.getResource("testfiles/index.php").getFile());
        InputStream inputStream = classLoader.getResourceAsStream("testfiles/index.php");
       
        
        falk.php.scanner.PHPLexer l = new falk.php.scanner.PHPLexer();
        l.test(inputStream);
    }
    
     
}
