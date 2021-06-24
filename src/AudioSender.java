import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author rafaelsiebeneichler
 */

public class AudioSender {
    
    private static String hostname = "190.15.46.13";
    private static int port = 5555;
    private static int BUFFER_SIZE = 1024;

    public static void main(String[] args) throws IOException {

        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, true);
        TargetDataLine microfone;
        SourceDataLine autoFalantes;
        try {
            microfone = AudioSystem.getTargetDataLine(format);

            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            microfone = (TargetDataLine) AudioSystem.getLine(info);
            microfone.open(format);

            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            int numBytesRead;
            byte[] data = new byte[microfone.getBufferSize() / 5];
            microfone.start();


            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
            autoFalantes = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            autoFalantes.open(format);
            autoFalantes.start();

            InetAddress address = InetAddress.getByName(hostname);
            DatagramSocket socket = new DatagramSocket();
            byte[] buffer = new byte[1024];
            for(;;) {                
                numBytesRead = microfone.read(data, 0, BUFFER_SIZE);
                // Salva os dados do microfone numa stream
                outStream.write(data, 0, numBytesRead); 
                // Para ouvir o próprio audio na máquina
                // autoFalantes.write(data, 0, numBytesRead);            
                DatagramPacket pacote = new DatagramPacket(data, numBytesRead, address, port);
                socket.send(pacote);
            }

        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } 
    }
}