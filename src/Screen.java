import javax.swing.*;
import java.awt.*;

public abstract class Screen extends JPanel {
    //Tüm ekranlar için ortak genişlik ve yükseklik
    private static int screenWidth;
    private static int screenHeight;
    //Ekran boyutlarını ayarlar ve temel özellikleri yapılandırır
    Screen() {
        //Eğer ekran boyutları henüz ayarlanmamışsa, varsayılan oyun ekran boyutlarını alır
        if(screenWidth == 0 && screenHeight == 0){
            //Varsayılan genişliği alır
            screenWidth = Game.getDefaultScreenSize().width;
            //Varsayılan yüksekliği alır
            screenHeight = Game.getDefaultScreenSize().height;
        }
        //Panel boyutunu ayarlar
        setPreferredSize(new Dimension(screenWidth,screenHeight));
        //Arka plan rengini ayarlar
        setBackground(Color.blue);
        //Bileşen yerleşimini manuel olarak kontrol etmek için layout'u null olarak ayarlarız
        setLayout(null);

    }

    //Getter ve Setterlar

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static void setScreenWidth(int screenWidth) {
        Screen.screenWidth = screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static void setScreenHeight(int screenHeight) {
        Screen.screenHeight = screenHeight;
    }
}
