/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package falk.php.model;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import java.util.List;

/**
 *
 * @author falk
 */
@Label("Class")
public interface PHPClass extends PHPDescriptor {

    @Relation("EXTENDS")
    PHPClass getSuperClass();
    void setSuperClass(PHPClass superClass);

    String getName();
    void setName(String name);
    
    PHPNamespace getNamespace();
    void setNamespace(PHPNamespace namespace);

    @Relation("HAS")
    List<PHPFunction> getMethods();
    void setMethods(List<PHPFunction> methods);
    
    @Relation("HAS")
    List<PHPProperty> getProperties();
    void setProperties(List<PHPProperty> properties);
}
