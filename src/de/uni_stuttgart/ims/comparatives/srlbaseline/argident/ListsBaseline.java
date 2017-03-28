// (c) Wiltrud Kessler
// 09.09.2013
// This code is distributed under a Creative Commons
// Attribution-NonCommercial-ShareAlike 3.0 Unported license
// http://creativecommons.org/licenses/by-nc-sa/3.0/


package de.uni_stuttgart.ims.comparatives.srlbaseline.argident;


import de.uni_stuttgart.ims.nlpbase.nlp.SRLSentence;


public class ListsBaseline extends ArgumentIdentification {


   private AspectListBaseline aspectsBL;
   private EntityListBaseline entitiesBL;


   public ListsBaseline (String aspectsFileName, String entityFileName) {
      aspectsBL = new AspectListBaseline (aspectsFileName);
      entitiesBL = new EntityListBaseline(entityFileName);
   }


   public void identifyArguments (SRLSentence sentence) {
      aspectsBL.identifyArguments(sentence);
      entitiesBL.identifyArguments(sentence);
   }
}
