package com.pagseguro.pagseguro.core;

import br.com.uol.pagseguro.plugpagservice.wrapper.*;

public class PaymentResult {
    private String transactionCode;
    private String transactionId;
    private String message;
    private int eventCode;

    private PlugPagTransactionResult transactionResult;

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setTransactionResult(PlugPagTransactionResult result) {
        this.transactionResult = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getEventCode() {
        return eventCode;
    }

    public void setEventCode(int eventCode) {
        this.eventCode = eventCode;
    }
}
