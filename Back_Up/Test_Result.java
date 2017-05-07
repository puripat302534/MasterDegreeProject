/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import weka.attributeSelection.CorrelationAttributeEval;
import weka.attributeSelection.GainRatioAttributeEval;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.OneRAttributeEval;
import weka.attributeSelection.Ranker;
import weka.attributeSelection.ReliefFAttributeEval;
import weka.attributeSelection.SymmetricalUncertAttributeEval;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.Logistic;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
  
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.attribute.Remove;
 
/**
 *
 * @author samy
 */
public class Test_Result {
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        BufferedReader br = null;
        int numFolds = 10;
       // br = new BufferedReader(new FileReader("/Users/Puripat.T/Desktop/General/Master Degree/Tesis/Experiment/Lymphoma96x4026+9classes.arff"));
       //br = new BufferedReader(new FileReader("/Users/Puripat.T/Desktop/General/Master Degree/Tesis/Experiment/uci-20070111-lung-cancer.arff"));
       br = new BufferedReader(new FileReader("/Users/Puripat.T/Desktop/General/Master Degree/Tesis/Experiment/Result/result.arff"));
        Instances trainData = new Instances(br);
        trainData.setClassIndex(trainData.numAttributes() - 1);
        br.close();
        
        
        //--Setting Classification--//
        //OneRAttributeEval t = new OneRAttributeEval();
        //InfoGainAttributeEval t = new InfoGainAttributeEval();
    	//GainRatioAttributeEval t = new GainRatioAttributeEval();
    	//SymmetricalUncertAttributeEval t = new SymmetricalUncertAttributeEval();
    	//ReliefFAttributeEval t = new ReliefFAttributeEval();
    	//CorrelationAttributeEval t = new CorrelationAttributeEval();
    	
        //AttributeSelection filter = new AttributeSelection();
        //Ranker ranker = new Ranker();
        Remove remove = new Remove();
        int i = trainData.classIndex();
        String strI = "" + i;
		String S = "401-"+strI;
		remove.setAttributeIndices(S);
		remove.setInvertSelection(false);
        Evaluation evaluation ;
        
        //IBk rf = new IBk(); //0.8211
        //J48 rf = new J48();  //0.8192
        //NaiveBayes rf = new NaiveBayes(); //0.9167
         //RandomForest rf = new RandomForest();
        //Logistic rf = new Logistic();//0.9703
         SMO rf = new SMO();//0.9548
         
      //--Ending Setting Classification--//
      	
       //-----Tuning Parameter-------//
        //t.setSeed(101); //101-20F->Lung	
        //t.setSeed(835); //835-10F->Lung	
        //t.setSeed(26); //26-5F->Lung
        //t.setSeed(1321);//1321-25F->Lym
        
        
        //t.setNumNeighbours(8);//2,4,5,7-->ReliefF
        //t.setSeed(1);
        
        //-----Tuning Parameter--end---//
 
		evaluation = new Evaluation(trainData);
		
		remove.setInputFormat(trainData);
		trainData = Filter.useFilter(trainData, remove);
           
     //   rf.buildClassifier(trainData);
        evaluation.crossValidateModel(rf, trainData, numFolds, new Random(1));
         
        System.out.println(evaluation.toSummaryString("Results\n======\n", true));
        System.out.println(evaluation.toClassDetailsString());
        //System.out.println(evaluation.weightedAreaUnderROC());
 
    }
    

}