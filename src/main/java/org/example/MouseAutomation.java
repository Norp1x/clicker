package org.example;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

public class MouseAutomation implements Runnable {
    private volatile boolean running = true;

    @Override
    public void run() {
        try {
            Robot robot = new Robot();
            Rectangle searchArea = new Rectangle(666, 233, 550, 650);

            outerloop:
            while (running) {
                BufferedImage screen = robot.createScreenCapture(searchArea);
                int yellowPixelCount = 0;
                for (int x = 0; x < screen.getWidth(); x++) {
                    for (int y = 0; y < screen.getHeight(); y++) {
                        Color pixelColor = new Color(screen.getRGB(x, y));

                        if (isYellowColor(pixelColor)) {
                            yellowPixelCount++;

                            if (yellowPixelCount > 1300) {
                                robot.mouseMove(searchArea.x + x, searchArea.y + y);

                                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

                                yellowPixelCount = 0; // Reset the counter

                                double sleepTimeInSeconds = 0.1 + ThreadLocalRandom.current().nextDouble(0.8);
                                long sleepTimeInMilliseconds = (long) (sleepTimeInSeconds * 1000);
                                Thread.sleep(sleepTimeInMilliseconds);

                                continue outerloop;
                            }
                        }
                    }
                }
                double sleepTimeInSeconds = 0.1 + ThreadLocalRandom.current().nextDouble(0.8);
                long sleepTimeInMilliseconds = (long) (sleepTimeInSeconds * 1000);
                Thread.sleep(sleepTimeInMilliseconds);
            }
        } catch (AWTException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        running = false;
    }

    private static boolean isYellowColor(Color color) {
        int redThreshold = 200;
        int greenThreshold = 200;
        int blueThreshold = 50;

        return color.getRed() >= redThreshold && color.getGreen() >= greenThreshold && color.getBlue() <= blueThreshold;
    }
}