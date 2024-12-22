package com.proghelp;
import java.awt.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.swing.*;
/**
 * @author Половникова Алиса 3312
 * @version 2.0
 */
public class logic 
{
	private static final Logger logger = LogManager.getLogger(logic.class);

	private static JFrame addWindow;
	
    private static void saving(JTextField nameP, JTextField time, JTextField nameD) throws EmptyFieldException
    {
        try
        {
            if (nameP.getText().isEmpty())
                throw new EmptyFieldException("Поле 'ФИО пациента' не может быть пустым.");
            if (time.getText().isEmpty())
                throw new EmptyFieldException("Поле 'Время' не может быть пустым.");
            if (nameD.getText().isEmpty())
                throw new EmptyFieldException("Поле 'Врач' не может быть пустым.");

            Main.model.addRow(new Object[] {nameP.getText(), time.getText(), nameD.getText()});
            Main.quant++;

            logger.info("Добавлена запись: " + nameP.getText() + ", " + time.getText() + ", " + nameD.getText());
        }
        catch (EmptyFieldException e)
        {
            logger.error("Ошибка при сохранении данных: " + e.getMessage(), e);
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception e)
        {
            logger.error("Неизвестная ошибка: " + e.getMessage(), e);
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
        
        addWindow.dispose();
    }

    public static void reportGenerateClick()
    {
        ReportGenerator.generateReport(Main.table);
        
    }

	public static void b1Click()
	{

        addWindow = new JFrame("Добавление");
        addWindow.setResizable(false);
		addWindow.setSize(400, 200);
		addWindow.setLocation(600, 100);
		addWindow.getContentPane().setBackground(new java.awt.Color(90, 90, 90));
        
        logger.info("Окно добавления данных открыто.");


        JLabel lable1 = new JLabel("Имя пациента");
        lable1.setForeground(Color.WHITE);
        JLabel lable2 = new JLabel("Время");
        lable2.setForeground(Color.WHITE);
        JLabel lable3 = new JLabel("Имя врача");
        lable3.setForeground(Color.WHITE);
        JTextField nameP = new JTextField("");
        nameP.setPreferredSize(new Dimension(300, 25));
        nameP.setBackground(new java.awt.Color(169, 169, 169));
        nameP.setForeground(new java.awt.Color(69, 69, 69));
        nameP.setBorder(BorderFactory.createLineBorder(new java.awt.Color(69, 69, 69), 2));
        
        JTextField time = new JTextField("");
        time.setPreferredSize(new Dimension(300, 25));
        time.setBackground(new java.awt.Color(169, 169, 169));
        time.setForeground(new java.awt.Color(69, 69, 69));
        time.setBorder(BorderFactory.createLineBorder(new java.awt.Color(69, 69, 69), 2));
        
        JTextField nameD = new JTextField("");
        nameD.setPreferredSize(new Dimension(300, 25));
        nameD.setBackground(new java.awt.Color(169, 169, 169));
        nameD.setForeground(new java.awt.Color(69, 69, 69));
        nameD.setBorder(BorderFactory.createLineBorder(new java.awt.Color(69, 69, 69), 2));

        JButton save = new JButton("Сохранить");
        save.setPreferredSize(new Dimension(100, 30));
        save.setBackground(new java.awt.Color(169, 169, 169));
        save.setForeground(new java.awt.Color(69, 69, 69));
        save.setBorder(BorderFactory.createLineBorder(new java.awt.Color(69, 69, 69), 2));
        save.addActionListener(ae -> {
            try {
                logic.saving(nameP, time, nameD);
            } catch (EmptyFieldException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        addWindow.setLayout(new GridLayout(4, 2));
        addWindow.add(lable1);
        addWindow.add(nameP);
        addWindow.add(lable2);
        addWindow.add(time);
        addWindow.add(lable3);
        addWindow.add(nameD);
        addWindow.add(save);

        
        
		addWindow.setVisible(true);
        //GridBagConstraints mainGrid = new GridBagConstraints();
	}

	
	public static void b2Click(){
		try 
        {
            int selectedRow = Main.table.getSelectedRow();
            if (selectedRow != -1) 
            {
                Main.model.removeRow(selectedRow);
                JOptionPane.showMessageDialog(Main.frame, "Удаление прошло успешно");
                logger.info("Удалена запись: " + selectedRow);
            } 
            else
                throw new Exception("Не выбрана строка для удаления");
        } 
        catch (Exception e) 
        {
            logger.error("Ошибка при удалении: " + e.getMessage(), e);
            JOptionPane.showMessageDialog(Main.frame, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
	}
}
