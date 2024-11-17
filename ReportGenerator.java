package com.proghelp;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.TableModel;

public class ReportGenerator 
{
    public static void generateReport(JTable table) 
    {
        try 
        {
            TableModel model = table.getModel();
            int rowCount = model.getRowCount();
            int columnCount = model.getColumnCount();

            List<Map<String, Object>> dataList = new ArrayList<>();

            for (int i = 0; i < rowCount; i++) 
            {
                Map<String, Object> rowData = new HashMap<>();

                for (int j = 0; j < columnCount; j++)
                    rowData.put(model.getColumnName(j), model.getValueAt(i, j));

                dataList.add(rowData);
            }

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);

            String jrxmlFilePath = "dynamic_report.jrxml";
            JRXMLGenerator.generateJRXML(table, jrxmlFilePath);

            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFilePath);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ReportTitle", "Generated Report");

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            JasperExportManager.exportReportToPdfFile(jasperPrint, "dynamic_report.pdf");
            JasperExportManager.exportReportToHtmlFile(jasperPrint, "dynamic_report.html");

        } 
        catch (JRException e) 
        {
            e.printStackTrace();
        }
    }
}
