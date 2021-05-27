
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
        setVisible(true);
        setTitle("Schoweiro 1.0");
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

                // int do posX
                byte auxPosX[] = new byte[4];
                for (int i = 0; i < 4; i++) {
                    auxPosX[i] = buffer[buffer.length - 8 + i]; // posX estava a 8 bytes atras no fim do pacote
                }
                int posX = Utils.bytesToInteger(auxPosX);

                // int do posY
                byte auxPosY[] = new byte[4];
                for (int i = 0; i < 4; i++) {
                    auxPosY[i] = buffer[buffer.length - 4 + i]; // posy estava a 4 bytes atras no fim do pacote
                }
                int posY = Utils.bytesToInteger(auxPosY);

                int aux = 0;
                for (int y = 0; y < Utils.BLOCK_Y; y++) {
                    for (int x = 0; x < Utils.BLOCK_X; x++) {

                        // int do posY
                        byte auxCor[] = new byte[4];
                        for (int i = 0; i < 4; i++) {
                            auxCor[i] = buffer[aux++]; // posy estava a 4 bytes atras no fim do pacote
                        }
                        int cor = Utils.bytesToInteger(auxCor);

                        bi.setRGB(posX + x, posY + y, cor);

                    }
                }

                // Thread.sleep(10);
                repaint();
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
