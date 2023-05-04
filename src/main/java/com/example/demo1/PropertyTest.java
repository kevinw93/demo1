package com.example.demo1;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class PropertyTest {

    public static void main(String[] args) {

        try (OutputStream output = new FileOutputStream("src/main/java/com/example/demo1/thing.properties")) {

            Properties prop = new Properties();

            // set the properties value
            prop.setProperty("db.url", "localhost");
            prop.setProperty("db.user", "root");
            prop.setProperty("db.password", "R3by$ound");

            // save properties to project root folder
            prop.store(output, null);

            System.out.println(prop);

        } catch (IOException io) {
            io.printStackTrace();
        }

    }
}
