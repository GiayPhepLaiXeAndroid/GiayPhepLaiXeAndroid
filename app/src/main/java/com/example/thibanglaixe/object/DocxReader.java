package com.example.thibanglaixe.object;

import android.content.Context;
import android.util.Log;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocxReader {
    public static ArrayList<Question> readDocxQuestions(Context context, String fileName) {
        ArrayList<Question> questions = new ArrayList<>();
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            XWPFDocument document = new XWPFDocument(inputStream);
            StringBuilder textContent = new StringBuilder();

            // Đọc toàn bộ nội dung của file .docx
            for (XWPFParagraph para : document.getParagraphs()) {
                String text = para.getText().trim();
                if (!text.isEmpty()) {
                    textContent.append(text).append("\n");
                }
            }
            inputStream.close();

            // Regex để tìm câu hỏi
            Pattern pattern = Pattern.compile("(Câu \\d+: .*?\\?)\\s*(?=\\d+\\.)", Pattern.DOTALL);
            String content = textContent.toString();
            Matcher matcher = pattern.matcher(content);
            List<String> matches = new ArrayList<>();

            while (matcher.find()) {
                matches.add(matcher.group(1)); // Lấy câu hỏi
                int nextIndex = matcher.end(); // Vị trí kết thúc của câu hỏi trong chuỗi gốc

                // Tìm phần đáp án phía sau câu hỏi
                int nextQuestionIndex = content.indexOf("Câu ", nextIndex);
                String answerBlock = nextQuestionIndex == -1 ?
                        content.substring(nextIndex) :
                        content.substring(nextIndex, nextQuestionIndex);

                matches.add(answerBlock.trim()); // Lưu phần đáp án
            }

            // Xử lý danh sách câu hỏi và đáp án
            for (int i = 0; i < matches.size(); i += 2) {
//                String questionText = matches.get(i).trim();
                String rawQuestion = matches.get(i).trim();

// Tách số thứ tự câu hỏi và nội dung câu hỏi
                Pattern questionPattern = Pattern.compile("^(Câu \\d+):\\s*(.*)$");
                Matcher questionMatcher = questionPattern.matcher(rawQuestion);

                String questionNumber = "";
                String questionText = "";

                if (questionMatcher.find()) {
                    questionNumber = questionMatcher.group(1); // Lấy "Câu x"
                    questionText = questionMatcher.group(2);  // Lấy nội dung câu hỏi
                }

                String answersText = (i + 1 < matches.size()) ? matches.get(i + 1).trim() : "";

                // Tách đáp án theo format "1. xxx", "2. xxx", ...
                Pattern answerPattern = Pattern.compile("(\\d+\\.\\s.*?)(?=\\s*\\d+\\.\\s|$)", Pattern.DOTALL);
                Matcher answerMatcher = answerPattern.matcher(answersText);
                List<String> answers = new ArrayList<>();

                while (answerMatcher.find()) {
                    answers.add(answerMatcher.group(1).trim());
                }

                // Tạo đối tượng Question và thêm vào danh sách
                String choice1 = answers.size() > 0 ? answers.get(0) : "";
                String choice2 = answers.size() > 1 ? answers.get(1) : "";
                String choice3 = answers.size() > 2 ? answers.get(2) : "";
                String choice4 = answers.size() > 3 ? answers.get(3) : "";

                questions.add(new Question(questionNumber, questionText, choice1, choice2, choice3, choice4, 1)); // Mặc định đáp án đúng là 1
            }
        } catch (Exception e) {
            Log.e("DocxReader", "Lỗi khi đọc file: " + e.getMessage());
        }
        return questions;
    }
}
