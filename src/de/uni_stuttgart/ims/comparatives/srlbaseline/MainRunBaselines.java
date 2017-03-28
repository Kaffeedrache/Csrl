// (c) Wiltrud Kessler
// 12.03.2013
// This code is distributed under a Creative Commons
// Attribution-NonCommercial-ShareAlike 3.0 Unported license
// http://creativecommons.org/licenses/by-nc-sa/3.0/


package de.uni_stuttgart.ims.comparatives.srlbaseline;

import de.uni_stuttgart.ims.comparatives.srlbaseline.argident.ArgumentIdentification;
import de.uni_stuttgart.ims.comparatives.srlbaseline.argident.AspectsAndCapitalizationBaseline;
import de.uni_stuttgart.ims.comparatives.srlbaseline.argident.HeuristicArgumentsBaseline;
import de.uni_stuttgart.ims.comparatives.srlbaseline.argident.ListsBaseline;
import de.uni_stuttgart.ims.comparatives.srlbaseline.predicateident.PredicateIdentification;
import de.uni_stuttgart.ims.comparatives.srlbaseline.predicateident.PredicateIdentificationBaselinePOS;
import de.uni_stuttgart.ims.comparatives.srlbaseline.predicateident.PredicateIdentificationKeywords;
import de.uni_stuttgart.ims.nlpbase.io.ParseReaderCoNLL;
import de.uni_stuttgart.ims.nlpbase.io.ParseWriterCoNLL;
import de.uni_stuttgart.ims.nlpbase.nlp.SRLSentence;
import de.uni_stuttgart.ims.nlpbase.nlp.Word;
import de.uni_stuttgart.ims.nlpbase.nlp.ArgumentType;
import de.uni_stuttgart.ims.util.Fileutils;




public class MainRunBaselines {


   /**
    * Jindal and Liu keywords.
    */
   static String keywordsFilename = "data/keywords.txt";


   /**
    * Run a baseline.
    * Usage: _0 -c markerP_markerA -p (pos | key | nopi) -a (heu | heu2 | asp -f aspectsFileName | list -f aspectsfileName -e entityFileName) inputFilename
    * Choose 1 Predicate Identification: POS baseline (-pos), Keyphrases baseline (-key), gold predicates (-nopi)
    * Choose 1 Argument Identification: heuristics (-heu), aspects list and capitalization (-asp), aspect and entity lists (-list)
    */
   public static void main(String[] args) {

      boolean debug = false;

      ParseReaderCoNLL parseReaderInput = null;
      ParseWriterCoNLL parseWriterBaseline = null;
      Boolean identifyPredicates = true;
      PredicateIdentification piBaseline = null;
      ArgumentIdentification aiBaseline = null;

      try {
         if (args.length < 3) {
            System.out.println("ERROR, not enough arguments.");
            System.out.println("usage: _0 -c markerP_markerA -p (pos | key | nopi) -a (heu | heu2 | asp -f aspectsFileName | list -f aspectsfileName -e entityFileName) inputFilename");
            System.out.println("Choose 1 Predicate Identification: POS baseline (-pos), Keyphrases baseline (-key), gold predicates (-nopi)");
            System.out.println("Choose 1 Argument Identification: heuristics (-heu), aspects list and capitalization (-asp), aspect and entity lists (-list)");
            System.exit(1);
         }

         String inputFilename = args[args.length-1];

         // Open input parsed sentences file
         parseReaderInput = new ParseReaderCoNLL(inputFilename);
         parseReaderInput.openFile();

         // Open output parsed sentences file for simple baseline
         parseWriterBaseline = new ParseWriterCoNLL(inputFilename + ".bl");
         parseWriterBaseline.openFile();

         // Create systems to compare
         String piBaselineType = null;
         String aiBaselineType = null;
         String aiFile = null;
         String eiFile = null;
         for (int i=0; i<args.length-1; i++) {
            if (args[i].equals("-p")) {
               piBaselineType = args[i+1];
            }
            if (args[i].equals("-a")) {
               aiBaselineType = args[i+1];
            }
            if (args[i].equals("-f")) {
               aiFile = args[i+1];
            }
            if (args[i].equals("-e")) {
               eiFile = args[i+1];
            }
            if (args[i].equals("-c")) {
               String[] markers = args[i+1].split(":");
               ArgumentType.setArgumentMarker(markers[1]);
            }
         }

         piBaseline = getPredicateBaseline(piBaselineType);
         identifyPredicates = !(piBaseline == null);
         aiBaseline = getArgumentBaseline(aiBaselineType, aiFile, eiFile);


         if (identifyPredicates && piBaseline == null) {
            Fileutils.closeSilently(parseReaderInput);
            Fileutils.closeSilently(parseWriterBaseline);
            throw new Exception("You need to declare a predicate identification baseline!");
         }

         if (aiBaseline == null) {

            Fileutils.closeSilently(parseReaderInput);
            Fileutils.closeSilently(parseWriterBaseline);
            throw new Exception("You need to declare an aspect identification baseline!");
         }


      } catch (Exception e) {
         System.err.println("ERROR in initialization: " + e.getMessage());
         e.printStackTrace();
         System.exit(1);
      }




      // Bookkeeping
      SRLSentence sentence = new SRLSentence();
      sentence.addWord(new Word ("bla")); // just to get into first iteration...

      // Do it!
      while (!sentence.isEmpty()) {

         if (identifyPredicates) {
            sentence = parseReaderInput.readParseOnlyDeps();
         } else {
            sentence = parseReaderInput.readParseSRLOnlyPreds();
         }

         if (sentence.isEmpty())
            break;


         if (identifyPredicates) {
            piBaseline.identifyPredicates(sentence);
         }

         aiBaseline.identifyArguments(sentence);

         parseWriterBaseline.writeParse(sentence);

         if (debug) {
            System.out.println("===========");
            sentence.printSRLTree();
         }


      }


      // Finished
      //System.out.println("Extracted " + sentenceNo + " sentences.");
      Fileutils.closeSilently(parseReaderInput);
      Fileutils.closeSilently(parseWriterBaseline);
      //System.out.println("done.");


   }



   /**
    * Map shortcut to prediciate baseline.
    */
   private static PredicateIdentification getPredicateBaseline(String pitype) throws Exception {

      if (pitype.equals("nopi"))
         return null;

      if (pitype.equals("pos"))
         return new PredicateIdentificationBaselinePOS();

      if (pitype.equals("key"))
         return new PredicateIdentificationKeywords(keywordsFilename);

      throw new Exception(pitype + " is no valid predicate identification baseline!");
   }


   /**
    * Map shortcut to argument baseline.
    */
   private static ArgumentIdentification getArgumentBaseline(String aitype, String aifile, String eifile) throws Exception {

      if (aitype.equals("heu")) {
         HeuristicArgumentsBaseline bl =  new HeuristicArgumentsBaseline();
         bl.aspectSameWordForJJRJJS = true;
         return bl;
      }

      if (aitype.equals("heu2")) {
         HeuristicArgumentsBaseline bl =  new HeuristicArgumentsBaseline();
         bl.aspectSameWordForJJRJJS = false;
         return bl;
      }

      if (aitype.equals("asp")) {
         if (aifile != null)
            return new AspectsAndCapitalizationBaseline(aifile);
         else
            throw new Exception("You must define an aspect file for the aspect baseline!");
      }

      if (aitype.equals("list")) {
         if (aifile != null && eifile != null)
            return new ListsBaseline(aifile,eifile);
         else
            throw new Exception("You must define an aspect file and an entity file for the lists baseline!");
      }

      throw new Exception(aitype + " is no valid argument identification baseline!");
   }



}
