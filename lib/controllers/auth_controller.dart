import 'package:bot_toast/bot_toast.dart';
import 'package:pagseguro/models/auth_handler.dart';

class AuthController extends AuthHandler {
  @override
  void onAuthProgress(String message) => BotToast.showText(text: message);

  @override
  void onError(String message) => BotToast.showText(text: message);

  @override
  void onLoading(bool show) => show ? BotToast.showLoading() : BotToast.closeAllLoading();

  @override
  void onSuccess(String message) => BotToast.showText(text: message);
}
