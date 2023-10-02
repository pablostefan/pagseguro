package com.pagseguro.pagseguro.auth;

import android.util.*;
import br.com.uol.pagseguro.plugpagservice.wrapper.*;
import io.flutter.plugin.common.*;
import io.reactivex.android.schedulers.*;
import io.reactivex.disposables.*;
import io.reactivex.schedulers.*;

public class AuthPresenter {
    private AuthUseCase authUseCase;

    private AuthFragment authFragment;

    private Disposable disposable;

    public AuthPresenter(PlugPag plugPag, MethodChannel channel) {
        this.authUseCase = new AuthUseCase(plugPag);
        this.authFragment = new AuthFragment(channel);
    }

    public void isAuthenticated() {
        disposable = authUseCase.isAuthenticated()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> authFragment.onLoading(true))
                .doOnComplete(() -> authFragment.onLoading(false))
                .subscribe(authFragment::showIsAuthenticated, throwable -> authFragment.onError(throwable.getMessage()));
    }

    public void authenticate(String activationCode) {
        disposable = authUseCase.initialize(activationCode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> authFragment.onLoading(true))
                .doOnComplete(() -> authFragment.onLoading(false))
                .doOnDispose(authFragment::onDispose)
                .subscribe(actionResult -> authFragment.onAuthProgress(actionResult.getMessage()), this::onErrorInitialize);
    }


    private void onErrorInitialize(Throwable throwable) {
        authFragment.onLoading(false);
        authFragment.onError(throwable.getMessage());
        Log.d("print", "Error: " + throwable.getMessage());
    }

    public void dispose() {
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
