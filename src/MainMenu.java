import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
//MainMenu sınıfı, oyunun ana menüsünü temsil eder.
public class MainMenu extends Screen {
    //MainMenu yapıcı metodu, ana menü ekranını oluşturur.
    MainMenu() {
        //Ekranın arka plan rengini ayarlar.
        setBackground(Color.DARK_GRAY);
        //Ekranın başlığı olarak "Game Name" yazısını ekler.
        MenuLabel title = new MenuLabel("Hamstrong", getScreenWidth() /2, getScreenHeight() *3/32, getScreenWidth() /2, getScreenHeight() /10,Color.green, getScreenHeight() /10f);
        add(title);
        //"New Game" butonu oluşturur.
        MenuButton nGameButton = new MenuButton("New Game", getScreenWidth() /2, getScreenHeight() *9/32,Main.getPixelFont(),Color.blue);
        add(nGameButton);
        //"Load Game" butonu oluşturur.
        MenuButton loadGameButton = new MenuButton("Load Game", getScreenWidth() /2, getScreenHeight() *15/32,Main.getPixelFont(),Color.orange);
        add(loadGameButton);
        //"Settings" butonu oluşturur.
        MenuButton settingsButton = new MenuButton("Settings", getScreenWidth() /2, getScreenHeight() *21/32,Main.getPixelFont(),Color.GRAY);
        add(settingsButton);
        //"Exit" butonu oluşturur.
        MenuButton exitButton = new MenuButton("Exit", getScreenWidth() /2, getScreenHeight() *27/32, Main.getPixelFont(),Color.RED);
        add(exitButton);
        //Kayıtlı oyunları kontrol eder. Kayıt dosyaları yoksa "Load Game" butonunu devre dışı bırakır.
        File[] files = Game.getSaves().listFiles();
        if(files != null){
            //Kayıt var ise etkinleştirir.
            loadGameButton.setEnabled(files.length > 0);
        }
        else {
            //Kayıt yok ise devre dışı bırakır.
            loadGameButton.setEnabled(false);
        }

        //"New Game" butonuna tıklanınca yapılacak işlemler.
        nGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Yeni oyun ekranına geçiş yapılır.(yeni kayıt açma ekranı)
                Game.setScreen(Game.newScreen("NewGameScreen"));
            }
        });
        //"Load Game" butonuna tıklanınca yapılacak işlemler.
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Kayıt yükleme ekranına geçiş yapılır.
                Game.setScreen(Game.newScreen("LoadGameScreen"));
            }
        });

        //"Settings" butonuna tıklanınca yapılacak işlemler.
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Ayarlar ekranına geçiş yapılır.
                Game.setScreen(Game.newScreen("SettingsScreen"));
            }
        });
        //"Exit" butonuna tıklanınca yapılacak işlemler.
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Uygulamayı sonlandırır.
                System.exit(0);
            }
        });
    }
}
