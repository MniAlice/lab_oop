package com.proghelp;

import java.awt.FileDialog;
import java.io.File;
import org.w3c.dom.*;

import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Serialization {

    private static final Logger logger = LogManager.getLogger(Serialization.class);
    
    // public static void SaveButtonClick(JTable table)
    // {
    //     FileDialog save = new FileDialog(Main.frame, "Сохранение данных", FileDialog.SAVE);
    //     save.setFile("*.txt");
    //     save.setVisible(true);

    //     String fileName = save.getDirectory() + save.getFile();
    //     if(fileName == null) 
    //         return;

    //     try (FileWriter writer = new FileWriter(fileName)) 
    //     {
    //         for (int col = 0; col < table.getColumnCount(); col++) 
    //             writer.write(table.getColumnName(col) + (col < table.getColumnCount() - 1 ? ", " : ""));

    //         writer.write("\n");

    //         for (int row = 0; row < table.getRowCount(); row++) 
    //         {
    //             for (int col = 0; col < table.getColumnCount(); col++)
    //                 writer.write(table.getValueAt(row, col).toString() + (col < table.getColumnCount() - 1 ? ", " : ""));

    //             writer.write("\n");
    //         }
    //     } 
    //     catch (IOException e) 
    //     {
    //         JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
    //     }
        
    // }

    // public static void LoadButtonClick(JTable table, DefaultTableModel model)
    // {
    //     FileDialog load = new FileDialog(Main.frame, "Загрузка данных", FileDialog.LOAD);
    //     load.setFile("*.txt");
    //     load.setVisible(true);

    //     String fileName = load.getDirectory() + load.getFile();
    //     if (fileName == null) 
    //         return;

    //     try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) 
    //     {
    //         String headerLine = reader.readLine();
    //         if (headerLine == null) 
    //         {
    //             JOptionPane.showMessageDialog(null, "Файл пустой или не содержит данных.", "Ошибка", JOptionPane.ERROR_MESSAGE);
    //             return;
    //         }
    //         model.setRowCount(0);

    //         String line;
    //         while ((line = reader.readLine()) != null) 
    //         {
    //             String[] rowData = line.split(",\\s*");
    //             model.addRow(rowData);
    //         }

    //         model.fireTableDataChanged();
    //     } 
    //     catch (IOException e) 
    //     {
    //         JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
    //     }
    // }

    public static void SaveButtonClick(JTable table) 
    {
        FileDialog save = new FileDialog(Main.frame, "Сохранение данных", FileDialog.SAVE);
        save.setFile("*.xml");
        save.setVisible(true);

        String fileName = save.getDirectory() + save.getFile();
        if(fileName == null) 
        {
            logger.warn("Пользователь отменил сохранение файла.");
            return;
        }

        try 
        {
            logger.info("Начало сохранения данных в файл: " + fileName);
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element rootElement = doc.createElement("Appointments");
            doc.appendChild(rootElement);

            for (int i = 0; i < model.getRowCount(); i++) 
            {
                Element appointment = doc.createElement("Appointment");
                rootElement.appendChild(appointment);

                for (int j = 0; j < model.getColumnCount(); j++) 
                {
                    Element column = doc.createElement(model.getColumnName(j));
                    column.appendChild(doc.createTextNode(model.getValueAt(i, j).toString()));
                    appointment.appendChild(column);
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName));
            transformer.transform(source, result);

            logger.info("Данные успешно сохранены в файл: " + fileName);
            JOptionPane.showMessageDialog(null, "Данные успешно сохранены в файл appointments.xml.");
        } 
        catch (Exception e) 
        {
            logger.error("Ошибка при сохранении данных: " + e.getMessage(), e);
            JOptionPane.showMessageDialog(null, "Ошибка при сохранении данных: " + e.getMessage());
        }
    }

    public static void LoadButtonClick(JTable table, DefaultTableModel model) {

        FileDialog load = new FileDialog(Main.frame, "Загрузка данных", FileDialog.LOAD);
        load.setFile("*.xml");
        load.setVisible(true);

        String fileName = load.getDirectory() + load.getFile();
        if (fileName == null) 
        {
            logger.warn("Пользователь отменил загрузку файла.");
            return;
        }

        try 
        {
            logger.info("Начало загрузки данных из файла: " + fileName);

            File file = new File(fileName);
            if (!file.exists()) 
            {
                logger.error("Файл не найден: " + fileName);
                JOptionPane.showMessageDialog(null, "Файл не найден.");
                return;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("Appointment");

            model.setRowCount(0);

            for (int i = 0; i < nodeList.getLength(); i++) 
            {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) 
                {
                    Element element = (Element) node;

                    Object[] rowData = new Object[model.getColumnCount()];
                    for (int j = 0; j < model.getColumnCount(); j++) 
                    {
                        String columnName = model.getColumnName(j);
                        Node columnNode = element.getElementsByTagName(columnName).item(0);
                        rowData[j] = (columnNode != null) ? columnNode.getTextContent() : "";
                    }

                    model.addRow(rowData);
                }
            }

            logger.info("Данные успешно загружены из файла: " + fileName);
            JOptionPane.showMessageDialog(null, "Данные успешно загружены из файла appointments.xml.");
        } 
        catch (Exception e) 
        {
            logger.error("Ошибка при загрузке данных: " + e.getMessage(), e);
            JOptionPane.showMessageDialog(null, "Ошибка при загрузке данных: " + e.getMessage());
        }
    }
}