import javax.swing.*;
import java.awt.*;

public class MenuLabel extends JLabel {
    public MenuLabel(String text,int x,int y,int width,int height,Color color,float fontSize,String alignment) {
        //JLabel'in üzerindeki yazıyı ayarlar
        super(text);
        //Yazı tipi ve boyutunu ayarlar
        Font font = Main.getPixelFont().deriveFont(fontSize);
        setFont(font);
        //Label'in boyutlarını ve pozisyonunu ayarlar
        setBounds(x-width/2,y-height/2,width,height);
        //Yazı rengini belirler
        setForeground(color);
        switch (alignment){
            case "left": //Yazıyı sola hizalar
                setHorizontalAlignment(SwingConstants.LEFT); break;
            case "right": //Yazıyı sağa hizalar
                setHorizontalAlignment(SwingConstants.RIGHT); break;
            default: //Varsayılan olarak yazıyı ortaya hizalar
                setHorizontalAlignment(SwingConstants.CENTER); break;

        }
    }
    public MenuLabel(String text,int x,int y,int width,int height,Color color,float fontSize) {
        //JLabel'in üzerine yazıyı ayarlar
        super(text);
        //Yazı tipi ve boyutunu ayarlar
        Font font = Main.getPixelFont().deriveFont(fontSize);
        setFont(font);
        //Label'in boyutlarını ve pozisyonunu ayarlar
        setBounds(x-width/2,y-height/2,width,height);
        //Yazı rengini belirler
        setForeground(color);
        //Yazıyı ortaya hizalar
        setHorizontalAlignment(SwingConstants.CENTER);
    }

}
