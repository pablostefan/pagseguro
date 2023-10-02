import 'package:flutter/services.dart';
import 'package:pagseguro/constants/constants.dart';
import 'package:pagseguro/core/auth/auth.dart';
import 'package:pagseguro/core/payment/payment.dart';
import 'package:pagseguro/models/auth_handler.dart';
import 'package:pagseguro/models/payment_handler.dart';

class PagseguroService {
  final MethodChannel _channel;
  static PagseguroService? _instance;
  Auth? _auth;
  Payment? _payment;

  PagseguroService(this._channel);

  static PagseguroService instance() {
    _instance ??= PagseguroService(const MethodChannel(Constants.channel));
    return _instance!;
  }

  void initAuth(AuthHandler authHandler) => _auth = Auth(channel: _channel, authHandler: authHandler);

  void initPayment(PaymentHandler paymentHandler) {
    _payment = Payment(channel: _channel, paymentHandler: paymentHandler);
  }

  Auth get auth {
    if (_auth == null) throw "AUTH NEED INITIALIZE! \n TRY: PagseguroService._instance.initAuth(authHandler)";

    return _auth!;
  }

  Payment get payment {
    if (_payment == null) throw "PAYMENT NEED INITIALIZE! \n TRY: PagseguroService._instance.initPayment(handler)";

    return _payment!;
  }
}
