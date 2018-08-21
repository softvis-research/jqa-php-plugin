/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package falk.php.model;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;

/**
 *
 * @author falk
 */
@Label("Namespace")
public interface PHPNamespace extends PHPDescriptor {
    
    String getName();
    void setName(String name);
    
    @Relation("CONTAINS")
    PHPNamespace getParent();
    void setParent(PHPNamespace parent);
    
}
