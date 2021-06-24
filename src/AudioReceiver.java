import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;   
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

public class AudioReceiver {
    
    private static String hostname = "190.15.46.13";
    private static int port = 5555;
    private static int BUFFER_SIZE = 1024;

    public static void main(String[] args) throws LineUnavailableException {

        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, true);
        TargetDataLine microfone;
        SourceDataLine autoFalantes;
        microfone = AudioSystem.getTargetDataLine(format);

        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        microfone = (TargetDataLine) AudioSystem.getLine(info);
        microfone.open(format);

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        int numBytesRead;
        byte[] data = new byte[microfone.getBufferSize() / 5];
        microfone.start();

        int bytesRead = 0;
        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
        autoFalantes = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
        autoFalantes.open(format);
        autoFalantes.start();

        try {
            InetAddress address = InetAddress.getByName(hostname);
            DatagramSocket socket = new DatagramSocket();

            DatagramSocket serverSocket = new DatagramSocket(5555);

            while (true) {
                byte[] buffer = new byte[BUFFER_SIZE];
                DatagramPacket pacote = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(pacote);

                outStream.write(pacote.getData(), 0, pacote.getData().length);
                autoFalantes.write(pacote.getData(), 0, pacote.getData().length);
                String stringAudio = new String(buffer, 0, pacote.getLength());

                System.out.println(stringAudio + "\n");
            }

        } catch (SocketTimeoutException ex) {
            System.out.println("Timeout error: " + ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Client error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}