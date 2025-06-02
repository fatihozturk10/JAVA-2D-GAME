import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

//Oyuncu karakterini temsil eden sınıf
//GameObject sınıfını genişletir ve Movable, Collidable arabirimlerini uygular
public class Player extends GameObject implements Movable,Collidable{
    //Hangi görüntüde olduğumuzu takip eden index
    private int imageIndex;
    //Oyuncunun hareket hızı
    private final int speed = Game.getPlayerSpeed();
    //Basılan tuşları tutan küme
    private static final Set<Integer> pressedKeys = new HashSet<>();
    //Animasyon görüntülerini tutan ArrayList
    private final ArrayList<Image> images = new ArrayList<>();
    //Oyuncunun başlangıç pozisyonu, boyutu ve animasyon görüntülerini ayarlayan yapıcı metod
    public Player(int x, int y,int width,int height) {
        //Karakterin pozisyon ve boyut bilgisini üst sınıf (GameObject'e gönderir)
        super(x,y,width,height);
        //Görüntüleri yükler ve listeye ekler
        for (int i = 1; i <= 3; i++) {
            Image image = new ImageIcon("images/player" + i + ".png").getImage();
            images.add(image);
        }
        //İlk görüntüyü ayarlar
        setImage(images.getFirst());

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
    //Oyuncunun hareketini gerçekleştiren metod
    public void move() {
        //Yukarı hareket
        if (pressedKeys.contains(KeyEvent.VK_W) && getY() - getSpeed() > 0) {
            setY(getY() - getSpeed());
        }
        //Aşağı hareket
        if (pressedKeys.contains(KeyEvent.VK_S) && getY() + getSpeed() < Screen.getScreenHeight()) {
            setY(getY() + getSpeed());
        }
        //Sola hareket
        if (pressedKeys.contains(KeyEvent.VK_A) && getX() - getSpeed() > 0) {
            setX(getX() - getSpeed());
        }
        //Sağa hareket
        if (pressedKeys.contains(KeyEvent.VK_D) && getX() + getSpeed() < Screen.getScreenWidth()) {
            setX(getX() + getSpeed());
        }
    }

    //Oyuncunun hızını döndüren getter
    public int getSpeed() {
        return speed;
    }
    //Basılan tuşları döndüren getter
    public static Set<Integer> getPressedKeys() {
        return pressedKeys;
    }
    //Oyuncunun başka bir GameObject ile çarpışıp çarpışmadığını kontrol eden metod
    public boolean isCollidingWith(GameObject gameObject){
        return Math.abs(this.getX() - gameObject.getX()) < (this.getWidth() / 2.0 + gameObject.getWidth() / 2.0) &&
                Math.abs(this.getY() - gameObject.getY()) < (this.getHeight() / 2.0 + gameObject.getHeight() / 2.0);
    }


    //Oyuncuyu belirli bir pozisyona ışınlayan metod
    public void teleport(int x,int y){
        setX(x);
        setY(y);
    }
    //Oyuncunun "ulti" becerisini gerçekleştiren yöntem
    //8 farklı yönde mermi oluşturur
    public void ulti(){
        //Her bir yönü belirten çok boyutlu liste
        int[][] directions = {
                {1, 0}, {-1, 0}, {0, -1}, {0, 1}, //Sağ, Sol, Yukarı, Aşağı
                {1, -1}, {1, 1}, {-1, -1}, {-1, 1} //Sağ-Yukarı, Sağ-Aşağı, Sol-Yukarı, Sol-Aşağı
        };
        //Her yönde :
        for (int[] dir : directions) {
            int dirX = dir[0];
            int dirY = dir[1];
            //Yeni bir mermi oluştur
            Bullet bullet = new Bullet(getX(), getY(), Screen.getScreenWidth() / 60, Screen.getScreenHeight() / 30, (int) getX() + dirX, (int) getY() + dirY
            );
        }
    }
}
