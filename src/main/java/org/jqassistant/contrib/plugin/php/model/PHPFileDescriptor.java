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

/**
 * PHP file descriptor
 * @author falk
 */
@Label("File")
public interface PHPFileDescriptor extends PHPDescriptor, NamedDescriptor, FileDescriptor {

    /**
     * lines of code
     * @return set of lines
     */
    @Relation("HAS_LINE")
    Set<PHPLineDescriptor> getLines();
    
    /**
     * included functions
     * @return list of functions 
     */
    @Relation("CONTAINS")
    List<PHPFunctionDescriptor> getFunctions();
    void setFunctions(List<PHPFunctionDescriptor> functions);
    
    /**
     * included classes
     * @return list of classes
     */
    @Relation("CONTAINS")
    List<PHPTypeDescriptor> getClasses();
    void setClasses(List<PHPTypeDescriptor> classes);
    
}