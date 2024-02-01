package org.example;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;

public class MouseAutomation {

    public static void main(String[] args) throws AWTException, InterruptedException {
        Robot robot = new Robot();

        Rectangle searchArea = new Rectangle(0, 0, 200, 200);

        outerloop:
        while (true) {
            BufferedImage screen = robot.createScreenCapture(searchArea);

            for (int x = 0; x < screen.getWidth(); x++) {
                for (int y = 0; y < screen.getHeight(); y++) {
                    Color pixelColor = new Color(screen.getRGB(x, y));

                    if (isYellowColor(pixelColor)) {
                        System.out.println("Found a yellow color area!");

                        robot.mouseMove(x, y);

                        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

                        Thread.sleep(1000);

                        continue outerloop;
                    }
                }
            }

            Thread.sleep(1000);
        }
    }

    private static boolean isYellowColor(Color color) {
        int redThreshold = 200;
        int greenThreshold = 200;
        int blueThreshold = 50;

        return color.getRed() >= redThreshold && color.getGreen() >= greenThreshold && color.getBlue() <= blueThreshold;
    }
}