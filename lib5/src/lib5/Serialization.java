package lib5;

import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Serialization {
    
    public static void SaveButtonClick(JTable table)
    {
        FileDialog save = new FileDialog(Main.frame, "Сохранение данных", FileDialog.SAVE);
        save.setFile("*.txt");
        save.setVisible(true);

        String fileName = save.getDirectory() + save.getFile();
        if(fileName == null) 
            return;

        try (FileWriter writer = new FileWriter(fileName)) 
        {
            for (int col = 0; col < table.getColumnCount(); col++) 
                writer.write(table.getColumnName(col) + (col < table.getColumnCount() - 1 ? ", " : ""));

            writer.write("\n");

            for (int row = 0; row < table.getRowCount(); row++) 
            {
                for (int col = 0; col < table.getColumnCount(); col++)
                    writer.write(table.getValueAt(row, col).toString() + (col < table.getColumnCount() - 1 ? ", " : ""));

                writer.write("\n");
            }
        } 
        catch (IOException e) 
        {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
        
    }

    public static void LoadButtonClick(JTable table, DefaultTableModel model)
    {
        FileDialog load = new FileDialog(Main.frame, "Загрузка данных", FileDialog.LOAD);
        load.setFile("*.txt");
        load.setVisible(true);

        String fileName = load.getDirectory() + load.getFile();
        if (fileName == null) 
            return;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) 
        {
            String headerLine = reader.readLine();
            if (headerLine == null) 
            {
                JOptionPane.showMessageDialog(null, "Файл пустой или не содержит данных.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }
            model.setRowCount(0);

            String line;
            while ((line = reader.readLine()) != null) 
            {
                String[] rowData = line.split(",\\s*");
                model.addRow(rowData);
            }

            model.fireTableDataChanged();
        } 
        catch (IOException e) 
        {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}