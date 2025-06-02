import javax.swing.*;
import java.awt.*;
import java.io.*;
// Game ana sınıfı, oyun ekranları, dosya işlemleri ve oyun içi değişkenlerin yönetildiği sınıftır.
public class Game {
    //Oyunun save dosyalarını tutacak dizin
    private static File saves = new File("saves");
    //JFrame nesnesi (ekran)
    private static JFrame frame;
    //Oyuncunun altın miktarı
    private static int gold;
    //Öldürülen düşman sayısı
    private static int kill;
    //Atılan mermi sayısı
    private static int bulletsFired;
    //Oyun seviyesinin numarası
    private static int level;
    //Oyuncunun ve merminin hız değerleri
    private static int playerSpeed;
    private static int bulletSpeed;
    //Ateş hızını kontrol eden değişken
    private static double fireCooldown;
    // Teleport yeteneği ve gizlilik yeteneği kontrol değişkenleri
    private static boolean canTeleport;
    private static boolean canInvisibility;
    //Şu anki kaydetme dosyasının adı
    private static String nowSaveFile;
    //Oyunun mevcut ekranı
    private static GameScreen gameScreen;
    //Ekran boyutlarını tutan sabit değer
    private final static Dimension defaultScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
    //Yeni ekrana geçiş veya oluşturma metodu
    public static Screen newScreen(String screenName) {
        try {
            switch (screenName) {
                case "MainMenu":
                    //Ana menü ekranı oluşturur ve döndürür.
                    return new MainMenu();
                case "GameScreen":
                    //Eğer duraklama ekranındaysa mevcut GameScreen'i döndür.
                    if(PauseScreen.isInPause()){
                        return gameScreen;
                    }
                    // Yeni GameScreen ekranı oluşturur ve döndürür.
                    return gameScreen = new GameScreen();
                case "SettingsScreen":
                    // Ayarlar ekranı oluşturur ve döndürür
                    return new SettingsScreen();
                case "PauseScreen":
                    // Duraklama ekranı oluşturur ve döndürür
                    return new PauseScreen();
                case "LevelFinishedScreen":
                    // Seviye bitirme oluşturur ve döndürür
                    return new LevelFinishedScreen();
                case "ShopScreen":
                    // Mağaza ekranı oluşturur ve döndürür
                    return new ShopScreen();
                case "GameOverScreen":
                    // Oyun bitiş ekranı oluşturur ve döndürür
                    return new GameOverScreen();
                case "NewGameScreen":
                    setDefaultValues();
                    // Yeni oyun ekranı oluşturur ve döndürür
                    return new NewGameScreen();
                case "LoadGameScreen":
                    // Yükleme ekranı oluşturur ve döndürür
                    return new LoadGameScreen();
                case "GameFinished":
                    // Oyun bitti (tamamlandı) ekranı oluşturur ve döndürür
                    return new GameFinished();

            }
            //Eğer hiçbir değer örtüşmezse özel hata fırlatır.
            throw new ScreenNotFound("Ekran bulunamadı");
        } catch (ScreenNotFound e) {
            //Hata mesajını gösterir.
            System.out.println(e.getMessage());
            // Uygulamayı sonlandır.
            System.exit(0);
        } catch (Exception e) {
            System.out.println("Ekran açılırken bir hata oluştu");
            //Uygulamayı sonlandır.
            System.exit(0);
        }
        return null;

    }
    //Oyunu başlatma metodu
    public static void startGame(){
        //Ekran boyutlarını ayarlar. Genişlik ve yükseklik değerlerini alır.
        int screenWidth = defaultScreenSize.width;
        int screenHeight = defaultScreenSize.height;

        //Yeni bir frame (ekran) oluşturur.
        frame = new JFrame();
        //Ekran büyüklüğünü default değerlere göre ayarlar.
        frame.setSize(screenWidth,screenHeight);
        //Ekranın elle yeniden boyutlandırılmasını engeller
        frame.setResizable(false);
        //Ekranın üstündeki çerçeveyi kaldırır.
        frame.setUndecorated(true);
        //Ekranı ortalar.
        frame.setLocationRelativeTo(null);
        //Kaydetme klasörünü kontrol et yoksa oluştur.
        createSaveFolder();

        //Ana menü ekranını yükle.
        Screen screen = newScreen("MainMenu");
        setScreen(screen);

        //İmleci ayarlar.
        frame.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        //Frame'i görünür yapar.
        frame.setVisible(true);

    }

