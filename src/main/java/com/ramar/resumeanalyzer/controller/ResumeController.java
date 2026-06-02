package com.ramar.resumeanalyzer.controller;

import com.ramar.resumeanalyzer.entity.ResumeAnalysis;
import com.ramar.resumeanalyzer.service.ResumeAnalyzerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ResumeController {

    private final ResumeAnalyzerService service;

    public ResumeController(ResumeAnalyzerService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/analyze")
    public String analyze(@RequestParam("resume") MultipartFile resume,
                          @RequestParam("jobDescription") String jobDescription,
                          Model model) {
        try {
            ResumeAnalysis analysis = service.analyze(resume, jobDescription);
            model.addAttribute("analysis", analysis);
            model.addAttribute("matchedList",
                    analysis.getMatchedKeywords().isBlank() ? new String[]{} : analysis.getMatchedKeywords().split(",\\s*"));
            model.addAttribute("missingList",
                    analysis.getMissingKeywords().isBlank() ? new String[]{} : analysis.getMissingKeywords().split(",\\s*"));
            model.addAttribute("questionsList", analysis.getInterviewQuestions().split("\n"));
            return "result";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "index";
        }
    }

    @GetMapping("/history")
    public String history(Model model) {
        model.addAttribute("historyList", service.getAllHistory());
        return "history";
    }
}