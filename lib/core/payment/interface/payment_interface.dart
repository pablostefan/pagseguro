abstract class PaymentInterface {
  Future<bool> creditPayment(int value);

  Future<bool> debitPayment(int value);

  Future<bool> lastTransaction();
}
