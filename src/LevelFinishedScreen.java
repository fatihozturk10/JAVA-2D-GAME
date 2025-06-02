import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//LevelFinishedScreen sınıfı, bir seviyenin tamamlandığını gösteren ekranı temsil eder.
public class LevelFinishedScreen extends Screen{
    public LevelFinishedScreen() {
        //Ekranın arka plan rengini ayarlar.
        setBackground(Color.DARK_GRAY);
        //Ekranın başlığı olarak "Level X Finished" yazısını ekler. (burada X mevcut seviyedir)
        MenuLabel title = new MenuLabel("Level " + Game.getLevel() +" Finished", getScreenWidth()/2 , getScreenHeight()*3 /32, getScreenWidth()/2,getScreenHeight()/10,Color.black,getScreenHeight() /12f);
        add(title);
        //"Shop" düğmesini oluşturur ve ekler. (shop ekranına gitmeyi sağlar)
        MenuButton shopButton = new MenuButton("Shop", getScreenWidth() /2, getScreenHeight() *27/32,Main.getPixelFont(),Color.orange);
        add(shopButton);
        //Öldürülen düşman sayısını gösteren metni ekler.
        MenuLabel killedEnemyText = new MenuLabel("Killed Enemy: " + Game.getKill(), getScreenWidth() *16/32 , getScreenHeight() *10/32, getScreenWidth() /2, getScreenHeight() /8,Color.white, getScreenHeight() / 20f);
        add(killedEnemyText);
        //Ateşlenen mermi sayısını gösteren metni ekler.
        MenuLabel bulletsFiredText = new MenuLabel("Bullets Fired: " + Game.getBulletsFired(), getScreenWidth() *16/32 , getScreenHeight() *16/32, getScreenWidth() /2, getScreenHeight() /8,Color.white, getScreenHeight() / 20f);
        add(bulletsFiredText);
        //"Game Saved" yazısını gösteren metni ekler.
        MenuLabel gameSavedText = new MenuLabel("Game Saved", getScreenWidth() *27/32 , getScreenHeight() *2/32, getScreenWidth() /2, getScreenHeight() /8,Color.orange, getScreenHeight() / 20f);
        add(gameSavedText);
        //"Shop" düğmesine tıklandığında yapılacak işlemler.
        shopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Shop ekranına geçiş yapılır.
                Game.setScreen(Game.newScreen("ShopScreen"));
            }
        });
        //Seviye sayısını bir artırır.
        Game.setLevel(Game.getLevel()+1);
        //Şu anki oyun durumunu kayıt dosyasına yazar.
        Game.fileWrite(Game.getNowSaveFile());
    }
}
