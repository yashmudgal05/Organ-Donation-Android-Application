package pranjal.learning.organdonation;

public class modelDr {

    String name, uniqueId, speciality, experience, purl, city, state, mail, contact, ch, worksAt;

    // we have to make empty constructor to send send to recyclerView
    modelDr() {

    }

    public modelDr(String name, String uniqueId, String speciality, String experience, String purl,
                   String city, String state, String mail, String contact, String ch, String worksAt) {

        this.name = name;
        this.uniqueId = uniqueId;
        this.speciality = speciality;
        this.experience = experience;
        this.purl = purl;
        this.city = city;
        this.contact = contact;
        this.mail = mail;
        this.state = state;
        this.ch = ch;
        this.worksAt = worksAt;
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

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public String getWorksAt() {
        return worksAt;
    }

    public void setWorksAt(String worksAt) {
        this.worksAt = worksAt;
    }
}
