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

/**
 *
 * @author falk
 */
@Label(value = "Class", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface PHPClass extends PHPDescriptor, PHPType {

    @Relation("EXTENDS")
    PHPClass getSuperClass();
    void setSuperClass(PHPClass superClass);

    @Relation("HAS_NAMESPACES")
    PHPNamespace getNamespace();
    void setNamespace(PHPNamespace namespace);

    @Relation("IMPEMENTS")
    List<PHPInterface> getInterfaces();
    void setInterfaces(List<PHPInterface> interfaces);
}
