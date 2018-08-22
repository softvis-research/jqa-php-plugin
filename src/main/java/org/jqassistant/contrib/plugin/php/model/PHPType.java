/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.model;
import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import java.util.List;
import com.buschmais.xo.neo4j.api.annotation.Relation.Incoming;
import com.buschmais.xo.neo4j.api.annotation.Relation.Outgoing;

/**
 *
 * @author falk
 */
@Label(value = "Type", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface PHPType extends PHPDescriptor, FullQualifiedNameDescriptor {
    
    String getName();
    void setName(String name);
    
    @Relation("HAS_METHODS")
    List<PHPFunction> getMethods();
    void setMethods(List<PHPFunction> methods);
    
    @Relation("HAS_PROPERTIES")
    List<PHPProperty> getProperties();
    void setProperties(List<PHPProperty> properties);
}