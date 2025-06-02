import java.awt.*;
//Temel oyun nesnesi sınıfıdır. Oyun nesnelerinin x, y konumlarını, boyutlarını ve görüntülerini (image) içerir.
public abstract class GameObject {
    //Nesnenin x konumu
    private double x;
    //Nesnenin y konumu
    private double y;
    //Nesnenin genişliği
    private int width;
    //Nesnenin yüksekliği
    private int height;
    //Nesnenin görüntüsü
    private Image image;
    //Yapıcı metod x, y konum, genişlik ve yükseklik değerlerini alır ve değişkenlere atar.
    public GameObject(double x, double y,int width,int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    //Ekranda nesneyi çizmek için kullanılan metot
    public void draw(Graphics g) {
        g.drawImage(getImage(), (int) getX() - getWidth() / 2, (int) getY() - getHeight() / 2,
                getWidth(), getHeight(), null);
    }
    //Getter ve setterlar
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


}
