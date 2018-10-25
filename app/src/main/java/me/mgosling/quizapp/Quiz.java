package me.mgosling.quizapp;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

class Quiz {

    private String userName, definition, correctAnswer;
    private int currentScore, currentQuestionIndex;
    private HashMap<String, String> quizMap = new HashMap<>();
    private List<String[]> quizList = new ArrayList<>();
    private List<String> possibleAnswers = new ArrayList<>();
    private boolean quizFinished = false;

    protected boolean isQuizFinished() {
        return quizFinished;
    }

    protected int getCurrentScore() {
        return currentScore;
    }

    protected String getUserName(){
        return userName;
    }

    protected String getDefinition() {
        return definition;
    }

    protected List<String> getPossibleAnswers(){
        return possibleAnswers;
    }

    protected int getTotalTerms() {
        return quizList.size();
    }


    public Quiz(String name, InputStream inputStream) {
        userName = name;

        readQuizData(inputStream);
        currentQuestionIndex = -1;
        nextQuestion();
    }

    protected boolean guessAnswer(String answer) {
        if (answer.equals(correctAnswer)) {
            currentScore++;
            nextQuestion();
            return true;
        } else {
            nextQuestion();
            return false;
        }
    }

    // grabs next question and random answers
    private void nextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < quizList.size()) {
            possibleAnswers.clear();

            definition = quizList.get(currentQuestionIndex)[0];
            correctAnswer = quizMap.get(definition);
            possibleAnswers.add(correctAnswer);

            while (possibleAnswers.size() < 4) {
                String answer = quizList.get(new Random().nextInt(quizList.size() - 1))[1];
                if (!answer.equals(correctAnswer) && !possibleAnswers.contains(answer)) {
                    possibleAnswers.add(answer);
                }
            }
            Collections.shuffle(possibleAnswers);
        } else {
            quizFinished = true;
        }
    }

    // Reads quiz file into quizMap hashmap.
    private void readQuizData(InputStream is) {
        // array to read in file lines
        List<String> lines = new ArrayList<>();

        // read file lines in
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = bufferedReader.readLine()) != null){
                lines.add(line);
            }

            bufferedReader.close();
            is.close();
        } catch (Exception e){
            Log.e("QUIZ_DATA_READING", "Failed while trying to read in text file.");
        }

        // shuffle lines so terms are in different order
        Collections.shuffle(lines);

        // read terms into hashmap and ArrayList
        for (String line : lines){
            String termAndDef[] = line.split(":");
            quizList.add(termAndDef);
            quizMap.put(termAndDef[0], termAndDef[1]);
        }


    }
}
