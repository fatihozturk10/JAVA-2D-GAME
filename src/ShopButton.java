import javax.swing.*;
import java.awt.*;

public class ShopButton extends JButton {
    //Constructor ile buton özelliklerini ayarlayan sınıf
    public ShopButton(int x, int y, Color color) {
        //Buton arka plan rengini ayarlar
        setBackground(color);
        //Odak çerçevesini kaldırır
        setFocusPainted(false);
        //Konumu ve boyutunu ayarlar
        setBounds(x - Screen.getScreenWidth()/20,y - Screen.getScreenHeight()/20, Screen.getScreenWidth() /10, Screen.getScreenHeight() /10);
    }
}
