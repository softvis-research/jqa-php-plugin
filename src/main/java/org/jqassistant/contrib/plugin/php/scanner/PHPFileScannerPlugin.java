/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buschmais.jqassistant.core.scanner.api.Scanner;
import com.buschmais.jqassistant.core.scanner.api.ScannerPlugin;
import com.buschmais.jqassistant.core.scanner.api.Scope;
import com.buschmais.jqassistant.core.store.api.Store;
import com.buschmais.jqassistant.plugin.common.api.model.FileDescriptor;
import com.buschmais.jqassistant.plugin.common.api.scanner.AbstractScannerPlugin;
import com.buschmais.jqassistant.plugin.common.api.scanner.filesystem.FileResource;
import org.jqassistant.contrib.plugin.php.model.PHPFileDescriptor;


import static java.util.Arrays.asList;

@ScannerPlugin.Requires(FileDescriptor.class)
public class PHPFileScannerPlugin extends AbstractScannerPlugin<FileResource, PHPFileDescriptor> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PHPFileScannerPlugin.class);
    public static final String JQASSISTANT_PLUGIN_PHP_SUFFIXES = "jqassistant.plugin.php.suffixes";

    private static List<String> suffixes = asList("php", "phtml");


    @Override
    public boolean accepts(final FileResource item, final String path, final Scope scope) throws IOException {
        int beginIndex = path.lastIndexOf(".");
        if(beginIndex > 0) {
            final String suffix = path.substring(beginIndex + 1).toLowerCase();

            boolean accepted = suffixes.contains(suffix);
            if(accepted) {
                LOGGER.info("Plaintext accepted path "+path);
            }

            return accepted;
        }

        return false;
    }

    @Override
    public PHPFileDescriptor scan(final FileResource item, final String path, final Scope scope, final Scanner scanner) throws IOException {
        final Store store = scanner.getContext().getStore();
		FileDescriptor fileDescriptor = scanner.getContext().getCurrentDescriptor();
		final PHPFileDescriptor phpFileDescriptor = store.addDescriptorType(fileDescriptor, PHPFileDescriptor.class);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(item.createStream()))) {
            final PHPLineParser pumlLineParser = new PHPLineParser(store, phpFileDescriptor);
            String line;
            while ((line = reader.readLine()) != null) {
                pumlLineParser.parseLine(line);
            }
        }

        return phpFileDescriptor;
    }

    @Override
    protected void configure() {
        super.configure();

        if(getProperties().containsKey(JQASSISTANT_PLUGIN_PHP_SUFFIXES)) {
            suffixes = new ArrayList<>();
            String serializedSuffixes = (String) getProperties().get(JQASSISTANT_PLUGIN_PHP_SUFFIXES);
            for (String suffix : serializedSuffixes.split(",")) {
                suffixes.add(suffix.toLowerCase().trim());
            }
        }

        LOGGER.info(String.format("php plugin looks for files with suffixes '%s'", suffixes.toString()));
    }

}