/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jqassistant.contrib.plugin.php.model;

import com.buschmais.xo.neo4j.api.annotation.Relation;
import java.util.List;

/**
 *
 * @author falk
 */
public interface PHPCalling{
    
    @Relation("CONTAINS")
    List<PHPMethod> getCalls();
    void setCalls(List<PHPMethod> calls);
    
}
