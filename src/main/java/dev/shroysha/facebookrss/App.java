package dev.shroysha.facebookrss;

import dev.shroysha.facebookrss.controller.FacebookRssReader;
import dev.shroysha.facebookrss.view.FacebookRssNotificationsPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new ShutdownHook());

        FacebookRssNotificationsPanel np = new FacebookRssNotificationsPanel(FacebookRssReader.getNotifications());
        JFrame frame = new JFrame("Notifications");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(400, 300);
        frame.add(np, BorderLayout.CENTER);
        frame.setVisible(true);

    }

    private static class ShutdownHook extends Thread {
        public void run() {
            try {
                FacebookRssReader.writeCached();
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
