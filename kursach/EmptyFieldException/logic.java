package com.proghelp;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import javax.swing.*;
/**
 * @author Половникова Алиса 3312
 * @version 2.0
 */
public class logic 
{
	private static final Logger logger = LogManager.getLogger(logic.class);

	private static JFrame addWindow;
	
    private static void saving(JTextField time, JTextField nameD, JTextField specialization) throws EmptyFieldException 
    {

    try
    {
        if (time.getText().isEmpty())
            throw new EmptyFieldException("Поле 'Время' не может быть пустым.");
        if (nameD.getText().isEmpty())
            throw new EmptyFieldException("Поле 'Имя врача' не может быть пустым.");
        if (specialization.getText().isEmpty())
            throw new EmptyFieldException("Поле 'Специализация' не может быть пустым.");

        Doctor doctor = new Doctor(nameD.getText(), specialization.getText(), time.getText(), new ArrayList<>());

        Main.model.addRow(doctor);
        Main.quant++;

        logger.info("Добавлена запись: Врач: " + nameD.getText());
        JOptionPane.showMessageDialog(null, "Запись успешно добавлена!", "Успех", JOptionPane.INFORMATION_MESSAGE);
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
    finally 
    {
        if (addWindow != null)
            addWindow.dispose();
    }
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

        JLabel lable2 = new JLabel("Время");
        lable2.setForeground(Color.WHITE);
        JLabel lable3 = new JLabel("Имя врача");
        lable3.setForeground(Color.WHITE);
        JLabel lable4 = new JLabel("специализация");
        lable4.setForeground(Color.WHITE);
        
        JTextField specialization = new JTextField("");
        specialization.setPreferredSize(new Dimension(300, 25));
        specialization.setBackground(new java.awt.Color(169, 169, 169));
        specialization.setForeground(new java.awt.Color(69, 69, 69));
        specialization.setBorder(BorderFactory.createLineBorder(new java.awt.Color(69, 69, 69), 2));
        
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
            try 
            {
                logic.saving(time, nameD, specialization);
            } 
            catch (EmptyFieldException e) 
            {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        addWindow.setLayout(new GridLayout(5, 2));
        addWindow.add(lable3);
        addWindow.add(nameD);
        addWindow.add(lable2);
        addWindow.add(time);
        addWindow.add(lable4);
        addWindow.add(specialization);
        addWindow.add(save);

        
        
		addWindow.setVisible(true);
	}

	
	public static void b2Click()
    {
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

    public static void dataPatientClick(JTable table)
    {
        DoctorTableModel model = (DoctorTableModel) table.getModel();
        List<MedicalRecord> allPatients = model.getAllPatients();

        Map<String, Integer> diseaseReport = new HashMap<>();
        for (MedicalRecord record : allPatients) 
        {
            String diagnosis = record.getDiagnosis();
            if (diagnosis != null && !diagnosis.isEmpty())
                diseaseReport.put(diagnosis, diseaseReport.getOrDefault(diagnosis, 0) + 1);
        }

        StringBuilder reportText = new StringBuilder("Общий отчет по заболеваниям:\n");
        for (Map.Entry<String, Integer> entry : diseaseReport.entrySet())
            reportText.append(entry.getKey()).append(" - ").append(entry.getValue()).append("\n");

        JOptionPane.showMessageDialog(null, reportText.toString(), "Общий отчет", JOptionPane.INFORMATION_MESSAGE);
    }
}
