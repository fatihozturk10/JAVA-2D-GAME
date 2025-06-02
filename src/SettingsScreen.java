import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// SettingsScreen sınıfı, kullanıcıların oyun ayarlarını yapabilecekleri bir ekran sağlar. (Şu anlık sadece ekran çözünürlüğü)
public class SettingsScreen extends Screen{
    SettingsScreen() {
        //Ekranın arka plan rengini ayarlar.
        setBackground(Color.blue);
        //Başlık (Settings) oluşturulup eklenir.
        MenuLabel title = new MenuLabel("Settings", getScreenWidth() /2 , getScreenHeight() *3/32, getScreenWidth() /2, getScreenHeight() /10,Color.black, getScreenHeight() /12f);
        add(title);
        // Çözünürlük seçeneklerini içeren bir JComboBox oluşturur.
        JComboBox<String> resolutionBox = new JComboBox<>();
        resolutionBox.setBounds(getScreenWidth() /2 - getScreenWidth() /8, getScreenHeight() /2, getScreenWidth() /4, getScreenHeight() /8);
        //Özel bir yazı tipi ayarlar.
        resolutionBox.setFont(Main.getPixelFont());
        //Varsayılan çözünürlük ve diğer çözünürlük seçeneklerini JComboBox'a ekler.
        resolutionBox.addItem(Game.getDefaultScreenSize().width + "x" + Game.getDefaultScreenSize().height);
        resolutionBox.addItem("1280x728");
        resolutionBox.addItem("1024x768");
        resolutionBox.addItem("960x540");
        resolutionBox.addItem("800x600");
        //JComboBox, ekrana eklenir.
        add(resolutionBox);
        //Kullanıcı bir çözünürlük seçtiğinde bu işlemler gerçekleşir.
        resolutionBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Seçilen öğeyi alır.
                Object selectedItem = resolutionBox.getSelectedItem();
                if (selectedItem != null) {
                    //Seçilen çözünürlüğü "genişlik x yükseklik" formatında ayırır.
                    String resolution = resolutionBox.getSelectedItem().toString();
                    int width = Integer.parseInt(resolution.split("x")[0]);
                    int height = Integer.parseInt(resolution.split("x")[1]);
                    //Yeni çözünürlüğe göre ekran boyutlarını günceller.
                    setScreenWidth(width);
                    setScreenHeight(height);

                    //Yazı tipi boyutunu, yeni ekran boyutuna göre ayarlar.
                    Main.setPixelFont(Main.getPixelFont().deriveFont(Screen.getScreenHeight() / 20f));
                    //Game sınıfındaki genel ekran boyutu güncellenir.
                    Game.setScreenSize(width, height);
                }
                else {
                    //Eğer hiçbir çözünürlük seçilmediyse, bir mesaj yazdırıyoruz.
                    System.out.println("Hiçbir seçenek seçilmedi.");
                }

            }
        });
        //Ana menüye dönme butonu oluştulur ve eklenir.
        MenuButton mainMenuButton = new MenuButton("Back", getScreenWidth() /2 , getScreenHeight() *27/32 , Main.getPixelFont(),Color.lightGray);
        add(mainMenuButton);

        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Ana menü ekranına geçiş yapılır.
                Game.setScreen(Game.newScreen("MainMenu"));
            }
        });
    }
}
