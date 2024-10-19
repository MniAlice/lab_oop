package lib3;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;

/**
 * @author Половникова Алиса 3312
 * @version 2.0
 */


public class main {
	/**
 * @param args
 * 
 * private JFrame frame -окно
 *	private DefaultTableModel model - модель таблицы
 *	private JScrollPane scroll - итоговая таблица с возможностью прокрутки
 *	private JTable table - таблица
 */
	public static JFrame frame;
	public static DefaultTableModel model;
	private JScrollPane scroll;
	public static JTable table;
	public static Object[][] data;
    public static Object[] columns = new String[] {"ФИО пациента", "Время", "Врач"};
    public static int quant = 3;
	
	public void show() {
		//Создание окна
		frame = new JFrame("Регистратура поликлиники");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(525, 300);
        frame.setLocation(500, 200);
        frame.getContentPane().setBackground(new java.awt.Color(90, 90, 90));
       
        //Создание кнопки сохранения
        JButton save = new JButton(new ImageIcon("./picture/download_white_button_icon_227821.png"));
        save.setPreferredSize(new Dimension(40,40));
        save.setBackground(new java.awt.Color(90, 90, 90));
        save.setBorder(BorderFactory.createLineBorder(new java.awt.Color(90, 90, 90), 7));
        
        //Создание возможности сортировки
        JComboBox searching = new JComboBox(new String[]{"Сортировка", "По имени", "По времени", "По лечащему врачу"});
        searching.setBackground(new java.awt.Color(69, 69, 69));
        searching.setForeground(new java.awt.Color(169, 169, 169));
        searching.setBorder(BorderFactory.createLineBorder(new java.awt.Color(169, 169, 169), 2));
        
        //Создание кнопок редактирования
        JButton b1 = new JButton("Добавить");
        b1.setPreferredSize(new Dimension(100, 30));
        b1.setForeground(new java.awt.Color(69, 69, 69));
        b1.setBorder(BorderFactory.createLineBorder(new java.awt.Color(69, 69, 69), 2));
        b1.setBackground(new java.awt.Color(169, 169, 169));
        b1.addActionListener(ae -> logic.b1Click());
      
        JButton b2 = new JButton("Удалить");
        b2.setPreferredSize(new Dimension(100, 30));
        b2.setBackground(new java.awt.Color(169, 169, 169));
        b2.setForeground(new java.awt.Color(69, 69, 69));
        b2.setBorder(BorderFactory.createLineBorder(new java.awt.Color(69, 69, 69), 2));
        b2.addActionListener(ae -> logic.b2Click());
        
        JButton b3 = new JButton("Редактировать");
        b3.setPreferredSize(new Dimension(100, 30));
        b3.setBackground(new java.awt.Color(169, 169, 169));
        b3.setForeground(new java.awt.Color(69, 69, 69));
        b3.setBorder(BorderFactory.createLineBorder(new java.awt.Color(69, 69, 69), 2));
        
        //Создание поиска
        JTextField b4 = new JTextField("Введите запрос...");
        b4.setPreferredSize(new Dimension(300, 25));
        b4.setBackground(new java.awt.Color(169, 169, 169));
        b4.setForeground(new java.awt.Color(69, 69, 69));
        b4.setBorder(BorderFactory.createLineBorder(new java.awt.Color(69, 69, 69), 2));
        
        JButton b5 = new JButton("Поиск");
        b5.setPreferredSize(new Dimension(45, 25));
        b5.setBackground(new java.awt.Color(169, 169, 169));
        b5.setForeground(new java.awt.Color(69, 69, 69));
        b5.setBorder(BorderFactory.createLineBorder(new java.awt.Color(69, 69, 69), 2));
        
        

        data = new String[][]{{"Куликов Ф.Р", "15:00", "Иванов А.У"},
            							{"Барченков А.У", "14:00", "Муров К.Л"}, {"Берестов Ф.Н", "12:00", "Лимонов О.Р"}};
        model= new DefaultTableModel(data, columns); //Создание модели
        table = new JTable(model); //Загрузка модели в таблицу
        table.setBackground(new java.awt.Color(169, 169, 169));
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(new java.awt.Color(169, 169, 169));
       
        //Создание итоговой таблицей с возможностью прокрутки
        scroll = new JScrollPane(table);
        scroll.setBackground(new java.awt.Color(69, 69, 69));
        scroll.setViewportBorder(BorderFactory.createLineBorder(Color.black));
        scroll.setBorder(BorderFactory.createCompoundBorder(scroll.getBorder(), BorderFactory.createEmptyBorder(5, 5,5, 5)));
        scroll.setWheelScrollingEnabled(true);
        table.setPreferredSize(new Dimension(400, 100));
        
        //Создание грида
        frame.setLayout(new GridBagLayout());
        GridBagConstraints mainGrid = new GridBagConstraints();

        //Расположение кнопок
        mainGrid.gridwidth = 1;
        mainGrid.gridheight = 1;   
        mainGrid.insets = new Insets(2, 15, 5, 5);
        mainGrid.gridx = 0;
        mainGrid.gridy = 0;
        mainGrid.anchor = GridBagConstraints.WEST;
        frame.add(save, mainGrid);
        
        mainGrid.anchor = GridBagConstraints.NORTH;
        mainGrid.ipadx = 200;
        mainGrid.gridx = 1;
        mainGrid.gridy = 0; 
        frame.add(searching, mainGrid);
       
        mainGrid.ipadx = 1;
        mainGrid.gridx = 0;
        mainGrid.gridy = 1;
        frame.add(b1, mainGrid);
        
        mainGrid.gridy = 2;
        frame.add(b2, mainGrid);
        
        mainGrid.gridy = 3;
        frame.add(b3, mainGrid);
        
        mainGrid.gridy = 4;
        mainGrid.anchor = GridBagConstraints.EAST;
        frame.add(b5, mainGrid); 
        
        mainGrid.anchor = GridBagConstraints.WEST;
        mainGrid.gridx = 1;
        frame.add(b4, mainGrid);
        
        mainGrid.gridy = 1;
        mainGrid.gridheight = 3;   
        mainGrid.fill = GridBagConstraints.NORTHWEST;
        scroll.setPreferredSize(new Dimension(320,130));
        frame.add(scroll, mainGrid);
            
        frame.setVisible(true);
	}
	
	public static void main(String[] args) 
    {
		new main().show();

	}
}