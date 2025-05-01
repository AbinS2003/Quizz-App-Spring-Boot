package com.abin.quizzapp.controller;

import com.abin.quizzapp.dto.LoginRequest;
import com.abin.quizzapp.dto.QuizForm;
import com.abin.quizzapp.dto.UserDto;
import com.abin.quizzapp.model.QuestionWrapper;
import com.abin.quizzapp.model.Response;
import com.abin.quizzapp.model.ResponseListWrapper;
import com.abin.quizzapp.model.User;
import com.abin.quizzapp.service.QuizService;
import com.abin.quizzapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.abin.quizzapp.dto.QuizCreationResponse;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @Autowired
    QuizService quizService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/")  // Home Page
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid email or password!");
        }
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    @GetMapping("/signup")
    public String signup(@ModelAttribute("user") UserDto userDto) {// Creates a blank User object
        return "signup";
    }

    @PostMapping("/signup")
    public String saveUser(@ModelAttribute("user") UserDto userDto, Model model) {

        userService.save(userDto);
        model.addAttribute("successMessage", "Registered Succesfully");
        return "signup";
    }

    @GetMapping("/quiz")
    public String quizPage() {

        return "quiz";
    }


//    @PostMapping("/quiz/create/form")
//    public String handleQuizCreationFromForm(@ModelAttribute QuizForm quizForm,
//                                             RedirectAttributes redirectAttributes) {
//        try {
//            String category = quizForm.getCategory();
//            String title = quizForm.getTitle();
//            int numQ = quizForm.getNumQ();
//
//            System.out.println("Category from pagecontroller " + category);
//            System.out.println("numQ from pagecontroller " + numQ);
//            System.out.println("title from pagecontroller " + title);
//
//            // Construct the URL for the API call
//            String url = "http://localhost:8080/quiz/create?category=" + category + "&numQ=" + numQ + "&title=" + title;
//
//            // Make the API request
//
//            ResponseEntity<QuizCreationResponse> response =
//                    restTemplate.postForEntity(url, null, QuizCreationResponse.class);
//
//            // Check if response body is null before accessing
//            if (response.getBody() == null) {
//                throw new RuntimeException("Received null response body");
//            }
//
//            // Get quizId from the response
//            Integer quizId = response.getBody().getQuizId();
//
//            System.out.println("QuizId = " + quizId);
//
//            // Redirect to the created quiz page
//            return "redirect:/quiz/" + quizId;
//        } catch (Exception e) {
//            e.printStackTrace();
//            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while creating the quiz");
//            return "redirect:/quiz"; // Or redirect to an error page
//        }
//    }

    @PostMapping("/quiz/create/form")
    public String handleQuizCreationFromForm(@ModelAttribute QuizForm quizForm,
                                             RedirectAttributes redirectAttributes) {
        try {
            // Call the service directly — no need to construct a URL or use RestTemplate
            ResponseEntity<String> response = quizService.createQuiz(
                    quizForm.getCategory(),
                    quizForm.getLevel(),
                    quizForm.getNumQ(),
                    quizForm.getTitle()
            );

            Integer quizId = Integer.parseInt(response.getBody());
            System.out.println("QuizId = " + quizId);

            return "redirect:/quiz/" + quizId;
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while creating the quiz");
            return "redirect:/quiz";
        }
    }





    @GetMapping("/quiz/{id}")
    public String getQuizPage(@PathVariable Integer id, Model model) {
        List<QuestionWrapper> questions = quizService.getQuizQuestions(id).getBody();
        model.addAttribute("quizId", id);
        model.addAttribute("questions", questions);

        // Prepare a blank ResponseListWrapper with one Response per question
        ResponseListWrapper responseList = new ResponseListWrapper();
        List<Response> responses = new ArrayList<>();

        for (QuestionWrapper q : questions) {
            Response response = new Response();
            response.setId(q.getId()); // Set question ID
            responses.add(response);
        }

        responseList.setResponses(responses);
        model.addAttribute("responseList", responseList); // <-- Required for form binding

        return "quizpage";
    }



    @PostMapping("/submit/{id}")
    public String submitQuiz(@PathVariable Integer id, @ModelAttribute ResponseListWrapper responseList, Model model) {
        List<Response> responses = responseList.getResponses();
        Integer result = quizService.calculateResult(id, responses).getBody();
        model.addAttribute("score", result);
        return "result";
    }


}

