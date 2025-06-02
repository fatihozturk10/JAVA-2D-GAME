import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
/*
 * GameScreen sınıfı, oyunun ana oyun ekranını temsil eder.
 * Oyun döngüsünü ve çeşitli oyun mekaniklerini içerir.
 */
public class GameScreen extends Screen implements ActionListener{
    private static final int MILLISECONDS_IN_A_SECOND = 1000; //1 saniye içerisindeki milisaniye sayısı
    private static long currentTime; //Mevcut zamanın kaydı
    private long lastEnemySpawnTime; //Son düşman oluşturma zamanı
    private long lastTeleportTime; //Son teleport kullanımı zamanı
    private long lastFireTime; //Son ateş etme zamanı
    private long lastInvisibilityTime; //Son görünmez olunan zaman
    private long invisibilityStartTime; //Görünmezliğin başladığı zaman
    private long lastUltiTime; //Ultideki en son mermi sekansının gerçekleştiği zaman
    private long ultiStartTime; //Ultimate kullanmaya başlanılan zaman
    private final Timer animate; //Animasyonlar için kullanılan zamanlayıcı
    private final Timer gameLoop; //Oyun döngüsü için kullanılan zamanlayıcı
    private final Player player; //Oyuncu karakteri
    private MenuLabel goldText; //Altın sayısını gösteren metin etiketi
    private final ArrayList<Enemy> enemies; //Düşmanları saklayan liste
    private static ArrayList<Bullet> bullets; //Mermileri saklayan liste
    private final ArrayList<SpawnMarker> spawnMarkers; //Spawn bölgelerini saklayan liste
    private static ArrayList<Bomb> bombs; //Bombaları saklayan liste
    private boolean isFireReady; //Ateş etme mekanizmasının hazır olup olmadığını kontrol eder
    private boolean canTeleport; //Oyuncunun teleport kullanıp kullanamayacağını belirler
    private boolean invisibility; //Görünmezlik yeteneğinin aktif olup olmadığını belirler
    private boolean isInvisibilityStarted; //Görünmezliğin başlatılıp başlatılmadığını kontrol eder
    private boolean ulti; //Ulti yeteneğinin kullanılabilir olup olmadığını kontrol eder
    private boolean isUltiStarted; //Ulti yeteneğinin aktif olup olmadığını kontrol eder
    private boolean isBossLevel; //Şu anki seviyenin bir boss seviyesi olup olmadığını belirler
    private final int fireCooldownMillis = (int) (Game.getFireCooldown() * MILLISECONDS_IN_A_SECOND); //Ateş etme yeteneği bekleme süresi
    private ProgressBar teleportBar; //Teleport yeteneğinin dolum çubuğu
    private ProgressBar invisibilityBar; //Görünmezlik yeteneğinin dolum çubuğu
    private final ProgressBar ultiBar; //Ulti yeteneğinin dolum çubuğu
    private final ProgressBar fireCooldownBar; //Ateş etme yeteneğinin bekleme çubuğu
    private double enemySpawnTime; //Düşmanların oluşturulma aralığı
    private int levelSecond; //Seviye süresi
    private MenuLabel timeText; //Kalan süreyi gösteren metin
    private long updateTime = System.currentTimeMillis(); //Zaman güncelleme değişkeni
    private Boss boss; //Boss
    private ProgressBar bossBar; //Boss'un can çubuğu
    //GameScreen yapıcı metodu, oyun ekranını ve oyun mekaniklerini başlatır.
    GameScreen() {
        //Seviye ayarlarını yapar
        setLevelSettings();
        //Arka plan rengini ve odaklanma özelliklerini ayarlar
        setBackground(Color.black);
        setFocusable(true);
        //Ekrana bir durdurma düğmesi (pause button) ekler
        MenuButton pauseButton = new MenuButton("P", getScreenWidth() *29/32, getScreenHeight()*2 /32,getScreenWidth()*3/32,getScreenHeight()/16, Main.getPixelFont(),Color.orange);
        add(pauseButton);
        //Oyun içi objeleri tutan listeleri oluşturur
        bombs = new ArrayList<>();
        spawnMarkers = new ArrayList<>();
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        //Oyuncu nesnesini oluşturur
        player = new Player(getScreenWidth() /2, getScreenHeight() /2, getScreenWidth() /25, getScreenHeight() /10);

        //Eğer seviye Boss seviyesi ise :
        if(isBossLevel){
            //Boss ve Boss Bar'ı oluşturur ve ekler
            boss = new Boss((double) getScreenWidth()/4, (double) getScreenHeight() /2,getScreenWidth()/4,getScreenHeight()*3/4);
            bossBar = new ProgressBar(0,100,getScreenWidth()/2,getScreenHeight()/32,getScreenWidth()*5/8,getScreenHeight()/64,100,Color.red,Color.darkGray);
            add(bossBar);
            //Oyuncunun başlangıç konumunu değiştirir
            player.setX((double) (getScreenWidth() * 7) /8);
        }
        else {
            //Zaman ve altın metinlerini oluşturur ve ekler
            timeText = new MenuLabel(levelSecond + "", getScreenWidth() /2 , getScreenHeight() /16, getScreenWidth() /8, getScreenHeight() /10,Color.blue, getScreenHeight() /25f);
            add(timeText);
            goldText = new MenuLabel("Gold: " + Game.getGold(), getScreenWidth() *3/32 , getScreenHeight() *2/32 , getScreenWidth() /4, getScreenHeight() /8,Color.yellow, getScreenHeight() /16f);
            add(goldText);
            //Görünmezlik özelliği varsa:
            if(Game.isInvisibility()){
                //Görünmezlik barı oluşturur ve ekler
                invisibilityBar = new ProgressBar(0, 100,getScreenWidth() * 3/32, getScreenHeight() * 6 / 32 , getScreenWidth() / 10, getScreenHeight() / 40 , 100,Color.green ,Color.pink);
                add(invisibilityBar);
            }
        }

        //Teleport özelliği kontrolü
        if(Game.isCanTeleport()){
            //Teleport barı oluşturur ve ekler
            teleportBar = new ProgressBar(0, 100,getScreenWidth() * 3/32, getScreenHeight() * 5 / 32 , getScreenWidth() / 10, getScreenHeight() / 40 , 100,Color.cyan ,Color.orange);
            add(teleportBar);
        }
        //Ulti ve ateş etme barlarını oluşturur ve ekler
        ultiBar = new ProgressBar(0, 100,getScreenWidth() * 16/32, getScreenHeight() * 29 / 32 , getScreenWidth() / 10, getScreenHeight() / 80 , 0,Color.yellow ,Color.lightGray);
        ultiBar.setBounds(getScreenWidth() * 16 / 32 - getScreenWidth() / 20, getScreenHeight() * 29 / 32 - getScreenHeight() / 160, getScreenWidth() / 10, getScreenHeight() / 80);
        add(ultiBar);
        fireCooldownBar = new ProgressBar(0, 100,getScreenWidth() * 16/32, getScreenHeight() * 30 / 32 , getScreenWidth() / 10, getScreenHeight() / 80 , 100,Color.gray ,Color.lightGray);
        add(fireCooldownBar);

        //Oyun döngüsü zamanlayıcısını başlatır
        gameLoop = new Timer(1000/144,this);
        //Klavye dinleyicilerini ekler
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                //Tuşa basıldığında tuşu basılan tuşlar listesine ekler.
                Player.getPressedKeys().add(e.getKeyCode());
                //Eğer E tuşuna basıldıysa ve teleport özelliği aktifse :
                if(e.getKeyCode() == KeyEvent.VK_E && canTeleport){
                    //O anki ekrana göre mouse konumunu alır ve değişkenlere atar.
                    int targetX = (int) GameScreen.super.getMousePosition().getX();
                    int targetY = (int) GameScreen.super.getMousePosition().getY();
                    //Oyuncuyu mouse konumuna ışınlar.
                    player.teleport(targetX,targetY);
                    //Işınlanma özelliğini deaktif yapar
                    canTeleport = false;
                    //Işınlanma barının değerini 0 yapar. (Barı boş duruma getirir.)
                    teleportBar.setValue(0);
                    //O anki zamanı alır ve değişkene atar.
                    lastTeleportTime = System.currentTimeMillis();
                }
                //Eğer Q tuşuna basıldıysa ve görünmezlik özelliği aktifse :
                if(e.getKeyCode() == KeyEvent.VK_Q && invisibility){
                    //Görünmezlik özelliğini deaktif yapar
                    invisibility = false;
                    //Görünmezliğin başladığını belirten değişkeni true yapar
                    isInvisibilityStarted = true;
                    //O anki zamanı alır ve değişkene atar.
                    invisibilityStartTime = System.currentTimeMillis();
                    //Bomber ve Fly düşmanlarının hızlarını sıfır yapar
                    Bomber.setSpeed(0);
                    Fly.setSpeed(0);
                }
            }
        });

        //Yeni bir tuş dinleyicisi ekler
        addKeyListener(new KeyAdapter() {
            //Bir tuş serbest bırakılınca:
            public void keyReleased(KeyEvent e) {
                //Tuş serbest bırakıldığında o tuşu basılan tuşlar listesinden kaldırır
                Player.getPressedKeys().remove(e.getKeyCode());
            }
        });
        //Yeni bir mouse dinleyicisi ekler
        addMouseListener(new MouseAdapter() {
            //Mouse'a tıklanınca:
            public void mousePressed(MouseEvent e) {
                //Odaklanmayı ayarlar
                requestFocusInWindow();
                //Oyun döngülerini başlatır
                gameLoop.start();
                animate.start();
                //Sol tıka basılırsa ve Ateş etme özelliği aktifse:
                if(e.getButton() == MouseEvent.BUTTON1 && isFireReady) {
                    //Mermi oluşturur
                    Bullet bullet = new Bullet(player.getX(), player.getY(), getScreenWidth() / 60, getScreenHeight() / 30,e.getX(), e.getY());
                    //Mermi cooldown barını sıfırlar
                    fireCooldownBar.setValue(0);
                    //Son ateş edilen zamanı şu anki zaman olarak ayarlar
                    lastFireTime = System.currentTimeMillis();
                    //Ateş etme özelliğini devre dışı bırakır
                    isFireReady = false;

                }
                //Sağ tıka basılırsa ve ulti özelliği aktifse :
                if(e.getButton() == MouseEvent.BUTTON3 && ulti){
                    //Ulti özelliğini deaktif eder
                    ulti = false;
                    //Ultinin başladığını belirten değişkeni true yapar
                    isUltiStarted = true;
                    //Ultinin başladığı zamanı kaydeder
                    ultiStartTime = System.currentTimeMillis();
                }
            }
        });
        //Animasyon zamanlayıcısını başlatır
        animate = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Tüm oyun elemanlarını animasyon için günceller
                for(Enemy enemy: enemies){
                    enemy.animate();
                }
                player.animate();
                for(Bullet bullet: bullets){
                    bullet.animate();
                }
                for(SpawnMarker spawnMarker: spawnMarkers){
                    spawnMarker.animate();
                }
                for (Bomb bomb : bombs){
                    bomb.animate();
                }
                if(boss != null) {
                    boss.animate();
                }
            }
        });
        //Pause düğmesi için eylem dinleyicisi ekler
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Pause butonuna tıklanırsa oyun durdulur. (Pause ekranı açılır)
                gamePaused();
            }
        });
    }


    //Her döngüde yeniden çizim işlemi için bu fonksiyon çağırılır
    //Çizim işlemini gerçekleştiren fonksiyon(düzenli olması amacıyla draw fonksiyonunu çağırır)
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Çizim için farklı bir fonksiyonu çağırır
        draw(g);
    }
    //Çizim işlemini gerçekleştiren fonksiyon
    public void draw(Graphics g) {
        //Oyuncuyu çizer
        player.draw(g);
        //Tüm düşmanları çizer
        for(Enemy enemy : enemies) enemy.draw(g);
        //Tüm mermileri çizer
        for(Bullet bullet : bullets)bullet.draw(g);
        //Spawn noktalarını çiz
        for(SpawnMarker spawnMarker : spawnMarkers) spawnMarker.draw(g);
        //Bombaları çiz
        for (Bomb bomb : bombs) bomb.draw(g);
        if(boss != null) {
            //Boss varsa onu da çiz
            boss.draw(g);
        }
    }

    //gameLoop yani oyun döngüsünün bulunduğu fonksiyon. Her framede bu fonksiyon çağrılır
    public void actionPerformed(ActionEvent e) {
        //Şu anki zamanı al
        currentTime = System.currentTimeMillis();
        //Eğer boss seviyesi ise:
        if(isBossLevel){
            //Boss hareket eder
            boss.move(player);
            //Boss ateş eder
            boss.attack(player);
        }
        else //Boss seviyesi değilse
        {
            //Düşman spawnlar
            enemySpawn();
            //Görünmezlik özelliği başlatılmışsa
            if(isInvisibilityStarted){
                //Görünmezliğin ne kadar süredir aktif olduğunu hesaplar
                int elapsed = (int) (currentTime - invisibilityStartTime);
                //Görünmezliğin toplam aktif kalma süresi (3 saniye)
                int invisibilityCooldownMillis = 3 * MILLISECONDS_IN_A_SECOND;
                //Görünmezlik yeteneğinin kalan kullanım süresini yüzdesel olarak hesaplar ve bara yansıtır.
                int progress = 100 - Math.min(100, (elapsed * 100) / invisibilityCooldownMillis);
                //Hesaplanan yüzdelik değeri görünmezlik barına uygular
                invisibilityBar.setValue(progress);
                //Görünmezliğin son aktif zamanını günceller
                lastInvisibilityTime = System.currentTimeMillis();
                //Görünmezlik barı 0 olursa:
                if(invisibilityBar.getValue() == 0){
                    //Görünmezlik sona erdi
                    isInvisibilityStarted = false;
                    //Düşmanların hızını varsayılan değerlerine döndürür.
                    Bomber.setSpeed(Bomber.getDefaultSpeed());
                    Fly.setSpeed(Fly.getDefaultSpeed());
                }
            }//Görünmezlik başlamamışsa:
            else {
                //Düşmanları oyuncuya göre hareket ettir
                for(Enemy enemy: enemies){
                    enemy.move(player);
                    //Düşman oyuncu ile temas ediyorsa oyunu bitir
                    if(player.isCollidingWith(enemy)){
                        gameOver();
                    }
                }
            }
            //Zamanlayıcı güncellemesi
            if(currentTime - updateTime > MILLISECONDS_IN_A_SECOND) {
                //Güncellenen zamanı kaydeder
                updateTime = System.currentTimeMillis();
                //Zamanı 1 azaltır
                int time = Integer.parseInt(timeText.getText()) - 1;
                //Zaman metnini günceller
                timeText.setText(time + "");
                //Eğer zaman 0'a ulaştıysa :
                if (time == 0) {
                    //Bölümü sona erdirir
                    levelFinished();
                }
            }
        }
        //Eğer teleport (ışınlanma) yapılamıyorsa ve oyun içinde teleport yeteneği etkinse:
        if (!canTeleport && Game.isCanTeleport()) {
            //Son teleport zamanından itibaren geçen süreyi hesaplar
            int elapsed = (int) (currentTime - lastTeleportTime);
            //Teleport bekleme süresini belirler (5 saniye)
            int teleportCooldownMillis = 5 * MILLISECONDS_IN_A_SECOND;
            //Teleport ilerleme yüzdesini hesaplar, maksimum %100 olur
            int progress = Math.min(100, (elapsed * 100) / teleportCooldownMillis);
            //Teleport barını günceller
            teleportBar.setValue(progress);
            //Eğer geçen süre teleport bekleme süresini geçtiyse:
            if (elapsed >= teleportCooldownMillis) {
                //Teleport yeteneğini aktif hale getir
                canTeleport = true;
                //Teleport barını tamamen doldur
                teleportBar.setValue(100);
            }
        }
        //Eğer ulti başlatıldıysa:
        if(isUltiStarted){
            //Son ulti başlama zamanından itibaren geçen süreyi hesaplar
            int elapsed = (int) (currentTime - ultiStartTime);
            //Ulti süresini belirler (3 saniye)
            int ultiCooldownMillis = 3 * MILLISECONDS_IN_A_SECOND;
            //Ultimate yeteneğinin kalan kullanım süresini yüzdesel olarak hesaplar ve bara yansıtır.
            int progress = 100 - Math.min(100, (elapsed * 100) / ultiCooldownMillis);
            //Ulti barının değerini günceller
            ultiBar.setValue(progress);
            //Eğer ulti barı sıfırlandıysa:
            if(ultiBar.getValue() == 0){
                //Ulti modunu sonlandırır
                isUltiStarted = false;
            }
            //Belirtilen zaman aralıklarında ulti kullanır
            //Eğer son ulti kullanımından itibaren 0.3 saniye geçtiyse:
            if (0.3 * MILLISECONDS_IN_A_SECOND <= currentTime - lastUltiTime) {
                //Oyuncunun ulti yeteneğini çağırır (8 yöne mermi atar)
                player.ulti();
                //Son ulti kullanım zamanını günceller
                lastUltiTime = System.currentTimeMillis();
            }

        }
        //Eğer görünmezlik aktif değilse, ve görünmezlik satın alınmışsa ve görünmezlik henüz başlatılmadıysa:
        if (!invisibility && Game.isInvisibility() && !isInvisibilityStarted) {
            //Son görünmezlik zamanından itibaren geçen süreyi hesaplar
            int elapsed = (int) (currentTime - lastInvisibilityTime);
            //Görünmezlik bekleme süresini belirler (5 saniye)
            int invisibilityCooldownMillis = 5 * MILLISECONDS_IN_A_SECOND;
            //İlerleme yüzdesini hesaplar (maksimum 100 değerini alır)
            int progress = Math.min(100, (elapsed * 100) / invisibilityCooldownMillis);
            //İlerleme çubuğunda ilerlemeyi günceller (görünmezlik barında)
            invisibilityBar.setValue(progress);
            //Eğer görünmezlik başladığından beri geçen süre görünmezlik bekleme süresini(cooldown) geçtiyse:
            if (elapsed >= invisibilityCooldownMillis) {
                //Görünmezlik durumunu aktif hale getir
                invisibility = true;
                //Görünmezlik çubuğunu tamamen dolu olarak ayarlar
                invisibilityBar.setValue(100);
            }
        }

        //Eğer ateş etme hazır değilse:
        if (!isFireReady) {
            //Son ateşten itibaren geçen süreyi hesaplar
            int elapsed = (int) (currentTime - lastFireTime);
            //İlerleme yüzdesini hesaplar (maksimum 100 değerini alır)
            int progress = Math.min(100, (elapsed * 100) / fireCooldownMillis);
            //İlerleme çubuğunda ilerlemeyi günceller (ateş etme barında)
            fireCooldownBar.setValue(progress);
            //Eğer son ateşten beri geçen süre ateş etme bekleme süresini(cooldown) geçtiyse:
            if (elapsed >= fireCooldownMillis) {
                //Ateş etmeyi hazır durumuna getirir
                isFireReady = true;
                //İlerleme çubuğunu tam dolu olarak ayarlar
                fireCooldownBar.setValue(100);
            }
        }

        // Mermilerin hareketi ve çarpışma kontrolleri
        for (int i = 0; i < bullets.size(); i++) {
            //İndexdeki mermiyi alır
            Bullet bullet = bullets.get(i);
            //Mermiyi hareket ettir
            bullet.move();
            //Sınır dışına çıkan mermiyi sil
            if(bullet.isOutOfBounds()){
                bullets.remove(bullet);
            }
            for (int j = 0; j < enemies.size(); j++) {
                Enemy enemy = enemies.get(j);
                //Mermi düşmanla çarpıştıysa:
                if(bullet.isCollidingWith(enemy)){
                    //Mermiyi listeden kaldır
                    bullets.remove(bullet);
                    //Düşmanı listeden kaldır
                    enemies.remove(enemy);
                    //Kill sayısını 1 arttır
                    Game.setKill(Game.getKill()+1);
                    //Altın sayısını 1 arttır
                    Game.setGold(Game.getGold()+1);
                    //Altın metnini güncelle
                    goldText.setText("Gold: " + Game.getGold());
                    //Eğer ulti henüz kullanılmadı ve ulti başlatılmadıysa
                    if(!ulti && !isUltiStarted){
                        //Ulti barını 10 artır
                        ultiBar.setValue(ultiBar.getValue() + 10);
                        //Ulti barı 100e ulaştıysa
                        if(ultiBar.getValue() == 100){
                            //Ulti özelliğini aktif eder
                            ulti = true;
                        }
                    }
                    /*Mermi düşmanla çarpıştığı için diğer düşmanlarla temasını kontrol etmeye gerek yok.
                     Bu yüzden döngüden çıkıyoruz */
                    break;
                }
            }
            //Eğer boss seviyesinde mermi bosa temas ettiyse:
            if(isBossLevel && bullet.isCollidingWith(boss)){
                //Mermiyi listeden kaldırır
                bullets.remove(bullet);
                //Boss barını (can) 1 azaltır.
                bossBar.setValue(bossBar.getValue() - 1);
                //Ulti barını 10 arttırır.
                ultiBar.setValue(ultiBar.getValue() + 10);
                //Eğer ulti barı 100 ise:
                if(ultiBar.getValue() == 100){
                    //Ulti özelliğini aktif et
                    ulti = true;
                }
                //Eğer boss barı 0 ise:
                if(bossBar.getValue() == 0){
                    //Oyun bitti fonksiyonu çalışır
                    gameFinished();
                }
                break;
            }
        }
        //Bombaların hareketi ve çarpışma kontrolleri
        for (int i = 0; i < bombs.size(); i++) {
            //İndexdeki bomba nesnesini alır
            Bomb bomb = bombs.get(i);
            //Bombayı hareket ettirir
            bomb.move();
            //Oyuncu bombayla çarpışırsa:
            if (player.isCollidingWith(bomb))
                {
                    //Oyun sona erer
                    gameOver();
                }
            //Bomba sınır dışına çıkarsa:
            if(bomb.isOutOfBounds()){
                //Bombayı siler
                bombs.remove(bomb);
            }
        }
        //Oyuncuyu hareket ettirir
        player.move();
        //Güncel verilere göre ekranı yeniden çizer (paintComponent fonksiyonunu çağırır)
        repaint();
    }

    //Oyun durumu değişiklikleri için metodlar
    public void gameOver(){
        //Oyun döngüsünü durdurur
        gameLoop.stop();
        //Animasyonu durdurur
        animate.stop();
        //GameOver ekranına geç
        Game.setScreen(Game.newScreen("GameOverScreen"));
    }

    public void levelFinished(){
        //Oyun döngüsünü durdurur
        gameLoop.stop();
        //Animasyonu durdurur
        animate.stop();
        //LevelFinished ekranına geç
        Game.setScreen(Game.newScreen("LevelFinishedScreen"));
    }

    public void gamePaused(){
        //Oyun döngüsünü durdurur
        gameLoop.stop();
        //Animasyonu durdurur
        animate.stop();
        //PauseScreen ekranına geç
        Game.setScreen(Game.newScreen("PauseScreen"));
    }
    public void gameFinished(){
        //Oyun döngüsünü durdurur
        gameLoop.stop();
        //Animasyonu durdurur
        animate.stop();
        //GameFinished ekranına geç
        Game.setScreen(Game.newScreen("GameFinished"));
    }

    public void setLevelSettings(){
        //Oyun seviyesine göre level süresini ayarlar
        levelSecond = 28 + Game.getLevel() * 2;
        //Oyun seviyesine göre düşman oluşma zamanını ayarlar
        enemySpawnTime = 2.1 - Game.getLevel() * 0.1;
        //ulti özelliğimiz başlangıçta yok olarak başlatır.
        ulti = false;
        //ateş etme özelliğimiz doğru olarak başlar.
        isFireReady = true;
        //Game sınıfındaki veriye göre görünmezlik özelliğini ayarlar.
        invisibility = Game.isInvisibility();
        //Game sınıfındaki veriye göre teleport özelliğini ayarlar.
        canTeleport = Game.isCanTeleport();
        //Eğer level 13 ise boss bölümü olduğunu gösterir.
        isBossLevel = Game.getLevel() == 13;
        //Oyuncunun önceki tıklamaları (klavye girişleri) temizlenir.
        Player.getPressedKeys().clear();
    }

    public void enemySpawn(){
        //Eğer belirlenen enemySpawnTime geçtiyse yeni düşmanlar spawn edilir.
        if(enemySpawnTime * MILLISECONDS_IN_A_SECOND < currentTime - lastEnemySpawnTime){
            //Rastgele bir konumda "Bomber" türü düşman spawn etmek için koordinatlar belirlenir.
            int randomBX = (int) (Math.random() * getScreenWidth());
            int randomBY = (int) (Math.random() * getScreenHeight());
            //Rastgele bir konumda "Fly" türü düşman spawn etmek için koordinatlar belirlenir.
            int randomFX = (int) (Math.random() * getScreenWidth());
            int randomFY = (int) (Math.random() * getScreenHeight());
            //"Bomber" türü düşman için SpawnMarker oluşturulup spawnMarkers listesine eklenir.
            SpawnMarker spawnMarkerB = new SpawnMarker(randomBX, randomBY, getScreenWidth() / 20, getScreenHeight() / 10,
                    "Bomber", getScreenWidth() / 15, getScreenHeight() / 10);
            spawnMarkers.add((spawnMarkerB));
            //"Fly" türü düşman için SpawnMarker oluşturulup spawnMarkers listesine eklenir.
            SpawnMarker spawnMarkerF = new SpawnMarker(randomFX, randomFY, getScreenWidth() / 20, getScreenHeight() / 10,
                    "Fly", getScreenWidth() / 20, getScreenHeight() / 20);
            spawnMarkers.add((spawnMarkerF));
            //spawnMarkers listesinde bulunan her SpawnMarker için döngü başlar.
            //Temel olarak buradaki işlem ekranda yeni spawnMarker oluştuğu anda eski spawnMarkerın konumuna düşman oluşturmaktır.
            for (int i = 0; i < spawnMarkers.size(); i++) {
                SpawnMarker spawnMarker = spawnMarkers.get(i);
                //Her spawnMarker'ın yaşam süresi azaltılır.
                spawnMarker.setLifeTime(spawnMarker.getLifeTime() - 1);
                //Eğer yaşam süresi 0 ise, bu spawnMarker silinir ve düşmanı oluşturup düşman listesine eklenir.
                if (spawnMarker.getLifeTime() == 0) {
                    spawnMarkers.remove(spawnMarker);
                    Enemy enemy = spawnMarker.spawnEnemy();
                    enemies.add(enemy);
                    i--; //Silinen spawnMarker'ın yerine geçen indeksle işlem yapmak için indexi düşürür.
                }}
            lastEnemySpawnTime = System.currentTimeMillis();}}

    //Getter setterlar
    public static ArrayList<Bullet> getBullets() {
        return bullets;
    }
    public static ArrayList<Bomb> getBombs() {
        return bombs;
    }
    public static long getCurrentTime() {
        return currentTime;
    }
}
