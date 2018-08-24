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
 * PHP Type
 * @author falk
 */
@Label(value = "Type", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface PHPTypeDescriptor extends PHPDescriptor, FullQualifiedNameDescriptor {
    
    String getName();
    void setName(String name);
    
    /**
     * methods
     * @return list of methods
     */
    @Relation("HAS_METHODS")
    List<PHPMethodDescriptor> getMethods();
    void setMethods(List<PHPMethodDescriptor> methods);
    
    /**
     * properties
     * @return list of properties
     */
    @Relation("HAS_PROPERTIES")
    List<PHPPropertyDescriptor> getProperties();
    void setProperties(List<PHPPropertyDescriptor> properties);
    
    /**
     * used super class
     * @return class
     */
    @Relation("EXTENDS")
    PHPClassDescriptor getSuperClass();
    void setSuperClass(PHPClassDescriptor superClass);

    /**
     * used namespace
     * @return namespace
     */
    @Relation("HAS_NAMESPACES")
    PHPNamespaceDescriptor getNamespace();
    void setNamespace(PHPNamespaceDescriptor namespace);

    /**
     * used interfaces
     * @return list of interfaces
     */
    @Relation("IMPEMENTS")
    List<PHPInterfaceDescriptor> getInterfaces();
    void setInterfaces(List<PHPInterfaceDescriptor> interfaces);
}