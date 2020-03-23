package dev.shroysha.facebookrss.controller;

import dev.shroysha.facebookrss.model.FacebookRssNotification;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FacebookRssReader {

    private static URL rssFeed;
    private static File cachedFile;
    private static FacebookRssNotification[] oldNots;

    /*
     * Example
     * <item>
      <guid isPermaLink="false">www.facebook.com/notification/21155753</guid>
      <title><![CDATA[Sarah Johnson likes your photo.]]></title>
      <link>http://www.facebook.com/n/?photo.php&amp;fbid=368320493192901&amp;set=a.104007672957519.8673.100000448606719&amp;type=1&amp;aref=21155753&amp;medium=rss</link>
      <description><![CDATA[<a href="http://www.facebook.com/sarah.a.johnson3">Sarah Johnson</a> likes your <a class="pronoun-link " href="http://www.facebook.com/n/?photo.php&amp;fbid=368320493192901&amp;set=a.104007672957519.8673.100000448606719&amp;type=1&amp;aref=21155753&amp;medium=rss">photo</a>.]]></description>
      <pubDate>Fri, 09 Mar 2012 00:27:25 -0500</pubDate>
    </item>
     */
    static {
        try {
            rssFeed = new URL("http://www.facebook.com/feeds/notifications.php?id=100000448606719&viewer=100000448606719&key=AWj7B57CCPvm8UFH&format=rss20");
            cachedFile = new File("src/notification/cachedNotifications");
            oldNots = readCached();
            Timer timer = new Timer(7500, new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    try {
                        oldNots = readRSS();
                    } catch (IOException ex) {
                        Logger.getLogger(FacebookRssReader.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            timer.start();
        } catch (IOException ex) {
            Logger.getLogger(FacebookRssReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    private static int countNumberOfNewNotifications(FacebookRssNotification[] nue, FacebookRssNotification[] old) {
        int num = 0;

        // go through the new array
        for (FacebookRssNotification notification : nue) {
            boolean found = false;
            int foundAt = -1;
            //and compare it with the old array
            for (int j = 0; j < old.length && !found; j++) {
                //then check if the new notification is the same as the old one
                if (notification.compareTo(old[j]) == 0) {
                    found = true;
                    foundAt = j;
                }
            }

            // if it's not found, it's a new notification
            if (!found) {
                num++;
            }

            // if it is found
            if (foundAt != -1) {
                // and still not checked
                if (!old[foundAt].isChecked()) {
                    num++;
                    System.out.println("Is unchecked " + foundAt + "    " + old[foundAt].getTitle());
                }
            }

        }

        return num;
    }

    public static FacebookRssNotification[] getNotifications() {
        return oldNots;
    }

    public static void setNotificationArray(FacebookRssNotification[] nots) {
        oldNots = nots;
    }

    public static FacebookRssNotification[] readRSS() throws IOException {
        try {

            URLConnection connection = rssFeed.openConnection();
            InputStream is = connection.getInputStream();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            Document document = builder.parse(is);
            XPathFactory xpfactory = XPathFactory.newInstance();
            XPath xpath = xpfactory.newXPath();

            FacebookRssNotification.setRSSTitle(xpath.evaluate("/rss/channel/title", document));

            final String path = "/rss/channel/";
            int numOfNots = Integer.parseInt(
                    xpath.evaluate("count(" + path + "item)", document)
            );

            ArrayList<FacebookRssNotification> notifications = new ArrayList<>();

            for (int i = 1; i <= numOfNots; i++) {

                String title = xpath.evaluate(path + "item[" + i + "]/title", document);
                String description = xpath.evaluate(path + "item[" + i + "]/description", document).replaceAll("&#039;", "'");
                String date = xpath.evaluate(path + "item[" + i + "]/pubDate", document);
                String link = xpath.evaluate(path + "item[" + i + "]/link", document);

                FacebookRssNotification not = new FacebookRssNotification(title, description, date, new URL(link), true);
                notifications.add(not);
            }

            FacebookRssNotification[] nots = notifications.toArray(new FacebookRssNotification[0]);

            int numberOfNew = countNumberOfNewNotifications(nots, oldNots);

            System.out.println(numberOfNew);

            for (int i = 0; i < numberOfNew; i++) {
                nots[i].setChecked(false);
            }

            oldNots = nots;

            is.close();

            return oldNots;

        } catch (ParserConfigurationException | XPathExpressionException | SAXException ex) {
            Logger.getLogger(FacebookRssReader.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }


    }

    private static FacebookRssNotification[] readCached() throws IOException {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            Document document = builder.parse(cachedFile);
            XPathFactory xpfactory = XPathFactory.newInstance();
            XPath xpath = xpfactory.newXPath();

            FacebookRssNotification.setRSSTitle(xpath.evaluate("/root/title", document));

            final String path = "/root/";
            ArrayList<FacebookRssNotification> notifications = new ArrayList<>();

            int numOfNots = Integer.parseInt(
                    xpath.evaluate("count(" + path + "item)", document)
            );

            for (int i = 1; i <= numOfNots; i++) {

                String title = xpath.evaluate(path + "item[" + i + "]/title", document);
                String description = xpath.evaluate(path + "item[" + i + "]/description", document).replaceAll("&#039;", "'");
                String date = xpath.evaluate(path + "item[" + i + "]/pubDate", document);
                String link = xpath.evaluate(path + "item[" + i + "]/link", document);
                boolean checked = Boolean.parseBoolean(xpath.evaluate(path + "item[" + i + "]/checked", document));

                FacebookRssNotification not = new FacebookRssNotification(title, description, date, new URL(link), checked);
                notifications.add(not);
            }


            return notifications.toArray(new FacebookRssNotification[0]);

        } catch (ParserConfigurationException | XPathExpressionException | SAXException ex) {
            return null;
        }


    }

    public static void writeCached() throws IOException {

        FileWriter write = new FileWriter(cachedFile);

        write.write("<root>\n");
        try {

            for (FacebookRssNotification oldNot : oldNots) {
                write.write(oldNot.toXML());

                write.write("\n");
            }

        } catch (Exception ex) {
            Logger.getLogger(FacebookRssReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        write.write("</root>");

        write.close();
    }


}
