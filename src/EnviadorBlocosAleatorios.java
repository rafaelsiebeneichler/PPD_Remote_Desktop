
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author Alexandre Sturmer Wolf
 */
public class EnviadorBlocosAleatorios {

    public static void main(String[] args) {
        // buffer para armazenar o bloco da tela
        byte buffer[] = new byte[Utils.BLOCK_X * Utils.BLOCK_Y * 4 + 4 + 4]; // pegar R, G, B, e alfa para cada pixel + 4 pois quero informar o posX e  + 4 posY (posicão sorteada)

        try {

            Robot robot = new Robot();
            DatagramSocket senderSocket = new DatagramSocket();
            InetAddress ipDestino = InetAddress.getByName("127.0.0.1"); // destinatário

            while (true) {

                try {

                    BufferedImage bi = robot.createScreenCapture(new Rectangle(Utils.RESOLUCAO_X, Utils.RESOLUCAO_Y)); // capturei a tela toda

                    int posX = 0;
                    int posY = 0;

                    for (int i = 0; i < 20; i++) {
                        for (int n = 0; n < 20; n++) {

                            // arqui poderia ser lancado uma Thread
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

                            DatagramPacket enviaPacote = new DatagramPacket(buffer, buffer.length, ipDestino, Utils.PORTA);
                            senderSocket.send(enviaPacote);

                           // Thread.sleep(10);

                            posX = posX + Utils.BLOCK_X;
                        }
                        posY = posY + Utils.BLOCK_Y;
                        posX = 0;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
