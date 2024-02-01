package org.example;

import com.tulskiy.keymaster.common.Provider;
import com.tulskiy.keymaster.common.HotKey;
import com.tulskiy.keymaster.common.HotKeyListener;

import javax.swing.*;
import java.awt.*;

public class App {
    private static MouseAutomation mouseAutomation;
    private static Thread thread;

    public static void main(String[] args) {
        JFrame frame = new JFrame("App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        JPanel panel = new JPanel();
        frame.add(panel);

        JButton startButton = new JButton("Start");
        panel.add(startButton);

        JButton stopButton = new JButton("Stop");
        panel.add(stopButton);

        frame.setVisible(true);

        // Register global hotkeys
        Provider provider = Provider.getCurrentProvider(false);
        provider.register(KeyStroke.getKeyStroke("control shift S"), new HotKeyListener() {
            public void onHotKey(HotKey hotKey) {
                mouseAutomation = new MouseAutomation();
                thread = new Thread(mouseAutomation);
                thread.start();
            }
        });
        provider.register(KeyStroke.getKeyStroke("control shift T"), new HotKeyListener() {
            public void onHotKey(HotKey hotKey) {
                if (mouseAutomation != null) {
                    mouseAutomation.stop();
                    thread.interrupt();
                }
            }
        });
    }
}