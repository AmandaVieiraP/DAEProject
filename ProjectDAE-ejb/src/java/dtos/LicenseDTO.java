/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Iolanda
 */
@XmlRootElement(name = "License")
@XmlAccessorType(XmlAccessType.FIELD)
public class LicenseDTO implements Serializable {

    private int code;
    private String licenceValue;
    private int module_code;

    public LicenseDTO() {
    }

    public LicenseDTO(int code, String licenceValue, int module_code) {
        this.code = code;
        this.licenceValue = licenceValue;
        this.module_code = module_code;
    }

    public void reset() {
        setCode(0);
        setLicenceValue(null);
        setModule_code(0);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLicenceValue() {
        return licenceValue;
    }

    public void setLicenceValue(String licenceValue) {
        this.licenceValue = licenceValue;
    }

    public int getModule_code() {
        return module_code;
    }

    public void setModule_code(int module_code) {
        this.module_code = module_code;
    }

}
