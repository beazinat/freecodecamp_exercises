package com.example.springredditclone.service;

import org.mapstruct.Context;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MailContentBuilder {

    private final TemplateEngine templateEngine;

    public String build(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        return templateEngine.process("mailTemplate", context);
    }
}
