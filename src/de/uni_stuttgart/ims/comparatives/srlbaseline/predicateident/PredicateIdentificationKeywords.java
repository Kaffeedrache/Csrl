// (c) Wiltrud Kessler
// 12.03.2013
// This code is distributed under a Creative Commons
// Attribution-NonCommercial-ShareAlike 3.0 Unported license
// http://creativecommons.org/licenses/by-nc-sa/3.0/

package de.uni_stuttgart.ims.comparatives.srlbaseline.predicateident;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.ims.util.CommonComparatives;
import de.uni_stuttgart.ims.nlpbase.nlp.PredicateType;
import de.uni_stuttgart.ims.nlpbase.nlp.PredicateDirection;
import de.uni_stuttgart.ims.nlpbase.nlp.SRLSentence;
import de.uni_stuttgart.ims.nlpbase.nlp.Word;

/**
 * Any word that is on the list of keywords is a predicate,
 * also any word that has a comparative part of speech.
 *
 * @author kesslewd
 *
 */
public class PredicateIdentificationKeywords extends PredicateIdentification {

   /**
    * The keywords (or better keyphrases, each entry is a String[] of tokens)
    */
   private List<String[]> keywords;

   /**
    *
    * Any word that is on the list of keywords is a predicate,
    * also any word that has a comparative part of speech.
    *
    * @param keywordsfilename
    */
   public PredicateIdentificationKeywords(String keywordsfilename) {
      keywords = new ArrayList<String[]>();
      try{
         DataInputStream in = new DataInputStream(new FileInputStream(keywordsfilename));
         BufferedReader br = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
         String strLine;
         while ((strLine = br.readLine()) != null)   {
            strLine = strLine.trim();
            strLine = strLine.toLowerCase();
            String[] tokens = strLine.split(" ");
            keywords.add(tokens);
         }
         in.close();
      } catch (Exception e){
         System.err.println("Error: " + e.getMessage());
      }

   }


   /**
    * Treat phrases in the keyword list, we have to find all tokens.
    */
   private boolean isPartOfKeyphrase ( List<Word> words, int i) {
      for (String[] keyphrase : keywords) {
         for (int j=0;j<keyphrase.length; j++) {
            if ((i+j) >= words.size()) {
               break; // abort if we have reached end of sentence
            }
            if (keyphrase[j].equals(words.get(i+j).getForm())) {
            } else {
               break; // try next keyphrase
            }
            if (j == keyphrase.length-1) { // have we exited early?
               return true;
            }
         }
      }
      return false;

   }


   /**
    * Return all words that are on the list or have a comparative part-of-speech.
    * Returns an empty list if none is found.
    */
   public List<Word> identifyPredicates (SRLSentence sentence) {

      ArrayList<Word> predicates = new ArrayList<Word>();
      List<Word> words = sentence.getWordList();

      for (int i=0; i<words.size(); i++) {
         Word word = words.get(i);
         if (CommonComparatives.isComparativePOS(word.getPOS()) || isPartOfKeyphrase(words,i)) {
            sentence.addPredicate(word, PredicateType.undefined, PredicateDirection.UNDEFINED);
            predicates.add(word);
         }
      }

      return predicates;

   }

}
