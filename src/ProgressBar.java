import javax.swing.*;
import java.awt.*;

public class ProgressBar extends JProgressBar {
    //İlerleme çubuğunun(bar) bazı özelliklerini ayarlar
    public ProgressBar(int min, int max, int x, int y, int width, int height, int value, Color foreGround, Color backGround) {
        //Minimum ve maksimum değerleri ayarlar
        super(min, max);
        //Çubuk (bar) pozisyonunu ve boyutunu ayarlar
        setBounds(x - width / 2, y - height / 2, width, height);
        //Barın başlangıç değerini ayarla
        setValue(value);
        //Dışındaki çerçeveyi kaldırır
        setBorder(null);
        //Arka plan rengini belirler
        setBackground(backGround);
        //Ön plan rengini (ilerleme rengi) belirler
        setForeground(foreGround);
    }
}