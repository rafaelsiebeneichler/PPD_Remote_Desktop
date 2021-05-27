
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 */
public class WorkerSender implements Runnable {

    protected DatagramSocket socket = null;
    protected InetAddress ipDestino;
    protected BufferedImage bi = null;
    protected int posX, posY, i, n;
    protected String[][] anterior;

    public WorkerSender(InetAddress ipDestino, BufferedImage bi, int posX, int posY, int i, int n, String[][] anterior) {
        try {
            this.socket = new DatagramSocket();
        } catch (SocketException ex) {
            Logger.getLogger(WorkerSender.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.ipDestino = ipDestino;
        this.bi = bi;
        this.posX = posX;
        this.posY = posY;
        this.i = i;
        this.n = n;
        this.anterior = anterior;
    }

    public void run() {
        try {
            byte buffer[] = new byte[Utils.BLOCK_X * Utils.BLOCK_Y * 4 + 4 + 4];

            int aux = 0;
            for (int y = 0; y < Utils.BLOCK_Y; y++) {
                for (int x = 0; x < Utils.BLOCK_X; x++) {

                    int cor = bi.getRGB(posX + x, posY + y); //ARGB

                    byte auxBuffer[] = Utils.integerToBytes(cor);
                    for (int j = 0; j < auxBuffer.length; j++) {
                        buffer[aux++] = auxBuffer[j];
                    }
                }
            }

            // bytes do posX
            byte auxBufferPosX[] = Utils.integerToBytes(posX);
            for (int j = 0; j < auxBufferPosX.length; j++) {
                buffer[aux++] = auxBufferPosX[j];
            }

            // bytes do posY
            byte auxBufferPosY[] = Utils.integerToBytes(posY);
            for (int j = 0; j < auxBufferPosY.length; j++) {
                buffer[aux++] = auxBufferPosY[j];
            }

            if (!anterior[i][n].equals(buffer.toString())) {
                DatagramPacket enviaPacote = new DatagramPacket(buffer, buffer.length, ipDestino, Utils.PORTA);
                socket.send(enviaPacote);
            } else {
                System.err.println("Não enviado " + i + " " + n + ": " + buffer.toString());
            }

            anterior[i][n] = buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
