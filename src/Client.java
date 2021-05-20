
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rafaelsiebeneichler
 */
public class Client {

    public JFrame frame;
    public Tela panel;
    public String serverAddr;
    
    public Client(String ip) {
        this.serverAddr = ip;
        this.frame = new JFrame();
        this.panel = new Tela();
        this.frame.setResizable(true);
        this.frame.add(panel);
        this.frame.pack();
        this.frame.setVisible(true);
        GetTela();
    }     
    
    public void GetTela(){
       try {
            BufferedImage image;
            Socket socket = new Socket(serverAddr, 8080);
            
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            image = ImageIO.read(inputStream);
            panel.setImg(image);
            panel.repaint();
            frame.setVisible(true);
            
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
