/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author white
 */
@XmlRootElement(name = "permiso")
@XmlAccessorType(XmlAccessType.FIELD)

public class Permiso {
    
    private String duenio;
    private Documento [] documentos;

    public Permiso(String usuario, Documento[] documentos) {
        this.duenio = usuario;
        this.documentos = documentos;
    }
    public Permiso(){
        
    }

    public String getUsuario() {
        return duenio;
    }

    public void setUsuario(String usuario) {
        this.duenio = usuario;
    }

    public Documento[] getDocumentos() {
        return documentos;
    }

    public void setDocumentos(Documento[] documentos) {
        this.documentos = documentos;
    }
    
    
    
    
}
