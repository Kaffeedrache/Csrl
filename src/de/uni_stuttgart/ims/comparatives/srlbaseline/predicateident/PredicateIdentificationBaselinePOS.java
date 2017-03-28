// (c) Wiltrud Kessler
// 12.03.2013
// This code is distributed under a Creative Commons
// Attribution-NonCommercial-ShareAlike 3.0 Unported license
// http://creativecommons.org/licenses/by-nc-sa/3.0/

package de.uni_stuttgart.ims.comparatives.srlbaseline.predicateident;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.ims.util.CommonComparatives;
import de.uni_stuttgart.ims.nlpbase.nlp.PredicateType;
import de.uni_stuttgart.ims.nlpbase.nlp.PredicateDirection;
import de.uni_stuttgart.ims.nlpbase.nlp.SRLSentence;
import de.uni_stuttgart.ims.nlpbase.nlp.Word;

/**
 * Any word with a comparative part-of-speech is a predicate.
 *
 * @author kesslewd
 *
 */
public class PredicateIdentificationBaselinePOS extends PredicateIdentification {

   /**
    * Return all words that have a comparative part-of-speech.
    * Returns an empty list if none is found.
    */
   public List<Word> identifyPredicates (SRLSentence sentence) {

      ArrayList<Word> predicates = new ArrayList<Word>();

      for (Word word : sentence.getWordList()) {
         if (CommonComparatives.isComparativePOS(word.getPOS())) {
            sentence.addPredicate(word, PredicateType.undefined, PredicateDirection.UNDEFINED);
            predicates.add(word);
         }
      }

      return predicates;

   }

}
