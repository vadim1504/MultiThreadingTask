package mode;

import classes.logger;
import mode.gui.InitForm;
import publicTransportation.PublicTransportation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Start {

    private Properties properties = null;
    private InputStream inputStream;
    private static String mode;

    public static void main(String[] args) {
        try {
            new Start().writeMode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(mode.equals("console")){
            PublicTransportation publicTransportation = new PublicTransportation(0);
            try {
                publicTransportation.writeProperties();
            } catch (IOException e) {
                logger.log("Ошибка при чтение properties файла!");
                e.printStackTrace();
            }
            publicTransportation.init();
            publicTransportation.start();
        }else if(mode.equals("gui")){
            new InitForm();
        }
    }

    public void writeMode() throws IOException {
        try {
            properties = new Properties();
            String propFileName = "initParameters";
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            if (inputStream != null) {
                properties.load(inputStream);
                mode = properties.getProperty("mode");
            } else {
                throw new FileNotFoundException();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
        }
    }
}
