package mode.gui;

import classes.*;
import com.sun.org.apache.xml.internal.security.Init;
import publicTransportation.PublicTransportation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class MainForm extends JFrame {

    private JPanel Jp;
    private JButton ButStart;
    public JTextArea textArea1;
    public static int flag = 0;
    public static int exit = 0;
    public static MainForm mainForm;

    public MainForm(PublicTransportation publicTransportation){
        super("Общественный транспорт");
        mainForm=this;
        publicTransportation.init();

        ButStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(flag==0) {
                    Thread thread = new Thread(new GraphicsPanel());
                    thread.start();
                    ButStart.setText("Старт");
                    ButStart.setBackground(Color.GREEN);
                    flag++;
                }else if(flag==1){
                    start();
                    ButStart.setText("Стоп");
                    ButStart.setBackground(Color.RED);
                    flag++;
                }
                else if(flag==2){
                    logger.log("Приложение остановлено!");
                    exit=1;
                    flag++;
                    ButStart.setText("Выход");
                    ButStart.setBackground(Color.BLUE);
                }
                else if(flag==3){
                    logger.log("Приложение завершило работу!");
                    System.exit(0);
                }
            }
        });

        setContentPane(Jp);
        pack();
        setBounds(20,20,600,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    private void start() {
        InitForm.publicTransportation.start();
    }

    public static void setTextArea1(String s){
        mainForm.textArea1.setText(s);
    }

}