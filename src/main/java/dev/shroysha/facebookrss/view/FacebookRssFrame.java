package dev.shroysha.facebookrss.view;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FacebookRssFrame extends JFrame {

    public static URL myRSS;
    private JTabbedPane tabbedPane;
    private FacebookRssNotificationsPanel notPanel;

    public FacebookRssFrame() throws MalformedURLException {
        super("Facebook");
        myRSS = new URL("http://www.facebook.com/feeds/notifications.php?id=100000448606719&viewer=100000448606719&key=AWj7B57CCPvm8UFH&format=rss20");
        init();
    }

    public static void main(String[] agrs) {
        try {
            FacebookRssFrame frame = new FacebookRssFrame();
            frame.setVisible(true);
        } catch (MalformedURLException ex) {
            Logger.getLogger(FacebookRssFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void init() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(400, 300);

        createTabbedPane();

        this.add(tabbedPane);
    }

    private void createTabbedPane() {
        tabbedPane = new JTabbedPane();

        createNotificationPanel();

        tabbedPane.addTab("Notifications", notPanel);

    }

    private void createNotificationPanel() {
//        try {
//            FacebookRSSReader reader = new FacebookRSSReader();
//        } catch (IOException ex) {
//            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ParserConfigurationException ex) {
//            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SAXException ex) {
//            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (XPathExpressionException ex) {
//            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }


}
