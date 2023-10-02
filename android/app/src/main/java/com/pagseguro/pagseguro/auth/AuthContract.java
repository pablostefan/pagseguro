package com.pagseguro.pagseguro.auth;

public interface AuthContract {

    void onDispose();
    void onError(String error);

    void onLoading(boolean loading);

    void onAuthProgress(String message);

    void showIsAuthenticated(Boolean isAuthenticated);
}
