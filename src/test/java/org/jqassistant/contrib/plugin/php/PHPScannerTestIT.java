package org.jqassistant.contrib.plugin.php;

import com.buschmais.jqassistant.core.scanner.api.DefaultScope;
import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.jqassistant.plugin.common.test.AbstractPluginIT;
import org.hamcrest.CoreMatchers;
import org.jqassistant.contrib.plugin.php.model.PHPClassDescriptor;
import org.jqassistant.contrib.plugin.php.model.PHPFileDescriptor;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


public class PHPScannerTestIT extends AbstractPluginIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(PHPScannerTestIT.class);


    @Test
    public void scanPHPFileTest() {
        LOGGER.info("START org.jqassistant.contrib.plugin.php.scanPHPFileTest()");
        store.beginTransaction();

        ClassLoader classLoader = new PHPScannerTestIT().getClass().getClassLoader();
        File testFile = new File(classLoader.getResource("testfiles/index.php").getFile());
        LOGGER.info(testFile.getAbsolutePath());
        assertTrue(testFile.exists());

        assertThat(getScanner().scan(testFile, "/index.php", DefaultScope.NONE), CoreMatchers.<Descriptor>instanceOf(PHPFileDescriptor.class));

        AbstractPluginIT.TestResult testResult = query("MATCH (PHPFile:PHP:File) RETURN PHPFile");
        List<PHPFileDescriptor> phpFiles = testResult.getColumn("PHPFile");
        assertThat(phpFiles.size(), equalTo(1));

        PHPFileDescriptor phpFile = phpFiles.get(0);
        assertThat(phpFile.getFileName(), equalTo("/index.php"));

        store.commitTransaction();

        LOGGER.info("END org.jqassistant.contrib.plugin.php.scanPHPFileTest()");
    }

    @Test
    public void scanPHPParserClassTest() {

        LOGGER.info("org.jqassistant.contrib.plugin.php.scanPHPParserClassTest()");
        store.beginTransaction();

        ClassLoader classLoader = new PHPScannerTestIT().getClass().getClassLoader();
        File testFile = new File(classLoader.getResource("testfiles/class.php").getFile());
        LOGGER.info(testFile.getAbsolutePath());
        assertTrue(testFile.exists());

        testFile = new File(classLoader.getResource("testfiles/class.php").getFile());
        assertThat(getScanner().scan(testFile, "/class.php", DefaultScope.NONE), CoreMatchers.<Descriptor>instanceOf(PHPFileDescriptor.class));

        AbstractPluginIT.TestResult testResult = query("MATCH (PHPClass:PHP:Class) RETURN PHPClass");
        List<PHPClassDescriptor> phpClasses = testResult.getColumn("PHPClass");
        assertThat(phpClasses.size(), equalTo(2));

        PHPClassDescriptor phpClass = phpClasses.get(0);
        assertThat(phpClass.getName(), equalTo("index"));
        assertThat(phpClass.getSuperClass().getName(), equalTo("d"));
        assertThat(phpClass.getInterfaces().size(), equalTo(2));

        store.commitTransaction();
    }

    @Test
    public void scanPHPParserSimpleTest() {

        LOGGER.info("org.jqassistant.contrib.plugin.php.scanPHPParserSimpleTest()");
        store.beginTransaction();

        ClassLoader classLoader = new PHPScannerTestIT().getClass().getClassLoader();
        File testFile = new File(classLoader.getResource("testfiles/class.php").getFile());
        LOGGER.info(testFile.getAbsolutePath());
        assertTrue(testFile.exists());

        testFile = new File(classLoader.getResource("testfiles/simple.php").getFile());
        assertThat(getScanner().scan(testFile, "/simple.php", DefaultScope.NONE), CoreMatchers.<Descriptor>instanceOf(PHPFileDescriptor.class));

        store.commitTransaction();
    }
}
