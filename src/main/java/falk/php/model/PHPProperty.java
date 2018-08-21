/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package falk.php.model;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;

/**
 *
 * @author falk
 */
@Label("Property")
public interface PHPProperty extends PHPDescriptor  {

    String getName();
    void setName(String name);
    
    @Property("visibility")
    VisibilityModifier getVisibility();
    void setVisibility(VisibilityModifier visibility);

    @Property("static")
    Boolean isStatic();
    void setStatic(Boolean s);
    
}