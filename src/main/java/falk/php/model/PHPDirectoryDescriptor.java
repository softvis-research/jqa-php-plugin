/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package falk.php.model;

import java.util.Set;

import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;

@Label("Directory")
public interface PHPDirectoryDescriptor extends PHPDescriptor, NamedDescriptor {

    @Relation("HAS_FILE")
    Set<PHPFileDescriptor> getFiles();

    @Relation("HAS_DIRECTORY")
    Set<PHPDirectoryDescriptor> getDirectories();

    PHPDirectoryDescriptor getParent();
    void setParent(PHPDirectoryDescriptor parent);
}
