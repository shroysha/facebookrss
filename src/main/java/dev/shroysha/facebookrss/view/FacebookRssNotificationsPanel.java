package dev.shroysha.facebookrss.view;

import dev.shroysha.facebookrss.controller.FacebookRssReader;
import dev.shroysha.facebookrss.model.FacebookRssNotification;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FacebookRssNotificationsPanel extends JPanel {

    private FacebookRssNotification[] notifications;
    private JLabel titleLabel;
    private JScrollPane scroller;
    private JPanel notPanel;

    public FacebookRssNotificationsPanel(FacebookRssNotification[] notifications) {
        this.notifications = notifications;
        init();
    }

    public static void main(String[] args) {


        FacebookRssNotificationsPanel np = new FacebookRssNotificationsPanel(FacebookRssReader.getNotifications());
        JFrame frame = new JFrame("Notifications");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(400, 300);
        frame.add(np, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void init() {
        this.setBackground(Color.WHITE);
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setLayout(new BorderLayout());


        notPanel = new JPanel();

        createTitleLabel();
        createNotificationScroller();

        this.add(titleLabel, BorderLayout.PAGE_START);
        this.add(scroller, BorderLayout.CENTER);

        Timer refresh = new Timer(7500, e -> refreshNotifications());
        refresh.start();

    }

    private void createTitleLabel() {
        titleLabel = new JLabel(FacebookRssNotification.getRSSTitle());
    }

    private void createNotificationScroller() {

        scroller = new JScrollPane();
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroller.getVerticalScrollBar().setUnitIncrement(15);
        createNotificationPanel();

        scroller.setViewportView(notPanel);
    }

    private void createNotificationPanel() {

        notPanel.setBackground(this.getBackground());
        notPanel.setLayout(new BoxLayout(notPanel, BoxLayout.Y_AXIS));
        notPanel.removeAll();

        for (int i = 0; i < notifications.length; i++) {

            //JPanel sNotPanel = createNotificationPanel(notifications[i]);
            JPanel sNotPanel = createNotificationPanel(i);

            sNotPanel.setAlignmentX(Box.LEFT_ALIGNMENT);
            notPanel.add(sNotPanel);
        }

        notPanel.validate();
    }

    private void refreshNotifications() {


        FacebookRssNotification[] newNots = FacebookRssReader.getNotifications();

        for (int i = 0; i < newNots.length; i++) {

            FacebookRssNotification old = notifications[i];
            FacebookRssNotification nue = newNots[i];

            boolean changed = false;

            if (old.compareTo(nue) != 0)
                changed = true;

            if (changed) {
                System.err.println("Needs reset");
                Toolkit.getDefaultToolkit().beep();

                notifications = FacebookRssReader.getNotifications();

                createNotificationPanel();
            }

        }


    }

    private JPanel createNotificationPanel(final int index) {
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EtchedBorder());

        Color bgcolor;

        if (notifications[index].isChecked())
            bgcolor = Color.WHITE;
        else
            bgcolor = Color.orange.brighter().brighter().brighter();

        panel.setBackground(bgcolor);

        final JTextArea descriptionArea;
        descriptionArea = new JTextArea(notifications[index].getDescription() + "\n\n" + notifications[index].getDate());
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        descriptionArea.setEditable(false);
        descriptionArea.addMouseListener(new MouseAdapter() {


            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                panel.setBackground(Color.BLUE);
                descriptionArea.setCursor(new Cursor(Cursor.HAND_CURSOR));
                descriptionArea.setBackground(Color.BLUE);
                descriptionArea.setForeground(Color.WHITE);
                notifications[index].setChecked(true);
                FacebookRssReader.setNotificationArray(notifications);
            }


            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                panel.setCursor(Cursor.getDefaultCursor());
                panel.setBackground(Color.WHITE);
                descriptionArea.setCursor(Cursor.getDefaultCursor());
                descriptionArea.setBackground(Color.WHITE);
                descriptionArea.setForeground(Color.BLACK);
            }


            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    try {
                        if (desktop.isSupported(Desktop.Action.BROWSE))
                            desktop.browse(notifications[index].getLink().toURI());

                    } catch (IOException ex) {
                        Logger.getLogger(FacebookRssNotificationsPanel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (URISyntaxException ignored) {

                    }
                }

            }

        });
        descriptionArea.setBackground(bgcolor);

        ImageIcon icon = createIconBasedOnDescription(notifications[index].getDescription());

        panel.add(Box.createVerticalStrut(5), BorderLayout.NORTH);
        panel.add(descriptionArea, BorderLayout.CENTER);

        if (icon != null) {
            JLabel iconLabel = new JLabel(icon);

            panel.add(iconLabel, BorderLayout.WEST);
        }

        panel.add(Box.createVerticalStrut(5), BorderLayout.SOUTH);

        return panel;
    }

    private ImageIcon createIconBasedOnDescription(String description) {

        if (description.contains("like"))
            return new ImageIcon("src/images/Like.png");

        if (description.contains("commented on"))
            return new ImageIcon("src/images/Comment.png");

        if (description.contains("post"))
            return new ImageIcon("src/images/Wall.png");

        if (description.contains("picture"))
            return new ImageIcon("src/Picture.png");

        return null;

    }
}
