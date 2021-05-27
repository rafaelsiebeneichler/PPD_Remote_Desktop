
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author Douglas
 */
public class EnviaUDP {

    public static final int PORTA = 5000;
    public static final int TAM_BUFFER = 1024;

    public static void main(String[] args) {
        try {

            byte[] buffer = new byte[TAM_BUFFER];

            byte dados_a_enviar[] = {10, 14, 23, 45, 76, 12, 67};

            buffer[0] = (byte) dados_a_enviar.length; // enviando a quantidade de bytes (cuidado, maximo até 127...) com 1 byte

            for (int i = 0; i < dados_a_enviar.length; i++) {
                buffer[i + 1] = dados_a_enviar[i];
            }

            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IpServidor = InetAddress.getByName("127.0.0.1");

            DatagramPacket sendPacket = new DatagramPacket(buffer, dados_a_enviar.length + 1, IpServidor, PORTA);
            clientSocket.send(sendPacket);

            clientSocket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
