package solutions.wo.it.data.ofx;
import javax.xml.bind.annotation.XmlElement;

public class Status {
    @XmlElement(name = "CODE")
    private String code;

    @XmlElement(name = "SEVERITY")
    private String severity;

}
