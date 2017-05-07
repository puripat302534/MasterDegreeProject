/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
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
    	
    	PrintWriter file = new PrintWriter("Classification_Propose_Result.txt");
    	
        BufferedReader br = null;
        int numFolds = 10;
        String range = "10-";
        
       br = new BufferedReader(new FileReader("/Users/Puripat.T/Desktop/General/Master Degree/Tesis/Experiment/Result/result.arff"));
        
       /*Instances trainData = new Instances(br);
        trainData.setClassIndex(trainData.numAttributes() - 1);
        br.close();
        
        
        Remove remove = new Remove();
        int i = trainData.classIndex();
        String strI = "" + i;
		String S = "10-"+strI;
		remove.setAttributeIndices(S);
		remove.setInvertSelection(false);
        Evaluation evaluation ;*/
         
      //--Ending Setting Classification--//
      	
        
        //-----Tuning Parameter--end---//
 
		/*evaluation = new Evaluation(trainData);
		remove.setInputFormat(trainData);
		trainData = Filter.useFilter(trainData, remove);
        evaluation.crossValidateModel(rf, trainData, numFolds, new Random(1));*/
       	Instances trainData = new Instances(br);
       	br.close();	
       	
          for(int j=1;j<=5;j++){
        	  
        	  Instances temp = trainData;
        	  
        	  temp.setClassIndex(temp.numAttributes() - 1);
              
              Remove remove = new Remove();
              int i = temp.classIndex();
              String strI = "" + i;
      		  String S = range+strI;
      		  remove.setAttributeIndices(S);
      		  remove.setInvertSelection(false);
              Evaluation evaluation ;
              
              evaluation = new Evaluation(temp);  			
  			  remove.setInputFormat(temp);
  			  temp = Filter.useFilter(temp, remove);
        	
        	switch(j){
        		case 1 :
        			IBk KNN = new IBk();
         
        	        evaluation.crossValidateModel(KNN, temp, numFolds, new Random(1));
        	         
        	        //System.out.println(evaluation.toSummaryString("Results:K-NN \n======\n", true));
        	        System.out.print(" Results:K-NN ======\n");
        	        System.out.println("Average AUC: "+evaluation.weightedAreaUnderROC()+"\n");
        	        System.out.println("Average F-Measure: "+evaluation.weightedFMeasure()+"\n");
        	        System.out.println("Average Recall: "+evaluation.weightedRecall()+"\n");
        	        System.out.println("Average Precision: "+evaluation.weightedPrecision()+"\n");
        	        System.out.println("===========================\n");
        	        
        	        file.print(" Results:K-NN ======\n");
        	        file.println("Average AUC: "+evaluation.weightedAreaUnderROC()+"\n");
        	        file.println("Average F-Measure: "+evaluation.weightedFMeasure()+"\n");
        	        file.println("Average Recall: "+evaluation.weightedRecall()+"\n");
        	        file.println("Average Precision: "+evaluation.weightedPrecision()+"\n");
        	        file.println("===========================\n");
        	        
        				break;
        		case 2 :
        			J48 C4_5 = new J48();
 
        	        evaluation.crossValidateModel(C4_5, temp, numFolds, new Random(1));
        	         
        	        System.out.print(" Results:C4.5 ======\n");
        	        System.out.println("Average AUC: "+evaluation.weightedAreaUnderROC()+"\n");
        	        System.out.println("Average F-Measure: "+evaluation.weightedFMeasure()+"\n");
        	        System.out.println("Average Recall: "+evaluation.weightedRecall()+"\n");
        	        System.out.println("Average Precision: "+evaluation.weightedPrecision()+"\n");
        	        System.out.println("===========================\n");
        	        
        	        file.print(" Results:C4.5 ======\n");
        	        file.println("Average AUC: "+evaluation.weightedAreaUnderROC()+"\n");
        	        file.println("Average F-Measure: "+evaluation.weightedFMeasure()+"\n");
        	        file.println("Average Recall: "+evaluation.weightedRecall()+"\n");
        	        file.println("Average Precision: "+evaluation.weightedPrecision()+"\n");
        	        file.println("===========================\n");
        	        
        	        //System.out.println(evaluation.toClassDetailsString());
        				break;
        		case 3 :
        			NaiveBayes NB = new NaiveBayes();
  
        	        evaluation.crossValidateModel(NB, temp, numFolds, new Random(1));
        	         
        	        //System.out.println(evaluation.toSummaryString("Results:Naive Bays\n======\n", true));
        	        System.out.print(" Results:Naive Bays ======\n");
        	        System.out.println("Average AUC: "+evaluation.weightedAreaUnderROC()+"\n");
        	        System.out.println("Average F-Measure: "+evaluation.weightedFMeasure()+"\n");
        	        System.out.println("Average Recall: "+evaluation.weightedRecall()+"\n");
        	        System.out.println("Average Precision: "+evaluation.weightedPrecision()+"\n");
        	        System.out.println("=================================\n");
        	        
        	        file.print(" Results:Naive Bays ======\n");
        	        file.println("Average AUC: "+evaluation.weightedAreaUnderROC()+"\n");
        	        file.println("Average F-Measure: "+evaluation.weightedFMeasure()+"\n");
        	        file.println("Average Recall: "+evaluation.weightedRecall()+"\n");
        	        file.println("Average Precision: "+evaluation.weightedPrecision()+"\n");
        	        file.println("=================================\n");
        	        //System.out.println(evaluation.toClassDetailsString());
        				break;
        		case 4 :
        			RandomForest RF = new RandomForest();

        	        evaluation.crossValidateModel(RF, temp, numFolds, new Random(1));
        	         
        	        //System.out.println(evaluation.toSummaryString("Results:Random Forest\n======\n", true));
        	        System.out.print(" Results:Random Forest ======\n");
        	        System.out.println("Average AUC: "+evaluation.weightedAreaUnderROC()+"\n");
        	        System.out.println("Average F-Measure: "+evaluation.weightedFMeasure()+"\n");
        	        System.out.println("Average Recall: "+evaluation.weightedRecall()+"\n");
        	        System.out.println("Average Precision: "+evaluation.weightedPrecision()+"\n");
        	        System.out.println("===================================\n");
        	        
        	        file.print(" Results:Random Forest ======\n");
        	        file.println("Average AUC: "+evaluation.weightedAreaUnderROC()+"\n");
        	        file.println("Average F-Measure: "+evaluation.weightedFMeasure()+"\n");
        	        file.println("Average Recall: "+evaluation.weightedRecall()+"\n");
        	        file.println("Average Precision: "+evaluation.weightedPrecision()+"\n");
        	        file.println("===================================\n");
        	        //System.out.println(evaluation.toClassDetailsString());
        				break;
        		case 5 :
        			SMO SVM = new SMO();

        	        evaluation.crossValidateModel(SVM, temp, numFolds, new Random(1));
        	         
        	        //System.out.println(evaluation.toSummaryString("Results: Support Vector..\n======\n", true));
        	        System.out.print(" Results:Support Vector.. ======\n");
        	        System.out.println("Average AUC: "+evaluation.weightedAreaUnderROC()+"\n");
        	        System.out.println("Average F-Measure: "+evaluation.weightedFMeasure()+"\n");
        	        System.out.println("Average Recall: "+evaluation.weightedRecall()+"\n");
        	        System.out.println("Average Precision: "+evaluation.weightedPrecision()+"\n");
        	        System.out.println("======================================\n");
        	        
        	        file.print(" Results:Support Vector.. ======\n");
        	        file.println("Average AUC: "+evaluation.weightedAreaUnderROC()+"\n");
        	        file.println("Average F-Measure: "+evaluation.weightedFMeasure()+"\n");
        	        file.println("Average Recall: "+evaluation.weightedRecall()+"\n");
        	        file.println("Average Precision: "+evaluation.weightedPrecision()+"\n");
        	        file.println("======================================\n");
        	        //System.out.println(evaluation.toClassDetailsString());
        				break;

        	}
        }
       
          file.close();
 
    }
    

}