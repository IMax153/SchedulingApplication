/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import models.User;

/**
 * Class which handles logging of {@link User} login events.
 *
 * @author maxbrown
 */
public class UserLogger {

    private final static Logger LOGGER = Logger.getLogger(UserLogger.class.getName());

    private static FileHandler LOG_FILE;

    public static void log(User user) {
        try {
            if (LOG_FILE == null) {
                LOG_FILE = new FileHandler("logins.txt", true);
                LOG_FILE.setFormatter(new SimpleFormatter());
                LOGGER.addHandler(LOG_FILE);
                LOGGER.setLevel(Level.INFO);
            }

            LOGGER.log(Level.INFO, user.toString());
        } catch (IOException e) {
            // Fail gracefully
            System.out.println(e.getMessage());
        }
    }

}
