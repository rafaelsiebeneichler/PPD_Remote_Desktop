
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class EchoClient {

    public JFrame frame;
    public Tela panel;
    private DatagramSocket socket;
    private InetAddress address;
    private String serverAddr;
    int TAM_BUFFER = 1024; //define o tamanho do buffer
    private byte[] bufRet = new byte[TAM_BUFFER]; //cria um vetor de byte com o tamanho do buffer

    public EchoClient(String ip) throws SocketException, UnknownHostException {
        this.serverAddr = ip;
        socket = new DatagramSocket(); //criar um novo socket
        address = InetAddress.getByName(ip); //pega o ip da máquina

        this.frame = new JFrame();
        this.panel = new Tela();
        this.frame.setResizable(true);
        this.frame.add(panel);
        this.frame.pack();
        //  this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        //   this.frame.setUndecorated(true);
        this.frame.setVisible(true);
        try {
           System.out.println(sendEcho("Teste"));
        } catch (IOException ex) {
            Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //método para enviar mensagem para o servidor
    public String sendEcho(String msg) throws IOException {
        byte[] buf = msg.getBytes(); //pega a mensagem e adiciona a um vetor de bytes
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445); //cria um pacote contendo a mensagem, tamanho da mensagem, ip do cliente e a porta de comunicação com o servidor
        socket.send(packet); //envia o pacote para o servidor
        // System.out.println("Client send: " + msg); //imprime mensagem enviada
        packet = new DatagramPacket(bufRet, bufRet.length); //Cria novo pacote pra receber
        socket.receive(packet); // Recebe pacote
        String received = new String(packet.getData(), 0, packet.getLength()).trim(); // Converte string e trinca
        return received; // retorna
    }

    public void close() {
        socket.close(); //encerra conexão
    }
}
