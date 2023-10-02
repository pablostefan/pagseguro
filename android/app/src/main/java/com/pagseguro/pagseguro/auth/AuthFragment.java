package com.pagseguro.pagseguro.auth;

import io.flutter.plugin.common.*;

public class AuthFragment implements AuthContract {
    final MethodChannel channel;

    private static final String ON_LOADING = "onLoading";
    private static final String ON_ERROR = "onError";
    private static final String ON_AUTH_PROGRESS = "onAuthProgress";
    private static final String ON_SUCCESS = "onSuccess";
    private static final String ON_DISPOSE = "onDispose";


    public AuthFragment(MethodChannel channel) {
        this.channel = channel;
    }

    @Override
    public void onDispose() {
        this.channel.invokeMethod(ON_DISPOSE, true);
    }

    @Override
    public void onError(String error) {
        this.channel.invokeMethod(ON_ERROR, error);
    }

    @Override
    public void onLoading(boolean loading) {
        this.channel.invokeMethod(ON_LOADING, loading);
    }

    @Override
    public void onAuthProgress(String message) {
        this.channel.invokeMethod(ON_AUTH_PROGRESS, message);
    }

    @Override
    public void showIsAuthenticated(Boolean isAuthenticated) {
        this.channel.invokeMethod(ON_SUCCESS, isAuthenticated);
    }
}