    //Oyunun ekran boyutunu verilen değere göre günceller
    public static void setScreenSize(int width,int height){
        //Ekran boyutunu tekrar ayarlar
        frame.setSize(width,height);
        //Ekranı yeniden merkezler (yeni ekran boyutunda ekran tam ortalanmamış olabilir)
        frame.setLocationRelativeTo(null);
        //Ayarlar ekranını yeniden oluşturur (güncel ekran boyutlarını uygulaması için)
        setScreen(newScreen("SettingsScreen"));
    }

    public static void setScreen(Screen screen){
        //Aktif olan ekrandaki tüm bileşenleri kaldır
        frame.getContentPane().removeAll();
        //Yeni JPanel nesnesini (ekran) frame'in içine atar.
        frame.add(screen);
        //Bileşenlerin yerleşim düzenini günceller.
        frame.revalidate();
        //Ekranı yeniden çizer.
        frame.repaint();
    }
    //Save dosyasını okuma metodu
    public static  void fileRead(String fileName){
        BufferedReader reader;
        File file = new File("saves",fileName);
        try {
            reader = new BufferedReader(new FileReader(file));
            level = Integer.parseInt(reader.readLine().split(" ")[1]); //Seviye numarasını okur
            playerSpeed = Integer.parseInt(reader.readLine().split(" ")[1]); //Oyuncu hızını okur
            fireCooldown = Double.parseDouble(reader.readLine().split(" ")[1]); //Ateş hızı ayarını okur
            bulletSpeed = Integer.parseInt(reader.readLine().split(" ")[1]);    //Mermi hızını okur
            canTeleport = Boolean.parseBoolean(reader.readLine().split(" ")[1]);    //Teleport yeteneğini okur
            canInvisibility = Boolean.parseBoolean(reader.readLine().split(" ")[1]);    //Gizliliği okur
            gold = Integer.parseInt(reader.readLine().split(" ")[1]);   //Altın miktarını okur
            bulletsFired = Integer.parseInt(reader.readLine().split(" ")[1]);   //Ateş edilen mermi sayısını okur
            kill = Integer.parseInt(reader.readLine().split(" ")[1]);   //Öldürülen düşman sayısını okur
            reader.close(); //Okuma işlemini kapatır
        }
        catch (Exception e){
            //Hata durumunda konsola mesaj gönderir
            System.out.println("Save dosyası bozuk!");
            //Uygulamayı sonlandır
            System.exit(0);
        } finally {
            //İşlem tamamlandı mesajı
            System.out.println("İşlem tamamlandı");
        }
    }
    //Save dosyasını yazma metodu
    public static void fileWrite(String fileName){
        File saves = new File("saves");
        File directory = new File(saves,fileName);
        PrintWriter writer = null;
        try {
            //Dosyayı yazmak için PrintWriter oluştur.
            writer = new PrintWriter(directory);

        } catch (FileNotFoundException e) {
            //Hata durumunda konsola mesaj yaz.
            System.out.println("Dosya oluşturulurken bir hata oluştu");
            //Uygulamayı sonlandır.
            System.exit(0);
        }
        writer.println("level " + level);  // Seviye bilgisini dosyaya yazar.
        writer.println("playerSpeed " + playerSpeed);  // Oyuncu hızını dosyaya yazar.
        writer.println("fireCooldown " + fireCooldown);  // Ateş hızı bilgilerini yazar.
        writer.println("bulletSpeed " + bulletSpeed);  // Mermi hızını yazar.
        writer.println("teleport " + canTeleport);  // Teleport bilgilerini yazar.
        writer.println("invisibility " + canInvisibility);  // Gizlilik bilgilerini yazar.
        writer.println("gold " + gold);  // Altın miktarını yaz.
        writer.println("bulletsFired " + bulletsFired);  // Atılan mermi sayısını yazar.
        writer.println("killedEnemies " + kill);  // Öldürülen düşman sayısını yazar.
        writer.close();  // Yazma işlemini tamamlar ve kapatır.
    }
    //Varsayılan değerleri ayarlama metodu
    public static void setDefaultValues(){
        gold = 0;
        kill = 0;
        bulletsFired = 0;
        level = 1;
        playerSpeed = 3;
        bulletSpeed = 5;
        fireCooldown = 0.6;
        canTeleport = false;
        canInvisibility = false;
    }
    //Sayıyı belirlenen basamağa yuvarlamak için bir metod
    //Çalışma mantığı: round(5.674545 ,2); 5.674545 * 10^2 --> 567.4545 (tam kısmı al) --> 567 / 10^2 --> 5.67
    public static double round(double number, int digit){
        double multiplier = Math.pow(10,digit);
        return Math.round(number*multiplier)/multiplier;
    }
    // Kaydetme klasörünü oluşturma (kontrol etme) metodu
    public static void createSaveFolder(){
        if (!saves.exists()) {
            // Eğer "saves" klasörü yoksa oluştur
            boolean created = saves.mkdir();
            if (created) {
                System.out.println("Saves klasörü başarıyla oluşturuldu.");
            } else {
                System.out.println("Saves klasörü oluşturulurken bir hata oluştu!");
            }
        }
    }
    //Getter ve Setterlar
    public static File getSaves() {
        return saves;
    }

