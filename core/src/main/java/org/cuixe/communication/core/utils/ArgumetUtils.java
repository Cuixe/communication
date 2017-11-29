package org.cuixe.communication.core.utils;

import java.util.Properties;

public class ArgumetUtils {

    public static Properties create(String [] args) {
        Properties properties = new Properties();
        for(int i = 0; i< args.length; i++) {
            String arg = args[i];
            String[] paths = arg.split("=");
            properties.setProperty(paths[0], paths[1]);
        }
        return properties;
    }
}
