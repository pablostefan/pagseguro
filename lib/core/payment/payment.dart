import 'package:flutter/services.dart';
import 'package:pagseguro/core/payment/interface/payment_interface.dart';
import 'package:pagseguro/helpers/payment_method.dart';
import 'package:pagseguro/models/payment_handler.dart';

class Payment implements PaymentInterface {
  final MethodChannel channel;
  final PaymentHandler paymentHandler;

  Payment({
    required this.channel,
    required this.paymentHandler,
  }) {
    channel.setMethodCallHandler(_callHandler);
  }

  @override
  Future<bool> creditPayment(int value) async {
    return await channel.invokeMethod(PaymentMethod.paymentCredit.name, {"value": value});
  }

  @override
  Future<bool> debitPayment(int value) async {
    return await channel.invokeMethod(PaymentMethod.paymentDebit.name, {"value": value});
  }

  @override
  Future<bool> lastTransaction() async {
    return await channel.invokeMethod(PaymentMethod.paymentLastTransaction.name);
  }

  Future<dynamic> _callHandler(MethodCall call) async {
    switch (call.method.handler) {
      case PaymentMethod.onError:
        return paymentHandler.onError(call.arguments);

      case PaymentMethod.onLoading:
        return paymentHandler.onLoading(call.arguments);

      case PaymentMethod.onTransactionSuccess:
        return paymentHandler.onTransactionSuccess();

      case PaymentMethod.onFinishedResponse:
        return paymentHandler.onFinishedResponse(call.arguments);

      default:
        return null;
    }
  }
}
