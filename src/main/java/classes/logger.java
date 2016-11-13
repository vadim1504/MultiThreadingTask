package classes;

import mode.gui.MainForm;
import org.apache.log4j.Logger;
import publicTransportation.PublicTransportation;

public class logger {
    private static String string = "";
    public final static Logger log = Logger.getLogger(logger.class);

    public synchronized static void log(String s) {
        if (PublicTransportation.mode == 0) {
            System.out.println(s);
        } else if (PublicTransportation.mode == 1) {
            string = string + "\n" + s;
            MainForm.setTextArea1(string);
        }
        log.info(s);
    }
}
