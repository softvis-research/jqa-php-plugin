/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.model;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;

/**
 * php namespace
 * @author falk
 */
@Label(value = "Namespace", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface PHPNamespaceDescriptor extends PHPDescriptor, FullQualifiedNameDescriptor {
    
    String getName();
    void setName(String name);
    
    /**
     * parent namespace
     * @return namespace
     */
    @Relation("CONTAINS")
    PHPNamespaceDescriptor getParent();
    void setParent(PHPNamespaceDescriptor parent);
    
}
