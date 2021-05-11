package pranjal.learning.organdonation;

public class DataholderAppoint {

    String pName, pAge, pGender, pContact, pDate, pSlot, dName, dSpecialite, dContact, dAddress, dUid, dateTime;

    public DataholderAppoint(String pName, String pAge, String pGender, String pContact, String pDate,
                             String pSlot, String dName, String dSpecialite, String dContact, String dAddress,
                             String dUid, String dateTime) {
        this.pName = pName;
        this.pAge = pAge;
        this.pGender = pGender;
        this.pContact = pContact;
        this.pDate = pDate;
        this.pSlot = pSlot;
        this.dName = dName;
        this.dSpecialite = dSpecialite;
        this.dContact = dContact;
        this.dAddress = dAddress;
        this.dUid = dUid;
        this.dateTime = dateTime;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpAge() {
        return pAge;
    }

    public void setpAge(String pAge) {
        this.pAge = pAge;
    }

    public String getpGender() {
        return pGender;
    }

    public void setpGender(String pGender) {
        this.pGender = pGender;
    }

    public String getpContact() {
        return pContact;
    }

    public void setpContact(String pContact) {
        this.pContact = pContact;
    }

    public String getpDate() {
        return pDate;
    }

    public void setpDate(String pDate) {
        this.pDate = pDate;
    }

    public String getpSlot() {
        return pSlot;
    }

    public void setpSlot(String pSlot) {
        this.pSlot = pSlot;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getdSpecialite() {
        return dSpecialite;
    }

    public void setdSpecialite(String dSpecialite) {
        this.dSpecialite = dSpecialite;
    }

    public String getdContact() {
        return dContact;
    }

    public void setdContact(String dContact) {
        this.dContact = dContact;
    }

    public String getdAddress() {
        return dAddress;
    }

    public void setdAddress(String dAddress) {
        this.dAddress = dAddress;
    }

    public String getdUid() {
        return dUid;
    }

    public void setdUid(String dUid) {
        this.dUid = dUid;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
