package com.proghelp;

import javax.swing.table.AbstractTableModel;
import java.util.List;

class MedicalRecordTableModel extends AbstractTableModel 
{
    private List<MedicalRecord> medicalRecords;
    private String[] columnNames = {"Имя пациента", "Диагноз"};

    public MedicalRecordTableModel(List<MedicalRecord> medicalRecords) 
    {
        this.medicalRecords = medicalRecords;
    }

    @Override
    public int getRowCount() 
    {
        return medicalRecords.size();
    }

    @Override
    public int getColumnCount() 
    {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) 
    {
        MedicalRecord record = medicalRecords.get(rowIndex);
        switch (columnIndex) 
        {
            case 0:
                return record.getPatientName();
            case 1:
                return record.getDiagnosis();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) 
    {
        return columnNames[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) 
    {
        return true;
    }
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) 
    {
        MedicalRecord record = medicalRecords.get(rowIndex);
        switch (columnIndex) 
        {
            case 0:
                record.setPatientName((String) aValue);
                break;
            case 1:
                record.setDiagnosis((String) aValue);
                break;
        }
        fireTableDataChanged();
    }


}

