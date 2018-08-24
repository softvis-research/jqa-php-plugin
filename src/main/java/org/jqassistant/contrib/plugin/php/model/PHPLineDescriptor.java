/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.model;

import com.buschmais.xo.neo4j.api.annotation.Label;

/**
 * line of file
 * @author falk
 */
@Label("Line")
public interface PHPLineDescriptor extends PHPDescriptor {

    /**
     * number of line
     * @return integer
     */
    int getLineNumber();
    void setLineNumber(int lineNumber);

    /**
     * text of line
     * @return string
     */
    String getText();
    void setText(String text);

}