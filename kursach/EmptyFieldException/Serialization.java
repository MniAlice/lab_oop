package com.proghelp;

import java.sql.*;
import javax.swing.*;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Serialization 
{
    private static final Logger logger = LogManager.getLogger(Serialization.class);

    private static final String DB_URL = "jdbc:sqlite:appointments.db";

    static 
    {
        try (Connection conn = DriverManager.getConnection(DB_URL)) 
        {
            try (Statement stmt = conn.createStatement()) 
            {
                stmt.execute("CREATE TABLE IF NOT EXISTS Doctors (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT NOT NULL," +
                        "specialization TEXT NOT NULL," +
                        "time TEXT NOT NULL)");

                stmt.execute("CREATE TABLE IF NOT EXISTS Patients (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "doctor_id INTEGER NOT NULL," +
                        "name TEXT NOT NULL," +
                        "disease TEXT NOT NULL," +
                        "FOREIGN KEY (doctor_id) REFERENCES Doctors (id))");
            }
        } 
        catch (SQLException e) 
        {
            logger.error("Ошибка при создании базы данных: " + e.getMessage(), e);
        }
    }

    public static void SaveButtonClick(JTable table) 
    {
        try (Connection conn = DriverManager.getConnection(DB_URL)) 
        {
            conn.setAutoCommit(false);

            DoctorTableModel model = (DoctorTableModel) table.getModel();

            try (PreparedStatement doctorStmt = conn.prepareStatement(
                    "INSERT INTO Doctors (name, specialization, time) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement patientStmt = conn.prepareStatement(
                         "INSERT INTO Patients (doctor_id, name, disease) VALUES (?, ?, ?)")) 
                         {

                for (int i = 0; i < model.getRowCount(); i++) 
                {
                    Doctor doctor = model.getDoctorAt(i);

                    doctorStmt.setString(1, doctor.getName());
                    doctorStmt.setString(2, doctor.getSpecialization());
                    doctorStmt.setString(3, doctor.getWorkSchedule());
                    doctorStmt.executeUpdate();

                    ResultSet rs = doctorStmt.getGeneratedKeys();
                    if (rs.next()) 
                    {
                        int doctorId = rs.getInt(1);

                        List<MedicalRecord> patients = doctor.getPatients();
                        for (MedicalRecord patient : patients) 
                        {
                            patientStmt.setInt(1, doctorId);
                            patientStmt.setString(2, patient.getPatientName());
                            patientStmt.setString(3, patient.getDiagnosis());
                            patientStmt.addBatch();
                        }
                        patientStmt.executeBatch();
                    }
                }

                conn.commit();
                logger.info("Данные успешно сохранены в SQLite.");
                JOptionPane.showMessageDialog(null, "Данные успешно сохранены.");
            } 
            catch (SQLException e) 
            {
                conn.rollback();
                throw e;
            }
        } 
        catch (Exception e) 
        {
            logger.error("Ошибка при сохранении данных: " + e.getMessage(), e);
            JOptionPane.showMessageDialog(null, "Ошибка при сохранении данных: " + e.getMessage());
        }
    }

    public static void LoadButtonClick(JTable table, DoctorTableModel model) 
    {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) 
             {

            ResultSet doctorRs = stmt.executeQuery("SELECT * FROM Doctors");

            while (doctorRs.next()) 
            {
                int doctorId = doctorRs.getInt("id");
                String name = doctorRs.getString("name");
                String specialization = doctorRs.getString("specialization");
                String time = doctorRs.getString("time");

                List<MedicalRecord> patients = new ArrayList<>();
                try (PreparedStatement patientStmt = conn.prepareStatement(
                        "SELECT name, disease FROM Patients WHERE doctor_id = ?")) 
                        {
                    patientStmt.setInt(1, doctorId);
                    ResultSet patientRs = patientStmt.executeQuery();
                    while (patientRs.next()) 
                    {
                        String patientName = patientRs.getString("name");
                        String disease = patientRs.getString("disease");
                        patients.add(new MedicalRecord(patientName, disease));
                    }
                }

                Doctor doctor = new Doctor(name, specialization, time, patients);
                model.addRow(doctor);
            }

            logger.info("Данные успешно загружены из SQLite.");
            JOptionPane.showMessageDialog(null, "Данные успешно загружены.");
        } 
        catch (Exception e) 
        {
            logger.error("Ошибка при загрузке данных: " + e.getMessage(), e);
            JOptionPane.showMessageDialog(null, "Ошибка при загрузке данных: " + e.getMessage());
        }
    }
}
