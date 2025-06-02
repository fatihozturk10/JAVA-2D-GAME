//Bu interface, çarpışabilir nesnelerin belirlenmesi için kullanılır.
//Bir nesnenin başka bir nesneyle çarpışıp çarpışmadığını kontrol eden bir metoda sahiptir.
public interface Collidable {
    //Diğer nesneyle çarpışıp çarpışmadığını kontrol eder
    boolean isCollidingWith(GameObject other);
}
