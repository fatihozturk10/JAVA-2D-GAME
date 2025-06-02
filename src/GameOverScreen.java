import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//GameOverScreen sınıfı, oyuncunun oyunu kaybettiği durumda gösterilen ekranı temsil eder.
public class GameOverScreen extends Screen{
    public GameOverScreen() {
        //Ekranın arka plan rengini ayarlar.
        setBackground(Color.DARK_GRAY);
        //Ekranın başlığı olarak "Game Over" yazısını ekler.
        MenuLabel title = new MenuLabel("Game Over", getScreenWidth()/2 , getScreenHeight()*3 /32, getScreenWidth()/2,getScreenHeight()/10,Color.black,getScreenHeight() /12f);
        add(title);
        //"New Game" düğmesini oluşturur ve ekler.
        MenuButton nGameButton = new MenuButton("New Game", getScreenWidth() /2, getScreenHeight() *15/32,Main.getPixelFont(),Color.blue);
        add(nGameButton);
        //"Main Menu" düğmesini oluşturur ve ekler.
        MenuButton mainMenuButton = new MenuButton("Main Menu", getScreenWidth() /2 , getScreenHeight() *21/32 , Main.getPixelFont(),Color.lightGray);
        add(mainMenuButton);
        //"Exit" düğmesini oluşturur ve ekler.
        MenuButton exitButton = new MenuButton("Exit", getScreenWidth() /2, getScreenHeight() *27/32, Main.getPixelFont(),Color.RED);
        add(exitButton);
        //"New Game" düğmesine tıklandığında yapılacak işlemler.
        nGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Yeni oyun ekranına geçiş yapılır.(yeni kayıt açma ekranı)
                Game.setScreen(Game.newScreen("NewGameScreen"));
            }
        });
        //"Main Menu" düğmesine tıklandığında yapılacak işlemler.
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Ana menü ekranına geçiş yapılır.
                Game.setScreen(Game.newScreen("MainMenu"));
            }
        });
        //"Exit" düğmesine tıklandığında yapılacak işlemler.
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Uygulamayı sonlandırır.
                System.exit(0);
            }
        });

    }
}
