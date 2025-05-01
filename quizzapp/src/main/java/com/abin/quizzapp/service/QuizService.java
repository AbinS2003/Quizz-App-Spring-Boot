package com.abin.quizzapp.service;

import com.abin.quizzapp.dao.QuestionRepository;
import com.abin.quizzapp.dao.QuizRepository;
import com.abin.quizzapp.model.Question;
import com.abin.quizzapp.model.QuestionWrapper;
import com.abin.quizzapp.model.Quiz;
import com.abin.quizzapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizRepository quizRepository;
    @Autowired
    QuestionRepository questionRepository;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        System.out.println("Category from qsevice" + category);
        System.out.println("numQ from qsevice" + numQ);
        System.out.println("title from qsevice" + title);

        List<Question> questions = questionRepository.findRandomQuestionsByCategory(category, numQ);

    // for testing the data from database
        System.out.println("Category: " + category);
        System.out.println("Questions fetched: " + questions.size());
        questions.forEach(q -> System.out.println(q.getQuestionTitle()));


        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);

        Quiz savedQuiz = quizRepository.save(quiz); // Save and capture returned entity

        System.out.println("Saved Quiz ID = " + savedQuiz.getId());

        return new ResponseEntity<>(String.valueOf(savedQuiz.getId()), HttpStatus.CREATED); // Return quizId as string
    }


    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
       Optional<Quiz> quiz = quizRepository.findById(id);
       List<Question> questionFromDB = quiz.get().getQuestions();
       List<QuestionWrapper> questionForUser = new ArrayList<>();
       for (Question q : questionFromDB) {
           QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
           questionForUser.add(qw);
       }

       return new ResponseEntity<>(questionForUser, HttpStatus.OK);

    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {

        Quiz quiz =  quizRepository.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        int right = 0;
        int i = 0;

        for (Response response : responses) {
            if (response.getResponse().equals(questions.get(i).getRightAnswer()))
                right++;
            i ++;
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
