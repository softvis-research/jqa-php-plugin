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
@Label(value = "Function", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface PHPFunction extends PHPDescriptor, FullQualifiedNameDescriptor {
    
    String getName();
    void setName(String name);
    
    @Property("linesOfCode")
    int getLinesOfCode();
    void setLinesOfCode(int linesOfCode);
    
    @Relation("HAS_PARAMETERS")
    Integer getParametersCount();
    void setParametersCount(Integer parametersCount);
    
    @Relation("HAS_CALLS")
    List<PHPFunction> getCalls();
    void setCalls(List<PHPFunction> calls);
    
    @Property("visibility")
    VisibilityModifier getVisibility();
    void setVisibility(VisibilityModifier visibility);

    @Property("static")
    Boolean isStatic();
    void setStatic(Boolean s);
}
