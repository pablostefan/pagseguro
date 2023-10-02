package com.pagseguro.pagseguro.payments;

public interface PaymentContract {
    void onTransactionSuccess();

    void onError(String message);

    void onMessage(String message);

    void disposeDialog();

    void writeToFile(String transactionCode, String transactionId, String response);

    void onTransactionInfo(String transactionCode, String transactionId, String response);

    void onFinishedResponse(String message);

}
