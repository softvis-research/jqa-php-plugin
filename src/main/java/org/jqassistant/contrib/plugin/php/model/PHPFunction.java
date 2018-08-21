/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.model;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Property;
import java.util.List;

/**
 *
 * @author falk
 */
@Label("Function")
public interface PHPFunction extends PHPDescriptor {
    
    String getName();
    void setName(String name);
      
    int getLinesOfCode();
    void setLinesOfCode(int linesOfCode);
    
    List<PHPFunctionParameter> getParameters();
    void setParameters(List<PHPFunctionParameter> parameters);
    
    List<PHPFunction> getCalls();
    void setCalls(List<PHPFunction> calls);
    
    @Property("visibility")
    VisibilityModifier getVisibility();
    void setVisibility(VisibilityModifier visibility);

    @Property("static")
    Boolean isStatic();
    void setStatic(Boolean s);
}
