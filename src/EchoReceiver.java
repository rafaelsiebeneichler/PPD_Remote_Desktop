
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class EchoReceiver extends JFrame implements Runnable {

    private Thread t = new Thread(this);
    private BufferedImage bi = new BufferedImage(Utils.RESOLUCAO_X, Utils.RESOLUCAO_Y, BufferedImage.TYPE_INT_ARGB);
    private byte buffer[] = new byte[Utils.BLOCK_X * Utils.BLOCK_Y * 4 + 4 + 4]; // pegar R, G, B, e alfa + 4 pois quero informar o posX e + 4 posY (posicão sorteada)

    private InetAddress address;
    private String serverAddr;

    public EchoReceiver(String ip) throws SocketException, UnknownHostException {
        this.serverAddr = ip;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Remote Desk");
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(Utils.RESOLUCAO_X, Utils.RESOLUCAO_Y);
        t.start();
    }

    @Override
    public void run() {
        DatagramSocket receiveSocket = null;
        while (true) {
            try {
                receiveSocket = new DatagramSocket(Utils.PORTA);
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                receiveSocket.receive(receivePacket);

                WorkerReceiver w = new WorkerReceiver(bi, buffer, this);
                w.run();
                //new Thread(w).start();
                
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                receiveSocket.close();
            }
        }

    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(bi, 0, 0, this);
    }
}
