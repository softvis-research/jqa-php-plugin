/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.model;

import java.util.Set;

import com.buschmais.jqassistant.plugin.common.api.model.FileDescriptor;
import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import java.util.List;

@Label("File")
public interface PHPFileDescriptor extends PHPDescriptor, NamedDescriptor, FileDescriptor, PHPCalling {

    @Relation("HAS_LINE")
    Set<PHPLineDescriptor> getLines();
    
    @Relation("CONTAINS")
    List<PHPFunction> getFunctions();
    void setFunctions(List<PHPFunction> functions);
    
    @Relation("CONTAINS")
    List<PHPClass> getClasses();
    void setClasses(List<PHPClass> classes);
    
}