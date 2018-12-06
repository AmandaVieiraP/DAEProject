package dtos;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Template")
@XmlAccessorType(XmlAccessType.FIELD)
public class TemplateDTO  implements Serializable{
    
    private int code;
    private String description;
    private String state;
    private int software_code;
    private int contract_code;
    private String version;
    private String artefactsRepository;

    public TemplateDTO() {
    }

    public TemplateDTO(int code, String description, String state, int software_code, int contract_code, String version, String artefactsRepository) {
        this.code = code;
        this.description = description;
        this.state = state;
        this.software_code = software_code;
        this.contract_code = contract_code;
        this.version = version;
        this.artefactsRepository = artefactsRepository;
    }
    
    public void reset () {
        setCode(0);
        setDescription(null);
        setState(null);
        setSoftware_code(0);
        setContract_code(0);
        setVersion(null);
        setArtefactsRepository(null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getSoftware_code() {
        return software_code;
    }

    public void setSoftware_code(int software_code) {
        this.software_code = software_code;
    }

    public int getContract_code() {
        return contract_code;
    }

    public void setContract_code(int contract_code) {
        this.contract_code = contract_code;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getArtefactsRepository() {
        return artefactsRepository;
    }

    public void setArtefactsRepository(String artefactsRepository) {
        this.artefactsRepository = artefactsRepository;
    } 
}
