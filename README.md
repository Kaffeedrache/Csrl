# CSRL

Comparison Semantic Role Labeler.
Code for baselines, added features.
Code for paper (Kessler and Kuhn, 2013), (Kessler and Kuhn, 2014), (Kessler, 2014).

__WARNING__: This is research code, it was not written with anybody else in mind nor with the goal of applying it "in real life". So it is hacky and may not be usable at all for you.



## Prerequisites

To run the code you will need:

- [CompBase](https://github.com/WiltrudKessler/CompBase): 
   Basic data structures, in-/output and just general helpful stuff for my project.
- [MATE SRL system](https://code.google.com/archive/p/mate-tools/)
   Which does the actual classification.


## Structure

- `src`: Code for the baselines.
- `doc`: Javadoc for `src`.
- `data`: Keywords for baselines.
- `features`: MATE SRL feature definition files and code for newly implemented features.


## Usage

### Running the baselines

Run a baseline with the following command:

```{bash}
java -cp bin:../NLPBase/bin de.uni_stuttgart.ims.comparatives.srlbaseline.MainRunBaselines -p $BLPI -a $BLAI $FILENAME
```

where 
- `$FILENAME` is the file with parsed sentences in CoNLL syntax that you want to annotate. 
-  `$BLPI` is the name of the baseline used for **predicate identification**. It may be one of the following:
   - `nopi`: Don't do predicate identification, i.e., use gold predicate identification that has to be present in the input file.
   - `pos`: use only comparative parts of speech.
   - `key`: use comparative parts of speech and keywords.
-  `$BLAI` is the name of the baseline used for **argument identification and classification**. It may be one of the following:
   - `heu`: Use heuristics (as explained in the paper, short version: entity1 is the first (pro)noun before the predicate; entity2 the first (pro)noun after the predicate; if a word is an adjective and modified by an predicate adverb, it is an aspect).
   - `asp`: Use a word list to find aspects and identify entities by capitalization.
   - `list`: Use word lists for both aspects and entities.
   
The output will be in `$FILENAME.bl`, also in CoNLL syntax.


### Training and running CSRL

For training a model, run the following (uses `$CORPUS`, creates `$MODEL`).
:

```bash
LANG="eng" # Language
MEM="2g" #Memory for the JVM, might need to be increased for large corpora.
CP="lib/liblinear-1.51-with-deps.jar:lib/anna.jar:lib/opennlp-tools-1.4.3.jar:bin" # Classpath
JVM_ARGS="-cp $CP -Xmx$MEM"

CORPUS=data.parsed.annotated.train # input file data for training
MODEL=data.model # newly learned model
#LLBINARY="-llbinary /home/anders/liblinear-1.6/train" #Path to locally compiled liblinear. Uncomment this and correct the path if you have it. This will make training models faster (30-40%)

java $JVM_ARGS se.lth.cs.srl.Learn $LANG $CORPUS $MODEL $LLBINARY -dontDeleteTrainData -skipUnknownPredicates"
```

For using a trained model, run the following (uses `$INPUT` and `$MODEL`, creates `$OUTPUT`).

```bash
LANG="eng" # Language
MEM="2g" #Memory for the JVM, might need to be increased for large corpora.
CP="lib/liblinear-1.51-with-deps.jar:lib/anna.jar:lib/opennlp-tools-1.4.3.jar:bin" # Classpath
JVM_ARGS="-cp $CP -Xmx$MEM"

CORPUS=data.parsed.test # input file data for testing
MODEL=data.model # used model
OUTPUT=data.parsed.test.out # output file for annotated test data

java $JVM_ARGS se.lth.cs.srl.Parse $LANG $INPUT $MODEL $NOPI $OUTPUT
```


### Features

The folder `features/featurefiles` contains the feature definition files for the MATE SRL system. Some of the features are a new implementation, the corresponding code is in the folder `features/src`.




## Licence and References

(c) Wiltrud Kessler

This code is distributed under a Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported license
[http://creativecommons.org/licenses/by-nc-sa/3.0/](http://creativecommons.org/licenses/by-nc-sa/3.0/)

Please cite:
Wiltrud Kessler and Jonas Kuhn (2013)
Detection of Product Comparisons - How Far Does an Out-of-the-box Semantic Role Labeling System Take You?
In Proceedings of the 2013 Conference on Empirical Methods in Natural Language Processing (EMNLP 2013), Seattle, USA, 18.-21. Oktober, 2013, pages 1892-1897


If you are interested in further reading, check:

Wiltrud Kessler and Jonas Kuhn (2014)
Detecting Comparative Sentiment Expressions - A Case Study in Annotation Design Decisions.
In Proceedings of 12. Konferenz zur Verarbeitung Natürlicher Sprache (KONVENS 2014). Hildesheim, Germany, 8.-10. October 2014, pages 165-170.

Wiltrud Kessler (2014)
Improving the Detection of Comparison Arguments in Product Reviews. 44. Jahrestagung der Gesellschaft für Informatik e.V. (INFORMATIK 2014), Stuttgart, Germany, 22.-26. September 2014, 
In Lecture Notes in Informatics (232), pages 2311-2316.

