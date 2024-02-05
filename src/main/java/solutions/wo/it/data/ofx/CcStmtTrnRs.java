package solutions.wo.it.data.ofx;

import javax.xml.bind.annotation.XmlElement;

public class CcStmtTrnRs {

    @XmlElement(name = "TRNUID")
    private String trnUid;

    @XmlElement(name = "STATUS")
    private Status status;

    @XmlElement(name = "CCSTMTRS")
    public CcStmtRs ccStmtRs;
}
