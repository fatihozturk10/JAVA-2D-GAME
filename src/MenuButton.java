import javax.swing.*;
import java.awt.*;

public class MenuButton extends JButton {
    //Butonun boyut ve pozisyon ayarlarını elle belirlemek için
    public MenuButton(String label, int x, int y, int width, int height, Font font, Color bgColor) {
        //Butonun üzerindeki yazıyı ayarlar
        super(label);
        //Yazı tipini ve boyutunu ayarlar
        setFont(font);
        //Butonun arka plan rengini belirler
        setBackground(bgColor);
        //Butonun üzerine odaklanıldığında çizilen çerçeveyi kaldırır
        setFocusPainted(false);
        //Yazı rengini beyaz olarak ayarlar
        setForeground(Color.white);
        //Butonun boyutlarını ve pozisyonunu ayarlar
        setBounds(x - width / 2, y - height / 2, width, height);
    }

    //Varsayılan boyutlar için ekran boyutuna göre ayar yapar
    public MenuButton(String label, int x, int y, Font font, Color bgColor) {
        //Butonun üzerindeki yazıyı ayarlar
        super(label);
        //Yazı tipini ve boyutunu ayarlar
        setFont(font);
        //Butonun arka plan rengini belirler
        setBackground(bgColor);
        //Butonun üzerine odaklanıldığında çizilen çerçeveyi kaldırır
        setFocusPainted(false);
        //Yazı rengini beyaz olarak ayarlar
        setForeground(Color.white);
        //Ekran boyutuna göre dinamik boyut ve pozisyon ayarları yapar
        setBounds(x - Screen.getScreenWidth() / 8, y - Screen.getScreenHeight() / 16,
                Screen.getScreenWidth() / 4, Screen.getScreenHeight() / 8);
    }
}