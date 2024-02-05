package solutions.wo.it.data.ofx;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "OFX")
public class OFXFile {
    @XmlElement(name = "OFXHEADER")
    private String header;

    @XmlElement(name = "DATA")
    private String data;

    @XmlElement(name = "VERSION")
    private String version;

    @XmlElement(name = "SECURITY")
    private String security;

    @XmlElement(name = "ENCODING")
    private String encoding;

    @XmlElement(name = "CHARSET")
    private String charset;

    @XmlElement(name = "COMPRESSION")
    private String compression;

    @XmlElement(name = "OLDFILEUID")
    private String oldFileUid;

    @XmlElement(name = "NEWFILEUID")
    private String newFileUid;

    @XmlElement(name = "SIGNONMSGSRSV1")
    private SignonMsgsRsV1 signonMsgsRsV1;

    @XmlElement(name = "CREDITCARDMSGSRSV1")
    public CreditCardMsgsRsV1 creditCardMsgsRsV1;

}
