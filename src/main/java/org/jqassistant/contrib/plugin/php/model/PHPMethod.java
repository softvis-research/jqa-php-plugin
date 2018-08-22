/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.model;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import java.util.List;

/**
 *
 * @author falk
 */
@Label(value = "Method", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface PHPMethod extends PHPDescriptor, FullQualifiedNameDescriptor, PHPCalling {
    
    String getName();
    void setName(String name);
    
    @Property("linesOfCode")
    int getLinesOfCode();
    void setLinesOfCode(int linesOfCode);
    
    @Property("count_of_parameters")
    Integer getParametersCount();
    void setParametersCount(Integer parametersCount);
    
    @Property("visibility")
    VisibilityModifier getVisibility();
    void setVisibility(VisibilityModifier visibility);

    @Property("static")
    Boolean isStatic();
    void setStatic(Boolean s);
    
}
