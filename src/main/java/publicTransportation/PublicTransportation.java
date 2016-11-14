package publicTransportation;

import classes.Bus;
import classes.BusStop;
import classes.DopBus;
import classes.Passenger;
import classes.logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

public class PublicTransportation {
    private Properties properties=null;
    private InputStream inputStream;
    public static volatile int busWorking;
    public static volatile int passengerWorking;
    public static int nBus;
    public int nPassengers;
    public static int route = 1000000000;
    public int nbusStop;
    public int capacityBus;
    public static int interval;
    public double speed;
    public static Bus[] buses;
    private Passenger[] passengers;
    public static BusStop[] busStopForward;
    public static BusStop[] busStopBack;
    public static int mode;

    public PublicTransportation(int mode){
        this.mode = mode;
    }

    public void writeProperties() throws IOException {
        try {
            properties = new Properties();
            String propFileName = "initParameters";
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            if (inputStream != null) {
                properties.load(inputStream);
                nBus=Integer.valueOf(properties.getProperty("bus"));
                nPassengers=Integer.valueOf(properties.getProperty("totalPassengers"));
                nbusStop=Integer.valueOf(properties.getProperty("busStop"));
                capacityBus=Integer.valueOf(properties.getProperty("capacityBus"));
                interval=Integer.valueOf(properties.getProperty("interval"));
                speed=Double.valueOf(properties.getProperty("speed"));
                busWorking=nBus;
                passengerWorking=nPassengers;
            } else {
                throw new FileNotFoundException();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
        }
    }

    public void init(){
        initPassenger();
        initBusStop();
        initBus();
    }

    private void initBus(){
        buses = new Bus[nBus];
        for(int i=0;i<buses.length;i++){
            buses[i] = new Bus(i,capacityBus,speed,route,busStopForward,busStopBack);
        }
    }

    private void initPassenger(){
        Random random = new Random();
        passengers = new Passenger[nPassengers];
        for(int i=0;i<passengers.length;i++){
            int start = random.nextInt(nbusStop);
            int stop = random.nextInt(nbusStop-start)+start;
            if(start==stop){
                i--;
            }else {
                passengers[i] = new Passenger(i,start, stop,random.nextInt(2));
            }

        }
    }

    private void initBusStop(){
        busStopForward = new BusStop[nbusStop];
        busStopBack = new BusStop[nbusStop];
        int interval = route / nbusStop;

        for (int i = 0; i < nbusStop; i++) {
            int p0=0;
            int p1=0;
            for (int j = 0; j < passengers.length; j++) {
                if (passengers[j].getStart() == i && passengers[j].getDirection() == 0) {
                    p0++;
                }
                if (passengers[j].getStart() == i && passengers[j].getDirection() == 1) {
                    p1++;
                }
            }
            if (i == 0) {
                busStopForward[0] = new BusStop(0, interval,p0);
                busStopBack[0] = new BusStop(0, interval,p1);
            } else {
                busStopForward[i] = new BusStop(i, (busStopForward[i - 1].getX() + interval),p0);
                busStopBack[i] = new BusStop(i, (busStopBack[i - 1].getX() + interval),p1);
            }
        }
    }

    public void start(){
        Thread[] threadsPassenger = new Thread[passengers.length];
        for(int i=0;i<threadsPassenger.length;i++){
            threadsPassenger[i]=new Thread(passengers[i]);
            threadsPassenger[i].start();
        }
        startBus();

        DopBus dopBusAndLog = new DopBus();
        dopBusAndLog.start();
    }

    public static void startBus(){
        busWorking=nBus;
        Thread[] threadsBus = new Thread[nBus];
        for(int i=0;i<threadsBus.length;i++){
            threadsBus[i]=new Thread(buses[i]);
            try {
                Thread.sleep(interval);
                threadsBus[i].start();
            } catch (InterruptedException e) {
                logger.log("Ошибка при запуске потоков (автобус)"+e.toString());
                e.printStackTrace();
            }
        }
    }

    public static synchronized void busWorkingRemove(){
        busWorking--;
    }
    public static synchronized void passengerWorkingRemove(){
        passengerWorking--;
    }

}