    public static void setSaves(File saves) {
        Game.saves = saves;
    }

    public static GameScreen getGameScreen() {
        return gameScreen;
    }

    public static void setGameScreen(GameScreen gameScreen) {
        Game.gameScreen = gameScreen;
    }

    public static Dimension getDefaultScreenSize() {
        return defaultScreenSize;
    }


    public static int getGold() {
        return gold;
    }

    public static void setGold(int gold) {
        Game.gold = gold;
    }

    public static int getKill() {
        return kill;
    }

    public static void setKill(int kill) {
        Game.kill = kill;
    }

    public static int getBulletsFired() {
        return bulletsFired;
    }

    public static void setBulletsFired(int bulletsFired) {
        Game.bulletsFired = bulletsFired;
    }

    public static int getLevel() {
        return level;
    }

    public static void setLevel(int level) {
        Game.level = level;
    }

    public static int getPlayerSpeed() {
        return playerSpeed;
    }

    public static void setPlayerSpeed(int playerSpeed) {
        Game.playerSpeed = playerSpeed;
    }

    public static int getBulletSpeed() {
        return bulletSpeed;
    }

    public static void setBulletSpeed(int bulletSpeed) {
        Game.bulletSpeed = bulletSpeed;
    }

    public static double getFireCooldown() {
        return fireCooldown;
    }

    public static void setFireCooldown(double fireCooldown) {
        Game.fireCooldown = fireCooldown;
    }

    public static boolean isCanTeleport() {
        return canTeleport;
    }

    public static void setCanTeleport(boolean canTeleport) {
        Game.canTeleport = canTeleport;
    }

    public static boolean isInvisibility() {
        return canInvisibility;
    }

    public static void setInvisibility(boolean canInvisibility) {
        Game.canInvisibility = canInvisibility;
    }

    public static String getNowSaveFile() {
        return nowSaveFile;
    }

    public static void setNowSaveFile(String nowSaveFile) {
        Game.nowSaveFile = nowSaveFile;
    }
}
