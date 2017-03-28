// (c) Wiltrud Kessler
// 02.09.2013
// This code is distributed under a Creative Commons
// Attribution-NonCommercial-ShareAlike 3.0 Unported license
// http://creativecommons.org/licenses/by-nc-sa/3.0/


package de.uni_stuttgart.ims.comparatives.srlbaseline.argident;


import de.uni_stuttgart.ims.nlpbase.nlp.SRLSentence;


/**
 * Entities are identified based on capitalization and pronouns,
 * aspect are identified based on a list.
 *
 * @author kesslewd
 *
 */
public class AspectsAndCapitalizationBaseline extends ArgumentIdentification {

   private AspectListBaseline aspectsBL;
   private CapitalizationBaseline entitiesBL;


   public AspectsAndCapitalizationBaseline (String aspectsFileName) {
      aspectsBL = new AspectListBaseline (aspectsFileName);
      entitiesBL = new CapitalizationBaseline();
   }


   public void identifyArguments (SRLSentence sentence) {
      aspectsBL.identifyArguments(sentence);
      entitiesBL.identifyArguments(sentence);
   }
}
