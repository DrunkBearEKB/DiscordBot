package utils;

import java.util.Date;

public class LogMessage {
    private String message;
    private final int tabLength;
    private boolean addTime = true;
    private int amountParts = 0;

    public LogMessage(String author) {
        this.message = "[" + author + "]: ";
        this.tabLength = this.message.length();

        if (this.addTime)
            amountParts += 1;
    }

    public LogMessage(String author, boolean addTime) {
        this.message = "[" + author + "]: ";
        this.tabLength = this.message.length();
        this.addTime = addTime;

        if (this.addTime)
            amountParts += 1;
    }

    public void addMessagePart(String name, String content) {
        this.message += name + ": \"" + content + "\";\n" +
                Functions.multiplyString(" ", this.tabLength);
        this.amountParts += 1;
    }

    public String ToString() {
        if (this.addTime) {
            Date date = new Date(System.currentTimeMillis());
            this.addMessagePart("Time", date.toString());
        }

        if (amountParts != 0)
            return this.message.substring(0, this.message.length() - tabLength - 1);
        return this.message + ";";
    }
}
