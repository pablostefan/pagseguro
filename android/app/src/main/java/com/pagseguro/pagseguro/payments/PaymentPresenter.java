package com.pagseguro.pagseguro.payments;

import com.google.gson.*;
import com.pagseguro.pagseguro.core.*;

import javax.inject.*;

import io.flutter.plugin.common.*;
import io.reactivex.disposables.*;
import br.com.uol.pagseguro.plugpagservice.wrapper.*;
import io.reactivex.android.schedulers.*;
import io.reactivex.schedulers.*;
import io.reactivex.*;

public class PaymentPresenter {
    private PaymentUseCase paymentUseCase;
    private PaymentFragment paymentFragment;
    private Disposable disposable;

    private int countPassword = 0;

    @Inject
    public PaymentPresenter(PlugPag plugPag, MethodChannel channel) {
        paymentUseCase = new PaymentUseCase(plugPag);
        paymentFragment = new PaymentFragment(channel);
    }

    public void creditPayment(int value) {
        doAction(paymentUseCase.doCreditPayment(value), value);
    }

    public void debitPayment(int value) {
        doAction(paymentUseCase.doDebitPayment(value), value);
    }


    private void doAction(Observable<PaymentResult> action, int value) {
        disposable = action
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(paymentFragment::onTransactionSuccess)
                .doFinally(paymentFragment::disposeDialog)
                .subscribe((PaymentResult result) -> {
                            writeToFile(result);
                            validateEventCode(result, value);
                            checkResponse(result);
                        },
                        throwable -> paymentFragment.onError(throwable.getMessage()));
    }

    public void abortTransaction() {
        disposable = paymentUseCase.abort()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void getLastTransaction() {
        disposable = paymentUseCase.getLastTransaction()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(actionResult -> {
                            String response;
                            response = new Gson().toJson(actionResult);
                            paymentFragment.onTransactionInfo(actionResult.getTransactionCode(), actionResult.getTransactionId(), response);
                        },
                        throwable -> paymentFragment.onError(throwable.getMessage()));
    }

    private void checkResponse(PaymentResult result) {
        String response = new Gson().toJson(result);
        paymentFragment.onFinishedResponse(response);
    }

    private void validateEventCode(PaymentResult result, int value) {
        if (result.getEventCode() == PlugPagEventData.EVENT_CODE_NO_PASSWORD ||
                result.getEventCode() == PlugPagEventData.EVENT_CODE_DIGIT_PASSWORD) {
            paymentFragment.onMessage(checkMessagePassword(result.getEventCode(), value));
        } else {
            paymentFragment.onMessage(checkMessage(result.getMessage()));
        }
    }

    private void writeToFile(PaymentResult result) {
        if (result.getTransactionCode() != null && result.getTransactionId() != null) {
            String response;
            response = new Gson().toJson(result);
            paymentFragment.writeToFile(result.getTransactionCode(), result.getTransactionId(), response);
        }
    }

    private String checkMessage(String message) {
        if (message != null && message.contains("SENHA")) {
            String[] strings = message.split("SENHA");
            return strings[0].trim();
        }

        return message;
    }

    private String checkMessagePassword(int eventCode, int value) {
        StringBuilder strPassword = new StringBuilder();

        if (eventCode == PlugPagEventData.EVENT_CODE_DIGIT_PASSWORD) {
            countPassword++;
        }
        if (eventCode == PlugPagEventData.EVENT_CODE_NO_PASSWORD) {
            countPassword = 0;
        }

        for (int count = countPassword; count > 0; count--) {
            strPassword.append("*");
        }
        return String.format("VALOR: %.2f\nSENHA: %s", (value / 100.0), strPassword);
    }
}
