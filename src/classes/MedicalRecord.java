package classes;

public class MedicalRecord {

    String medicalRecordId, recordType, recordContent;
    int doctorId, patientId;

    public MedicalRecord() {
    }

    public MedicalRecord(String medicalRecordId, String recordType, String recordContent, Doctors doctor, Patients patient) {
        this.medicalRecordId = medicalRecordId;
        this.recordType = recordType;
        this.recordContent = recordContent;
        this.doctorId = doctor.getDoctorId();
        this.patientId = patient.getPatientId();
    }

    public String getMedicalRecordId() {
        return medicalRecordId;
    }

    public void setMedicalRecordId(String medicalRecordId) {
        this.medicalRecordId = medicalRecordId;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getRecordContent() {
        return recordContent;
    }

    public void setRecordContent(String recordContent) {
        this.recordContent = recordContent;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    @Override
    public String toString() {
        return "Medical Record --> {" + "Date of creation the record=(" + medicalRecordId + ") / recordType=(" + recordType + ") / recordContent {" + recordContent.replaceAll("\n", " ") + "} / doctorId=(" + doctorId + ") / patientId=(" + patientId + ")}";
    }

}
