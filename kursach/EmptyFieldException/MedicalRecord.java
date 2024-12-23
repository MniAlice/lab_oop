package com.proghelp;

public class MedicalRecord {
    private String patientName;
    private String diagnosis;

    public MedicalRecord(String patientName, String diagnosis) 
    {
        this.patientName = patientName;
        this.diagnosis = diagnosis;
    }

    public MedicalRecord() {}

    public String getPatientName() 
    {
        return patientName;
    }

    public String getDiagnosis() 
    {
        return diagnosis;
    }

    public void setPatientName(String patientName)
    {
        this.patientName = patientName;
    }

    public void setDiagnosis(String diagnosis)
    {
        this.diagnosis = diagnosis;
    }
}
