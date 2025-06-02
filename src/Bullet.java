import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Bullet extends GameObject implements Collidable,Movable{
    //Fırlatılan merminin doğrultusunu belirleyen açı
    private final double angle;
    //Mermi hızını ayarlayan değişken
    private static int speed;
    //Animasyon görüntülerini tutan ArrayList
    private final ArrayList<Image> images = new ArrayList<>();
    //Hangi görüntüde olduğumuzu takip eden index
    private int imageIndex;
    //Mermi sınıfının yapıcı metodu
    public Bullet(double x, double y, int width, int height, int targetX, int targetY) {
        //Konum ve boyut bilgilerini GameObject sınıfına gönderir ve ayarlar
        super(x, y, width, height);
        //Mermiyi mermi listesine ekler
        GameScreen.getBullets().add(this);
        //Fırlatılan mermi sayısını 1 arttırır
        Game.setBulletsFired(Game.getBulletsFired()+1);
        //Hızı ayarlar
        speed = Game.getBulletSpeed();
        //Görüntüleri yükler ve listeye ekler
        for (int i = 1; i <= 3; i++) {
            Image image = new ImageIcon("images/bullet" + i + ".png").getImage();
            images.add(image);
        }
        //İlk görüntüyü ayarlar
        setImage(images.getFirst());
        //Hedef ve mevcut konum arasındaki farkı bulur
        double deltaY =  targetY - y;
        double deltaX = targetX - x;
        //Hedefe olan mesafeye göre arctan ile açı hesaplanır
        this.angle = Math.atan2(deltaY,deltaX);
    }

    //Fırlatılan merminin hareket fonksiyonu
    public void move(){
        //X eksenindeki hareket açının cosinüsü ile sağlanır
        setX(getX() + speed * Math.cos(angle));
        //Y eksenindeki hareket açının sinüsü ile sağlanır
        setY(getY() + speed * Math.sin(angle));
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    //Çarpışma kontrolü fonksiyonu
    public boolean isCollidingWith(GameObject gameObject){
        return Math.abs(this.getX() - gameObject.getX()) < (this.getWidth() / 2.0 + gameObject.getWidth() / 2.0) &&
                Math.abs(this.getY() - gameObject.getY()) < (this.getHeight() / 2.0 + gameObject.getHeight() / 2.0);
    }

    //Animasyon için görüntü değiştiren fonksiyon
    public void animate(){
        //Mevcut görüntüyü ayarlar
        setImage(images.get(imageIndex));
        //Görüntü indeksini bir artırır
        imageIndex++;
        //Son görüntüden sonra başa dön
        if(imageIndex == 3){
            imageIndex = 0;
        }
    }
    // Merminin ekran sınırları içinde olup olmadığını kontrol eder
    public boolean isOutOfBounds(){
        return getX() > Screen.getScreenWidth() || getX() < 0 || getY() < 0 || getY() > Screen.getScreenHeight();
    }
}
