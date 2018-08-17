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
@Label("Property")
public interface PHPProperty extends PHPDescriptor  {

    enum AccessIdentifiers
        {
           PROTECTED ,PUBLIC, PRIVATE;
        }
    
    String getName();
    void setName(String name);
    
    AccessIdentifiers getAccess();
    void setAccess(AccessIdentifiers parent);

    
}
