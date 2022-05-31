package classes;

public class Patients {

    int patientId, patientNumber;
    String patientFirstName, patientLastName, patientAddress, atientCity;

    public Patients(){
    }
        
    public Patients(int patientId, int patientNumber, String patientFirstName, String patientLastName, String patientAddress, String atientCity) {
        this.patientId = patientId;
        this.patientNumber = patientNumber;
        this.patientFirstName = patientFirstName;
        this.patientLastName = patientLastName;
        this.patientAddress = patientAddress;
        this.atientCity = atientCity;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getPatientNumber() {
        return patientNumber;
    }

    public void setPatientNumber(int patientNumber) {
        this.patientNumber = patientNumber;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getAtientCity() {
        return atientCity;
    }

    public void setAtientCity(String atientCity) {
        this.atientCity = atientCity;
    }

    @Override
    public String toString() {
        return "patients{" + "patientId=" + patientId + ", patientNumber=" + patientNumber + ", patientFirstName=" + patientFirstName + ", patientLastName=" + patientLastName + ", patientAddress=" + patientAddress + ", atientCity=" + atientCity + '}';
    }

}
