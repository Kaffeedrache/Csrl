// (c) Wiltrud Kessler
// 02.09.2013
// This code is distributed under a Creative Commons
// Attribution-NonCommercial-ShareAlike 3.0 Unported license
// http://creativecommons.org/licenses/by-nc-sa/3.0/


package de.uni_stuttgart.ims.comparatives.srlbaseline.argident;


import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.ims.nlpbase.nlp.ArgumentType;
import de.uni_stuttgart.ims.util.HeadFinder;
import de.uni_stuttgart.ims.nlpbase.nlp.POSUtils;
import de.uni_stuttgart.ims.nlpbase.nlp.SRLSentence;
import de.uni_stuttgart.ims.nlpbase.nlp.Word;


/**
 * Entities are identified based on capitalization and pronouns.
 * The first one before the predicate is entity1, the first one after is entity2.
 * No aspects are identified.
 *
 * @author kesslewd
 *
 */
public class CapitalizationBaseline extends ArgumentIdentification {


   /**
    * Entities are upper-case sequences or pronouns.
    * Entity1 is the first entity BEFORE the predicate,
    * entity2 is the first entity AFTER the predicate.
    * No aspects are identified.
    */
   public void identifyArguments (SRLSentence sentence) {

      List<Word> foundEntities = new ArrayList<Word>();

      ArrayList<Word> upperCaseSequence = new ArrayList<Word>();

      for (Word word : sentence.getWordList()) {

         // Add uppercase sequences
         char firstLetter = word.getForm().charAt(0);
         if (word.getId() == 1) // force the first word in the sentence to be lowercase to eliminate false positives
            firstLetter = Character.toLowerCase(firstLetter);
         if (Character.isUpperCase(firstLetter) || Character.isDigit(firstLetter)) { // this word is uppercase
            upperCaseSequence.add(word);
         } else { // this word is lowercase
            if (upperCaseSequence.size() >= 2) {
               foundEntities.add(HeadFinder.getArgumentHead(sentence, upperCaseSequence));
            }
            upperCaseSequence = new ArrayList<Word>();
         }

         // Add pronouns
         if (POSUtils.isPronounPOS(word.getPOS())) {
            foundEntities.add(word);
         }

      }

      if (foundEntities.size() == 0)
         return;


      List<Word> preds = sentence.getPredicates();

      for (Word pred : preds) {

         // Set the closest entitites before as entity1
         int minDist = 0;
         Word preferredCandidate = null;
         for (Word argumentCandidate : foundEntities) {
            int theDistance = sentence.compareSequence(pred, argumentCandidate);
            if (theDistance < 0) // skip entities after the predicate
               continue;
            if (preferredCandidate == null || theDistance < minDist) {
               minDist = theDistance;
               preferredCandidate = argumentCandidate;
            }
         }
         if (preferredCandidate != null) {
            sentence.addArgument(pred, preferredCandidate, ArgumentType.entity1);
         }

         // Set the closest entitites before as entity1
         minDist = 0;
         preferredCandidate = null;
         for (Word argumentCandidate : foundEntities) {
            int theDistance = sentence.compareSequence(pred, argumentCandidate);
            if (theDistance > 0) // skip entities before the predicate
               continue;
            if (preferredCandidate == null || theDistance < minDist) {
               minDist = theDistance;
               preferredCandidate = argumentCandidate;
            }
         }
         if (preferredCandidate != null) {
            sentence.addArgument(pred, preferredCandidate, ArgumentType.entity2);
         }


      }


   }
}
