package com.example.thibanglaixe.object;

public class Question {
    String numberQuestion;
    String title;
    String choice1;
    String choice2;
    String choice3;
    String choice4;
    int answer;
    int yourAnswer;

    public Question(String numberQuestion, String title, String choice1, String choice2, String choice3, String choice4, int answer) {
        this.numberQuestion = numberQuestion;
        this.title = title;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
        this.answer = answer;
        this.yourAnswer = -1; // Mặc định chưa chọn đáp án
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public String getChoice1() {
        return choice1;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public String getChoice3() {
        return choice3;
    }

    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    public String getChoice2() {
        return choice2;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public String getChoice4() {
        return choice4;
    }

    public void setChoice4(String choice4) {
        this.choice4 = choice4;
    }

    public String getNumberQuestion() {
        return numberQuestion;
    }

    public void setNumberQuestion(String numberQuestion) {
        this.numberQuestion = numberQuestion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYourAnswer() {
        return yourAnswer;
    }

    public void setYourAnswer(int yourAnswer) {
        this.yourAnswer = yourAnswer;
    }

    @Override
    public String toString() {
        return numberQuestion + ": " + title + "\n" +
                "1. " + choice1 + "\n" +
                "2. " + choice2 + "\n" +
                "3. " + choice3 + "\n" +
                "4. " + choice4 + "\n" +
                "Đáp án đúng: " + answer + "\n" +
                "Bạn chọn: " + (yourAnswer == -1 ? "Chưa chọn" : yourAnswer) + "\n";
    }
}
