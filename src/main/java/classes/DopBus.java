package classes;

import mode.gui.MainForm;
import publicTransportation.PublicTransportation;

public class DopBus extends Thread {

    private int i;

    public void run(){
        i=0;
        while (true) {
            while (PublicTransportation.busWorking != i){
                if(MainForm.exit==1){
                    break;
                }
            }
            if(MainForm.exit==1){
                break;
            }
            if (PublicTransportation.passengerWorking > 0) {
                logger.log("Не уехавшие пассажиры " + PublicTransportation.passengerWorking);
                logger.log("Автобусы пошли на дополнительный круг");
                PublicTransportation.startBus();
            }
            if(PublicTransportation.passengerWorking ==0){
                break;
            }
        }
    }
}
