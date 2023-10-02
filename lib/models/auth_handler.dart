abstract class AuthHandler {
  void onError(String message);

  void onSuccess(String message);

  void onLoading(bool show);

  void onAuthProgress(String message);
}
