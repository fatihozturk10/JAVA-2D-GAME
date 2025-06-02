import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
//NewGameScreen sınıfı, yeni bir oyun başlatmak için kullanılan ekranı temsil eder.
public class NewGameScreen extends Screen{
    public NewGameScreen() {
        //Ekranın arka plan rengini ayarlar.
        setBackground(Color.DARK_GRAY);
        //Ekranın başlığı olarak "New Game" yazısını ekler.
        MenuLabel title = new MenuLabel("New Game", getScreenWidth()/2 , getScreenHeight()*3 /32, getScreenWidth()/2,getScreenHeight()/10,Color.black,getScreenHeight() /12f);
        add(title);

        //"Save Name:" adında bir metin ekler.
        MenuLabel saveName = new MenuLabel("Save Name: ", getScreenWidth() *8/32 , getScreenHeight() *14/32, getScreenWidth() /2, getScreenHeight() /8,Color.white, getScreenHeight() / 20f);
        add(saveName);

        //Kullanıcıdan kayıt adı (save name) almak için bir metin kutusu oluşturur.
        JTextField textField = new JTextField();
        //text alanının fontunu ayarlar.
        textField.setFont(Main.getPixelFont());
        //text alanının boyut ve konumunu ayarlar.
        textField.setBounds(getScreenWidth()/2 - getScreenWidth()/8, getScreenHeight() *14/32 - Main.getPixelFont().getSize()/2,getScreenWidth()/4,Main.getPixelFont().getSize());
        add(textField);
        //"Start" butonu oluşturulur. (oyunu başlatmayı sağlar)
        MenuButton startButton = new MenuButton("Start", getScreenWidth() /2, getScreenHeight() *21/32 ,Main.getPixelFont(),Color.green);
        add(startButton);

        //"Start" butonuna tıklanınca yapılacak işlemler.
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Yeni dosya adı için bir değişken.
                String newFileName;
                //Eğer metin kutusu boşsa, tarih ve saat tabanlı bir dosya adı oluşturur.
                if(textField.getText().isEmpty()){
                    //Şimdiki tarih ve saat alınır.
                    Date date = new Date();
                    //Tarih formatı belirlenir.
                    String pattern = "dd/MM/yyyy HH:mm:ss";
                    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                    // Tarih, dosya adı olarak kullanılabilir bir forma dönüştürülür. ('/' ve '-' karakterleri dosya adında kullanılamazlar)
                    newFileName = sdf.format(date).replace("/","-").replace(":","_") + ".txt";

                }
                else {
                    //Eğer kullanıcı bir ad girmişse, bu ad dosya adı olarak kullanılır.
                    newFileName = textField.getText() + ".txt";
                }
                //Yeni dosya, oyun verileriyle oluşturulur.
                Game.fileWrite(newFileName);
                //Şu anki kayıt dosyası olarak belirlenir.
                Game.setNowSaveFile(newFileName);
                //Oyun ekranına geçiş yapılır.
                Game.setScreen(Game.newScreen("GameScreen"));
            }
        });
    }
}
