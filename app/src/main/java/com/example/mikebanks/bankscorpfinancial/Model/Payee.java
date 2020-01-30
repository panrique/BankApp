package com.example.mikebanks.bankscorpfinancial.Model;

/**
 * Created by mikebanks on 2018-01-04.
 */

public class Payee {

    private String payeeID;
    private String payeeUsername;
    private long dbId;

    public Payee (String payeeID, String payeeUsername) {
        this.payeeID = payeeID;
        this.payeeUsername = payeeUsername;
    }

    public Payee (String payeeID, String payeeUsername, long dbId) {
        this(payeeID, payeeUsername);
        this.dbId = dbId;
    }


    public String getPayeeUsername() {
        return payeeUsername;
    }
    public String getPayeeID() { return payeeID; }

    public void setDbId(long dbId) {
        this.dbId = dbId;
    }

    public String toString() { return (payeeUsername + " (" + payeeID + ")"); }
}
