package com.proghelp;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTable;
import javax.swing.table.TableModel;

public class JRXMLGenerator 
{
    public static void generateJRXML(JTable table, String filePath) 
    {
        StringBuilder jrxmlContent = new StringBuilder();

        jrxmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        jrxmlContent.append("<jasperReport xmlns=\"http://jasperreports.sourceforge.net/jasperreports\"\n");
        jrxmlContent.append("              xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n");
        jrxmlContent.append("              xsi:schemaLocation=\"http://jasperreports.sourceforge.net/jasperreports\n");
        jrxmlContent.append("                                  http://jasperreports.sourceforge.net/xsd/jasperreport.xsd\"\n");
        jrxmlContent.append("              name=\"dynamic_report\" language=\"java\" pageWidth=\"595\" pageHeight=\"842\"\n");
        jrxmlContent.append("              columnWidth=\"515\" leftMargin=\"40\" rightMargin=\"40\" topMargin=\"50\"\n");
        jrxmlContent.append("              bottomMargin=\"50\">\n");

        TableModel model = table.getModel();
        int columnCount = model.getColumnCount();

        for (int i = 0; i < columnCount; i++) 
        {
            jrxmlContent.append("    <field name=\"")
                        .append(model.getColumnName(i))
                        .append("\" class=\"java.lang.String\"/>\n");
        }

        jrxmlContent.append("    <title>\n");
        jrxmlContent.append("        <band height=\"80\">\n");
        jrxmlContent.append("            <staticText>\n");
        jrxmlContent.append("                <reportElement x=\"0\" y=\"0\" width=\"515\" height=\"40\"/>\n");
        jrxmlContent.append("                <textElement textAlignment=\"Center\"/>\n");
        jrxmlContent.append("                <text><![CDATA[Dynamic Report]]></text>\n");
        jrxmlContent.append("            </staticText>\n");
        jrxmlContent.append("        </band>\n");
        jrxmlContent.append("    </title>\n");

        jrxmlContent.append("    <detail>\n");
        jrxmlContent.append("        <band height=\"30\">\n");

        for (int i = 0; i < columnCount; i++) 
        {
            jrxmlContent.append("            <textField>\n");
            jrxmlContent.append("                <reportElement x=\"" + (i * 120) + "\" y=\"2\" width=\"100\" height=\"25\"/>\n");
            jrxmlContent.append("                <textElement/>\n");
            jrxmlContent.append("                <textFieldExpression><![CDATA[$F{")
                        .append(model.getColumnName(i))
                        .append("}]]></textFieldExpression>\n");
            jrxmlContent.append("            </textField>\n");
        }

        jrxmlContent.append("        </band>\n");
        jrxmlContent.append("    </detail>\n");

        jrxmlContent.append("    <summary>\n");
        jrxmlContent.append("        <band height=\"20\"/>\n");
        jrxmlContent.append("    </summary>\n");

        jrxmlContent.append("</jasperReport>\n");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) 
        {
            writer.write(jrxmlContent.toString());
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}

