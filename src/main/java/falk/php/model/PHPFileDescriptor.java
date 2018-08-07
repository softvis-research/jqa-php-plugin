/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package falk.php.model;

import java.util.Set;

import com.buschmais.jqassistant.plugin.common.api.model.FileDescriptor;
import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Relation;

public interface PHPFileDescriptor extends PHPDescriptor, NamedDescriptor, FileDescriptor {

    @Relation("HAS_LINE")
    Set<PHPLineDescriptor> getLines();
}