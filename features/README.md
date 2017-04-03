# Features


## Feature files used for the experiments

The files with the prefix `pi` are used for predicate identification, the  files with the prefix `ai` are used for argument identification and classification.

- `_min`: Minimal feature set (context-independent baseline).
- `_syntax`: Minimal feature set plus syntax information.
- `_windowX`: Minimal feature set plus information from the context words in a symmetric window the size of `X` tokens to the left and right.
- `_syntaxwindowX`: Minimal feature set plus both `_syntax` and `_windowX` combined.
- `_sentiment`: The `_syntax` feature set plus one feature for sentiment polarity.
- `_clusters`: The `_syntax` feature set plus one feature for Brown cluster information.
- `_predtype`: The `_syntax` feature set plus one feature to model the type of the predicate (this type is assumed to be where in SRL the word sense would be).

The other features are transformations of the input data.


## Newly implemented features

- Window context features: Code added to `se.lth.cs.srl.features.WordExtractor`
- Sentiment feature: See file `src/SentimentWordFeature.java`


## Code snippets to add

Code-basis for my changes was version `srl-20130917`. Feel free to do this in a more intelligent way.

### Add to class `se.lth.cs.srl.features.FeatureName`

Add the new values to the enumeration.

```java
LeftContextWord, LeftContextPOS,
LeftContext2Word, LeftContext2POS,
LeftContext3Word, LeftContext3POS,
LeftContext4Word, LeftContext4POS,
LeftContext5Word, LeftContext5POS,
LeftContext6Word, LeftContext6POS,
LeftContext7Word, LeftContext7POS,
LeftContext8Word, LeftContext8POS,
LeftContext9Word, LeftContext9POS,
LeftContext10Word, LeftContext10POS,
RightContextWord, RightContextPOS,
RightContext2Word, RightContext2POS,
RightContext3Word, RightContext3POS,
RightContext4Word, RightContext4POS,
RightContext5Word, RightContext5POS,
RightContext6Word, RightContext6POS,
RightContext7Word, RightContext7POS,
RightContext8Word, RightContext8POS,
RightContext9Word, RightContext9POS,
RightContext10Word, RightContext10POS,

PredLeftContextWord, PredLeftContextPOS,
PredLeftContext2Word, PredLeftContext2POS,
PredLeftContext3Word, PredLeftContext3POS,
PredLeftContext4Word, PredLeftContext4POS,
PredLeftContext5Word, PredLeftContext5POS,
PredLeftContext6Word, PredLeftContext6POS,
PredLeftContext7Word, PredLeftContext7POS,
PredLeftContext8Word, PredLeftContext8POS,
PredLeftContext9Word, PredLeftContext9POS,
PredLeftContext10Word, PredLeftContext10POS,
PredRightContextWord, PredRightContextPOS,
PredRightContext2Word, PredRightContext2POS,
PredRightContext3Word, PredRightContext3POS,
PredRightContext4Word, PredRightContext4POS,
PredRightContext5Word, PredRightContext5POS,
PredRightContext6Word, PredRightContext6POS,
PredRightContext7Word, PredRightContext7POS,
PredRightContext8Word, PredRightContext8POS,
PredRightContext9Word, PredRightContext9POS,
PredRightContext10Word, PredRightContext10POS,

SentimentWordPOS,
```




### Add to class `se.lth.cs.srl.features.FeatureGenerator`

In the `switch` statement in `public Feature getFeature(...)`

