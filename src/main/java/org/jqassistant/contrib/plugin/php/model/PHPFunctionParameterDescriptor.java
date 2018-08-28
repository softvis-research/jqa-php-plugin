/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.model;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

/**
 * Parameter of function
 * @author falk
 */
@Label("FunctionParameter")
public interface PHPFunctionParameterDescriptor extends PHPDescriptor{
    
    @Property("index")
    int getIndex();

    void setIndex(int index);
    
    String getName();
    void setName(String name);
    
}
