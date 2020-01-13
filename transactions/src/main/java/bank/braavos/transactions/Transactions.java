package bank.braavos.transactions;

import org.springframework.stereotype.Component;


@Component
public class Transactions {

    // Unique identifier for the company
    private Integer companyId;
    private Integer bankId;
    private String accountId;
    private String transactionId;
    private String bookingDate;
    private String valueDate;
    private String description;
    private String currencyCode;
    private String type;
    private String amount;
    private String flow;

    public String getCategory() {
        return category;
    }

    public Transactions setCategory(String category) {
        this.category = category;
        return this;
    }

    private String category;


    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }


    public Integer getCompanyId() {
        return companyId;
    }

    public Transactions setCompanyId(Integer companyId) {
        this.companyId = companyId;
        return this;
    }

    public Integer getBankId() {
        return bankId;
    }

    public Transactions setBankId(Integer bankId) {
        this.bankId = bankId;
        return this;
    }

    public String getAccountId() {
        return accountId;
    }

    public Transactions setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Transactions setTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public Transactions setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Transactions setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public Transactions setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public String getType() {
        return type;
    }

    public Transactions setType(String type) {
        this.type = type;
        return this;
    }

    public String getAmount() {
        return amount;
    }

    public Transactions setAmount(String amount) {
        this.amount = amount;
        return this;
    }

 }
