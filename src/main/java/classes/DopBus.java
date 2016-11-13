package classes;

import publicTransportation.PublicTransportation;

public class DopBus extends Thread {

    private int i;

    public void run(){
        i=0;
        while (true) {
            while (PublicTransportation.k != i){
            }
            i = i - PublicTransportation.nBus;
            if (PublicTransportation.k1 > 0) {
                logger.log("Не уехавшие пассажиры " + PublicTransportation.k1);
                logger.log("Автобусы пошли на дополнительный круг");
                PublicTransportation.startBus();
            }
            if(PublicTransportation.k1 ==0){
                break;
            }
        }
    }
}
