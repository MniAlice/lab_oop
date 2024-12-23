package com.proghelp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Doctor 
{
    private String name;
    private String specialization;
    private String workSchedule;
    private List<MedicalRecord> patients;

    public Doctor(String name, String specialization, String workSchedule, List<MedicalRecord> patients) 
    {
        this.name = name;
        this.specialization = specialization;
        this.workSchedule = workSchedule;
        this.patients = patients;
    }

    public String getName() 
    {
        return name;
    }

    public String getSpecialization() 
    {
        return specialization;
    }

    public String getWorkSchedule() 
    {
        return workSchedule;
    }

    public List<MedicalRecord> getPatients() 
    {
        return patients;
    }

    public void addMedicalRecord(MedicalRecord record) 
    {
        this.patients.add(record);
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setSpecialization(String specialization)
    {
        this.specialization = specialization;
    }

    public void setWorkSchedule(String workSchedule)
    {
        this.workSchedule = workSchedule;
    }

    public void removeMedicalRecord(int index) 
    {
        if (index >= 0 && index < patients.size())
            this.patients.remove(index);
    }

    public int countDiseases() 
    {
        Set<String> uniqueDiseases = new HashSet<>();
        for (MedicalRecord record : patients)
            if (record.getDiagnosis() != null)
                uniqueDiseases.add(record.getDiagnosis());

        return uniqueDiseases.size();
    }

    public Map<String, Integer> getDiseaseReport() 
    {
        Map<String, Integer> diseaseReport = new HashMap<>();

        for (MedicalRecord record : patients) 
        {
            String diagnosis = record.getDiagnosis();
            
            if (diagnosis != null && !diagnosis.isEmpty()) 
                diseaseReport.put(diagnosis, diseaseReport.getOrDefault(diagnosis, 0) + 1);
        }

        return diseaseReport;
    }
}
