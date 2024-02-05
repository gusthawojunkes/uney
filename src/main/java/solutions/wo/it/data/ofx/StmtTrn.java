package solutions.wo.it.data.ofx;

import javax.xml.bind.annotation.XmlElement;

public class StmtTrn {

    @XmlElement(name = "TRNTYPE")
    public String trnType;

    @XmlElement(name = "DTPOSTED")
    public String dtPosted;

    @XmlElement(name = "TRNAMT")
    public String trnAmt;

    @XmlElement(name = "FITID")
    public String fitId;

    @XmlElement(name = "MEMO")
    public String memo;

}