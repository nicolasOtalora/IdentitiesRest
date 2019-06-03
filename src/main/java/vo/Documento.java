/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vo;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author white
 */
@XmlRootElement(name = "documento")
@XmlAccessorType(XmlAccessType.FIELD)

public class Documento implements Cloneable {

    private String nombre;
    private String url;
    private String hash;

    public Object clone() throws
            CloneNotSupportedException {
        return super.clone();
    }

    public Documento(String nombre, String url, String hash) {
        this.nombre = nombre;
        this.url = url;
        this.hash = hash;

    }

    public Documento() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

}