```java
case PredLeftContextWord:  ret=new PredDependentAttrFeature(fn,WordData.Form,TargetWord.PredLeftContext,includeAllWords,POSPrefix); break; 
case PredLeftContextPOS:   ret=new PredDependentAttrFeature(fn,WordData.POS,TargetWord.PredLeftContext,includeAllWords,POSPrefix); break; 
case PredLeftContext2Word:  ret=new PredDependentAttrFeature(fn,WordData.Form,TargetWord.PredLeftContext2,includeAllWords,POSPrefix); break; 
case PredLeftContext2POS:   ret=new PredDependentAttrFeature(fn,WordData.POS,TargetWord.PredLeftContext2,includeAllWords,POSPrefix); break; 
case PredLeftContext3Word:  ret=new PredDependentAttrFeature(fn,WordData.Form,TargetWord.PredLeftContext3,includeAllWords,POSPrefix); break; 
case PredLeftContext3POS:   ret=new PredDependentAttrFeature(fn,WordData.POS,TargetWord.PredLeftContext3,includeAllWords,POSPrefix); break; 
case PredLeftContext4Word:  ret=new PredDependentAttrFeature(fn,WordData.Form,TargetWord.PredLeftContext4,includeAllWords,POSPrefix); break; 
case PredLeftContext4POS:   ret=new PredDependentAttrFeature(fn,WordData.POS,TargetWord.PredLeftContext4,includeAllWords,POSPrefix); break; 
case PredLeftContext5Word:  ret=new PredDependentAttrFeature(fn,WordData.Form,TargetWord.PredLeftContext5,includeAllWords,POSPrefix); break; 
case PredLeftContext5POS:   ret=new PredDependentAttrFeature(fn,WordData.POS,TargetWord.PredLeftContext5,includeAllWords,POSPrefix); break; 
case PredLeftContext6Word:  ret=new PredDependentAttrFeature(fn,WordData.Form,TargetWord.PredLeftContext6,includeAllWords,POSPrefix); break; 
case PredLeftContext6POS:   ret=new PredDependentAttrFeature(fn,WordData.POS,TargetWord.PredLeftContext6,includeAllWords,POSPrefix); break; 
case PredLeftContext7Word:  ret=new PredDependentAttrFeature(fn,WordData.Form,TargetWord.PredLeftContext7,includeAllWords,POSPrefix); break; 
case PredLeftContext7POS:   ret=new PredDependentAttrFeature(fn,WordData.POS,TargetWord.PredLeftContext7,includeAllWords,POSPrefix); break; 
case PredLeftContext8Word:  ret=new PredDependentAttrFeature(fn,WordData.Form,TargetWord.PredLeftContext8,includeAllWords,POSPrefix); break; 
case PredLeftContext8POS:   ret=new PredDependentAttrFeature(fn,WordData.POS,TargetWord.PredLeftContext8,includeAllWords,POSPrefix); break; 
case PredLeftContext9Word:  ret=new PredDependentAttrFeature(fn,WordData.Form,TargetWord.PredLeftContext9,includeAllWords,POSPrefix); break; 
case PredLeftContext9POS:   ret=new PredDependentAttrFeature(fn,WordData.POS,TargetWord.PredLeftContext9,includeAllWords,POSPrefix); break; 
case PredLeftContext10Word:  ret=new PredDependentAttrFeature(fn,WordData.Form,TargetWord.PredLeftContext10,includeAllWords,POSPrefix); break; 
case PredLeftContext10POS:   ret=new PredDependentAttrFeature(fn,WordData.POS,TargetWord.PredLeftContext10,includeAllWords,POSPrefix); break; 

case PredRightContextWord:  ret=new PredDependentAttrFeature(fn,WordData.Form,TargetWord.PredRightContext,includeAllWords,POSPrefix); break; 
case PredRightContextPOS:   ret=new PredDependentAttrFeature(fn,WordData.POS,TargetWord.PredRightContext,includeAllWords,POSPrefix); break; 
case PredRightContext2Word:  ret=new PredDependentAttrFeature(fn,WordData.Form,TargetWord.PredRightContext2,includeAllWords,POSPrefix); break; 
case PredRightContext2POS:   ret=new PredDependentAttrFeature(fn,WordData.POS,TargetWord.PredRightContext2,includeAllWords,POSPrefix); break; 
case PredRightContext3Word:  ret=new PredDependentAttrFeature(fn,WordData.Form,TargetWord.PredRightContext3,includeAllWords,POSPrefix); break; 
case PredRightContext3POS:   ret=new PredDependentAttrFeature(fn,WordData.POS,TargetWord.PredRightContext3,includeAllWords,POSPrefix); break; 
case PredRightContext4Word:  ret=new PredDependentAttrFeature(fn,WordData.Form,TargetWord.PredRightContext4,includeAllWords,POSPrefix); break; 
case PredRightContext4POS:   ret=new PredDependentAttrFeature(fn,WordData.POS,TargetWord.PredRightContext4,includeAllWords,POSPrefix); break; 
case PredRightContext5Word:  ret=new PredDependentAttrFeature(fn,WordData.Form,TargetWord.PredRightContext5,includeAllWords,POSPrefix); break; 
case PredRightContext5POS:   ret=new PredDependentAttrFeature(fn,WordData.POS,TargetWord.PredRightContext5,includeAllWords,POSPrefix); break; 
case PredRightContext6Word:  ret=new PredDependentAttrFeature(fn,WordData.Form,TargetWord.PredRightContext6,includeAllWords,POSPrefix); break; 
case PredRightContext6POS:   ret=new PredDependentAttrFeature(fn,WordData.POS,TargetWord.PredRightContext6,includeAllWords,POSPrefix); break; 
case PredRightContext7Word:  ret=new PredDependentAttrFeature(fn,WordData.Form,TargetWord.PredRightContext7,includeAllWords,POSPrefix); break; 
case PredRightContext7POS:   ret=new PredDependentAttrFeature(fn,WordData.POS,TargetWord.PredRightContext7,includeAllWords,POSPrefix); break; 
case PredRightContext8Word:  ret=new PredDependentAttrFeature(fn,WordData.Form,TargetWord.PredRightContext8,includeAllWords,POSPrefix); break; 
case PredRightContext8POS:   ret=new PredDependentAttrFeature(fn,WordData.POS,TargetWord.PredRightContext8,includeAllWords,POSPrefix); break; 
case PredRightContext9Word:  ret=new PredDependentAttrFeature(fn,WordData.Form,TargetWord.PredRightContext9,includeAllWords,POSPrefix); break; 
case PredRightContext9POS:   ret=new PredDependentAttrFeature(fn,WordData.POS,TargetWord.PredRightContext9,includeAllWords,POSPrefix); break; 
case PredRightContext10Word:  ret=new PredDependentAttrFeature(fn,WordData.Form,TargetWord.PredRightContext10,includeAllWords,POSPrefix); break; 
case PredRightContext10POS:   ret=new PredDependentAttrFeature(fn,WordData.POS,TargetWord.PredRightContext10,includeAllWords,POSPrefix); break; 

case LeftContextWord:  ret=new ArgDependentAttrFeature(fn,WordData.Form,TargetWord.LeftContext,POSPrefix); break; 
case LeftContextPOS:   ret=new ArgDependentAttrFeature(fn,WordData.POS,TargetWord.LeftContext,POSPrefix); break; 
case LeftContext2Word:  ret=new ArgDependentAttrFeature(fn,WordData.Form,TargetWord.LeftContext2,POSPrefix); break; 
case LeftContext2POS:   ret=new ArgDependentAttrFeature(fn,WordData.POS,TargetWord.LeftContext2,POSPrefix); break; 
case LeftContext3Word:  ret=new ArgDependentAttrFeature(fn,WordData.Form,TargetWord.LeftContext3,POSPrefix); break; 
case LeftContext3POS:   ret=new ArgDependentAttrFeature(fn,WordData.POS,TargetWord.LeftContext3,POSPrefix); break; 
case LeftContext4Word:  ret=new ArgDependentAttrFeature(fn,WordData.Form,TargetWord.LeftContext4,POSPrefix); break; 
case LeftContext4POS:   ret=new ArgDependentAttrFeature(fn,WordData.POS,TargetWord.LeftContext4,POSPrefix); break; 
case LeftContext5Word:  ret=new ArgDependentAttrFeature(fn,WordData.Form,TargetWord.LeftContext5,POSPrefix); break; 
case LeftContext5POS:   ret=new ArgDependentAttrFeature(fn,WordData.POS,TargetWord.LeftContext5,POSPrefix); break; 
case LeftContext6Word:  ret=new ArgDependentAttrFeature(fn,WordData.Form,TargetWord.LeftContext6,POSPrefix); break; 
case LeftContext6POS:   ret=new ArgDependentAttrFeature(fn,WordData.POS,TargetWord.LeftContext6,POSPrefix); break; 
case LeftContext7Word:  ret=new ArgDependentAttrFeature(fn,WordData.Form,TargetWord.LeftContext7,POSPrefix); break; 
case LeftContext7POS:   ret=new ArgDependentAttrFeature(fn,WordData.POS,TargetWord.LeftContext7,POSPrefix); break; 
case LeftContext8Word:  ret=new ArgDependentAttrFeature(fn,WordData.Form,TargetWord.LeftContext8,POSPrefix); break; 
case LeftContext8POS:   ret=new ArgDependentAttrFeature(fn,WordData.POS,TargetWord.LeftContext8,POSPrefix); break; 
case LeftContext9Word:  ret=new ArgDependentAttrFeature(fn,WordData.Form,TargetWord.LeftContext9,POSPrefix); break; 
case LeftContext9POS:   ret=new ArgDependentAttrFeature(fn,WordData.POS,TargetWord.LeftContext9,POSPrefix); break; 
case LeftContext10Word:  ret=new ArgDependentAttrFeature(fn,WordData.Form,TargetWord.LeftContext10,POSPrefix); break; 
case LeftContext10POS:   ret=new ArgDependentAttrFeature(fn,WordData.POS,TargetWord.LeftContext10,POSPrefix); break; 

case RightContextWord:  ret=new ArgDependentAttrFeature(fn,WordData.Form,TargetWord.RightContext,POSPrefix); break; 
case RightContextPOS:   ret=new ArgDependentAttrFeature(fn,WordData.POS,TargetWord.RightContext,POSPrefix); break; 
case RightContext2Word:  ret=new ArgDependentAttrFeature(fn,WordData.Form,TargetWord.RightContext2,POSPrefix); break; 
case RightContext2POS:   ret=new ArgDependentAttrFeature(fn,WordData.POS,TargetWord.RightContext2,POSPrefix); break; 
case RightContext3Word:  ret=new ArgDependentAttrFeature(fn,WordData.Form,TargetWord.RightContext3,POSPrefix); break; 
case RightContext3POS:   ret=new ArgDependentAttrFeature(fn,WordData.POS,TargetWord.RightContext3,POSPrefix); break; 
case RightContext4Word:  ret=new ArgDependentAttrFeature(fn,WordData.Form,TargetWord.RightContext4,POSPrefix); break; 
case RightContext4POS:   ret=new ArgDependentAttrFeature(fn,WordData.POS,TargetWord.RightContext4,POSPrefix); break; 
case RightContext5Word:  ret=new ArgDependentAttrFeature(fn,WordData.Form,TargetWord.RightContext5,POSPrefix); break; 
case RightContext5POS:   ret=new ArgDependentAttrFeature(fn,WordData.POS,TargetWord.RightContext5,POSPrefix); break; 
case RightContext6Word:  ret=new ArgDependentAttrFeature(fn,WordData.Form,TargetWord.RightContext6,POSPrefix); break; 
case RightContext6POS:   ret=new ArgDependentAttrFeature(fn,WordData.POS,TargetWord.RightContext6,POSPrefix); break; 
case RightContext7Word:  ret=new ArgDependentAttrFeature(fn,WordData.Form,TargetWord.RightContext7,POSPrefix); break; 
case RightContext7POS:   ret=new ArgDependentAttrFeature(fn,WordData.POS,TargetWord.RightContext7,POSPrefix); break; 
case RightContext8Word:  ret=new ArgDependentAttrFeature(fn,WordData.Form,TargetWord.RightContext8,POSPrefix); break; 
case RightContext8POS:   ret=new ArgDependentAttrFeature(fn,WordData.POS,TargetWord.RightContext8,POSPrefix); break; 
case RightContext9Word:  ret=new ArgDependentAttrFeature(fn,WordData.Form,TargetWord.RightContext9,POSPrefix); break; 
case RightContext9POS:   ret=new ArgDependentAttrFeature(fn,WordData.POS,TargetWord.RightContext9,POSPrefix); break; 
case RightContext10Word:  ret=new ArgDependentAttrFeature(fn,WordData.Form,TargetWord.RightContext10,POSPrefix); break; 
case RightContext10POS:   ret=new ArgDependentAttrFeature(fn,WordData.POS,TargetWord.RightContext10,POSPrefix); break; 

case SentimentWordPOS:   ret=new SentimentWordFeature(POSPrefix, true); break;
```


