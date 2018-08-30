/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.model;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

/**
 * php class property
 * @author falk
 */
@Label("Field")
public interface PHPPropertyDescriptor extends PHPDescriptor  {

    String getName();
    void setName(String name);
    
    /**
     * visibility modifier
     * @return visibility modifier
     */
    @Property("visibility")
    VisibilityModifier getVisibility();
    void setVisibility(VisibilityModifier visibility);
    
    /**
     * is property static
     * @return boolean
     */
    @Property("static")
    Boolean isStatic();
    void setStatic(Boolean s);
    
    /**
     * Number of line in source file
     * @return integer 
     */
    @Property("lineNumber")
    int getLineNumber();
    void setLineNumber(int lineNumber);
   
}