package mode.gui;

import classes.Bus;
import classes.BusStop;
import publicTransportation.PublicTransportation;

import javax.swing.*;
import java.awt.*;

public class GraphicsPanel extends JFrame implements Runnable{

    private JPanel Jpanel;

    private int x = PublicTransportation.route/1000;

    public GraphicsPanel(){
        super("Общественный транспорт");
        setContentPane(Jpanel);
        pack();
        setBounds(150,150,1100,500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void run(){
        while (true){
            try {
                Thread.sleep(300);
                repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(MainForm.exit==1){
                break;
            }
        }
    }

    public void paint(Graphics g){
        super.paint(g);

        if(MainForm.exit==1){
            this.setVisible(false);
        }

        g.setColor(Color.BLUE);
        g.drawString("Количество не перевезенных пассажиров: "+ PublicTransportation.k1,50,50);
        g.setColor(Color.RED);
        g.drawString("Количество автобусов в пути: "+ PublicTransportation.k,50,400);



        g.setColor(Color.BLACK);
        g.drawLine(50,200,1050,200);
        g.drawLine(50,250,1050,250);

        for (BusStop b : PublicTransportation.busStopForward) {
            g.setColor(Color.BLACK);
            int t=180;
            g.drawOval(b.getX()/x+50, 190, 20, 20);
            if(b.getPassengers()>10){
                g.drawString(">10",b.getX() / x + 57, t);
            }else {
                for (int i = 0; i < b.getPassengers(); i++) {
                    g.setColor(Color.BLUE);
                    g.drawRect(b.getX() / x + 57, t, 5, 5);
                    t = t - 10;
                }
            }
        }
        for (BusStop b : PublicTransportation.busStopBack) {
            g.setColor(Color.BLACK);
            int t1 = 270;
            g.drawOval((PublicTransportation.route - b.getX())/x + 50, 240, 20, 20);
            if(b.getPassengers()>10){
                g.drawString(">10",(PublicTransportation.route - b.getX()) / x + 57, t1);
            }else {
                for (int i = 0; i < b.getPassengers(); i++) {
                    g.setColor(Color.BLUE);
                    g.drawRect((PublicTransportation.route - b.getX()) / x + 57, t1, 5, 5);
                    t1 = t1 + 10;
                }
            }
        }
        g.setColor(Color.RED);
        int y=415;
        int xx =50;
        int e = 0;
        for(Bus b: PublicTransportation.buses) {
            e++;
            g.drawString("В автобусу №: " + b.getNomer() + ", Пассажиров: " + b.getPassenger() + "/" + b.getCapacity(), xx, y);
            xx=xx+250;
            if(e%4==0){
                xx=50;
                y = y + 15;
            }
            if (b.getDirection() == 0) {
                g.drawOval((int) b.getX() / x + 50, 190, 10, 10);
            } else if (b.getDirection() == 1) {
                g.drawOval((PublicTransportation.route - (int) b.getX()) / x + 50, 240, 10, 10);
            }
        }

    }
}