enum AuthMethod {
  authAuthenticate,
  authIsAuthenticated,
  onError,
  onSuccess,
  onLoading,
  onAuthProgress,
}

extension AuthHandlerMethod on String {
  AuthMethod get handler {
    switch (this) {
      case "onError":
        return AuthMethod.onError;
      case "onSuccess":
        return AuthMethod.onSuccess;
      case "onLoading":
        return AuthMethod.onLoading;
      case "onAuthProgress":
        return AuthMethod.onAuthProgress;
      default:
        throw "NOT IMPLEMENTED";
    }
  }
}
