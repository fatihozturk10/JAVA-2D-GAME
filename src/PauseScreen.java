import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//PauseScreen sınıfı, oyunun duraklatıldığı ekranı temsil eder.
public class PauseScreen extends Screen{
    //Oyunun duraklatıldığını belirtir.
    private static boolean inPause;
    PauseScreen() {
        //Oyunun duraklatıldığını gösterecek şekilde ayarlanır.
        inPause = true;
        //Oyuncunun basılı tuttuğu tuşlar temizlenir. (Yeni ekran açıldığında oluşan istemsiz hareket hatasını engellemek için)
        Player.getPressedKeys().clear();
        //Ekranın arka plan rengini ayarlar.
        setBackground(new Color(0,0,0,0.5f));
        //Ekranın başlığı olarak "Pause" yazısı eklenir.
        MenuLabel title = new MenuLabel("Pause", getScreenWidth()/2 , getScreenHeight()*9 /32, getScreenWidth()/2,getScreenHeight()/10,Color.black,getScreenHeight() /12f);
        add(title);
        //"Resume" butonu eklenir. (oyunu devam ettirir)
        MenuButton resumeButton = new MenuButton("Resume", getScreenWidth() /2 , getScreenHeight() *15/32 , Main.getPixelFont(),Color.green);
        add(resumeButton);
        //"Main Menu" butonu eklenir. (ana menüye dönmeyi sağlar)
        MenuButton mainMenuButton = new MenuButton("Main Menu", getScreenWidth() /2 , getScreenHeight() *21/32 , Main.getPixelFont(),Color.lightGray);
        add(mainMenuButton);
        //"Exit" butonu eklenir. (oyundan çıkış yapmayı sağlar)
        MenuButton exitButton = new MenuButton("Exit", getScreenWidth() /2 , getScreenHeight() *27/32 , Main.getPixelFont(),Color.RED);
        add(exitButton);

        //"Resume" butonuna tıklanınca yapılacak işlemler.
        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Oyun ekranına geri dönülür.
                Game.setScreen(Game.newScreen("GameScreen"));
                //Duraklatma durumunu değiştirir. (yeni ekrana geçildikten sonra false yapmalıyız)
                inPause = false;
            }
        });
        //"Main Menu" butonuna tıklanınca yapılacak işlemler.
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Duraklatma durumunu değiştirir.
                inPause = false;
                //Ana menü ekranına geçilir.
                Game.setScreen(Game.newScreen("MainMenu"));
            }
        });

        //"Exit" butonuna tıklanınca yapılacak işlemler.
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Uygulama kapatılır.
                System.exit(0);
            }
        });
    }
    //Oyunun duraklatılmış (pause) durumda olup olmadığını kontrol eden getter metodu
    public static boolean isInPause() {
        //Duraklatma durumunu döndürür (true = duraklatılmış, false = devam ediyor)
        return inPause;
    }
    //Oyunun duraklatma durumunu ayarlayan setter metodu
    public static void setInPause(boolean inPause) {
        // Duraklatma durumunu günceller
        PauseScreen.inPause = inPause;
    }
}