### Add to class `se.lth.cs.srl.features.TargetWord`

Add the new values to the enumeration:

```java
LeftContext,   //This is the word to the left of the (potential) argument 
LeftContext2,   //This is the word two to the left of the (potential) argument 
LeftContext3,   //This is the word 3 to the left of the (potential) argument 
LeftContext4,   //This is the word 3 to the left of the (potential) argument 
LeftContext5,   //This is the word 3 to the left of the (potential) argument 
LeftContext6,   //This is the word 3 to the left of the (potential) argument 
LeftContext7,   //This is the word 3 to the left of the (potential) argument 
LeftContext8,   //This is the word 3 to the left of the (potential) argument 
LeftContext9,   //This is the word 3 to the left of the (potential) argument 
LeftContext10,   //This is the word 3 to the left of the (potential) argument 

RightContext,   //This is the word to the right of the (potential) argument 
RightContext2,   //This is the word two to the right of the (potential) argument 
RightContext3,   //This is the word 3 to the right of the (potential) argument 
RightContext4,   //This is the word 3 to the left of the (potential) argument 
RightContext5,   //This is the word 3 to the left of the (potential) argument 
RightContext6,   //This is the word 3 to the left of the (potential) argument 
RightContext7,   //This is the word 3 to the left of the (potential) argument 
RightContext8,   //This is the word 3 to the left of the (potential) argument 
RightContext9,   //This is the word 3 to the left of the (potential) argument 
RightContext10,   //This is the word 3 to the left of the (potential) argument 

PredLeftContext,   //This is the word to the left of the (potential) predicate 
PredLeftContext2,   //This is the word two to the left of the (potential) predicate 
PredLeftContext3,   //This is the word 3 to the left of the (potential) predicate 
PredLeftContext4,   //This is the word 3 to the left of the (potential) predicate 
PredLeftContext5,   //This is the word 3 to the left of the (potential) predicate 
PredLeftContext6,   //This is the word 3 to the left of the (potential) predicate 
PredLeftContext7,   //This is the word 3 to the left of the (potential) predicate 
PredLeftContext8,   //This is the word 3 to the left of the (potential) predicate 
PredLeftContext9,   //This is the word 3 to the left of the (potential) predicate 
PredLeftContext10,   //This is the word 3 to the left of the (potential) predicate 

PredRightContext,   //This is the word to the right of the (potential) predicate 
PredRightContext2,   //This is the word two to the right of the (potential) predicate 
PredRightContext3,   //This is the word 3 to the right of the (potential) predicate 
PredRightContext4,   //This is the word 3 to the right of the (potential) predicate 
PredRightContext5,   //This is the word 3 to the right of the (potential) predicate 
PredRightContext6,   //This is the word 3 to the right of the (potential) predicate 
PredRightContext7,   //This is the word 3 to the right of the (potential) predicate 
PredRightContext8,   //This is the word 3 to the right of the (potential) predicate 
PredRightContext9,   //This is the word 3 to the right of the (potential) predicate 
PredRightContext10,   //This is the word 3 to the right of the (potential) predicate 
```



