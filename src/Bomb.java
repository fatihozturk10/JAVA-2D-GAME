import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Bomb extends GameObject{
    //Bombanın hareket açısı
    private final double angle;
    //Bombanın hızı
    private int speed = 3;
    //Animasyon görüntülerini tutan ArrayList
    private final ArrayList<Image> images = new ArrayList<>();
    //Hangi görüntüde olduğumuzu takip eden index
    private int imageIndex;
    //Bomb sınıfının yapıcı metodu
    public Bomb(double x, double y, int width, int height, int targetX, int targetY) {
        super(x, y, width, height); //GameObject yapıcı metodunu çağır
        //Görüntüleri yükler ve listeye ekler
        for (int i = 1; i <= 4; i++) {
            Image image = new ImageIcon("images/bomb" + i + ".png").getImage();
            images.add(image);
        }
        //İlk görüntüyü ayarlar
        setImage(images.getFirst());
        //Hedef ile bombanın y eksenindeki farkı hesaplar
        double deltaY =  targetY - y;
        //Hedef ile bombanın x eksenindeki farkı hesaplar
        double deltaX = targetX - x;
        //Bombanın hareket edeceği açıyı arctan ile hesaplar
        this.angle = Math.atan2(deltaY,deltaX);
    }
    //Bombanın hareket metodu
    public void move(){
        //X eksenindeki hareket açının cosinüsü ile sağlanır
        setX(getX() + speed * Math.cos(angle));
        //Y eksenindeki hareket açının sinüsü ile sağlanır
        setY(getY() + speed * Math.sin(angle));
    }

    //Animasyon için görüntü değiştiren fonksiyon
    public void animate(){
        //Mevcut görüntüyü ayarlar
        setImage(images.get(imageIndex));
        //Görüntü indeksini bir artırır
        imageIndex++;
        //Son görüntüden sonra başa dön
        if(imageIndex == 4){
            imageIndex = 0;
        }
    }

    //setter
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    //Bomba ekranın dışına çıktı mı onu kontrol eder
    public boolean isOutOfBounds(){
        return getX() > Screen.getScreenWidth() || getX() < 0 || getY() < 0 || getY() > Screen.getScreenHeight();
    }
}
