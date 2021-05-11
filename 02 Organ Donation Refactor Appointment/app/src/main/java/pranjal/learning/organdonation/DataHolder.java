package pranjal.learning.organdonation;

public class DataHolder {

    String name, uniqueId, age, gender, organ, contact, mail, city, state, purl;

    public DataHolder(String name) {
        this.name = name;
    }

    public DataHolder(String name, String uniqueId, String age, String gender, String organ,
                      String contact, String mail, String city, String state, String purl) {

        this.name = name;
        this.uniqueId = uniqueId;
        this.age = age;
        this.gender = gender;
        this.organ = organ;
        this.contact = contact;
        this.mail = mail;
        this.city = city;
        this.state = state;
        this.purl = purl;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
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
}
