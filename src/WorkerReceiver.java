import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class WorkerReceiver implements Runnable {

    protected JFrame frame;
    protected BufferedImage bi;
    protected byte buffer[] = new byte[Utils.BLOCK_X * Utils.BLOCK_Y * 4 + 4 + 4];

    public WorkerReceiver(BufferedImage bi, byte[] buffer, JFrame j) {
        this.bi = bi;
        this.buffer = buffer.clone();
        this.frame = j;
    }

    public void run() {
        // int do posX
        byte auxPosX[] = new byte[4];
        for (int i = 0; i < 4; i++) {
            auxPosX[i] = buffer[buffer.length - 8 + i]; // posX estava a 8 bytes atras no fim do pacote
        }
        int posX = Utils.bytesToInteger(auxPosX);
        // int do posY
        byte auxPosY[] = new byte[4];
        for (int i = 0; i < 4; i++) {
            auxPosY[i] = buffer[buffer.length - 4 + i]; // posY estava a 4 bytes atras no fim do pacote
        }
        int posY = Utils.bytesToInteger(auxPosY);
        int aux = 0;
        for (int y = 0; y < Utils.BLOCK_Y; y++) {
            for (int x = 0; x < Utils.BLOCK_X; x++) {

                byte auxCor[] = new byte[4];
                for (int i = 0; i < 4; i++) {
                    auxCor[i] = buffer[aux++]; 
                }
                int cor = Utils.bytesToInteger(auxCor);

                bi.setRGB(posX + x, posY + y, cor);
            }
        }
      this.frame.repaint();
    }
}
