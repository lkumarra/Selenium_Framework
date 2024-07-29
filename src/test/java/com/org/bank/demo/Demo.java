package com.org.bank.demo;


import java.util.*;

public class Demo {
    public static void main(String[] args) {
        List<String> words = new ArrayList<>();
        words.add("cat");
        words.add("dog");
        words.add("god");
        words.add("tac");
        List<String> queries = new ArrayList<>();
        queries.add("act");
        queries.add("god");
        queries.add("dog");
        List<HashMap<String, List<String>>> anagramList = getAnagramList(words, queries);
        System.out.println(anagramList);
    }

    public static void findThirdLargestElement(int[] inputArray) {

    }

    public static List<HashMap<String, List<String>>> getAnagramList(List<String> words, List<String> queries) {
        List<HashMap<String, List<String>>> outputList = new ArrayList<>();
        for (String query : queries) {
            HashMap<String, List<String>> anagramMap = new HashMap<>();
            List<String> anagramList = new ArrayList<>();
            char[] queryArray = query.toCharArray();
            Arrays.sort(queryArray);
            String sortedQuery = new String(queryArray);
            for (String word : words) {
                char[] wordArray = word.toCharArray();
                Arrays.sort(wordArray);
                String sortedWord = new String(wordArray);
                if (sortedQuery.equals(sortedWord)) {
                    anagramList.add(word);
                }
                Collections.sort(anagramList);
            }
            anagramMap.put(query, anagramList);
            outputList.add(anagramMap);
        }
        return outputList;
    }


}
