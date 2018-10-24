package me.mgosling.quizapp;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

class Quiz {

    private String userName, term, correctAnswer;
    private int currentScore, currentQuestionIndex;
    private HashMap<String, String> quizMap = new HashMap<>();
    private List<String[]> quizList = new ArrayList<>();
    private List<String> possibleAnswers = new ArrayList<>();

    protected String getUserName(){
        return userName;
    }

    protected String getTerm(){
        return term;
    }

    protected String getCorrectAnswer(){
        return correctAnswer;
    }

    protected List<String> getPossibleAnswers(){
        return possibleAnswers;
    }


    Quiz(String name, Context ctx){
        userName = name;

        readQuizData(ctx);
        currentQuestionIndex = -1;
        newQuestion();
    }

    private void newQuestion(){
        possibleAnswers.clear();
        currentQuestionIndex++;
        term = quizList.get(currentQuestionIndex)[0];
        correctAnswer = quizMap.get(term);
        possibleAnswers.add(correctAnswer);

        while(possibleAnswers.size() < 4){
            String answer = quizList.get(new Random().nextInt(quizList.size()-1))[1];
            if (!answer.equals(correctAnswer)){
                possibleAnswers.add(answer);
            }
        }

    }

    // Reads quiz file into quizMap hashmap.
    private void readQuizData(Context ctx){
        // array to read in file lines
        List<String> lines = new ArrayList<>();

        // read file lines in
        try {
            InputStream inputStream = ctx.getResources().openRawResource(R.raw.quiz);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = bufferedReader.readLine()) != null){
                lines.add(line);
            }

            bufferedReader.close();
            inputStream.close();
        } catch (Exception e){
            e.printStackTrace();
        }

        // shuffle lines so terms are in different order
        Collections.shuffle(lines);


        // read terms into hashmap and ArrayList
        for (String line : lines){
            String termAndDef[] = line.split(",");
            quizList.add(termAndDef);
            quizMap.put(termAndDef[0], termAndDef[1]);
        }


    }
}
