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
    
    /**
     * Return the super class.
     *
     * @return The super class.
     */
    @Relation("EXTENDS")
    PHPClass getSuperClass();

    /**
     * Set the super class.
     *
     * @param superClass
     *            The super class.
     */
    void setSuperClass(PHPClass superClass);

    String getName();
    void setName(String name);
    
    PHPFileDescriptor getFile();
    void setFile(PHPFileDescriptor file);
    
    PHPNamespace getNamespace();
    void setNamespace(PHPNamespace namespace);


    List<PHPFunction> getMethods();
    void setMethods(List<PHPFunction> methods);
    
    List<PHPProperty> getProperties();
    void setProperties(List<PHPProperty> properties);
}
