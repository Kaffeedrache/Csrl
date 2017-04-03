package se.lth.cs.srl.features;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import se.lth.cs.srl.corpus.Predicate;
import se.lth.cs.srl.corpus.Sentence;
import se.lth.cs.srl.corpus.Word;
import se.lth.cs.srl.features.FeatureName;
import se.lth.cs.srl.features.SingleFeature;

/**
 * Works only on pred.
 *
 * @author kesslewd
 *
 */
public class SentimentWordFeature extends SingleFeature {
   private static final long serialVersionUID = 1L;
   /*
    * Look up in sentiment dictionary
    */
   public static final String POSITIVE = "P";
   public static final String NEGATIVE = "N";
   public static final String NONE = "X";

   private boolean usePOS = false;

   HashMap<String, String> sentimentDictionary;
   private String dataFile = "data/subjclueslen1-HLTEMNLP05.tff";

   public SentimentWordFeature(String POSPrefix, boolean usePOS) {
      super(usePOS ? FeatureName.SentimentWordPOS : FeatureName.SentimentWord, true, false, POSPrefix);
      this.usePOS = usePOS;
      indices.put(POSITIVE, Integer.valueOf(1));
      indices.put(NEGATIVE, Integer.valueOf(2));
      indices.put(NONE, Integer.valueOf(3));
      indexcounter = 4;
      readDictionaryFile();
   }

   private void readDictionaryFile() {
      sentimentDictionary = new HashMap<String, String>();
      InputStream is;
      try {
         is = new FileInputStream(dataFile);
         BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF8"));
         String line;
         while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");

            String word = "";
            String polarity = NONE;
            List<String> pos = new ArrayList<String>();
            for (String part : parts) {
               String[] subparts = part.split("=");
               if (subparts[0].equals("word1")) { // lemma
                  word = subparts[1];
               } else if (subparts[0].equals("priorpolarity")) { // polarity
                  if (subparts[1].equals("positive"))
                     polarity = POSITIVE;
                  else if (subparts[1].equals("negative"))
                     polarity = NEGATIVE;
                  else
                     polarity = NONE;
               } else if (subparts[0].equals("pos1")) { // POS
                  // Convert names to our POS tags
                  if (subparts[1].equals("noun")) {
                     pos.add("NN");
                     pos.add("NNS");
                     pos.add("NP");
                     pos.add("NPS");
                  } else if (subparts[1].equals("adj")) {
                     pos.add("JJ");
                     pos.add("JJR");
                     pos.add("JJS");
                  } else if (subparts[1].equals("verb")) {
                     pos.add("VB");
                     pos.add("VBG");
                     pos.add("VBP");
                     pos.add("VBZ");
                     pos.add("VBN");
                  } else if (subparts[1].equals("adverb")) {
                     pos.add("RB");
                     pos.add("RBR");
                     pos.add("RBS");
                  } else if (subparts[1].equals("anypos")) {
                     pos.add("NN");
                     pos.add("NNS");
                     pos.add("NP");
                     pos.add("NPS");
                     pos.add("JJ");
                     pos.add("JJR");
                     pos.add("JJS");
                     pos.add("VB");
                     pos.add("VBG");
                     pos.add("VBP");
                     pos.add("VBZ");
                     pos.add("VBN");
                     pos.add("RB");
                     pos.add("RBR");
                     pos.add("RBS");
                  }
               }

            }

            for (String onepos : pos) {
               if (!polarity.equals(NONE))
                  if (usePOS) {
                     sentimentDictionary.put(word + "_" + onepos, polarity);
                  } else {
                     sentimentDictionary.put(word, polarity);
                  }
            }

         }
         reader.close();

      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (UnsupportedEncodingException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   @Override
   public void addFeatures(Sentence s, Collection<Integer> indices,
         int predIndex, int argIndex, Integer offset, boolean allWords) {
      indices.add(indexOf(getFeatureString(s, predIndex, argIndex)) + offset);
   }

   @Override
   public void addFeatures(Collection<Integer> indices, Predicate pred,
         Word arg, Integer offset, boolean allWords) {
      indices.add(indexOf(getFeatureString(pred, arg)) + offset);
   }

   @Override
   protected void performFeatureExtraction(Sentence s, boolean allWords) {
      // Do nothing, the map is constructed in the constructor.
   }

   @Override
   public String getFeatureString(Sentence s, int predIndex, int argIndex) {
      Word word = s.get(predIndex);
      String sentiment = null;
      if (usePOS) {
         sentiment = sentimentDictionary.get(word.getLemma() + "_" + word.getPOS());
      } else {
         sentiment = sentimentDictionary.get(word.getLemma());
      }

      if (sentiment != null) {
         return sentiment;
      } else {
         return NONE;
      }
   }

   @Override
   public String getFeatureString(Predicate pred, Word arg) {
      return getFeatureString(pred.getMySentence(), pred.getIdx(), 0);
   }

}
