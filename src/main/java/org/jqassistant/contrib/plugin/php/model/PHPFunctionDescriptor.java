/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.model;

import com.buschmais.jqassistant.core.store.api.model.FullQualifiedNameDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import java.util.List;

/**
 * php function
 * @author falk
 */
@Label(value = "Function", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface PHPFunctionDescriptor extends PHPDescriptor, FullQualifiedNameDescriptor {
    
    /**
     * used namespace
     * @return namespace 
     */
    @Relation("HAS_NAMESPACE")
    PHPNamespaceDescriptor getNamespace();
    void setNamespace(PHPNamespaceDescriptor namespace);
    
    String getName();
    void setName(String name);
    
    /**
     * count of lines
     * @return integer 
     */
    @Property("linesOfCode")
    int getLinesOfCode();
    void setLinesOfCode(int linesOfCode);
    
    /**
     * Return all declared parameters of this method.
     *
     * @return The declared parameters.
     */
    @Relation("HAS")
    List<PHPFunctionParameterDescriptor> getParameters();
    
    /**
     * visibility modifier
     * @return visibility modifier
     */
    @Property("visibility")
    VisibilityModifier getVisibility();
    void setVisibility(VisibilityModifier visibility);

    /**
     * is method static
     * @return boolean
     */
    @Property("static")
    Boolean isStatic();
    void setStatic(Boolean s);
    
    /**
     * Number of first line in source file
     * @return integer 
     */
    @Property("lineNumber")
    int getLineNumber();
    void setLineNumber(int lineNumber);

}
