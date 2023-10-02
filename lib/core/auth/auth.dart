import 'package:flutter/services.dart';
import 'package:pagseguro/core/auth/interface/auth_interface.dart';
import 'package:pagseguro/helpers/auth_method.dart';
import 'package:pagseguro/models/auth_handler.dart';

class Auth implements IAuth {
  final MethodChannel channel;
  final AuthHandler authHandler;

  Auth({
    required this.channel,
    required this.authHandler,
  }) {
    channel.setMethodCallHandler(_callHandler);
  }

  String get _code => "....";

  @override
  Future<void> authenticate() async {
    await channel.invokeMethod(AuthMethod.authAuthenticate.name, {"code": _code});
  }

  @override
  Future<bool> isAuthenticated() async {
    return await channel.invokeMethod(AuthMethod.authIsAuthenticated.name);
  }

  Future<dynamic> _callHandler(MethodCall call) async {
    switch (call.method.handler) {
      case AuthMethod.onError:
        return authHandler.onError(call.arguments);

      case AuthMethod.onLoading:
        return authHandler.onLoading(call.arguments);

      case AuthMethod.onAuthProgress:
        return authHandler.onAuthProgress(call.arguments);

      case AuthMethod.onSuccess:
        return authHandler.onSuccess(call.arguments);

      default:
        return null;
    }
  }
}
