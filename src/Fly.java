import javax.swing.*;
import java.awt.*;

public class Fly extends Enemy{
    //Hareket hızı
    private static int speed = 3;
    //Varsayılan hız değeri
    private static final int defaultSpeed = 3;

    public Fly(double x, double y, int width, int height) {
        //Kalıtım alınan Enemy sınıfının yapıcı metodu çağrılır
        super(x, y, width, height);
        //Görüntüleri yükler ve listeye ekler
        for (int i = 1; i <= 3; i++) {
            Image image = new ImageIcon("images/fly" + i + ".png").getImage();
            getImages().add(image);
        }
        //İlk görüntüyü ayarlar
        setImage(getImages().getFirst());
    }
    public void move(Player player){
        if(player.getX() < getX()){
            setX(getX() - getSpeed());
        }
        if(player.getX() > getX()){
            setX(getX() + getSpeed());
        }
        if(player.getY() < getY()){
            setY(getY() - getSpeed());
        }
        if(player.getY() > getY()){
            setY(getY() + getSpeed());
        }
    }
    //Animasyon için görüntü değiştiren fonksiyon
    public void animate(){
        //Mevcut görüntüyü ayarlar
        setImage(getImages().get(getImageIndex()));
        //Görüntü indeksini bir artırır
        setImageIndex(getImageIndex()+1);
        //Son görüntüden sonra başa dön
        if(getImageIndex() == 3){
            setImageIndex(0);
        }
    }

    //Getter ve setterlar
    public static int getSpeed() {
        return speed;
    }

    public static void setSpeed(int speed) {
        Fly.speed = speed;
    }
    public static int getDefaultSpeed() {
        return defaultSpeed;
    }
}
