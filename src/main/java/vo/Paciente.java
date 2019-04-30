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
@XmlRootElement(name = "employee")
@XmlAccessorType(XmlAccessType.FIELD)
public class Paciente {

    public Paciente(String fName, String sName, String address, String birth, String telephone, String medicare, String status) {
        this.fName = fName;
        this.sName = sName;
        this.address = address;
        this.birth = birth;
        this.telephone = telephone;
        this.medicare = medicare;
        this.status = status;
    }

    public Paciente() {
    }
    
    
    private String fName;
    private String sName;
    private String address;
    private String birth;
    private String telephone;
    private String medicare;
    private String status;

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMedicare() {
        return medicare;
    }

    public void setMedicare(String medicare) {
        this.medicare = medicare;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
