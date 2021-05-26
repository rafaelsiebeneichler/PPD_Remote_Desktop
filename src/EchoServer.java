
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EchoServer extends Thread {

    //SOCKET
    private DatagramSocket socket;
    private boolean running;
    private int TAM_BUFFER = 1024; //define o tamanho do buffer
    private byte[] buf = new byte[TAM_BUFFER]; //cria um vetor de byte com o tamanho do buffer

    public EchoServer() throws SocketException {
        socket = new DatagramSocket(4445); //porta de comunicação com o cliente
    }

    public void run() {
        running = true;
        String received;
        String r = "Teste";

        while (running) {
            DatagramPacket packet = new DatagramPacket(buf, buf.length); //
            try {
                received = "";
                socket.receive(packet); //recebe pacote
                InetAddress address = packet.getAddress(); //adiciona o ip do cliente na variável "address"
                int port = packet.getPort(); //adiciona a porta de comunicação na variável "port"
                packet = new DatagramPacket(buf, buf.length, address, port); //
                received = new String(packet.getData(), 0, packet.getLength()).trim(); //
                System.out.println("Server received: " + received); //imprime o que o servidor recebeu do cliente

                packet = new DatagramPacket(r.getBytes(), r.getBytes().length, address, port); // empacota mensagem
                socket.send(packet); // retorna pacote da mensagem
            } catch (IOException ex) {
                Logger.getLogger(EchoServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        socket.close(); //encerra conexão
    }
}
