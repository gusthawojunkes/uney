package solutions.wo.it.data.ofx;

import javax.xml.bind.annotation.XmlElement;

public class CcStmtRs {

    @XmlElement(name = "CURDEF")
    private String curDef;

    @XmlElement(name = "CCACCTFROM")
    private CcAcctFrom ccAcctFrom;

    @XmlElement(name = "BANKTRANLIST")
    public BankTranList bankTranList;

    @XmlElement(name = "LEDGERBAL")
    private LedgerBal ledgerBal;
}