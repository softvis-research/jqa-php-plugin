/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package falk.php.model;

import com.buschmais.xo.neo4j.api.annotation.Label;

/**
 *
 * @author falk
 */
@Label("Namespace")
public interface PHPNamespace extends PHPDescriptor {
    
    String getName();
    void setName(String name);
    
    PHPNamespace getParent();
    void setParent(PHPNamespace parent);
    
}
