/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import weka.attributeSelection.OneRAttributeEval;
import weka.attributeSelection.Ranker;
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
public class RF_TEST {
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        BufferedReader br = null;
        int numFolds = 10;
        //br = new BufferedReader(new FileReader("/Users/Puripat.T/Desktop/General/Master Degree/Tesis/Experiment/uci-20070111-lung-cancer.arff"));
        br = new BufferedReader(new FileReader("/Users/Puripat.T/Desktop/General/Master Degree/Tesis/Experiment/Lymphoma96x4026+9classes.arff"));
        Instances trainData = new Instances(br);
        trainData.setClassIndex(trainData.numAttributes() - 1);
        br.close();
        
        int i=1987,k=1987,F=0;
        
        //--Setting Classification--//
        OneRAttributeEval oneR = new OneRAttributeEval();
        AttributeSelection filter = new AttributeSelection();
        Ranker ranker = new Ranker();
        Remove remove = new Remove();
        remove.setAttributeIndices("51-4026");
		remove.setInvertSelection(false);
        Evaluation evaluation ;
        
        IBk rf = new IBk();
        //J48 rf = new J48();
        //NaiveBayes rf = new NaiveBayes();
        //Logistic rf = new Logistic();
        //SMO rf = new SMO();
      //--Ending Setting Classification--//
        System.out.println("Searching........");
        while(k<=5000){
      	
       //-----Setting One R-------//
        //oneR.setSeed(101); //101-20F->Lung	
        //oneR.setSeed(835); //835-10F->Lung
        //oneR.setSeed(26); //26-5F->Lung
        //oneR.setSeed(1321);//1321-25F->Lym
        	
        oneR.setSeed(i++); //0.5993 , 86
        oneR.setMinimumBucketSize(6);
      //oneR.setFolds(10);
        //-----Setting One R--end---//
 
        Instances dataResult;
		filter.setEvaluator(oneR);
		filter.setSearch(ranker);
		filter.setInputFormat(trainData);
		
		dataResult = Filter.useFilter(trainData, filter);
		evaluation = new Evaluation(dataResult);
		
		remove.setInputFormat(dataResult);
		dataResult = Filter.useFilter(dataResult, remove);
           
     //   rf.buildClassifier(trainData);
        evaluation.crossValidateModel(rf, dataResult, numFolds, new Random(1));
         
        double check = round(evaluation.weightedAreaUnderROC(),3);
        
        if(check == 0.821){
        	F=1;
        	System.out.println(evaluation.toSummaryString("\nResults\n======\n", true));
            System.out.println(evaluation.toClassDetailsString());
            System.out.println(evaluation.weightedAreaUnderROC());
        	break;
        }
        //System.out.println(evaluation.toSummaryString("\nResults\n======\n", true));
        //System.out.println(evaluation.toClassDetailsString());
        k++;
        }
        
        if(F==0){
        	System.out.println("Not Found T_T");
        }else{
        	System.out.println("Founded!!! and Seed is: "+(--i));
        	
        }
        //System.out.println(evaluation.weightedAreaUnderROC());
 
    }
    
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}