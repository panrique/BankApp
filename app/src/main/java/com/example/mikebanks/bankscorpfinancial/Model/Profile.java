package com.example.mikebanks.bankscorpfinancial.Model;

import java.util.ArrayList;

/**
 * Created by mikebanks on 2017-12-05.
 */
public class Profile {

    private String firstName;
    private String lastName;
    private String email;
    private String country;
    private String username;
    private String password;
    private ArrayList<Account> accounts;
    private ArrayList<Payee> payees;
    private long dbId;

    public Profile() {

    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public void setPayees(ArrayList<Payee> payees) {
        this.payees = payees;
    }

    public Profile (String firstName, String lastName, String email, String country, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
		this.email = email;
        this.country = country;
        this.username = username;
        this.password = password;
        accounts = new ArrayList<>();
        payees = new ArrayList<>();
    }

    public Profile (String firstName, String lastName, String email, String country, String username, String password, long dbId) {
        this(firstName, lastName, email, country, username, password);
        this.dbId = dbId;
    }

    /**
     * getters used to access the private fields of the profile
     */
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
	public String getEmail() {
        return email;
    }
    public String getCountry() {
        return country;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public ArrayList<Account> getAccounts() { return accounts; }
    public ArrayList<Payee> getPayees() { return payees; }
    public long getDbId() { return dbId; }
    public void setDbId(long dbId) { this.dbId = dbId; }

    public void addAccount(String accountName, double accountBalance) {
        String accNo = "A" + (accounts.size() + 1);
        Account account = new Account(accountName, accNo, accountBalance);
        accounts.add(account);
    }
    public void setAccountsFromDB(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public void addTransferTransaction(Account sendingAcc, Account receivingAcc, double transferAmount) {

        sendingAcc.setAccountBalance(sendingAcc.getAccountBalance() - transferAmount);
        receivingAcc.setAccountBalance(receivingAcc.getAccountBalance() + transferAmount);

        int sendingAccTransferCount = 0;
        int receivingAccTransferCount = 0;
        for (int i = 0; i < sendingAcc.getTransactions().size(); i ++) {
            if (sendingAcc.getTransactions().get(i).getTransactionType() == Transaction.TRANSACTION_TYPE.TRANSFER) {
                sendingAccTransferCount++;
            }
        }
        for (int i = 0; i < receivingAcc.getTransactions().size(); i++) {
            if (receivingAcc.getTransactions().get(i).getTransactionType() == Transaction.TRANSACTION_TYPE.TRANSFER) {
                receivingAccTransferCount++;
            }
        }

        sendingAcc.getTransactions().add(new Transaction("T" + (sendingAcc.getTransactions().size() + 1) + "-T" + (sendingAccTransferCount+1), sendingAcc.toTransactionString(), receivingAcc.toTransactionString(), transferAmount));
        receivingAcc.getTransactions().add(new Transaction("T" + (receivingAcc.getTransactions().size() + 1) + "-T" + (receivingAccTransferCount+1), sendingAcc.toTransactionString(), receivingAcc.toTransactionString(), transferAmount));
    }

    public void addPayee(String payeeEmail) {
        String payeeID = "P" + (payees.size() + 1);
        Payee payee = new Payee(payeeID, payeeEmail);
        payees.add(payee);
    }

    public void setPayeesFromDB(ArrayList<Payee> payees) {
        this.payees = payees;
    }
}
