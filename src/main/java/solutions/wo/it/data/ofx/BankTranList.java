package solutions.wo.it.data.ofx;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

public class BankTranList {

    @XmlElement(name = "DTSTART")
    private String dtStart;

    @XmlElement(name = "DTEND")
    private String dtEnd;

    @XmlElementWrapper(name = "STMTTRN")
    @XmlElement(name = "STMTTRN")
    public List<StmtTrn> stmtTrnList;
}
