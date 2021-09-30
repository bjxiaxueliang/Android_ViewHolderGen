package com.vh.run;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class VhUI extends JFrame implements ActionListener {


    // button
    JButton jBtn = new JButton();

    public VhUI() {
        // 窗口的标题
        this.setTitle("Android ViewHolder生成工具");
        // 按钮提醒
        StringBuffer sb = new StringBuffer();
        sb.append("1、将*.xml文件添加到input/xml/路径下；");
        sb.append("  ");
        sb.append("2、点击该按钮生成ViewHolder。");
        jBtn.setText(sb.toString());

        // 窗口添加按钮
        this.add(jBtn, BorderLayout.CENTER);
        // 窗口大小
        this.setBounds(0, 0, 600, 400);
        // 显示状态
        this.setVisible(true);
        // 点击关闭按钮 退出
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Btn 大小
        jBtn.setBounds(0, 0, 600, 400);
        // Btn 点击状态
        jBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 点击按钮
        if (e.getSource() == jBtn) {
            // 生成代码
            try {
                VhRunner.run();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        // swing
        new VhUI();
    }
}
