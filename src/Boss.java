import javax.swing.*;
import java.awt.*;

public class Boss extends Enemy{
    //Son bomba atışının yapıldığı zaman
    private long lastBombTime;
    //Yön hareketini belirler (yukarı mı aşağı mı)
    private boolean moveUp;
    //Boss sınıfının yapıcı metodu
    public Boss(double x, double y, int width, int height) {
        //Enemy yapısını çağırır
        super(x, y, width, height);
        //Görüntüleri yükler ve listeye ekler
        for (int i = 1; i <= 14; i++) {
            Image image = new ImageIcon("images/boss" + i + ".png").getImage();
            getImages().add(image);
        }
        //İlk görüntüyü ayarlar
        setImage(getImages().getFirst()); //İlk görüntüyü ayarla
    }

    //Oyuncuya göre boss'un hareketi (yukarı ve aşağı hareket)
    void move(Player player){
        if(moveUp){
            //Yukarı hareket (y ekseni ters!)
            setY(getY()-1);
            //Ekranın 4'te 1 konumuna yükselince aşağı hareket eder
            if (getY() == (double) Screen.getScreenHeight() /4){
                moveUp = false;
            }
        }
        else {
            //Aşağı hareket
            setY(getY()+1);
            //Ekranın 4'te 3  konumuna yükselince aşağı hareket eder
            if (getY() == (double) (Screen.getScreenHeight() * 3) /4){
                moveUp = true;
            }
        }
    }
    //Oyuncuya bomba saldırısı fonksiyonu
    void attack(Player player){
        //Bomba atışları 1 saniyede bir kontrol edilir
        if(GameScreen.getCurrentTime() - lastBombTime > 1000) {
            //Bomba nesnesi oluşturulur
            Bomb bomb = new Bomb(getX() + (double) Screen.getScreenWidth() /8, getY() - (double) Screen.getScreenHeight() /12, Screen.getScreenWidth() / 40, Screen.getScreenHeight() / 20, (int) player.getX(), (int) player.getY());
            //Bombanın hızı oyuncunun hızına bağlı olarak değiştirilir.
            bomb.setSpeed(Game.getPlayerSpeed()*2);
            //Bomba listesine eklenir
            GameScreen.getBombs().add(bomb);
            //Son bomba atış zamanını günceller
            lastBombTime = System.currentTimeMillis();
        }
    }

    //Animasyon için görüntü değiştiren fonksiyon
    public void animate(){
        //Mevcut görüntüyü ayarlar
        setImage(getImages().get(getImageIndex()));
        //Görüntü indeksini bir artırır
        setImageIndex(getImageIndex()+1);
        //Son görüntüden sonra başa dön
        if(getImageIndex() == 14){
            setImageIndex(0);
        }
    }
}
