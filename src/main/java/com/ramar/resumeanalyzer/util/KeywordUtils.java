package com.ramar.resumeanalyzer.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class KeywordUtils {

    private static final Set<String> STOP_WORDS = Set.of(
            "a", "an", "the", "and", "or", "to", "of", "in", "for", "on", "with", "by", "is", "are",
            "was", "were", "be", "been", "this", "that", "it", "as", "at", "from", "into", "your",
            "you", "we", "our", "their", "they", "i", "me", "my", "will", "can", "should", "may",
            "have", "has", "had", "not", "but", "about", "using", "use"
    );

    private static final Pattern NON_WORD = Pattern.compile("[^a-zA-Z0-9+#. ]");

    public static Set<String> extractKeywords(String text) {
        if (text == null || text.isBlank()) return Collections.emptySet();

        String cleaned = NON_WORD.matcher(text.toLowerCase()).replaceAll(" ");
        return Arrays.stream(cleaned.split("\\s+"))
                .filter(word -> word.length() > 2)
                .filter(word -> !STOP_WORDS.contains(word))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public static List<String> generateInterviewQuestions(Set<String> matchedKeywords) {
        List<String> questions = new ArrayList<>();
        for (String keyword : matchedKeywords.stream().limit(8).toList()) {
            questions.add("Explain your experience with " + keyword + ".");
            questions.add("How have you used " + keyword + " in a project?");
        }
        if (questions.isEmpty()) {
            questions.add("Tell me about yourself.");
            questions.add("Describe your strongest technical project.");
            questions.add("What are your key strengths?");
        }
        return questions;
    }
}