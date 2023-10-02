package com.pagseguro.pagseguro.payments;

import br.com.uol.pagseguro.plugpagservice.wrapper.*;
import br.com.uol.pagseguro.plugpagservice.wrapper.exception.*;
import io.reactivex.*;

import com.pagseguro.pagseguro.core.*;

public class PaymentUseCase {
    private final PlugPag plugPag;

    public PaymentUseCase(PlugPag plugPag) {
        this.plugPag = plugPag;
    }

    public static final String USER_REFERENCE = "APPDEMO";

    public Observable<PaymentResult> doCreditPayment(int value) {
        return doPayment(new PlugPagPaymentData(
                PlugPag.TYPE_CREDITO,
                value,
                PlugPag.INSTALLMENT_TYPE_A_VISTA,
                1,
                USER_REFERENCE));
    }

    public Observable<PaymentResult> doDebitPayment(int value) {
        return doPayment(new PlugPagPaymentData(
                PlugPag.TYPE_DEBITO,
                value,
                PlugPag.INSTALLMENT_TYPE_A_VISTA,
                1,
                USER_REFERENCE));
    }

    public Observable<PaymentResult> getLastTransaction() {
        return Observable.create(emitter -> {
            PaymentResult actionResult = new PaymentResult();
            PlugPagTransactionResult result = plugPag.getLastApprovedTransaction();
            sendResponse(emitter, result, actionResult);
        });
    }

    private Observable<PaymentResult> doPayment(final PlugPagPaymentData paymentData) {
        return Observable.create(emitter -> {
            PaymentResult result = new PaymentResult();
            setListener(emitter, result);
            PlugPagTransactionResult plugPagTransactionResult = plugPag.doPayment(paymentData);
            sendResponse(emitter, plugPagTransactionResult, result);
        });
    }

    private void setListener(ObservableEmitter<PaymentResult> emitter, PaymentResult result) {
        plugPag.setEventListener(plugPagEventData -> {
            result.setEventCode(plugPagEventData.getEventCode());
            result.setMessage(plugPagEventData.getCustomMessage());
            emitter.onNext(result);
        });
    }


    private void sendResponse(
            ObservableEmitter<PaymentResult> emitter,
            PlugPagTransactionResult plugPagTransactionResult,
            PaymentResult result
    ) {
        if (plugPagTransactionResult.getResult() != 0) {
            onError(emitter, plugPagTransactionResult);
        } else {
            onNext(emitter, result, plugPagTransactionResult);
        }
        emitter.onComplete();
    }

    private void onError(ObservableEmitter<PaymentResult> emitter, PlugPagTransactionResult plugPagTransactionResult) {
        assert plugPagTransactionResult.getMessage() != null : "Error";
        assert plugPagTransactionResult.getErrorCode() != null : "Error";
        emitter.onError(new PlugPagException(plugPagTransactionResult.getMessage(), plugPagTransactionResult.getErrorCode()));
    }


    private void onNext(ObservableEmitter<PaymentResult> emitter, PaymentResult result, PlugPagTransactionResult plugPagTransactionResult) {
        result.setTransactionCode(plugPagTransactionResult.getTransactionCode());
        result.setTransactionId(plugPagTransactionResult.getTransactionId());
        result.setTransactionResult(plugPagTransactionResult);
        emitter.onNext(result);
    }

    public Completable abort() {
        return Completable.create(emitter -> plugPag.abort());
    }
}
