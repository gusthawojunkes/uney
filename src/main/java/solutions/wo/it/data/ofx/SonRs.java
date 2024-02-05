package solutions.wo.it.data.ofx;

import javax.xml.bind.annotation.XmlElement;

public class SonRs {

    @XmlElement(name = "STATUS")
    private Status status;

    @XmlElement(name = "DTSERVER")
    private String dtServer;

    @XmlElement(name = "LANGUAGE")
    private String language;

}
