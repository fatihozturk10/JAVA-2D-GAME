import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
//LoadGameScreen sınıfı, kullanıcıya mevcut kayıtlı oyunları listeleyip yükleme yapmasına olanak tanır.
public class LoadGameScreen extends Screen {
    public LoadGameScreen() {
        //Ekranın arka plan rengini ayarlar.
        setBackground(Color.DARK_GRAY);
        //Ekranın başlığı olarak "Load Game" yazısını ekler.
        MenuLabel title = new MenuLabel("Load Game", getScreenWidth()/2 , getScreenHeight()*3 /32, getScreenWidth()/2,getScreenHeight()/10,Color.black,getScreenHeight() /12f);
        add(title);
        // "Saves: " adında bir metin ekler.
        MenuLabel saveName = new MenuLabel("Saves: ", getScreenWidth() *9/32 , getScreenHeight() *17/32, getScreenWidth() /2, getScreenHeight() /8,Color.white, getScreenHeight() / 20f);
        add(saveName);
        //Kayıtlı oyunları listelemek için bir JComboBox oluşturur.
        JComboBox<String> savesBox = new JComboBox<>();
        savesBox.setBounds(getScreenWidth() /2 - getScreenWidth() /8, getScreenHeight() /2, getScreenWidth() /4, getScreenHeight() /8);
        // Yazı tipini ayarlıyoruz.
        savesBox.setFont(Main.getPixelFont());
        //Oyun kayıtlarının bulunduğu dizindeki dosyaları alır.
        File[] files = Game.getSaves().listFiles();
        //Eğer dizinde dosya varsa: Her bir dosyanın adını JComboBox'a ekler.
        if(files != null) {
            for (File file : files) {
                savesBox.addItem(file.getName());
            }
        }
        add(savesBox);
        //"Back" butonu oluşturur. (ana menüye dönmeyi sağlar)
        MenuButton backButton = new MenuButton("Back", getScreenWidth() /2 , getScreenHeight() *27/32 , Main.getPixelFont(),Color.lightGray);
        add(backButton);

        //JComboBox üzerindeki bir kayıt seçildiğinde yapılacak işlemler.
        savesBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Seçilen öğe alınır.
                Object selectedItem = savesBox.getSelectedItem();
                // Eğer bir öğe seçildiyse:
                if (selectedItem != null) {
                    //Kayıt adı alınır.
                    String saveName = selectedItem.toString();
                    //Seçilen nesne şu anki kayıt olarak ayarlanır.
                    Game.setNowSaveFile(saveName);
                    //Kayıt dosyası okunur.
                    Game.fileRead(saveName);
                    //Shop ekranına geçiş yapılır.
                    Game.setScreen(Game.newScreen("ShopScreen"));
                } else { // Eğer hiçbir öğe seçilmediyse:
                    // Konsola bilgi yazdırılıyor.
                    System.out.println("Hiçbir kayıt seçilmedi.");
                }
            }
        });
        //"Back" butonuna tıklanınca yapılacak işlemler.
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Ana menü ekranına geçiş yapılır.
                Game.setScreen(Game.newScreen("MainMenu"));
            }
        });
    }


}
