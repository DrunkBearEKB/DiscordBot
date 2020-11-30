package utils;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Functions {
    public static String multiplyString(String str, int number) {
        if (number > 0) {
            StringBuilder stringBuilder = new StringBuilder(str);
            for (int i = 0; i < number - 1; i++)
                stringBuilder.append(str);
            return stringBuilder.toString();
        } else if (number == 0)
            return "";
        else
            return multiplyString((new StringBuilder(str)).reverse().toString(), -number);
    }

    public static String title(String str) {
        if (str.length() != 0) {
            try {
                return str.substring(0, 1).toUpperCase() + str.substring(1);
            } catch (Exception exception) {
                exception.printStackTrace();
                return str;
            }
        }

        throw new StringIndexOutOfBoundsException();
    }

    public static void createSystemTray(String text, String iconName) throws Exception {
        if (SystemTray.isSupported()) {
            String iconPath = "resources/systemTrayImages/";
            if (iconName.equals("information") || iconName.equals("error") || iconName.equals("success"))
                iconPath += iconName + ".png";
            else
                iconPath += "information.png";

            String trayName = title(iconName);
            TrayIcon.MessageType trayType;
            if (iconName.equals("error"))
                trayType = TrayIcon.MessageType.ERROR;
            else
                trayType = TrayIcon.MessageType.INFO;

            SystemTray tray = SystemTray.getSystemTray();

            java.awt.Image image = Toolkit.getDefaultToolkit().getImage(iconPath);
            TrayIcon trayIcon = new TrayIcon(image);
            tray.add(trayIcon);
            trayIcon.setImageAutoSize(true);

            trayIcon.displayMessage(trayName, text, trayType);
        }
    }

    public static boolean checkNetAvailable() {
        try {
            URL url = new URL("http://www.yandex.ru");
            URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }
}
