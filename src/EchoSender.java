
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EchoSender extends Thread {

    private boolean running;
    private String ipAdress;

    public EchoSender(String ipAdress) {
        this.ipAdress = ipAdress;
    }

    public void run() {
        this.running = true;
        byte buffer[] = new byte[Utils.BLOCK_X * Utils.BLOCK_Y * 4 + 4 + 4]; // pegar R, G, B, e alfa para cada pixel + 4 pois quero informar o posX e  + 4 posY (posicão sorteada)
        String anterior[][] = new String[20][20];

        try {
            for (int i = 0; i < 20; i++) {
                for (int n = 0; n < 20; n++) {
                    anterior[i][n] = "";
                }
            }

            Robot robot = new Robot();
            DatagramSocket senderSocket = new DatagramSocket();
            InetAddress ipDestino = InetAddress.getByName(ipAdress); // destinatário

            while (running) {
                try {
                    // captura a tela toda
                    BufferedImage bi = robot.createScreenCapture(new Rectangle(Utils.RESOLUCAO_X, Utils.RESOLUCAO_Y));

                    int posX = 0;
                    int posY = 0;

                    for (int i = 0; i < 20; i++) {
                        for (int n = 0; n < 20; n++) {
                            WorkerSender w = new WorkerSender(ipDestino, bi, posX, posY);
                            w.run();
                            //new Thread(w).start();

                            posX = posX + Utils.BLOCK_X;
                        }
                        posY = posY + Utils.BLOCK_Y;
                        posX = 0;
                    }

                    Thread.sleep(100);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < 20; i++) {
                    for (int n = 0; n < 20; n++) {
                        System.out.println(anterior[i][n]);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
