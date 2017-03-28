// (c) Wiltrud Kessler
// 04.04.2013
// This code is distributed under a Creative Commons
// Attribution-NonCommercial-ShareAlike 3.0 Unported license
// http://creativecommons.org/licenses/by-nc-sa/3.0/


package de.uni_stuttgart.ims.comparatives.srlbaseline.argident;


import java.util.List;

import de.uni_stuttgart.ims.nlpbase.nlp.ArgumentType;
import de.uni_stuttgart.ims.util.CommonComparatives;
import de.uni_stuttgart.ims.nlpbase.nlp.POSUtils;
import de.uni_stuttgart.ims.nlpbase.nlp.SRLSentence;
import de.uni_stuttgart.ims.nlpbase.nlp.Word;

/**
 * Arguments are identified based on heuristics.
 *
 * @author kesslewd
 *
 */
public class HeuristicArgumentsBaseline extends ArgumentIdentification {

   /**
    * If a predicate is JJR or JJS, mark the same word also as aspect?
    * (this is done in the JDPA data)
    */
   public boolean aspectSameWordForJJRJJS = false;


   /**
    * Use heuristics to identify the arguments:
    * - identify entity 1 as first noun or pronoun BEFORE predicate
    * - identify entity 2 as first noun or pronoun AFTER predicate
    * - identify aspect as the word itself in case of a comparative adjective
    * - in case of adjecitve/determiner/adverb/preposition use parent if this is a noun or adjective
    */
   public void identifyArguments (SRLSentence sentence) {

      List<Word> preds = sentence.getPredicates();

      for (Word pred : preds) {

         int index = pred.getId();
         Word argumentCandidate;
         int i;

         // identify entity 1 as first noun or pronoun BEFORE predicate
         i = index-1;
         while ((argumentCandidate = sentence.getWord(i)) != null) {
            if (POSUtils.isNounPOS(argumentCandidate.getPOS()) || POSUtils.isPronounPOS(argumentCandidate.getPOS())) {
               sentence.addArgument(pred, argumentCandidate, ArgumentType.entity1);
               break;
            }
            i--;
         }

         // identify entity 2 as first noun or pronoun AFTER predicate
         i = index+1;
         while ((argumentCandidate = sentence.getWord(i)) != null) {
            if (POSUtils.isNounPOS(argumentCandidate.getPOS()) || POSUtils.isPronounPOS(argumentCandidate.getPOS())) {
               sentence.addArgument(pred, argumentCandidate, ArgumentType.entity2);
               break;
            }
            i++;
         }

         // identify aspect as the word itself in case of a comparative adjective (comp adverb is harmful)
         // in case of adjecitve/determiner/adverb/preposition use parent if this is a noun or adjective
         String predPos = pred.getPOS();
         Word predParent = pred.getHead();
         if (CommonComparatives.isComparativePOS(predPos) && POSUtils.isAdjectivePOS(predPos)) {
            if (aspectSameWordForJJRJJS )
               sentence.addArgument(pred, pred, ArgumentType.aspect);
         } else  {
            if (POSUtils.isAdjectivePOS(predPos) || POSUtils.isDeterminerPOS(predPos) ||
                  POSUtils.isAdverbPOS(predPos) || POSUtils.isPrepositionPOS(predPos)) {
                     sentence.addArgument(pred, predParent, ArgumentType.aspect);
            }

         }

      }

   }

}
