//Özel bir hata sınıfı
public class ScreenNotFound extends Exception {
  //Hata mesajını parametre olarak alır ve üst sınıf (exception) constructorına iletir
  public ScreenNotFound(String message) {
    super(message);
  }
}
