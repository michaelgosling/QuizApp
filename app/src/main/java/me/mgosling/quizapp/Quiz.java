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

    // private properties
    private String userName, definition, correctAnswer;
    private int currentScore, currentQuestionIndex;
    private HashMap<String, String> quizMap = new HashMap<>();
    private List<String[]> quizList = new ArrayList<>();
    private List<String> possibleAnswers = new ArrayList<>();
    private boolean quizFinished = false;

    // getters and setters
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


    // constructor that takes the name of the user and an input stream for the file
    public Quiz(String name, InputStream inputStream) {
        userName = name;

        readQuizData(inputStream);

        // set the current question index to -1 and then call nextQuestion
        currentQuestionIndex = -1;
        nextQuestion();
    }


    // tries an answer and if it's correct increments the score, calls nextQuestion and returns true
    // otherwise it calls nextQuestion and returns false.
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

        // increment current question index
        currentQuestionIndex++;

        // if the current question index is smaller than the size of the quiz list
        if (currentQuestionIndex < quizList.size()) {

            // clear the possible answers
            possibleAnswers.clear();

            // grab the definition of current question, assign the correct answer variable
            // and add it to the list of possible answers.
            definition = quizList.get(currentQuestionIndex)[0];
            correctAnswer = quizMap.get(definition);
            possibleAnswers.add(correctAnswer);

            // while there's less than 4 possible answers, get a random answer
            // and if it hasn't already been added, add it to the list.
            while (possibleAnswers.size() < 4) {
                String answer = quizList.get(new Random().nextInt(quizList.size() - 1))[1];
                if (!possibleAnswers.contains(answer)) {
                    possibleAnswers.add(answer);
                }
            }

            // shuffle possible answers so they display in a random order
            Collections.shuffle(possibleAnswers);
        } else {
            // if the question index is out of bounds, set quiz as finished
            quizFinished = true;
        }
    }

    // Reads quiz file into hashmap and list
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
            // log an error if fails
            Log.e("QUIZ_DATA_READING", "Failed while trying to read in text file.");
        }

        // shuffle lines so terms are in different order
        Collections.shuffle(lines);

        // read terms into hashmap and ArrayList by splitting on colons
        for (String line : lines){
            String termAndDef[] = line.split(":");
            quizList.add(termAndDef);
            quizMap.put(termAndDef[0], termAndDef[1]);
        }
    }
}
