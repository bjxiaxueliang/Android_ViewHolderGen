package com.vh.run;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class VhUI extends JFrame implements ActionListener {


    // 操作步骤描述
    JTextArea jTextArea = new JTextArea();
    // ViewHolder生成按钮
    JButton jButton = new JButton();


    public VhUI() {


        // 窗口的标题
        this.setTitle("Android ViewHolder生成工具");
        // 窗口大小
        this.setBounds(0, 0, 600, 500);
        // 显示状态
        this.setVisible(true);
        // 点击关闭按钮 退出
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 窗口添加按钮
        this.add(jTextArea, BorderLayout.CENTER);
        this.add(jButton, BorderLayout.PAGE_END);

        // 使用提示：
        StringBuffer sb = new StringBuffer();
        sb.append("\n");
        sb.append("\n");
        sb.append("\t说明：");
        sb.append("\n");
        sb.append("\t1、将*.xml文件添加到input/xml/路径下；");
        sb.append("\n");
        sb.append("\t2、点击JButton按钮生成ViewHolder。");
        sb.append("\n");
        sb.append("\n");
        sb.append("\t注意：");
        sb.append("\n");
        sb.append("\t若模板不满足您的需求，");
        sb.append("\n");
        sb.append("\t可对 input/tm/vh_template.vm 模板文件进行微调。");
        jTextArea.setLineWrap(true);
        jTextArea.setText(sb.toString());
        jTextArea.setBounds(0, 0, 600, 300);

        // button 按钮：
        jButton.setText("点击生成ViewHolder");
        jButton.setBounds(0, 300, 600, 500);
        // Btn 点击状态
        jButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 点击按钮
        if (e.getSource() == jButton) {
            // 生成代码
            try {
                // 执行
                VhRunner.run();
                // 执行完毕
                StringBuffer sb = new StringBuffer();
                sb.append("\n");
                sb.append("\n");
                sb.append("\tViewHolder 代码生成完毕 !!!");
                jTextArea.setText(sb.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
                // 展示错误日志
                StringBuffer sb = new StringBuffer();
                sb.append("\n");
                sb.append(ex.getMessage());
                jTextArea.setText(sb.toString());
            }
        }
    }

    public static void main(String args[]) {
        // swing
        new VhUI();
    }
}
