package com.abin.quizzapp.controller;

import com.abin.quizzapp.model.Question;
import com.abin.quizzapp.model.QuestionWrapper;
import com.abin.quizzapp.model.Response;
import com.abin.quizzapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.abin.quizzapp.dto.QuizCreationResponse;


import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;


//    @PostMapping("create")
//    public ResponseEntity<QuizCreationResponse> createQuiz(@RequestParam String category,
//                                                           @RequestParam int numQ,
//                                                           @RequestParam String title) {
//
//        System.out.println("Category from pagecontroller " + category);
//        System.out.println("numQ from pagecontroller " + numQ);
//        System.out.println("title from pagecontroller " + title);
//
//        // Call the service to create the quiz
//        ResponseEntity<String> response = quizService.createQuiz(category, numQ, title);
//
//        Integer quizId = Integer.parseInt(response.getBody());
//
//        System.out.println("Quiz id in quiz service = " + quizId);
//
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(new QuizCreationResponse(quizId));
//    }


    // Custom response class to send quizId as part of the response
//    public static class QuizCreationResponse {
//        private Integer quizId;
//
//        public QuizCreationResponse(Integer quizId) {
//            this.quizId = quizId;
//        }
//
//        public Integer getQuizId() {
//            return quizId;
//        }
//
//        public void setQuizId(Integer quizId) {
//            this.quizId = quizId;
//        }
//    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>>getQuizQuestions(@PathVariable Integer id) {
       return quizService.getQuizQuestions(id);

    }

//    @PostMapping("submit/{id}")
//    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses) {
//
//        return quizService.calculateResult(id, responses);
//    }
}
