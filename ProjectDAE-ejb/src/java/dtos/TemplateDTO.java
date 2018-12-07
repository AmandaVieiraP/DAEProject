package dtos;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Template")
@XmlAccessorType(XmlAccessType.FIELD)
public class TemplateDTO extends ConfigurationSuperDTO implements Serializable{

    public TemplateDTO() {
    }

    public TemplateDTO(int code, String description, int softwareCode, String softwareName, int contractCode, String version) {
        super(code, description, softwareCode, softwareName, contractCode, version);
    }
    
    @Override
    public void reset(){
        super.reset();
    }
    
}
