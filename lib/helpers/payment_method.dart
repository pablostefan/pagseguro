enum PaymentMethod {
  paymentLastTransaction,
  paymentDebit,
  paymentAbort,
  paymentCredit,
  onError,
  onLoading,
  onTransactionSuccess,
  onFinishedResponse,
  disposeDialog,
  onMessage,
  onTransactionInfo,
  onActivationDialog,
}

extension AuthHandlerMethod on String {
  PaymentMethod get handler {
    switch (this) {
      case "onError":
        return PaymentMethod.onError;
      case "onTransactionSuccess":
        return PaymentMethod.onTransactionSuccess;
      case "onLoading":
        return PaymentMethod.onLoading;
      default:
        throw "NOT IMPLEMENTED";
    }
  }
}
