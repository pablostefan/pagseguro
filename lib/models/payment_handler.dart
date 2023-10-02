abstract class PaymentHandler {
  void onTransactionSuccess();

  void onError(String message);

  void onMessage(String message);

  void onFinishedResponse(String message);

  void onLoading(bool show);

  void disposeDialog();

  void onActivationDialog(String message);

  void writeToFile(String transactionCode, String transactionId, String response);

  void onTransactionInfo(String transactionCode, String transactionId, String response);
}
