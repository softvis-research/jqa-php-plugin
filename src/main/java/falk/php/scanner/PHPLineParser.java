/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package falk.php.scanner;

import com.buschmais.jqassistant.core.store.api.Store;
import falk.php.model.PHPFileDescriptor;
import falk.php.model.PHPLineDescriptor;

class PHPLineParser {
    private final Store store;
    private final PHPFileDescriptor plaintextFileDescriptor;

    private int lineNumber = 1;

    PHPLineParser(final Store store, final PHPFileDescriptor plaintextFileDescriptor) {
        this.store = store;
        this.plaintextFileDescriptor = plaintextFileDescriptor;
    }

    void parseLine(final String line) {
        PHPLineDescriptor plaintextLineDescriptor = store.create(PHPLineDescriptor.class);
        plaintextLineDescriptor.setLineNumber(lineNumber++);
        plaintextLineDescriptor.setText(line);
        plaintextFileDescriptor.getLines().add(plaintextLineDescriptor);
    }
}