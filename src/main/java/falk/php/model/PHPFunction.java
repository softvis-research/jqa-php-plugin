/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package falk.php.model;

import com.buschmais.xo.neo4j.api.annotation.Label;
import java.util.List;

/**
 *
 * @author falk
 */
@Label("Function")
public interface PHPFunction extends PHPDescriptor {
    
    String getName();
    void setName(String name);
      
    PHPClass getPHPClass();
    void setPHPClass(PHPClass name);
    
    int getLinesOfCode();
    void setLinesOfCode(int linesOfCode);
    
    PHPFileDescriptor getFile();
    void setFile(PHPFileDescriptor file);
    
    List<PHPFunction> getCalls();
    void setCalls(List<PHPFunction> calls);
}
