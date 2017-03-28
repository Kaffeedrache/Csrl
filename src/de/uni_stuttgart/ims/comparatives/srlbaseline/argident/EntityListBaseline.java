// (c) Wiltrud Kessler
// 9.09.2013
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
import de.uni_stuttgart.ims.util.HeadFinder;
import de.uni_stuttgart.ims.nlpbase.nlp.POSUtils;
import de.uni_stuttgart.ims.nlpbase.nlp.SRLSentence;
import de.uni_stuttgart.ims.nlpbase.nlp.Word;


/**
 * Entiteis are identified based on a list.
 *
 * @author kesslewd
 *
 */
public class EntityListBaseline extends ArgumentIdentification {


   /**
    * The entity phrases, each entry is a String[] of tokens
    */
   private HashMap<String, String[]> entities;


   /**
    * Read a list of entities from the file.
    */
   public EntityListBaseline(String entitiesFileName) {

      entities = new HashMap<String, String[]>();

      DataInputStream fstream;
      try {
         fstream = new DataInputStream(new FileInputStream(entitiesFileName));
         BufferedReader br = new BufferedReader(new InputStreamReader(fstream, Charset.forName("UTF-8")));
         String line = "";
         int lineno = 0;
         while ((line = br.readLine()) != null) {
            lineno++;
            String[] parts = line.split(" ");
            entities.put(line.trim(), parts);
         }
         System.out.println("Read " + lineno + " entities from file.");
         br.close();
      } catch (FileNotFoundException e1) {
         e1.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }

   }


   /**
    * Potential entities are phrases from the list and pronouns.
    * The closest potential entity for each predicate are marked,
    * entity1 is before, entity2 after the preicate.
    */
   public void identifyArguments (SRLSentence sentence) {

      List<Word> foundEntities = new ArrayList<Word>();

      // Add entities from the loaded lists
      for (Word word : sentence.getWordList()) {
         int index = word.getId();
         for (String[] aspect : entities.values()) {
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
               foundEntities.add(HeadFinder.getArgumentHead(sentence, foundit));
            }
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

         // Set the closest entitites after as entity2
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
