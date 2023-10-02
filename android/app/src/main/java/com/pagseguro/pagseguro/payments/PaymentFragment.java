package com.pagseguro.pagseguro.payments;

import java.util.*;
import io.flutter.plugin.common.*;

public class PaymentFragment implements PaymentContract {

    final MethodChannel channel;

    public PaymentFragment(MethodChannel channel) {
        this.channel = channel;
    }

    private static final String ON_TRANSACTION_SUCCESS = "onTransactionSuccess";
    private static final String ON_ERROR = "onError";
    private static final String ON_MESSAGE = "onMessage";
    private static final String ON_FINISHED_RESPONSE = "onFinishedResponse";
    private static final String WRITE_TO_FILE = "writeToFile";
    private static final String DISPOSE_DIALOG = "disposeDialog";
    private static final String ON_TRANSACTION_INFO = "onTransactionInfo";

    @Override
    public void onTransactionSuccess() {
        this.channel.invokeMethod(ON_TRANSACTION_SUCCESS, true);
    }

    @Override
    public void onError(String message) {
        this.channel.invokeMethod(ON_ERROR, message);
    }

    @Override
    public void onMessage(String message) {
        this.channel.invokeMethod(ON_MESSAGE, message);
    }

    @Override
    public void onFinishedResponse(String message) {
        this.channel.invokeMethod(ON_FINISHED_RESPONSE, message);
    }

    @Override
    public void disposeDialog() {
        this.channel.invokeMethod(DISPOSE_DIALOG, true);
    }

    @Override
    public void writeToFile(String transactionCode, String transactionId, String response) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("transactionCode", transactionCode);
        map.put("transactionId", transactionId);
        map.put("response", response);
        this.channel.invokeMethod(WRITE_TO_FILE, map);
    }

    @Override
    public void onTransactionInfo(String transactionCode, String transactionId, String response) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("transactionCode", transactionCode);
        map.put("transactionId", transactionId);
        map.put("response", response);
        this.channel.invokeMethod(ON_TRANSACTION_INFO, map);
    }
}
