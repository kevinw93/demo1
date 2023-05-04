/*
Kevin Wang
3 May 2023
AP Computer Science A
2nd Period
Master Project
Main Class
 */

package com.example.demo1;


import java.awt.Taskbar;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {

    public static void main(String[] args) throws IOException {

        //Retrieve Operating System Name from System (OS)
        String systemOS = System.getProperty("os.name").toLowerCase();

        //Using indexOf to determine if OS is macOS
        if (systemOS.indexOf("mac") >=0 ) {

            //Creating taskbar
            Taskbar taskbar = Taskbar.getTaskbar();

            //Getting image to user as icon
            BufferedImage image = ImageIO.read(new File("C:\\Users\\kevin\\IdeaProjects\\demo1\\src\\main\\resources\\com\\example\\demo1\\icon.png"));

            //Setting image to taskbar
            taskbar.setIconImage(image);

        }

        //Running main FX file to run controller
        MainFX.main(args);

    }


}


