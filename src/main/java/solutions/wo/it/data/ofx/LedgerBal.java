package solutions.wo.it.data.ofx;

import javax.xml.bind.annotation.XmlElement;

public class LedgerBal {

    @XmlElement(name = "BALAMT")
    private String balAmt;

    @XmlElement(name = "DTASOF")
    private String dtAsOf;
}