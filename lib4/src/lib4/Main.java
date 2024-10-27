package lib4;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;

/**
 * @author Половникова Алиса 3312
 * @version 3.0
 */


public class Main {
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
		// Создание окна
        frame = new JFrame("Регистратура поликлиники");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 220);
        frame.setLocation(500, 200);
        frame.getContentPane().setBackground(new java.awt.Color(90, 90, 90));
       
        // Создание кнопки сохранения
        JButton save = new JButton(new ImageIcon("./picture/download_white_button_icon_227821.png"));
        save.setPreferredSize(new Dimension(40,40));
        save.setBackground(new java.awt.Color(90, 90, 90));
        save.setBorder(BorderFactory.createLineBorder(new java.awt.Color(90, 90, 90), 7));
        
        // Создание возможности сортировки
        JComboBox searching = new JComboBox(new String[]{"Сортировка", "По имени", "По времени", "По лечащему врачу"});
        searching.setBackground(new java.awt.Color(69, 69, 69));
        searching.setForeground(new java.awt.Color(169, 169, 169));
        searching.setBorder(BorderFactory.createLineBorder(new java.awt.Color(169, 169, 169), 2));
        
        // Создание кнопок редактирования
        JButton b1 = new JButton("Добавить");
        b1.setForeground(new java.awt.Color(69, 69, 69));
        b1.setBorder(BorderFactory.createLineBorder(new java.awt.Color(69, 69, 69), 2));
        b1.setBackground(new java.awt.Color(169, 169, 169));
        b1.addActionListener(ae -> logic.b1Click());
    
        JButton b2 = new JButton("Удалить");
        b2.setBackground(new java.awt.Color(169, 169, 169));
        b2.setForeground(new java.awt.Color(69, 69, 69));
        b2.setBorder(BorderFactory.createLineBorder(new java.awt.Color(69, 69, 69), 2));
        b2.addActionListener(ae -> logic.b2Click());
    
        JButton b3 = new JButton("Редактировать");
        b3.setBackground(new java.awt.Color(169, 169, 169));
        b3.setForeground(new java.awt.Color(69, 69, 69));
        b3.setBorder(BorderFactory.createLineBorder(new java.awt.Color(69, 69, 69), 2));
    
        // Создание поиска
        JTextField b4 = new JTextField("Введите запрос...");
        b4.setBackground(new java.awt.Color(169, 169, 169));
        b4.setForeground(new java.awt.Color(69, 69, 69));
        b4.setBorder(BorderFactory.createLineBorder(new java.awt.Color(69, 69, 69), 2));
    
        JButton b5 = new JButton("Поиск");
        b5.setBackground(new java.awt.Color(169, 169, 169));
        b5.setForeground(new java.awt.Color(69, 69, 69));
        b5.setBorder(BorderFactory.createLineBorder(new java.awt.Color(69, 69, 69), 2));
    
        data = new String[][]{
            {"Куликов Ф.Р", "15:00", "Иванов А.У"},
            {"Барченков А.У", "14:00", "Муров К.Л"}, 
            {"Берестов Ф.Н", "12:00", "Лимонов О.Р"}
        };
        model = new DefaultTableModel(data, columns);
        table = new JTable(model);
        table.setBackground(new java.awt.Color(169, 169, 169));
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(new java.awt.Color(169, 169, 169));
        table.setGridColor(new java.awt.Color(120, 120, 120));
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(1, 1));
        
        scroll = new JScrollPane(table);
        scroll.setViewportBorder(BorderFactory.createLineBorder(Color.black));
        scroll.setBorder(BorderFactory.createCompoundBorder(scroll.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        scroll.setWheelScrollingEnabled(true);
        scroll.getViewport().setBackground(new java.awt.Color(90, 90, 90));
        scroll.setBorder(BorderFactory.createLineBorder(new java.awt.Color(169, 169, 169), 2));

        // Создание грида
        frame.setLayout(new GridBagLayout());
        GridBagConstraints mainGrid = new GridBagConstraints();
        mainGrid.insets = new Insets(5, 5, 5, 5);
    
        // Кнопка сохранения
        mainGrid.gridx = 0;
        mainGrid.gridy = 0;
        mainGrid.gridwidth = 1;
        mainGrid.anchor = GridBagConstraints.WEST;
        frame.add(save, mainGrid);
    
        // Комбо-бокс сортировки
        mainGrid.gridx = 1;
        mainGrid.fill = GridBagConstraints.HORIZONTAL;
        mainGrid.weightx = 1.0;
        frame.add(searching, mainGrid);
    
        // Кнопки редактирования
        mainGrid.gridx = 0;
        mainGrid.gridy = 1;
        mainGrid.weightx = 0;
        mainGrid.fill = GridBagConstraints.NONE;
        frame.add(b1, mainGrid);
    
        mainGrid.gridy = 2;
        frame.add(b2, mainGrid);
    
        mainGrid.gridy = 3;
        frame.add(b3, mainGrid);
    
        // Поле для поиска
        mainGrid.gridx = 1;
        mainGrid.gridy = 4;
        mainGrid.gridwidth = 1;
        mainGrid.weightx = 1.0;
        mainGrid.fill = GridBagConstraints.HORIZONTAL;
        frame.add(b4, mainGrid);
    
        // Кнопка поиска
        mainGrid.gridx = 0;
        mainGrid.weightx = 0;
        mainGrid.fill = GridBagConstraints.NONE;
        frame.add(b5, mainGrid);
    
        // Таблица с прокруткой
        mainGrid.gridx = 1;
        mainGrid.gridy = 1;
        mainGrid.gridwidth = 1;
        mainGrid.gridheight = 3;
        mainGrid.weighty = 1.0;
        mainGrid.weightx = 1.0;
        mainGrid.fill = GridBagConstraints.BOTH;
        scroll.setPreferredSize(new Dimension(320, 130));
        frame.add(scroll, mainGrid);
    
        frame.setVisible(true);
    
	}
	
	public static void main(String[] args) 
    {
		new Main().show();

	}
}