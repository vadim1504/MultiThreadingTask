package mode.consolee;

import classes.logger;
import publicTransportation.PublicTransportation;

import java.io.IOException;

public class Start {

    public static void main(String[] args){
        PublicTransportation publicTransportation = new PublicTransportation(0);
        try {
            publicTransportation.writeProperties();
        } catch (IOException e) {
            logger.log("Ошибка при чтение properties файла!");
            e.printStackTrace();
        }
        publicTransportation.init();
        publicTransportation.start();
    }
}
