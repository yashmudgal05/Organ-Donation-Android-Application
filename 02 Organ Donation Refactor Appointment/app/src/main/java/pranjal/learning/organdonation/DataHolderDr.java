package pranjal.learning.organdonation;

public class DataHolderDr {

    String name, uniqueId, speciality, experience, worksAt, contact, mail, city, state, purl, ch;

    public DataHolderDr(String name) {
        this.name = name;
    }

    public DataHolderDr(String name, String uniqueId, String speciality, String experience, String worksAt,
                        String contact, String mail, String city, String state, String purl, String ch) {

        this.name = name;
        this.uniqueId = uniqueId;
        this.speciality = speciality;
        this.experience = experience;
        this.worksAt = worksAt;
        this.contact = contact;
        this.mail = mail;
        this.city = city;
        this.state = state;
        this.purl = purl;
        this.ch = ch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getWorksAt() {
        return worksAt;
    }

    public void setWorksAt(String worksAt) {
        this.worksAt = worksAt;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }

    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }
}
