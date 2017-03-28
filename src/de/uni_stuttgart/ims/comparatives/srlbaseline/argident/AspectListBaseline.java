// (c) Wiltrud Kessler
// 30.08.2013
// This code is distributed under a Creative Commons
// Attribution-NonCommercial-ShareAlike 3.0 Unported license
// http://creativecommons.org/licenses/by-nc-sa/3.0/


package de.uni_stuttgart.ims.comparatives.srlbaseline.argident;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.uni_stuttgart.ims.nlpbase.nlp.ArgumentType;
import de.uni_stuttgart.ims.util.CommonComparatives;
import de.uni_stuttgart.ims.util.HeadFinder;
import de.uni_stuttgart.ims.nlpbase.nlp.SRLSentence;
import de.uni_stuttgart.ims.nlpbase.nlp.Word;


/**
 * Aspects are identified based on a list.
 *
 * @author kesslewd
 *
 */
public class AspectListBaseline extends ArgumentIdentification {

   /**
    * The aspect phrases, each entry is a String[] of tokens
    */
   private HashMap<String, String[]>  aspects;


   /**
    * Read a list of aspects from the file.
    */
   public AspectListBaseline(String aspectsFileName) {

      aspects = new HashMap<String, String[]> ();

      DataInputStream fstream;
      try {
         fstream = new DataInputStream(new FileInputStream(aspectsFileName));
         BufferedReader br = new BufferedReader(new InputStreamReader(fstream, Charset.forName("UTF-8")));
         String line = "";
         int lineno = 0;
         while ((line = br.readLine()) != null) {
            lineno++;
            String[] parts = line.split(" ");
            aspects.put(line.trim(), parts);
         }
         System.out.println("Read " + lineno + " aspects from file.");
         br.close();
      } catch (FileNotFoundException e1) {
         e1.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }



   /**
    * Potential aspects are phrases from the list and predicates with comparative POS.
    * The closest potential aspect for each predicate is marked.
    */
   public void identifyArguments (SRLSentence sentence) {

      List<Word> preds = sentence.getPredicates();
      List<Word> foundAspects = new ArrayList<Word>();

      // Add aspects from the loaded lists
      for (Word word : sentence.getWordList()) {
         int index = word.getId();
         for (String[] aspect : aspects.values()) {
            List<Word> foundit = new ArrayList<Word>();
            for (int i=0; i<aspect.length; i++) {
               Word thisword = sentence.getWord(index+i);
               if (thisword == null) {
                  foundit = null;
                  break;
               }
               if (!thisword.getForm().toLowerCase().equals(aspect[i].toLowerCase())) {
                  foundit = null;
                  break;
               }
               foundit.add(thisword);
            }
            if (foundit != null) {
               foundAspects.add(HeadFinder.getArgumentHead(sentence, foundit));
            }
         }
      }

      // Add comparative words themselves (hack for JDPA data)
      for (Word pred : preds) {
         String predPos = pred.getPOS();
         if (CommonComparatives.isComparativePOS(predPos)) {
            sentence.addArgument(pred, pred, ArgumentType.aspect);
         }
      }

      if (foundAspects.size() == 0)
         return;



      for (Word pred : preds) {

         // Set the closest aspect as aspect
         int minDist = 0;
         Word preferredCandidate = null;
         for (Word argumentCandidate : foundAspects) {
            int theDistance = java.lang.Math.abs(sentence.compareSequence(pred, argumentCandidate));
            if (preferredCandidate == null || theDistance < minDist) {
               minDist = theDistance;
               preferredCandidate = argumentCandidate;
            }
         }

         if (preferredCandidate != null) {
            sentence.addArgument(pred, preferredCandidate, ArgumentType.aspect);
         }

      }


   }
}
