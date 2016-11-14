package classes;


public class BusStop {

    private Bus bus;
    public Object passengerOut = new Object();
    public Object passengerEntered = new Object();
    private int passenger;
    private int nomer;
    private int x;

    public BusStop(int nomer, int x,int passenger) {
        this.nomer = nomer;
        this.x = x;
        this.passenger=passenger;
    }


    public void setBus(Bus bus) {
        this.bus = bus;

        logger.log(bus.getCheckBusStop(nomer)+" пассажиров на выход с "+bus.getCheckBusStop(nomer)+" на остановке "+nomer);

        synchronized (passengerOut) {
            passengerOut.notifyAll();
        }

        while (true){
            if(bus.getCheckBusStop(nomer)==0){
                break;
            }
        }

        if(bus.getCapacity()-bus.getPassenger()!=0) {


            logger.log(passenger+" пассажиров на вход в автобус "+bus.getNomer()+ " на остановке "+nomer);

            synchronized (passengerEntered) {
                passengerEntered.notifyAll();
            }

            while (true) {

                if (bus.getCapacity() - bus.getPassenger() == 0 || passenger == 0) {
                    break;
                }

            }
        }

        this.bus=null;
    }

    public Bus getBus() {
        return bus;
    }

    public int getNomer() {
        return nomer;
    }

    public int getX() {
        return x;
    }

    public synchronized void removePassenger(){
        passenger--;
    }

    public int getPassengers(){
        return passenger;
    }
}