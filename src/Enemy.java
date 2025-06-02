import java.awt.*;
import java.util.ArrayList;

public abstract class Enemy extends GameObject {
    //Hangi görüntüde olduğumuzu takip eden index
    private int imageIndex;
    //Animasyon görüntülerini tutan ArrayList
    private final ArrayList<Image> images = new ArrayList<>();
    //Düşmanın başlangıç pozisyonu ve boyutunu ayarlayan (super classta ayarlıyor) yapıcı metod
    public Enemy(double x, double y, int width, int height) {
        super(x, y, width, height);
    }
    //Düşmanın hareketini belirlemek için uygulanması gereken abstract metot
    abstract void move(Player player);


    //Getter ve setterlar

    public int getImageIndex() {
        return imageIndex;
    }

    public void setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    abstract void animate();
}
