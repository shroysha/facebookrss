package dev.shroysha.facebookrss.model;

import java.net.URL;

public class FacebookRssNotification implements Comparable<FacebookRssNotification> {

    private static String rssTitle;
    private final String title, description;
    private final URL link;
    private final String date;
    private boolean alreadySeen;

    public FacebookRssNotification(String title, String description, String dateText, URL link, boolean alreadySeen) {
        this.title = title.trim();
        this.description = removeHTML(description.trim());
        this.date = dateText;
        this.link = link;
        this.alreadySeen = alreadySeen;
    }

    public static String getRSSTitle() {
        return rssTitle;
    }

    public static void setRSSTitle(String rssTitle) {
        FacebookRssNotification.rssTitle = rssTitle;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public URL getLink() {
        return link;
    }

    public String toString() {
        return "Notification{" + "title=" + title + ", description=" + description + ", date=" + date + '}';
    }

    private String removeHTML(String toRemoveFrom) {
        final char open = '<';
        final char close = '>';

        StringBuilder result = new StringBuilder();
        boolean append = true;
        for (int i = 0; i < toRemoveFrom.length(); i++) {
            char charAt = toRemoveFrom.charAt(i);

            if (charAt == open) {
                append = false;
            }

            if (append) {
                result.append(charAt);
            }

            if (charAt == close) {
                append = true;
            }
        }

        return result.toString();
    }

    public int compareTo(FacebookRssNotification other) {
        return this.toString().compareTo(other.toString());
    }

    public String toXML() {
        return "<item>\n"
                + "<title><![CDATA[" + this.getTitle() + "]]></title>\n"
                + "<link>" + link.toString().split("&")[0] + "</link>\n"
                + "<description><![CDATA[" + this.getDescription() + "]]></description>\n"
                + "<pubDate>" + this.getDate() + "</pubDate>\n"
                + "<checked>" + alreadySeen + "</checked>\n"
                + "</item>\n";
    }

    public boolean isChecked() {
        return alreadySeen;
    }

    public void setChecked(boolean checked) {
        this.alreadySeen = checked;
    }

}
