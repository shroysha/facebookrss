package dev.shroysha.facebookrss.view;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class FacebookRssLogInDialog extends javax.swing.JFrame {

    private static BufferedReader input;
    private static OutputStream output;

    public FacebookRssLogInDialog() {
        initComponents();
    }

    public static void main(String[] args) {
        connectToFacebook();
        new FacebookRssLogInDialog().setVisible(true);
    }

    private static void connectToFacebook() {

        try {
            URL url = new URL("http://www.facebook.com/");
            URLConnection connection = url.openConnection();
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL Facebook.com");
        } catch (IOException e) {
            System.out.println("Can't Connect to Facebook.com");
        }


    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        javax.swing.JButton loginbutton = new javax.swing.JButton();
        javax.swing.JTextField jTextField1 = new javax.swing.JTextField();
        javax.swing.JTextField jTextField2 = new javax.swing.JTextField();
        javax.swing.JLabel logoLabel = new javax.swing.JLabel();
        javax.swing.JLabel usernameLabel = new javax.swing.JLabel();
        javax.swing.JLabel passwordLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 204));

        loginbutton.setBackground(new java.awt.Color(102, 102, 255));
        loginbutton.setFont(new java.awt.Font("Tahoma", Font.ITALIC, 36));
        loginbutton.setText("Log In");
        loginbutton.addActionListener(evt -> loginbuttonActionPerformed());

        logoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/facebook.jpg"))); // NOI18N

        usernameLabel.setForeground(new java.awt.Color(255, 255, 255));
        usernameLabel.setText("email");

        passwordLabel.setForeground(new java.awt.Color(255, 255, 255));
        passwordLabel.setText("password");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(passwordLabel)
                                                        .addComponent(usernameLabel))
                                                .addGap(60, 60, 60)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                                                        .addComponent(loginbutton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addContainerGap(93, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(logoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 337, Short.MAX_VALUE)
                                                .addGap(21, 21, 21))))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(logoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(usernameLabel))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(passwordLabel)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(42, 42, 42)
                                .addComponent(loginbutton)
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }

    private void loginbuttonActionPerformed() {
    }
}
