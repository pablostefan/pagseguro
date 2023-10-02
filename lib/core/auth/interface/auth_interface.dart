abstract class IAuth {
  Future<bool> isAuthenticated();

  Future<void> authenticate();
}
