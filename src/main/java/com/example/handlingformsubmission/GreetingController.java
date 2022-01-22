package com.example.handlingformsubmission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GreetingController {

    @Autowired
    private DynamoDBEnhanced dynamoDBEnhanced;

    @Autowired
    private PublishTextSMS msg;

    @GetMapping("/")
    public String greetingForm(final Model model) {
        model.addAttribute("greeting", new Greeting());
        return "greeting";
    }

    @PostMapping("/greeting")
    public String greetingSubmit(@ModelAttribute final Greeting greeting) {
        System.out.println(greeting);

        // Persist submitted data into a DynamoDB table
        dynamoDBEnhanced.injectDynamoItem(greeting);

        // Send mobile notification
        msg.sendMessage(greeting.getId());

        return "result";
    }
}
