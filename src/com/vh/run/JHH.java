package com.vh.run;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class JHH extends JFrame implements ActionListener {

    JButton chengButton = new JButton(
            "1、将*.xml文件添加到xml目中  " +
                    "\n" +
                    "2、点击该按钮生成ViewHolder");

    public JHH() {
        this.setTitle("http://blog.csdn.net/xiaxl");
        this.add(chengButton, BorderLayout.CENTER);
        this.setBounds(0, 0, 600, 400);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chengButton.setBounds(0, 0, 600, 400);
        chengButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == chengButton) {
            try {
                VHRun.run();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        // swing
        new JHH();
    }
}
