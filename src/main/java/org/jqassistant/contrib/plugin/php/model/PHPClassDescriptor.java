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
 * PHP Class Descriptor
 * @author falk
 */
@Label(value = "Class", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface PHPClassDescriptor extends PHPDescriptor, PHPTypeDescriptor {
    
    /**
     * is abstract class
     * @return BOOLEAN
     */
    @Property("abstract")
    Boolean isAbstract();
    void setAbstract(Boolean isAbstract);
    
    /**
     * used traits
     * @return List of used traits 
     */
    @Relation("USE")
    List<PHPTraitDescriptor> getTraits();
    void setTraits(List<PHPTraitDescriptor> traits);
}
