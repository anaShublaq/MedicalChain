package classes;

public class Doctors {

    int doctorId, doctorNumber;
    String doctorFirstName, doctorLastName, doctorAddress, doctorCity, doctorSpecialization, doctorSection, doctorEmail;

    public Doctors() {
    }

    public Doctors(int doctorId, int doctorNumber, String doctorFirstName, String doctorLastName, String doctorAddress, String doctorCity, String doctorSpecialization, String doctorSection, String doctorEmail) {
        this.doctorId = doctorId;
        this.doctorNumber = doctorNumber;
        this.doctorFirstName = doctorFirstName;
        this.doctorLastName = doctorLastName;
        this.doctorAddress = doctorAddress;
        this.doctorCity = doctorCity;
        this.doctorSpecialization = doctorSpecialization;
        this.doctorSection = doctorSection;
        this.doctorEmail = doctorEmail;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getDoctorNumber() {
        return doctorNumber;
    }

    public void setDoctorNumber(int doctorNumber) {
        this.doctorNumber = doctorNumber;
    }

    public String getDoctorFirstName() {
        return doctorFirstName;
    }

    public void setDoctorFirstName(String doctorFirstName) {
        this.doctorFirstName = doctorFirstName;
    }

    public String getDoctorLastName() {
        return doctorLastName;
    }

    public void setDoctorLastName(String doctorLastName) {
        this.doctorLastName = doctorLastName;
    }

    public String getDoctorAddress() {
        return doctorAddress;
    }

    public void setDoctorAddress(String doctorAddress) {
        this.doctorAddress = doctorAddress;
    }

    public String getDoctorCity() {
        return doctorCity;
    }

    public void setDoctorCity(String doctorCity) {
        this.doctorCity = doctorCity;
    }

    public String getDoctorSpecialization() {
        return doctorSpecialization;
    }

    public void setDoctorSpecialization(String doctorSpecialization) {
        this.doctorSpecialization = doctorSpecialization;
    }

    public String getDoctorSection() {
        return doctorSection;
    }

    public void setDoctorSection(String doctorSection) {
        this.doctorSection = doctorSection;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    @Override
    public String toString() {
        return "doctors{" + "doctorId=" + doctorId + ", doctorNumber=" + doctorNumber + ", doctorFirstName=" + doctorFirstName + ", doctorLastName=" + doctorLastName + ", doctorAddress=" + doctorAddress + ", doctorCity=" + doctorCity + ", doctorSpecialization=" + doctorSpecialization + ", doctorSection=" + doctorSection + ", doctorEmail=" + doctorEmail + '}';
    }

}
