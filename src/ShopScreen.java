import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//ShopScreen sınıfı, oyuncunun oyun içi altınlarını kullanarak özelliklerini geliştirdiği bir mağaza ekranıdır.
public class ShopScreen extends Screen{
    //Mağazadaki her bir ürünün fiyatları sabit olarak tanımlanmıştır.
    private final int shopB1price = 20; //Player Speed artırma fiyatı
    private final int shopB2price = 5; //Bullet Speed artırma fiyatı
    private final int shopB3price = 15; //Fire Cooldown azaltma fiyatı
    private final int shopB4price = 50; //Teleport özelliği açma fiyatı
    private final int shopB5price = 60; //Invisibility özelliği açma fiyatı
    public ShopScreen() {

        //Arkaplan rengi ayarlanır.
        setBackground(Color.darkGray);

        //Başlık (Shop) oluşturulup eklenir.
        MenuLabel title = new MenuLabel("Shop", getScreenWidth()/2 , getScreenHeight()*3 /32 , getScreenWidth()/2,getScreenHeight()/10,Color.black,getScreenHeight() /12f);
        add(title);

        //Oyuncunun mevcut altın miktarını göstermek için bir metin eklenir.
        MenuLabel goldText = new MenuLabel("Gold: " + Game.getGold(), getScreenWidth() *3/32 , getScreenHeight() *2/32, getScreenWidth() /4, getScreenHeight() /8,Color.yellow, getScreenHeight() /16f);
        add(goldText);

        // Oyuncu özelliklerini gösteren metinler eklenir.
        MenuLabel playerSpeedText = new MenuLabel("Player Speed: " + Game.getPlayerSpeed(), getScreenWidth() *13/32 , getScreenHeight() *10/32, getScreenWidth() /2, getScreenHeight() /8,Color.white, getScreenHeight() / 20f ,"left");
        add(playerSpeedText);
        MenuLabel bulletSpeedText = new MenuLabel("Bullet Speed: " + Game.getBulletSpeed(), getScreenWidth() *13/32 , getScreenHeight() *16/32, getScreenWidth() /2, getScreenHeight() /8,Color.white, getScreenHeight() /20f, "left");
        add(bulletSpeedText);
        MenuLabel fireCoolDownText = new MenuLabel("Fire CoolDown: " + Game.getFireCooldown(), getScreenWidth() *13/32 , getScreenHeight() *22/32, getScreenWidth() /2, getScreenHeight() /8,Color.white, getScreenHeight() /20f,"left");
        add(fireCoolDownText);

        //Teleport ve Invisibility özelliklerini temsil eden yazılar eklenir.
        MenuLabel teleportText = new MenuLabel("Teleport", getScreenWidth() *19/32 , getScreenHeight() *10/32, getScreenWidth() /2, getScreenHeight() /8,Color.white, getScreenHeight() /20f,"right");
        add(teleportText);
        MenuLabel invisibilityText = new MenuLabel("Invisibility", getScreenWidth() *19/32 , getScreenHeight() *16/32, getScreenWidth() /2, getScreenHeight() /8,Color.white, getScreenHeight() /20f,"right");
        add(invisibilityText);

        //Her bir ürün için fiyat ve satın alma düğmesi oluşturulur.
        //Player Speed
        //Fiyatı gösteren metin
        MenuLabel shopB1PriceText = new MenuLabel(shopB1price + "", getScreenWidth() *3/32 , getScreenHeight() *7/32, getScreenWidth() /2, getScreenHeight() /8,Color.white, getScreenHeight() / 20f);
        add(shopB1PriceText);
        //Satın alma butonu
        ShopButton shopButton1 = new ShopButton(getScreenWidth() *3/32, getScreenHeight() *10/32,Color.BLUE);
        add(shopButton1);

        //Eğer Player Speed maksimum seviyedeyse (15) buton pasif hale getirilir.
        if(Game.getPlayerSpeed() >= 15){
            shopButton1.setEnabled(false);
        }

        //Bullet Speed
        //Fiyatı gösteren metin
        MenuLabel shopB2PriceText = new MenuLabel(shopB2price + "", getScreenWidth() *3/32 , getScreenHeight() *13/32, getScreenWidth() /2, getScreenHeight() /8,Color.white, getScreenHeight() / 20f);
        add(shopB2PriceText);
        //Satın alma butonu
        ShopButton shopButton2 = new ShopButton( getScreenWidth() *3/32, getScreenHeight() *16/32,Color.BLUE);
        add(shopButton2);

        //Eğer Bullet Speed maksimum seviyedeyse (15) buton pasif hale getirilir.
        if(Game.getBulletSpeed() >= 15){
            shopButton2.setEnabled(false);
        }

        //Fire Cooldown
        //Fiyatı gösteren metin
        MenuLabel shopB3PriceText = new MenuLabel(shopB3price + "", getScreenWidth() *3/32 , getScreenHeight() *19/32, getScreenWidth() /2, getScreenHeight() /8,Color.white, getScreenHeight() / 20f);
        add(shopB3PriceText);
        //Satın alma butonu
        ShopButton shopButton3 = new ShopButton(getScreenWidth() *3/32, getScreenHeight() *22/32,Color.BLUE);
        add(shopButton3);

        //Eğer Fire Cooldown minimum seviyedeyse (0.1) buton pasif hale getirilir.
        if(Game.getFireCooldown() <= 0.1){
            shopButton3.setEnabled(false);
        }
        //Teleport
        //Fiyatı gösteren metin
        MenuLabel shopB4PriceText = new MenuLabel(shopB4price + "", getScreenWidth() *29/32 , getScreenHeight() *7/32, getScreenWidth() /2, getScreenHeight() /8,Color.white, getScreenHeight() / 20f);
        add(shopB4PriceText);
        //Satın alma butonu
        ShopButton shopButton4 = new ShopButton( getScreenWidth() *29/32, getScreenHeight() *10/32,Color.CYAN);
        add(shopButton4);

        //Teleport özelliği açıksa buton pasif hale getirilir.
        if(Game.isCanTeleport()){
            shopButton4.setEnabled(false);
        }

        //Invisibility
        //Fiyatı gösteren metin
        MenuLabel shopB5PriceText = new MenuLabel(shopB5price + "", getScreenWidth() *29/32 , getScreenHeight() *13/32, getScreenWidth() /2, getScreenHeight() /8,Color.white, getScreenHeight() / 20f);
        add(shopB5PriceText);
        //Satın alma butonu
        ShopButton shopButton5 = new ShopButton(getScreenWidth() *29/32, getScreenHeight() *16/32,Color.CYAN);
        add(shopButton5);

        //Görünmezlik özelliği açıksa buton pasif hale getirilir.
        if(Game.isInvisibility()){
            shopButton5.setEnabled(false);
        }

        //"Next Level" butonu oluşturulur ve eklenir.
        MenuButton nextLevelButton = new MenuButton("Next Level", getScreenWidth() /2, getScreenHeight() *27/32 ,Main.getPixelFont(),Color.green);
        add(nextLevelButton);

        shopButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Eğer yeterli altın varsa, satın alma işlemini gerçekleştirir
                if(Game.getGold() - shopB1price >= 0){
                    //Altın miktarını azaltır ve oyuncunun hızını artırır
                    Game.setGold(Game.getGold() - shopB1price);
                    Game.setPlayerSpeed(Game.getPlayerSpeed()+1);
                    //Altın ve hız bilgilerini günceller (text)
                    goldText.setText("Gold: " + Game.getGold());
                    playerSpeedText.setText("Player Speed: " + Game.getPlayerSpeed());
                    // Oyuncu hızı maksimum değere ulaştığında düğmeyi devre dışı bırak
                    if(Game.getPlayerSpeed() >= 10){
                        shopButton1.setEnabled(false);
                    }
                }
            }
        });

        shopButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Eğer yeterli altın varsa, satın alma işlemini gerçekleştirir
                if(Game.getGold() - shopB2price >= 0){
                    //Altın miktarını azaltır ve mermi hızını artırır
                    Game.setGold(Game.getGold() - shopB2price);
                    Game.setBulletSpeed(Game.getBulletSpeed()+1);
                    //Altın ve mermi hızı bilgilerini günceller (text)
                    goldText.setText("Gold: " + Game.getGold());
                    bulletSpeedText.setText("Bullet Speed: " + Game.getBulletSpeed());
                    // Mermi hızı maksimum değere ulaştığında düğmeyi devre dışı bırakır
                    if(Game.getBulletSpeed() >= 15){
                        shopButton2.setEnabled(false);
                    }

                }
            }
        });

        shopButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Eğer yeterli altın varsa, satın alma işlemini gerçekleştirir
                if(Game.getGold() - shopB3price >= 0){
                    //Altın miktarını azaltır ve fireCooldown süresini azaltır
                    Game.setGold(Game.getGold() - shopB3price);
                    Game.setFireCooldown(Game.round(Game.getFireCooldown() - 0.02f,2));
                    //Altın ve fireCooldown süresi bilgilerini günceller
                    goldText.setText("Gold: " + Game.getGold());
                    fireCoolDownText.setText("Fire CoolDown: " + Game.getFireCooldown());
                    //fireCooldown süresi minimum değere ulaştığında düğmeyi devre dışı bırak
                    if(Game.getFireCooldown() <= 0.1){
                        shopButton3.setEnabled(false);
                    }
                }
            }
        });

        shopButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Eğer yeterli altın varsa, satın alma işlemini gerçekleştirir
                if(Game.getGold() - shopB4price >= 0){
                    //Altın miktarını azaltır ve teleport özelliğini etkinleştirir
                    Game.setGold(Game.getGold() - shopB4price);
                    Game.setCanTeleport(true);
                    //Altın bilgisini günceller ve düğmeyi devre dışı bırakır
                    goldText.setText("Gold: " + Game.getGold());
                    shopButton4.setEnabled(false);
                }
            }
        });

        shopButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Eğer yeterli altın varsa, satın alma işlemini gerçekleştirir
                if(Game.getGold() - shopB5price >= 0){
                    //Altın miktarını azaltır ve görünmezlik özelliğini etkinleştir
                    Game.setGold(Game.getGold() - shopB5price);
                    Game.setInvisibility(true);
                    //Altın bilgisini günceller ve düğmeyi devre dışı bırakır
                    goldText.setText("Gold: " + Game.getGold());
                    shopButton5.setEnabled(false);
                }
            }
        });

        nextLevelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Oyunu bir sonraki ekrana geçirir
                Game.setScreen(Game.newScreen("GameScreen"));
            }
        });
    }
}