/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.model;

import com.buschmais.xo.neo4j.api.annotation.Label;

@Label("Line")
public interface PHPLineDescriptor extends PHPDescriptor {

    int getLineNumber();
    void setLineNumber(int lineNumber);

    String getText();
    void setText(String text);

}