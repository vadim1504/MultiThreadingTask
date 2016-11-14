package classes;


import mode.gui.MainForm;
import publicTransportation.PublicTransportation;

public class Passenger implements Runnable {

    private int start;
    private int stop;
    private BusStop busStopStart;
    private BusStop busStopStop;
    private int nomer;
    private int direction;
    private int n;

    public Passenger(int nomer,int start, int stop,int direction) {
        this.nomer=nomer;
        this.start = start;
        this.stop = stop;
        this.direction = direction;
    }

    @Override
    public void run() {

        logger.log("Пассажир "+nomer+" пришел на остановку "+start+" едет до "+stop+" направление "+direction);
        if(direction==0){
            busStopStart= PublicTransportation.busStopForward[start];
            busStopStop= PublicTransportation.busStopForward[stop];
        }else if(direction==1){
            busStopStart= PublicTransportation.busStopBack[start];
            busStopStop= PublicTransportation.busStopBack[stop];
        }
        go();
        out();
    }

    private void go(){
        while (true) {
            if(MainForm.exit==1){
                break;
            }
            synchronized (busStopStart.passengerEntered) {
                try {
                    busStopStart.passengerEntered.wait();
                    if(busStopStart.getBus()!=null){
                        int nomerBus = busStopStart.getBus().getNomer();
                        if (busStopStart.getBus().addPassenger(stop, direction)) {
                            logger.log("Пассажир " + nomer + " cел в автобус " + nomerBus + " на остановке " + start);
                            n = nomerBus;
                            busStopStart.removePassenger();
                            break;
                        }
                    }
                } catch (InterruptedException e) {
                    logger.log("Пассажир " + nomer + " Ошибка: " + e.toString());
                    e.printStackTrace();
                }
            }
        }

    }
    private void out(){
        while (true) {
            if(MainForm.exit==1){
                break;
            }
            synchronized (busStopStop.passengerOut) {
                try {
                    busStopStop.passengerOut.wait();
                    if(busStopStop.getBus()!=null) {
                        if (n == busStopStop.getBus().getNomer()) {
                            logger.log("Пассажир " + nomer + " вышел с автобус " + busStopStop.getBus().getNomer() + " на остановке " + stop);
                            busStopStop.getBus().removePassenger(stop);
                            PublicTransportation.passengerWorkingRemove();
                            break;
                        }
                    }
                } catch (InterruptedException e) {
                    logger.log("Пассажир " + nomer + " Ошибка: " + e.toString());
                    e.printStackTrace();
                }
            }
        }
    }

    public int getStart() {
        return start;
    }

    public int getDirection() {
        return direction;
    }
}