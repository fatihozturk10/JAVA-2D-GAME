import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SpawnMarker extends GameObject{
    //Ömrü belirten süre
    private int lifeTime = 2;
    //Animasyon görüntülerini tutan ArrayList
    private final ArrayList<Image> images = new ArrayList<>();
    //Hangi görüntüde olduğumuzu takip eden index
    private int imageIndex;
    //Yaratılacak düşman sınıfı
    private final String enemyClass;
    //Düşman genişliği
    private final int enemyWidth;
    //Düşman yüksekliği
    private final int enemyHeight;

    //SpawnMarker nesnesinin constructor'ı
    public SpawnMarker(double x, double y, int width, int height,String enemyClass,int enemyWidth,int enemyHeight) {
        super(x, y, width, height); //GameObject'in constructor'ını çağırır
        this.enemyClass = enemyClass; //Düşman sınıfını atar
        this.enemyWidth = enemyWidth; //Düşman genişliğini atar
        this.enemyHeight = enemyHeight; //Düşman yüksekliğini atar
        //Görüntüleri yükler ve listeye ekler
        for (int i = 1; i <= 4; i++) {
            Image image = new ImageIcon("images/spawnmarker" + i + ".png").getImage();
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
        if(imageIndex == 4){
            imageIndex = 0;
        }
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }
    //Düşmanı spawn eden fonskiyon
    public Enemy spawnEnemy(){
        if(enemyClass.equals("Bomber")){
            //Bomber düşmanı yaratır
            return new Bomber(getX(),getY(),enemyWidth,enemyHeight);
        }
        else if(enemyClass.equals("Fly")){
            //Fly düşmanı yaratır
            return new Fly(getX(),getY(),enemyWidth,enemyHeight);
        }
        else {
            //Eğer tanımlanmayan bir düşman sınıfı gelirse, hata mesajı yazar
            System.err.println("Hatalı düşman sınıfı: " + enemyClass);
            return null;
        }
    }
}
