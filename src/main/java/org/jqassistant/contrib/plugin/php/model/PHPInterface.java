/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.model;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import java.util.List;

/**
 *
 * @author falk
 */
@Label("Interface")
public interface PHPInterface extends PHPDescriptor, PHPType {

    @Relation("EXTENDS")
    PHPClass getSuperClass();
    void setSuperClass(PHPClass superClass);

    PHPNamespace getNamespace();
    void setNamespace(PHPNamespace namespace);

    @Relation("IMPEMENTS")
    List<PHPInterface> getInterfaces();
    void setInterfaces(List<PHPInterface> interfaces);
}