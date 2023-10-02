package com.pagseguro.pagseguro.auth;

import com.pagseguro.pagseguro.core.*;
import br.com.uol.pagseguro.plugpagservice.wrapper.*;
import io.reactivex.*;

public class AuthUseCase {
    private final PlugPag plugPag;

    public AuthUseCase(PlugPag plugPag) {
        this.plugPag = plugPag;
    }

    public Observable<Boolean> isAuthenticated() {
        return Observable.create(emitter -> {
            emitter.onNext(plugPag.isAuthenticated());
            emitter.onComplete();
        });
    }

    public Observable<AuthResult> initialize(String activationCode) {
        return Observable.create(emitter -> {
            setEventListener(emitter);
            validateInitialize(emitter, activationCode);
            emitter.onComplete();
        });
    }

    public void setEventListener(ObservableEmitter<AuthResult> emitter) {
        AuthResult actionResult = new AuthResult();
        plugPag.setEventListener(plugPagEventData -> {
            actionResult.setMessage(plugPagEventData.getCustomMessage());
            emitter.onNext(actionResult);
        });
    }

    public void validateInitialize(ObservableEmitter<AuthResult> emitter, String activationCode) {
        PlugPagInitializationResult result = plugPag.initializeAndActivatePinpad(new PlugPagActivationData(activationCode));
        if (result.getResult() == PlugPag.RET_OK) {
            emitter.onNext(new AuthResult());
        } else {
            emitter.onError(new RuntimeException(result.getErrorMessage()));
        }
    }
}
