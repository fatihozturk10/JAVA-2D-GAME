//Bu interface, hareket eden nesnelerin ortak davranışlarını tanımlar.
//Tüm `Movable` nesneler, `move()` ve  `getSpeed()` metodunu uygulamak zorundadır.
public interface Movable {
    void move();
    int getSpeed();
}
