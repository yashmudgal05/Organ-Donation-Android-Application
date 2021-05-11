package pranjal.learning.organdonation;

public class modelQ {

    String question, answer;

    // we have to make empty constructor to send send to recyclerView
    modelQ() {

    }

    public modelQ(String question, String answer) {

        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
