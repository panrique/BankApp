package com.example.mikebanks.bankscorpfinancial.Model;

/**
 * Created by mikebanks on 2018-01-04.
 */

public class Payee {

    private String payeeID;
    private String payeeEmail;
    private long dbId;

    public Payee (String payeeID, String payeeEmail) {
        this.payeeID = payeeID;
        this.payeeEmail = payeeEmail;
    }

    public Payee (String payeeID, String payeeEmail, long dbId) {
        this(payeeID, payeeEmail);
        this.dbId = dbId;
    }


    public String getPayeeEmail() {
        return payeeEmail;
    }
    public String getPayeeID() { return payeeID; }

    public void setDbId(long dbId) {
        this.dbId = dbId;
    }

    public String toString() { return (payeeEmail + " (" + payeeID + ")"); }
}
