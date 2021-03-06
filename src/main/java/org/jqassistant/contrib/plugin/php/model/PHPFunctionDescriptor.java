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
@Label(value = "Method", usingIndexedPropertyOf = FullQualifiedNameDescriptor.class)
public interface PHPFunctionDescriptor extends PHPDescriptor, FullQualifiedNameDescriptor {
    
    String getName();
    void setName(String name);
    
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
    @Property("firstLineNumber")
    int getFirstLineNumber();
    void setFirstLineNumber(int firstLineNumber);
    
    /**
     * Number of last line in source file
     * @return integer 
     */
    @Property("lastLineNumber")
    int getLastLineNumber();
    void setLastLineNumber(int lastLineNumber);
    
    
    /**
     * count of lines with commands
     * @return integer 
     */
    @Property("effectiveLineCount")
    int getEffectiveLineCount();
    void setEffectiveLineCount(int effectiveLineCount);
    
    /**
     * Signature of function
     * @return integer 
     */
    @Property("signature")
    String getSignature();
    void setSignature(String signature);
    

}