### Add to class `se.lth.cs.srl.features.WordExtractor`

In the `switch` statement in `public static WordExtractor getExtractor(TargetWord tw)`:

```java
case LeftContext: return new Context(false, false, 1); 
case LeftContext2: return new Context(false, false, 2); 
case LeftContext3: return new Context(false, false, 3); 
case LeftContext4: return new Context(false, false, 4); 
case LeftContext5: return new Context(false, false, 5); 
case LeftContext6: return new Context(false, false, 6); 
case LeftContext7: return new Context(false, false, 7); 
case LeftContext8: return new Context(false, false, 8); 
case LeftContext9: return new Context(false, false, 9); 
case LeftContext10: return new Context(false, false, 10); 
case RightContext: return new Context(true, false, 1); 
case RightContext2: return new Context(true, false, 2); 
case RightContext3: return new Context(true, false, 3); 
case RightContext4: return new Context(true, false, 4); 
case RightContext5: return new Context(true, false, 5); 
case RightContext6: return new Context(true, false, 6); 
case RightContext7: return new Context(true, false, 7); 
case RightContext8: return new Context(true, false, 8); 
case RightContext9: return new Context(true, false, 9); 
case RightContext10: return new Context(true, false, 10); 
case PredLeftContext: return new Context(false, true, 1); 
case PredLeftContext2: return new Context(false, true, 2); 
case PredLeftContext3: return new Context(false, true, 3); 
case PredLeftContext4: return new Context(false, true, 4); 
case PredLeftContext5: return new Context(false, true, 5); 
case PredLeftContext6: return new Context(false, true, 6); 
case PredLeftContext7: return new Context(false, true, 7); 
case PredLeftContext8: return new Context(false, true, 8); 
case PredLeftContext9: return new Context(false, true, 9); 
case PredLeftContext10: return new Context(false, true, 10); 
case PredRightContext: return new Context(true, true, 1); 
case PredRightContext2: return new Context(true, true, 2); 
case PredRightContext3: return new Context(true, true, 3); 
case PredRightContext4: return new Context(true, true, 4); 
case PredRightContext5: return new Context(true, true, 5); 
case PredRightContext6: return new Context(true, true, 6); 
case PredRightContext7: return new Context(true, true, 7); 
case PredRightContext8: return new Context(true, true, 8); 
case PredRightContext9: return new Context(true, true, 9); 
case PredRightContext10: return new Context(true, true, 10); 
```

And add the class `Context` in the lower part of the file:
```java

   private static class Context extends WordExtractor { 
      private static final long serialVersionUID = 1L;
      private boolean right;
      private boolean usePred;
      private int number;
      public Context(boolean right, boolean usePred, int number) {
         this.right = right;
         this.usePred = usePred;
         this.number = number;
      }
      public Word getWord(Sentence s, int predIndex, int argIndex) {
         int index;
         if (usePred) // take pred or arg
            index = predIndex;
         else
            index = argIndex;
         if (right)  // go to the left or right
            index = index+number;
         else 
            index = index-number;

         // check valid borders
         if (index < 1 | index >= s.size())
            return null;
         else
            return s.get(index);
      }
      public Word getWord(Word pred,Word arg){
         if (usePred)
            return getWord(pred.getMySentence(), pred.getIdx(), 0);
         else
            return getWord(arg.getMySentence(), 0, arg.getIdx());
      }
   }
```



