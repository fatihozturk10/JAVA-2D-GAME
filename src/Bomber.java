import javax.swing.*;
import java.awt.*;


public class Bomber extends Enemy{
    //Son bomba atış zamanını tutar
    private long lastBombTime;
    //Bomber'ın hareket hızı
    private static int speed = 2;
    //Varsayılan hız değeri
    private static final int defaultSpeed = 2;
    //Bomber sınıfının yapıcı metodu
    public Bomber(double x, double y, int width, int height) {
        //Enemy sınıfının yapıcı metodu çağrılır
        super(x, y, width, height);
        //Görüntüleri yükler ve listeye ekler
        for (int i = 1; i <= 4; i++) {
            Image image = new ImageIcon("images/bomber" + i + ".png").getImage();
            getImages().add(image);
        }
        //İlk görüntüyü ayarlar
        setImage(getImages().getFirst());
    }
    //Bomber'ın oyuncuya göre hareketi
    public void move(Player player){
        //Bomber, oyuncudan 250 birim uzaktaysa oyuncuya doğru hareket eder
        if(Math.hypot(getX() - player.getX(),getY() - player.getY()) > 250) {
            if (player.getX() < getX()) {
                //X ekseninde sola hareket
                setX(getX() - getSpeed());
            }
            if (player.getX() > getX()) {
                //X ekseninde sağa hareket
                setX(getX() + getSpeed());
            }
            if (player.getY() < getY()) {
                //Y ekseninde yukarı hareket
                setY(getY() - getSpeed());
            }
            if (player.getY() > getY()) {
                //Y ekseninde aşağı hareket
                setY(getY() + getSpeed());
            }
        }
        else {
            //Eğer oyuncuya yakınsa ve bomba atma zamanı geldiyse saldırı yapar (2 saniyelik gecikme kontrolü)
            if (GameScreen.getCurrentTime() - lastBombTime >= 2 * 1000){
                attack(player);
            }
        }
    }

    //Bomber'ın oyuncuya bomba atması
    public void attack(Player player){
        //Oyuncunun konumu hedef olan bomba nesnesi üretir
        Bomb bomb = new Bomb(getX(),getY(),Screen.getScreenWidth() / 40, Screen.getScreenHeight() / 20,(int) player.getX(), (int) player.getY());
        //Bomba listeye eklenir
        GameScreen.getBombs().add(bomb);
        //Son bomba atış zamanı güncellenir
        lastBombTime = System.currentTimeMillis();
    }

    //Animasyon için görüntü değiştiren fonksiyon
    public void animate(){
        //Mevcut görüntüyü ayarlar
        setImage(getImages().get(getImageIndex()));
        //Görüntü indeksini bir artırır
        setImageIndex(getImageIndex()+1);
        //Son görüntüden sonra başa dön
        if(getImageIndex() == 4){
            setImageIndex(0);
        }
    }

    //Getter ve setterlar
    public static int getSpeed() {
        return speed;
    }

    public static void setSpeed(int speed) {
        Bomber.speed = speed;
    }

    public static int getDefaultSpeed() {
        return defaultSpeed;
    }
}
