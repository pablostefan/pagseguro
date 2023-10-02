import 'package:bot_toast/bot_toast.dart';
import 'package:flutter/material.dart';
import 'package:pagseguro/controllers/auth_controller.dart';
import 'package:pagseguro/controllers/payment_controller.dart';
import 'package:pagseguro/core/service/pagseguro.dart';
import 'package:pagseguro/widgets/button_widget.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        title: 'Flutter Demo',
        navigatorObservers: [BotToastNavigatorObserver()],
        builder: BotToastInit(),
        theme: ThemeData(colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple), useMaterial3: true),
        home: const HomePage());
  }
}

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  @override
  void initState() {
    super.initState();
    PagseguroService.instance().initAuth(AuthController());
    PagseguroService.instance().initPayment(PaymentController());
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: ListView(children: [
      ButtonWidget(
        text: "Authenticate",
        onTap: () async => await PagseguroService.instance().auth.authenticate(),
      ),
      ButtonWidget(
        text: "IsAuthenticate",
        onTap: () async => await PagseguroService.instance().auth.isAuthenticated(),
      ),
      ButtonWidget(
        text: "creditPayment",
        onTap: () async => await PagseguroService.instance().payment.creditPayment(1000),
      ),
      ButtonWidget(
        text: "debitPayment",
        onTap: () async => await PagseguroService.instance().payment.debitPayment(1000),
      ),
    ]));
  }
}
