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
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User {
    private String usuario;
    private String contrasena;
    private Documento [] documentos;
    private Permiso [] permisos;

    public Documento[] getDocumentos() {
        return documentos;
    }

    public void setDocumentos(Documento[] documentos) {
        this.documentos = documentos;
    }

    public Permiso[] getPermisos() {
        return permisos;
    }

    public void setPermisos(Permiso[] permisos) {
        this.permisos = permisos;
    }

    public User(String usuario, String contrasena, Documento[] documentos, Permiso[] permisos) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.documentos = documentos;
        this.permisos = permisos;
    }
    public User (){
        
    }

    
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    

    
}
