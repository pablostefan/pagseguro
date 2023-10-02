package com.pagseguro.pagseguro.core;

import android.content.*;
import com.pagseguro.pagseguro.auth.*;
import com.pagseguro.pagseguro.payments.*;
import br.com.uol.pagseguro.plugpagservice.wrapper.*;
import io.flutter.plugin.common.*;

public class PagseguroService {
    final PlugPag plugPag;
    final MethodChannel channel;

    AuthPresenter auth;

    PaymentPresenter payment;


    public PagseguroService(Context context, MethodChannel channel) {
        this.plugPag = new PlugPag(context);
        this.channel = channel;
    }

    private static final String PAYMENT_DEBIT = "paymentDebit";
    private static final String PAYMENT_CREDIT = "paymentCredit";
    private static final String PAYMENT_ABORT = "paymentAbort";
    private static final String LAST_TRANSACTION = "paymentLastTransaction";

    private static final String AUTH_AUTHENTICATE = "authAuthenticate";

    private static final String AUTH_IS_AUTHENTICATE = "authIsAuthenticated";

    public void initAuth(MethodCall call, MethodChannel.Result result) {
        setAuthPresenter();
        callAuth(call, result);
    }

    public void initPayment(MethodCall call, MethodChannel.Result result) {
        setPaymentPresenter();
        callPayment(call, result);
    }

    private void callAuth(MethodCall call, MethodChannel.Result result) {
        if (call.method.equals(AUTH_IS_AUTHENTICATE)) this.auth.isAuthenticated();
        else if (call.method.equals(AUTH_AUTHENTICATE)) this.auth.authenticate(call.argument("code"));
        else result.notImplemented();
    }

    private void callPayment(MethodCall call, MethodChannel.Result result) {
        if (call.method.equals(PAYMENT_DEBIT)) this.payment.debitPayment(call.argument("value"));
        else if (call.method.equals(PAYMENT_CREDIT)) this.payment.creditPayment(call.argument("value"));
        else if (call.method.equals(PAYMENT_ABORT)) this.payment.abortTransaction();
        else if (call.method.equals(LAST_TRANSACTION)) this.payment.getLastTransaction();
        else result.notImplemented();
    }

    private void setPaymentPresenter() {
        if (this.payment == null) this.payment = new PaymentPresenter(this.plugPag, this.channel);
    }

    private void setAuthPresenter() {
        if (this.auth == null) this.auth = new AuthPresenter(this.plugPag, this.channel);
    }

    public void dispose() {
        if (this.auth != null) this.auth.dispose();
    }
}
