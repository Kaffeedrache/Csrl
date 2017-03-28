// (c) Wiltrud Kessler
// 12.03.2013
// This code is distributed under a Creative Commons
// Attribution-NonCommercial-ShareAlike 3.0 Unported license 
// http://creativecommons.org/licenses/by-nc-sa/3.0/

package de.uni_stuttgart.ims.comparatives.srlbaseline.predicateident;

import java.util.List;

import de.uni_stuttgart.ims.nlpbase.nlp.SRLSentence;
import de.uni_stuttgart.ims.nlpbase.nlp.Word;

public abstract class PredicateIdentification {

   public abstract List<Word> identifyPredicates (SRLSentence sentence);
   

}
