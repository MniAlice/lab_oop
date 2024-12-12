package lib9;

import java.awt.*;

import javax.swing.*;
/**
 * @author Половникова Алиса 3312
 * @version 2.0
 */
public class logic 
{
	
	public static JFrame addWindow;
	
    public static void saving(JTextField nameP, JTextField time, JTextField nameD)
    {
        Main.quant = Main.quant + 1;
        Object[][] newData = new String[Main.quant][3];
        for(int i = 0; i < Main.quant; i++){
            for(int j = 0; j < 3; j++){
                if (i == Main.quant - 1){
                    newData[i][0] = nameP.getText();
                    newData[i][1] = time.getText();
                    newData[i][2] = nameD.getText();
                    break;
                }
                else 
                    newData[i][j] = Main.data[i][j];
            }
        }
        

        Main.data = newData;
        Main.model.fireTableDataChanged();
        Main.model.addRow(new Object[]  {nameP.getText(), time.getText(), nameD.getText()});
        
        addWindow.dispose();
    }

	public static void b1Click()
	{
        addWindow = new JFrame("Добавление");
        addWindow.setResizable(false);
		addWindow.setSize(400, 200);
		addWindow.setLocation(600, 100);
		addWindow.getContentPane().setBackground(new java.awt.Color(90, 90, 90));
        
        
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
        save.addActionListener(ae -> logic.saving(nameP, time, nameD));
        
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
		int selectedRow = Main.table.getSelectedRow();
        Main.model.removeRow(selectedRow);
    }
}
