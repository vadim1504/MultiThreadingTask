package classes;

import publicTransportation.PublicTransportation;

import java.util.ArrayList;
import java.util.List;

public class Bus implements Runnable {

    private double x;
    private int direction;
    private int capacity;
    private double speed;
    private int nomer;
    private int passenger=0;
    private BusStop[] busStopForward;
    private List<Integer> checkBusStopForward;
    private BusStop[] busStopBack;
    private List<Integer> checkBusStopBack;
    private int route;

    public Bus(int nomer, int capacity, double speed, int route, BusStop[] busStopForward, BusStop[] busStopBack) {
        this.capacity = capacity;
        this.nomer = nomer;
        this.speed = speed;
        this.busStopForward = busStopForward;
        this.busStopBack = busStopBack;
        this.route = route;
        initMap();
    }

    public void run(){
        x=0;
        direction=0;
        logger.log("Автобус " + nomer + " начал работу!");
        forward();
        logger.log("Автобус " + nomer + " приехал на конечную станцию!");
        direction=1;
        x=0;
        back();
        logger.log("Автобус " + nomer + " закончил работу!");
        PublicTransportation.k();
    }

    public void forward(){
        int i=0;
        while (x<= route+speed) {
            if(i< busStopForward.length) {
                if (busStopForward[i].getX() <= x) {
                    logger.log("Автобус " + nomer + " привехал на остановку номер: " + busStopForward[i].getNomer() + " едет вперёд. Количество пассажров " + passenger);
                    busStopForward[i].setBus(this);
                    i++;
                }
            }
            x = x + speed;
        }
    }
    public void back(){
        int i=0;
        while (x<= route+speed){
            if(i<busStopBack.length) {
                if (busStopBack[i].getX() <= x) {
                    logger.log("Автобус " + nomer + " привехал на остановку номер: " + busStopBack[i].getNomer() + " едет назад. Количество пассажров " + passenger);
                    busStopBack[i].setBus(this);
                    i++;
                }
            }
            x=x+speed;
        }
    }

    public synchronized boolean addPassenger(int stop,int direction){
        if(passenger<capacity){
            if(direction==0){
                int x = checkBusStopForward.get(stop);
                checkBusStopForward.set(stop,++x);
            }
            if(direction==1){
                int x = checkBusStopBack.get(stop);
                checkBusStopBack.set(stop,++x);
            }
            passenger++;
            return true;
        }else
            return false;
    }

    public synchronized void removePassenger(int stop){
        passenger--;
        if(direction==0){
            int x = checkBusStopForward.get(stop);
            checkBusStopForward.set(stop,--x);
        }
        if(direction==1){
            int x = checkBusStopBack.get(stop);
            checkBusStopBack.set(stop,--x);
        }
    }

    private void initMap(){
        checkBusStopForward = new ArrayList<Integer>();
        checkBusStopBack = new ArrayList<Integer>();

        for(BusStop b: busStopForward){
            checkBusStopForward.add(b.getNomer(),0);
        }
        for(BusStop b: busStopBack){
            checkBusStopBack.add(b.getNomer(),0);
        }
    }


    public synchronized int getCheckBusStop(int id){
        if(direction==0) {
            return checkBusStopForward.get(id).intValue();
        }
        if(direction==1){
            return checkBusStopBack.get(id).intValue();
        }
        return 0;
    }

    public int getNomer(){
        return nomer;
    }

    public synchronized int getPassenger() {
        return passenger;
    }

    public synchronized int getCapacity() {
        return capacity;
    }

    public int getDirection(){
        return direction;
    }

    public double getX(){
        return x;
    }
}