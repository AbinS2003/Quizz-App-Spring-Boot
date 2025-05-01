package com.abin.quizzapp.service;

import com.abin.quizzapp.dao.QuestionRepository;
import com.abin.quizzapp.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

    @Service
    public class QuestionService {

        @Autowired
        QuestionRepository questionRepo;

        public ResponseEntity<List<Question>> getAllQuestions() {
            try {
                return new ResponseEntity<>(questionRepo.findAll(), HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }


        public ResponseEntity<List<Question>> getQuestionByCategory(String category) {
            try {
                return new ResponseEntity<>(questionRepo.findByCategory(category), HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }

        // Add a new question
        public ResponseEntity<String> addQuestion(Question question) {
            try {
                questionRepo.save(question);
                return new ResponseEntity<>("Success", HttpStatus.CREATED);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new ResponseEntity<>("Failed to add question", HttpStatus.BAD_REQUEST);
        }


        public ResponseEntity<String> deleteQuestion(Integer id) {
            try {
                Optional<Question> question = questionRepo.findById(id);
                if (question.isPresent()) {
                    questionRepo.deleteById(id);
                    return new ResponseEntity<>("Question deleted successfully", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new ResponseEntity<>("Failed to delete question", HttpStatus.BAD_REQUEST);
        }

        public ResponseEntity<String> updateQuestion(Integer id, Question updatedQuestion) {
            try {
                Optional<Question> existingQuestion = questionRepo.findById(id);
                if (existingQuestion.isPresent()) {
                    Question question = existingQuestion.get();
                    question.setQuestionTitle(updatedQuestion.getQuestionTitle());
                    question.setOption1(updatedQuestion.getOption1());
                    question.setOption2(updatedQuestion.getOption2());
                    question.setOption3(updatedQuestion.getOption3());
                    question.setOption4(updatedQuestion.getOption4());
                    question.setRightAnswer(updatedQuestion.getRightAnswer());
                    question.setDifficultylevel(updatedQuestion.getDifficultylevel());
                    question.setCategory(updatedQuestion.getCategory());

                    // Save the updated question
                    questionRepo.save(question);
                    return new ResponseEntity<>("Question updated successfully", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new ResponseEntity<>("Failed to update question", HttpStatus.BAD_REQUEST);
        }
    }

