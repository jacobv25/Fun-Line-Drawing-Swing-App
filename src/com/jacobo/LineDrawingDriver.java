package com.jacobo;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LineDrawingDriver implements Runnable{
    //Animation thread
    protected static Thread anim_thread;
    //Interval between frames in millisec
    protected int delay = 100;
    //Animation State
    protected static boolean playing = false;
    //Color State
    public static boolean isValentinesDay = false;
    //Pulse State
    public static boolean isPulsing = false;
    //Lining Panel
    protected static LiningPanel panel;

    public static void main(String[] args) {
        //Frame
        JFrame frame = new JFrame("Line Drawing Assignment");
        //Buttons
        JButton playButton = new JButton("Draw");
        JButton valentinesButton = new JButton("Is it Valentines Day?");
        JButton pulseButton = new JButton("Pulse");
        JButton clearButton = new JButton("Clear");
        //Spinner
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(20, 5, 500, 5));
        JComponent comp = spinner.getEditor();
        JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
        DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
        formatter.setCommitsOnValidEdit(true);
        spinner.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e)
            {
                LiningPanel.lines = (int)spinner.getValue();
            }
        });


        //Panel for buttons & spinner
        JPanel UIPanel = new JPanel(new FlowLayout());

        //Set Action Listener
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                playing = !playing;

                if(playing)
                {
                    playButton.setText("Stop");
                }
                else
                {
                    playButton.setText("Resume");
                }
            }
        });
        //Set Action Listener
        valentinesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                isValentinesDay = !isValentinesDay;

                if(isValentinesDay)
                {
                    valentinesButton.setText("Regular Colors");
                }
                else
                {
                    valentinesButton.setText("Valentines Day Colors");
                }
            }
        });
        //Set Action Listener
        pulseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                isPulsing = !isPulsing;

                if(isPulsing)
                {
                    pulseButton.setText("Stop Pulsing");
                }
                else
                {
                    pulseButton.setText("Resume Pulsing");
                }
            }
        });
        //Set Action Listener
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.offScreen.clearRect(0,0, panel.getWidth(), panel.getHeight());
            }
        });
        //Set Frame Layout
        frame.setLayout(new BorderLayout());
        //Add button panel
        frame.add(UIPanel, BorderLayout.NORTH);
        //Add buttons
        UIPanel.add(playButton);
        UIPanel.add(valentinesButton);
        UIPanel.add(pulseButton);
        //Add spinner
        UIPanel.add(spinner);
        //Add clear button
        UIPanel.add(clearButton);

        panel = new LiningPanel(800  , 800);

        frame.setBackground(Color.BLACK);

        frame.getContentPane().add(panel);

        frame.setSize(panel.width, panel.height);

        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }
        });

        anim_thread = new Thread(new LineDrawingDriver());
        anim_thread.start();
    }



    @Override
    public void run()
    {
        while (true)
        {
            //If I delete this line, the program fails. I have no idea why :( DO NOT DELETE!
            System.out.println("playing = " + playing);

            //Reset so the drawing can be repainted
            if(LiningPanel.iterations >= LiningPanel.lines)
            {
                LiningPanel.iterations = 0;
            }

            //If start/resume button is pressed, then paint.
            //Else do nothing and pause the painting.
            if(playing)
            {
                panel.repaint();

                try
                {
                    Thread.sleep(delay);
                }
                catch (InterruptedException e)
                {
                    System.out.println("Exception: " + e.getMessage());
                }
            }
        }
    }

}

