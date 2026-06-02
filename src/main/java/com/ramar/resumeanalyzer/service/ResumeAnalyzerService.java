package com.ramar.resumeanalyzer.service;

import com.ramar.resumeanalyzer.entity.ResumeAnalysis;
import com.ramar.resumeanalyzer.repository.ResumeAnalysisRepository;
import com.ramar.resumeanalyzer.util.KeywordUtils;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ResumeAnalyzerService {

    private final ResumeAnalysisRepository repository;

    public ResumeAnalyzerService(ResumeAnalysisRepository repository) {
        this.repository = repository;
    }

    public ResumeAnalysis analyze(MultipartFile file, String jobDescription) throws IOException {
        String resumeText = extractTextFromPdf(file);

        Set<String> resumeKeywords = KeywordUtils.extractKeywords(resumeText);
        Set<String> jobKeywords = KeywordUtils.extractKeywords(jobDescription);

        Set<String> matched = new LinkedHashSet<>(resumeKeywords);
        matched.retainAll(jobKeywords);

        Set<String> missing = new LinkedHashSet<>(jobKeywords);
        missing.removeAll(resumeKeywords);

        int score = calculateScore(matched.size(), jobKeywords.size());
        List<String> questions = KeywordUtils.generateInterviewQuestions(matched);

        ResumeAnalysis analysis = new ResumeAnalysis();
        analysis.setResumeFileName(file.getOriginalFilename());
        analysis.setResumeText(resumeText);
        analysis.setJobDescription(jobDescription);
        analysis.setAtsScore(score);
        analysis.setMatchedKeywords(String.join(", ", matched));
        analysis.setMissingKeywords(String.join(", ", missing));
        analysis.setInterviewQuestions(String.join("\n", questions));
        analysis.setAnalyzedAt(LocalDateTime.now());

        return repository.save(analysis);
    }

    public List<ResumeAnalysis> getAllHistory() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(ResumeAnalysis::getAnalyzedAt).reversed())
                .collect(Collectors.toList());
    }

    public ResumeAnalysis getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Analysis not found"));
    }

    private int calculateScore(int matchedCount, int totalJobKeywords) {
        if (totalJobKeywords == 0) return 0;
        double score = ((double) matchedCount / totalJobKeywords) * 100;
        return (int) Math.min(100, Math.round(score));
    }

    private String extractTextFromPdf(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        File tempFile = File.createTempFile("resume-", ".pdf");
        file.transferTo(tempFile);

        try (PDDocument document = Loader.loadPDF(tempFile)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        } finally {
            Files.deleteIfExists(tempFile.toPath());
        }
    }
}