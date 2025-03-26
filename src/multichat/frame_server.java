/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package multichat;
import java.io.*;
import java.net.*;
import java.util.*;
/**
 *
 * @author Najwa_
 */
public class frame_server extends javax.swing.JFrame {
    String username = "Server", address = "localhost";
    ArrayList<PrintWriter> clientOutputStreams;
    ArrayList<String> users;
    int port = 2222;
    PrintWriter writer;
    BufferedReader reader;
    Socket s;
    Boolean isConnected = false;
    ArrayList<String> userss = new ArrayList<>();

    private void ListenThread() {
    throw new UnsupportedOperationException("Not supported yet.");
}
    private void sendDisconnect() {
    throw new UnsupportedOperationException("Not supported yet.");
}
    private void Disconnect() {
    throw new UnsupportedOperationException("Not supported yet.");
}

    private void tellEveryone(String server_is_stopping_and_all_users_will_be_) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public class ServerStart implements Runnable {
        @Override
        public void run() {
        clientOutputStreams = new ArrayList<>();
        users = new ArrayList<>();
        
        try {
            ServerSocket serverSock = new ServerSocket(2222);
            while (true) {
                Socket clientSock = serverSock.accept();
                PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
                clientOutputStreams.add(writer);
                
                Thread listener = new Thread(new ClientHandler(clientSock, writer));
                listener.start();
                
                ta_chat.append("Got a connection.\n");
            }
        } catch (Exception ex) {
            ta_chat.append("Error making a connection.\n");
        }
    }
}
    public class ClientHandler implements Runnable {
    BufferedReader reader;
    Socket sock;
    PrintWriter client;
    
    private void tellEveryone(String message) {
    Iterator<PrintWriter> it = clientOutputStreams.iterator();
    
    while (it.hasNext()) {
        try {
            PrintWriter writer = it.next();
            writer.println(message);
            ta_chat.append("Sending: " + message + "\n");
            writer.flush();
            ta_chat.setCaretPosition(ta_chat.getDocument().getLength());
        } catch (Exception ex) {
            ta_chat.append("Error telling everyone.\n");
        }
    }
}

    public ClientHandler(Socket clientSocket, PrintWriter user) {
        client = user;
        try {
            sock = clientSocket;
            InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
            reader = new BufferedReader(isReader);
        } catch (Exception ex) {
            ta_chat.append("Unexpected error... \n");
        }
    }

    public void ListenThread() {
        Thread IncomingReader = new Thread(new IncomingReader());
        IncomingReader.start();
    }

    public void userAdd(String data) {
         String message;
         String add = ": :Connect";
         String done = "Server: :Done";
         String name = data;

         ta_chat.append("Before " + name + " added.\n");
         users.add(name);
         ta_chat.append("After " + name + " added.\n");

         String[] tempList = new String[users.size()];
         users.toArray(tempList);

         for (String token : tempList) {
            message = (token + add);
            tellEveryone(message);
        }

        tellEveryone(done);
        }

    public void userRemove(String data) {
         String message;
         String add = ": :Connect";
         String done = "Server: :Done";
         String name = data;
         
         users.remove(name);
         String[] tempList = new String[users.size()];
         users.toArray(tempList);
         for (String token : tempList) {
             message = (token + add);
             tellEveryone(message);
         }
         tellEveryone(done);
    }

    public void writeUsers() {
        String[] tempList = new String[users.size()];
        users.toArray(tempList);
        for (String token : tempList) {
            // users.append(token + "\n");
        }
    }

    public void sendDisconnect() {
        String bye = (username + ": :Disconnect");
        try {
            writer.println(bye);
            writer.flush();
        } catch (Exception e) {
            ta_chat.append("Could not send Disconnect message.\n");
        }
    }

    public void Disconnect() {
        try {
            ta_chat.append("Disconnected.\n");
            sock.close();
        } catch (Exception ex) {
            ta_chat.append("Failed to disconnect. \n");
        }
        isConnected = false;
    }
    

    public class IncomingReader implements Runnable {
        @Override
        public void run() {
            String[] data;
            String stream, done = "Done", connect = "Connect", disconnect = "Disconnect", chat = "Chat";
            try {
                while ((stream = reader.readLine()) != null) {
                    data = stream.split(":");
                    if (data[2].equals(chat)) {
                        ta_chat.append(data[0] + ": " + data[1] + "\n");
                        ta_chat.setCaretPosition(ta_chat.getDocument().getLength());
                    } else if (data[2].equals(connect)) {
                        ta_chat.removeAll();
                        userAdd(data[0]);
                    } else if (data[2].equals(disconnect)) {
                        userRemove(data[0]);
                    } else if (data[2].equals(done)) {
                        // users.setText("");
                        writeUsers();
                        userss.clear();
                    }
                }
            } catch (Exception ex) {
                // Handle exception
            }
        }
    }

