import 'package:bot_toast/bot_toast.dart';
import 'package:pagseguro/models/payment_handler.dart';

class PaymentController extends PaymentHandler {
  @override
  void onError(String message) => BotToast.showText(text: message);

  @override
  void onLoading(bool show) => show ? BotToast.showLoading() : BotToast.closeAllLoading();

  @override
  void disposeDialog() => BotToast.showText(text: "disposeDialog");

  @override
  void onActivationDialog(String message) => BotToast.showText(text: message);

  @override
  void onFinishedResponse(String message) => BotToast.showText(text: message);

  @override
  void onMessage(String message) => BotToast.showText(text: message);

  @override
  void onTransactionInfo(String transactionCode, String transactionId, String response) {
    BotToast.showText(text: transactionCode);
  }

  @override
  void onTransactionSuccess() => BotToast.showText(text: "Transacao com successo!");

  @override
  void writeToFile(String transactionCode, String transactionId, String response) {
    BotToast.showText(text: transactionCode);
  }
}
