package solutions.wo.it.data.ofx;

import javax.xml.bind.annotation.XmlElement;

public class CreditCardMsgsRsV1 {

    @XmlElement(name = "CCSTMTTRNRS")
    public CcStmtTrnRs ccStmtTrnRs;

}