    @Override
    public void run() {
        String message, connect = "Connect", disconnect = "Disconnect", chat = "Chat";
        String[] data;
        try {
            while ((message = reader.readLine()) != null) {
                ta_chat.append("Received: " + message + "\n");
                data = message.split(":");
                for (String token : data) {
                    ta_chat.append(token + "\n");
                }

                if (data[2].equals(connect)) {
                    tellEveryone((data[0] + ":" + data[1] + ":" + chat));
                    userAdd(data[0]);
                } else if (data[2].equals(disconnect)) {
                    tellEveryone((data[0] + ": has disconnected." + ":" + chat));
                    userRemove(data[0]);
                } else if (data[2].equals(chat)) {
                    tellEveryone(message);
                } else {
                    ta_chat.append("No Conditions were met. \n");
                }
            }
        } catch (Exception ex) {
            ta_chat.append("Lost a connection. \n");
            ex.printStackTrace();
            clientOutputStreams.remove(client);
        }
    }
}
    
    
    /**
     * Creates new form frame_server
     */
    public frame_server() {
        initComponents();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        ta_chat = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        ta_online = new javax.swing.JTextArea();
        b_start = new javax.swing.JButton();
        b_end = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        b_send = new javax.swing.JButton();
        b_clear = new javax.swing.JButton();
        b_users = new javax.swing.JButton();
        tf_chat = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(0, 32));

        ta_chat.setBackground(new java.awt.Color(204, 204, 255));
        ta_chat.setColumns(20);
        ta_chat.setFont(new java.awt.Font("Open Sans", 0, 12)); // NOI18N
        ta_chat.setRows(5);
        jScrollPane1.setViewportView(ta_chat);

        ta_online.setBackground(new java.awt.Color(204, 204, 255));
        ta_online.setColumns(20);
        ta_online.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        ta_online.setRows(5);
        ta_online.setText("Online User :");
        jScrollPane2.setViewportView(ta_online);

        b_start.setBackground(new java.awt.Color(255, 102, 204));
        b_start.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        b_start.setText("START");
        b_start.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        b_start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_startActionPerformed(evt);
            }
        });

        b_end.setBackground(new java.awt.Color(255, 102, 204));
        b_end.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        b_end.setText("END");
        b_end.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        b_end.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_endActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Open Sans", 1, 18)); // NOI18N
        jLabel1.setText("Currently Online");

        b_send.setBackground(new java.awt.Color(255, 102, 255));
        b_send.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        b_send.setText("Send");
        b_send.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        b_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_sendActionPerformed(evt);
            }
        });

        b_clear.setBackground(new java.awt.Color(255, 102, 204));
        b_clear.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        b_clear.setText("Clear");
        b_clear.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        b_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_clearActionPerformed(evt);
            }
        });

        b_users.setBackground(new java.awt.Color(255, 102, 204));
        b_users.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        b_users.setText("Online Us..");
        b_users.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        b_users.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_usersActionPerformed(evt);
            }
        });

        tf_chat.setBackground(new java.awt.Color(255, 204, 255));
        tf_chat.setFont(new java.awt.Font("Open Sans", 0, 12)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(b_start, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(110, 110, 110)
                        .addComponent(b_end, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tf_chat)
                        .addGap(18, 18, 18)
                        .addComponent(b_send, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(b_clear, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(b_users, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(b_start, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(b_end, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tf_chat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(b_clear, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(b_users, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(b_send, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void b_sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_sendActionPerformed
        // TODO add your handling code here:
         String nothing = "";
    if (tf_chat.getText().equals(nothing)) {
        tf_chat.setText("");
        tf_chat.requestFocus();
    } else {
        try {
            writer.println(username + ":" + tf_chat.getText() + ":" + "Chat");
            writer.flush();
        } catch (Exception ex) {
            ta_chat.append("Message was not sent. \n");
        }
        tf_chat.setText("");
        tf_chat.requestFocus();
    }
    }//GEN-LAST:event_b_sendActionPerformed

    private void b_endActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_endActionPerformed
        // TODO add your handling code here:
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt(); // Mengatur status interrupt
        }
        tellEveryone("Server: is stopping and all users will be disconnected.\n:Chat");
        ta_chat.append("Server stopping... \n");
        ta_chat.setText("");
    }//GEN-LAST:event_b_endActionPerformed

    private void b_startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_startActionPerformed
        // TODO add your handling code here:
        Thread starter = new Thread(new ServerStart());
        starter.start();

        ta_chat.append("Server started...\n");

        if (!isConnected) {
            username = "Server";
            try {
                s = new Socket(address, port);
                InputStreamReader sr = new InputStreamReader(s.getInputStream());
                reader = new BufferedReader(sr);
                writer = new PrintWriter(s.getOutputStream());
                writer.println(username + ":has connected.:Connect");
                writer.flush();
                isConnected = true;
            } catch (Exception ex) {
                ta_chat.append("Cannot Connect! Try Again. \n");
            }
            ListenThread();
        } else {
            ta_chat.append("You are already connected. \n");}
    }//GEN-LAST:event_b_startActionPerformed

    private void b_usersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_usersActionPerformed
        // TODO add your handling code here:
        ta_online.append("\n");
        for (String current_user : users)
        {
            ta_online.append(current_user);
            ta_online.append("\n");
        } 
    }//GEN-LAST:event_b_usersActionPerformed

    private void b_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_clearActionPerformed
        // TODO add your handling code here:
        ta_chat.setText("");
        ta_online.setText("");
    }//GEN-LAST:event_b_clearActionPerformed

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
            java.util.logging.Logger.getLogger(frame_server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frame_server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frame_server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frame_server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frame_server().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b_clear;
    private javax.swing.JButton b_end;
    private javax.swing.JButton b_send;
    private javax.swing.JButton b_start;
    private javax.swing.JButton b_users;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea ta_chat;
    private javax.swing.JTextArea ta_online;
    private javax.swing.JTextField tf_chat;
    // End of variables declaration//GEN-END:variables
}
