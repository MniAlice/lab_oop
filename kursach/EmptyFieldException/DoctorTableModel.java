package com.proghelp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DoctorTableModel extends AbstractTableModel 
{
    private List<Doctor> doctors = new ArrayList<>();
    private String[] columnNames = {"Врач", "Время", "specialization", "pacients"};

    public DoctorTableModel(List<Doctor> doctors)
    {
        this.doctors = doctors;
    }

    @Override
    public int getRowCount()
    {
        return doctors.size();
    }

    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        Doctor doctor = doctors.get(rowIndex);
        switch (columnIndex) 
        {
            case 0: return doctor.getName();
            case 1: return doctor.getWorkSchedule();
            case 2: return doctor.getSpecialization();
            case 3: return "Подробнее";
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column)
    {
        return columnNames[column];
    }

    public void addRow(Doctor doctor)
    {
        doctors.add(doctor);
        fireTableRowsInserted(doctors.size()-1, doctors.size()-1);
    }

    public Doctor getDoctorAt(int rowIndex) 
    {
        return doctors.get(rowIndex);
    }

    public void removeRow(int rowIndex) 
    {
        if (doctors == null) 
        {
            System.out.println("Список doctors равен null!");
            return;
        }
    
        if (rowIndex >= 0 && rowIndex < doctors.size()) 
        {
            try 
            {
                System.out.println("Попытка удалить строку с индексом: " + rowIndex);
                doctors.remove(rowIndex);
                fireTableRowsDeleted(rowIndex, rowIndex);
                System.out.println("Размер списка после удаления: " + doctors.size());
            } 
            catch (Exception e) 
            {
                System.out.println("Ошибка при удалении: " + e.getMessage());
                e.printStackTrace();
            }
        } 
        else 
        {
            System.out.println("Индекс выходит за пределы списка: " + rowIndex);
        }
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) 
    {
        Doctor doctor = doctors.get(rowIndex);
        switch (columnIndex) 
        {
            case 0:
                doctor.setName((String) aValue);
                break;
            case 1:
                doctor.setWorkSchedule((String) aValue);
                break;
            case 2:
                doctor.setSpecialization((String) aValue);
                break;
            default:
                return;
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }


    public void setRowCount() 
    {
        doctors.clear();
        fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) 
    {
        return true;
    }

    public List<MedicalRecord> getAllPatients() 
    {
        List<MedicalRecord> allPatients = new ArrayList<>();
        for (Doctor doctor : doctors)
            allPatients.addAll(doctor.getPatients());
        return allPatients;
    }
    
}

class ButtonEditor extends AbstractCellEditor implements TableCellEditor 
{
    private JButton button;
    private JTable table;
    private String label;

    public ButtonEditor(JTable table) 
    {
        System.out.println("Кнопка нажата!");
        this.table = table;
        button = new JButton();
        button.setOpaque(true);

        button.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                System.out.println("Кнопка нажата!123");
                int row = table.getSelectedRow();
                if (row >= 0) 
                {
                    DoctorTableModel model = (DoctorTableModel) table.getModel();
                    Doctor doctor = model.getDoctorAt(row);

                    JFrame frame1 = new JFrame("Медицинские записи врача: " + doctor.getName());
                    frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame1.setSize(600, 400);

                    MedicalRecordTableModel recordModel = new MedicalRecordTableModel(doctor.getPatients());
                    JTable recordTable = new JTable(recordModel);

                    JPanel buttonPanel = new JPanel();
                    buttonPanel.setLayout(new FlowLayout());

                    JButton reportButton = new JButton("Отчет по заболеваниям");
                    reportButton.addActionListener(new ActionListener() 
                    {
                        @Override
                        public void actionPerformed(ActionEvent e) 
                        {
                            Map<String, Integer> report = doctor.getDiseaseReport();

                            StringBuilder reportText = new StringBuilder("Отчет по заболеваниям:\n");
                            for (Map.Entry<String, Integer> entry : report.entrySet())
                                reportText.append(entry.getKey()).append(" - ").append(entry.getValue()).append("\n");

                            JOptionPane.showMessageDialog(frame1, reportText.toString(), "Отчет", JOptionPane.INFORMATION_MESSAGE);
                        }
                    });
                    buttonPanel.add(reportButton);


                    JButton addButton = new JButton("Добавить запись");
                    addButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                            doctor.addMedicalRecord(new MedicalRecord());
                            recordModel.fireTableDataChanged();
                        }
                    });
                    buttonPanel.add(addButton);

                    JButton deleteButton = new JButton("Удалить запись");
                    deleteButton.addActionListener(new ActionListener() 
                    {
                        @Override
                        public void actionPerformed(ActionEvent e) 
                        {
                            int selectedRow = recordTable.getSelectedRow();
                            if (selectedRow != -1) 
                            {
                                doctor.removeMedicalRecord(selectedRow);
                                recordModel.fireTableDataChanged();
                            }
                        }
                    });
                    buttonPanel.add(deleteButton);

                    frame1.add(buttonPanel, BorderLayout.NORTH);

                    frame1.add(new JScrollPane(recordTable), BorderLayout.CENTER);

                    frame1.setVisible(true);
                }

                fireEditingStopped();
            }
        });
    }

    
    

    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                boolean isSelected, int row, int column) 
                                                {
        System.out.println("Редактор активирован для строки: " + row);
        label = value == null ? "Подробнее" : value.toString();
        button.setText(label);
        return button;
    }


    @Override
    public Object getCellEditorValue() 
    {
        return label;
    }
}

class ButtonRenderer extends JButton implements TableCellRenderer 
{
    public ButtonRenderer() 
    {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
    {
        setText((value == null) ? "Подробнее" : value.toString());
        return this;
    }
}