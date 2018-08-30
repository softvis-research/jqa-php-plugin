package org.jqassistant.contrib.plugin.php;

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

import org.jqassistant.contrib.plugin.php.model.PHPFileDescriptor;
import org.jqassistant.contrib.plugin.php.model.PHPLineDescriptor;
import org.jqassistant.contrib.plugin.php.model.PHPClassDescriptor;

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
        System.out.println("org.jqassistant.contrib.plugin.php.scanPHPFileTest()");
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
        
//        Set<PHPLineDescriptor> lines = phpFile.getLines();
//        assertThat(lines.size(), equalTo(9));
//        
//        PHPLineDescriptor row0 = lines.iterator().next();
//        assertThat(row0.getLineNumber(), equalTo(1));
//        assertThat(row0.getText(), equalTo("<?php"));
         
        store.commitTransaction();
    }
    
    @Test
    public void scanPHPParserTest() {
        System.out.println("org.jqassistant.contrib.plugin.php.scanPHPParserTest()");
         store.beginTransaction();
         
         ClassLoader classLoader = new PHPScannerTest().getClass().getClassLoader();
         File testFile = new File(classLoader.getResource("testfiles/class.php").getFile());
          System.out.println(testFile.getAbsolutePath());
          assertTrue (testFile.exists());
          
//        getScanner().scan(testFile, "/index.php", DefaultScope.NONE);
//         assertThat(getScanner().scan(testFile, "/index.php", DefaultScope.NONE), CoreMatchers.<Descriptor>instanceOf(PHPFileDescriptor.class));
         
        testFile = new File(classLoader.getResource("testfiles/class.php").getFile());
         assertThat(getScanner().scan(testFile, "/class.php", DefaultScope.NONE), CoreMatchers.<Descriptor>instanceOf(PHPFileDescriptor.class));

//        testFile = new File(classLoader.getResource("testfiles/impl_ext.php").getFile());
//         assertThat(getScanner().scan(testFile, "/impl_ext.php", DefaultScope.NONE), CoreMatchers.<Descriptor>instanceOf(PHPFileDescriptor.class));
         
//         testFile = new File(classLoader.getResource("testfiles/interface.php").getFile());
//         assertThat(getScanner().scan(testFile, "/interface.php", DefaultScope.NONE), CoreMatchers.<Descriptor>instanceOf(PHPFileDescriptor.class));
//         
//        testFile = new File(classLoader.getResource("testfiles/trait.php").getFile());
//         assertThat(getScanner().scan(testFile, "/trait.php", DefaultScope.NONE), CoreMatchers.<Descriptor>instanceOf(PHPFileDescriptor.class));
        
//        testFile = new File(classLoader.getResource("testfiles/challenge.php").getFile());
//         assertThat(getScanner().scan(testFile, "/challenge.php", DefaultScope.NONE), CoreMatchers.<Descriptor>instanceOf(PHPFileDescriptor.class));
         
        store.commitTransaction();
    }
     
    @Test
    public void StoreTest() {
         System.out.println("org.jqassistant.contrib.plugin.php.StoreTest()");
         store.beginTransaction();
         
         PHPClassDescriptor v = store.create(PHPClassDescriptor.class);
          store.create(PHPClassDescriptor.class);
         v.setFullQualifiedName("test");
         v.setName("blub");
         // AbstractPluginIT.TestResult testResult;
        
//         testResult = query("MATCH (PHPClass:PHP:Class) RETURN PHPClass");
//        List<PHPClass> phpClasses= testResult.getColumn("PHPClass");
//        
//         System.out.println(phpClasses.size());
         
//         for( Iterator<PHPClass> it = phpClasses.iterator(); it.hasNext(); )
//        {
//            PHPClass b = it.next();
//            System.out.println(b.getFullQualifiedName());
//        }
         
        //System.out.println("test find");
        PHPClassDescriptor f = store.find(PHPClassDescriptor.class, "test");
         assertThat(f.getName(), equalTo("blub"));
        
        store.commitTransaction();
    }
}
