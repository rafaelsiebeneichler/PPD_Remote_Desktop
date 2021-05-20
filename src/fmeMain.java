
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rafaelsiebeneichler
 */
public class fmeMain extends javax.swing.JFrame {

    /**
     * Creates new form FmeMain
     */
    public fmeMain() {
        initComponents();
        this.setLocationRelativeTo(null);
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("google.com", 80));
            jTxYourIP.setText(socket.getLocalAddress().toString());
        } catch (IOException e) {
        }
        Server server = new Server();
        new Thread(server).start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPnlSuaMaquina = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTxYourIP = new javax.swing.JTextField();
        jPnlConectar = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTxtConnectIP = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(600, 600));
        setMinimumSize(new java.awt.Dimension(200, 200));
        setPreferredSize(new java.awt.Dimension(450, 400));

        jPnlSuaMaquina.setMaximumSize(new java.awt.Dimension(400, 400));
        jPnlSuaMaquina.setPreferredSize(new java.awt.Dimension(200, 200));

        jLabel1.setText("Seu IP:");

        jTxYourIP.setEditable(false);
        jTxYourIP.setText("127.0.0.1");

        javax.swing.GroupLayout jPnlSuaMaquinaLayout = new javax.swing.GroupLayout(jPnlSuaMaquina);
        jPnlSuaMaquina.setLayout(jPnlSuaMaquinaLayout);
        jPnlSuaMaquinaLayout.setHorizontalGroup(
            jPnlSuaMaquinaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlSuaMaquinaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnlSuaMaquinaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTxYourIP, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPnlSuaMaquinaLayout.setVerticalGroup(
            jPnlSuaMaquinaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlSuaMaquinaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxYourIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(270, Short.MAX_VALUE))
        );

        getContentPane().add(jPnlSuaMaquina, java.awt.BorderLayout.WEST);

        jPnlConectar.setMaximumSize(new java.awt.Dimension(200, 200));
        jPnlConectar.setPreferredSize(new java.awt.Dimension(200, 200));

        jLabel2.setText("Conectar IP:");

        jButton1.setText("Conectar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPnlConectarLayout = new javax.swing.GroupLayout(jPnlConectar);
        jPnlConectar.setLayout(jPnlConectarLayout);
        jPnlConectarLayout.setHorizontalGroup(
            jPnlConectarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlConectarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnlConectarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTxtConnectIP)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPnlConectarLayout.setVerticalGroup(
            jPnlConectarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlConectarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtConnectIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(241, Short.MAX_VALUE))
        );

        getContentPane().add(jPnlConectar, java.awt.BorderLayout.EAST);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Client c = new Client(jTxtConnectIP.getText());
        //
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(fmeMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(fmeMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(fmeMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(fmeMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new fmeMain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPnlConectar;
    private javax.swing.JPanel jPnlSuaMaquina;
    private javax.swing.JTextField jTxYourIP;
    private javax.swing.JTextField jTxtConnectIP;
    // End of variables declaration//GEN-END:variables
}