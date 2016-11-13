package mode.gui;

import classes.logger;
import publicTransportation.PublicTransportation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class InitForm extends JFrame{
    public static PublicTransportation publicTransportation;
    private JTextField nPassengers;
    private JTextField capacityBus;
    private JTextField nBus;
    private JTextField nbusStop;
    private JTextField interval;
    private JTextField speed;
    private JButton startButton;
    private JPanel Jp;
    private JButton propertiesButton;


    InitForm(){
        super("Общественный транспорт");

        JOptionPane.showMessageDialog(new JFrame(), " Для корректного отображения GUI и работы приложения начальные параметры должны варьироваться...\n Число пассажирова влияет только на нагрузку приложения\n Количество остоновок не более 30 и не менее 2 (влияет только на GUI)\n Количество автобусов не более 10 (влияет на нагрузки и GUI)\n Интервал запуска автобусов не менее 3000\n Скорость передвижения автобусов от 0,1 до 1\n Рекомендуемые параметры хранятся в properties файле", "Информация", JOptionPane.INFORMATION_MESSAGE);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            if(Integer.valueOf(nBus.getText())>10){
                                JOptionPane.showMessageDialog(new JFrame(), "Не корректно отобразиться на GUI", "Информация", JOptionPane.INFORMATION_MESSAGE);
                            }
                            if(Integer.valueOf(nbusStop.getText())>30){
                                JOptionPane.showMessageDialog(new JFrame(), "Не корректно отобразиться на GUI", "Информация", JOptionPane.INFORMATION_MESSAGE);
                            }
                            if(Integer.valueOf(nbusStop.getText())<2){
                                JOptionPane.showMessageDialog(new JFrame(), "Не заработает приложения не менее 2 остановок", "Информация", JOptionPane.INFORMATION_MESSAGE);
                                throw new Exception();
                            }
                            if(Integer.valueOf(interval.getText())<3000){
                                JOptionPane.showMessageDialog(new JFrame(), "Маленький интервал запуска автобусов (могут возникнуть проблемы) и не корректно отобразиться на GUI", "Информация", JOptionPane.INFORMATION_MESSAGE);
                            }
                            if( Double.valueOf(speed.getText())<0.1){
                                JOptionPane.showMessageDialog(new JFrame(), "Слишком маленькая скорость передвижения автобусов", "Информация", JOptionPane.INFORMATION_MESSAGE);
                            }
                            if( Double.valueOf(speed.getText())>1){
                                JOptionPane.showMessageDialog(new JFrame(), "Слишком большая скорость передвижения автобусов", "Информация", JOptionPane.INFORMATION_MESSAGE);
                            }

                            MainForm mainForm = new MainForm(publicTransportation);
                            mainForm.setVisible(true);
                            setVisible(false);
                        }catch (Exception e){
                            logger.log("Не корректные исходные данные!");
                            JOptionPane.showMessageDialog(new JFrame(), "Не корректные исходные данные!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        propertiesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    publicTransportation = new PublicTransportation(1);
                    publicTransportation.writeProperties();
                    properties();
                } catch (IOException e1) {
                    logger.log("Не удалось найти файл!");
                    JOptionPane.showMessageDialog(new JFrame(), "Не удалось найти файл!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                }
            }
        });


        setContentPane(Jp);
        pack();
        setBounds(50,50,600,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void properties() throws IOException {
        nBus.setText(String.valueOf(publicTransportation.nBus));
        nPassengers.setText(String.valueOf(publicTransportation.nPassengers));
        nbusStop.setText(String.valueOf(publicTransportation.nbusStop));
        capacityBus.setText(String.valueOf(publicTransportation.capacityBus));
        interval.setText(String.valueOf(publicTransportation.interval));
        speed.setText(String.valueOf(publicTransportation.speed));
    }
}