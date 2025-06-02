import java.awt.*;
import java.io.File;
import java.io.IOException;
public class Main {
    //Oyunda kullanılacak yazı tipini tutar.
    private static Font pixelFont;
    public static void main(String[] args){
        try {
            //Özel bir yazı tipi dosyasını yükler ve ölçeklendirir.
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File("assets/pixelFont.ttf"))
                    .deriveFont(Game.getDefaultScreenSize().height / 20f);
        } catch (IOException | FontFormatException e) {
            //Yazı tipi yüklenirken bir hata olursa uyarı mesajı yazdırılır.
            System.out.println("Font yüklenirken hata oluştu!");
        }
        finally {
            //Eğer yazı tipi yüklenemezse, varsayılan olarak Arial kullanılır.
            if (pixelFont == null) {
                pixelFont = new Font("Arial", Font.PLAIN, Game.getDefaultScreenSize().height / 20);
            }
        }
        //Oyunu başlatır.
        Game.startGame();

    }
    //Uygulama genelinde kullanılan "Pixel" yazı tipini döndüren yöntem
    public static Font getPixelFont() {
        return pixelFont;
    }
    //Uygulama genelinde kullanılan "Pixel" yazı tipini ayarlayan yöntem
    public static void setPixelFont(Font pixelFont) {
        Main.pixelFont = pixelFont;
    }
}






