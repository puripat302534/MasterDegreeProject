/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
import java.io.BufferedReader;
import java.io.FileOutputStream;
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

public class Test_Run {
	
	static PrintWriter file = null;
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        BufferedReader br = null;
                
        int numFolds = 10;
        //br = new BufferedReader(new FileReader("/Users/Puripat.T/Desktop/General/Master Degree/Tesis/Experiment/Lymphoma96x4026+9classes.arff"));
        
        
        //New Data to test
        //br = new BufferedReader(new FileReader("/Users/Puripat.T/Desktop/General/Master Degree/Tesis/Experiment/Data/BreastCancer/breastCancer-train.arff"));
        //br = new BufferedReader(new FileReader("/Users/Puripat.T/Desktop/General/Master Degree/Tesis/Experiment/Data/Ovarian_Cancer/Ovarian.arff"));
        //br = new BufferedReader(new FileReader("/Users/Puripat.T/Desktop/General/Master Degree/Tesis/Experiment/Data/AllCancer/GCM_Training.arff"));
        //br = new BufferedReader(new FileReader("/Users/Puripat.T/Desktop/General/Master Degree/Tesis/Experiment/Data/Leukemia/leukemia_train_38x7129.arff"));
        
        
        
       br = new BufferedReader(new FileReader("/Users/Puripat.T/Desktop/General/Master Degree/Tesis/Experiment/uci-20070111-lung-cancer.arff"));
       //br = new BufferedReader(new FileReader("/Users/Puripat.T/Desktop/General/Master Degree/Tesis/Experiment/Result/result.arff"));
        Instances trainData = new Instances(br);
        trainData.setClassIndex(trainData.numAttributes() - 1);
        br.close();
        
        file = new PrintWriter("Classification_Result.txt");
        
        //--Setting Classification--//
        String range = "11-";
        
        for(int i=1;i<=5;i++){
        	
        	switch(i){
        		case 1 :
        			
        			file.println("===Gain Ratio Ranker->Start==\n");
        			     			
        			System.out.println("===Gain Ratio Ranker->Start==\n");
        			calculateResultClassification(i,trainData,numFolds,range);
        			System.out.println("===Gain Ratio Ranker->End==\n");
        			
        			file.println("===Gain Ratio Ranker->End==\n");
        				break;
        		case 2 :
        			
        			file.println("===ReliefF Ranker->Start==\n");
        			
        			System.out.println("===ReliefF Ranker->Start==\n");
        			calculateResultClassification(i,trainData,numFolds,range);
        			System.out.println("===ReliefF Ranker->End==\n");
        			
        			file.println("===ReliefF Ranker->End==\n");
        				break;
        		case 3 :
     
        			file.println("===Symmetric Un.. Ranker->Start==\n");
        			
        			System.out.println("===Symmetric Un.. Ranker->Start==\n");
        			calculateResultClassification(i,trainData,numFolds,range);
        			System.out.println("===Symmetric Un..  Ranker->End==\n");
        			
        			file.println("===Symmetric Un..  Ranker->End==\n");
        				break;
        		case 4 :
        			
        			file.println("===One R Ranker->Start==\n");
        			
        			System.out.println("===One R Ranker->Start==\n");
        			calculateResultClassification(i,trainData,numFolds,range);
        			System.out.println("===One R Ranker->End==\n");
        			
        			file.println("===One R Ranker->End==\n");
        				break;
        		case 5 :
        			
        			file.println("===Information Gain Ranker->Start==\n");
        			
        			System.out.println("===Information Gain Ranker->Start==\n");
        			calculateResultClassification(i,trainData,numFolds,range);
        			System.out.println("===Information Gain Ranker->End==\n");
        			
        			file.println("===Information Gain Ranker->End==\n");
        				break;
        		
        	
        	}

        }
        
        file.close();
        //OneRAttributeEval t = new OneRAttributeEval();
        //InfoGainAttributeEval t = new InfoGainAttributeEval();
    	//GainRatioAttributeEval t = new GainRatioAttributeEval();
    	//SymmetricalUncertAttributeEval t = new SymmetricalUncertAttributeEval();
    	//ReliefFAttributeEval t = new ReliefFAttributeEval();
    	//CorrelationAttributeEval t = new CorrelationAttributeEval();
    	
        /*AttributeSelection filter = new AttributeSelection();
        Ranker ranker = new Ranker();
        Remove remove = new Remove();
        int i = trainData.classIndex();
        String strI = "" + i;
		String S = "401-"+strI;
		remove.setAttributeIndices(S);
		remove.setInvertSelection(false);
        Evaluation evaluation ;*/
        
        //IBk rf = new IBk(); //0.8211
        //J48 rf = new J48();  //0.8192
       // NaiveBayes rf = new NaiveBayes(); //0.9167
        //RandomForest rf = new RandomForest();
        //Logistic rf = new Logistic();//0.9703
        //SMO rf = new SMO();//0.9548
     
      //--Ending Setting Classification--//
      	
       //-----Tuning Parameter-------//
        //t.setSeed(101); //101-20F->Lung	
        //t.setSeed(835); //835-10F->Lung	
        //t.setSeed(26); //26-5F->Lung
        //t.setSeed(1321);//1321-25F->Lym
        
        
        //t.setNumNeighbours(8);//2,4,5,7-->ReliefF
        //t.setSeed(1);
        
        /*//-----Tuning Parameter--end---//
 
        Instances dataResult;
		filter.setEvaluator(t);
		filter.setSearch(ranker);
		filter.setInputFormat(trainData);
		
		dataResult = Filter.useFilter(trainData, filter);
		evaluation = new Evaluation(dataResult);
		
		remove.setInputFormat(dataResult);
		dataResult = Filter.useFilter(dataResult, remove);
           
     //   rf.buildClassifier(trainData);
        evaluation.crossValidateModel(rf, dataResult, numFolds, new Random(1));
         
        System.out.println(evaluation.toSummaryString("Results\n======\n", true));
        System.out.println(evaluation.toClassDetailsString());
        //System.out.println(evaluation.weightedAreaUnderROC());*/
 
    }
    
    public static void calculateResultClassification(int index_ranker,Instances trainData,int numFolds,String range )throws Exception{
    	 
    	GainRatioAttributeEval GR = new GainRatioAttributeEval();
         ReliefFAttributeEval RFF = new ReliefFAttributeEval();
         SymmetricalUncertAttributeEval SU = new SymmetricalUncertAttributeEval();
         OneRAttributeEval OneR = new OneRAttributeEval();
         InfoGainAttributeEval IG = new InfoGainAttributeEval();
    	
    	AttributeSelection filter = new AttributeSelection();
        Ranker ranker = new Ranker();
        Remove remove = new Remove();
        int i = trainData.classIndex();
        String strI = "" + i;
		String S = range+strI;
		remove.setAttributeIndices(S);
		remove.setInvertSelection(false);
        Evaluation evaluation ;
        
        Instances dataResult;
        
        for(int j=1;j<=5;j++){
        	
        	switch(j){
        		case 1 :
        			IBk KNN = new IBk();
        			
        			if(index_ranker == 1){
        				
        				file.print("Gain Ration->");
        				
        				System.out.print("Gain Ration->");
        				filter.setEvaluator(GR);
        	        }else if(index_ranker == 2){
        	        	
        	        	file.print("ReliefF->");
        	        	
        	        	System.out.print("ReliefF->");
        	        	filter.setEvaluator(RFF);
        	        }else if(index_ranker == 3){
        	        	
        	        	file.print("Symmetric Un...->");
        	        	
        	        	System.out.print("Symmetric Un...->");
        	        	filter.setEvaluator(SU);
        	        }else if(index_ranker == 4){
        	        	
        	        	file.print("OneR->");
        	        	
        	        	System.out.print("OneR->");
        	        	filter.setEvaluator(OneR);
        	        }else if(index_ranker == 5){
        	        	
        	        	file.print("Information Gain->");
        	        	
        	        	System.out.print("Information Gain->");
        	        	filter.setEvaluator(IG);
        	        }
        			
        			filter.setSearch(ranker);
        			filter.setInputFormat(trainData);
        			
        			dataResult = Filter.useFilter(trainData, filter);
        			evaluation = new Evaluation(dataResult);
        			
        			remove.setInputFormat(dataResult);
        			dataResult = Filter.useFilter(dataResult, remove);
        	           
        	     //   rf.buildClassifier(trainData);
        	        evaluation.crossValidateModel(KNN, dataResult, numFolds, new Random(1));
        	         
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
 
        			if(index_ranker == 1){
        				
        				file.print("Gain Ration->");
        				
        				System.out.print("Gain Ration->");
        				filter.setEvaluator(GR);
        	        }else if(index_ranker == 2){
        	        	
        	        	file.print("ReliefF->");
        	        	
        	        	System.out.print("ReliefF->");
        	        	filter.setEvaluator(RFF);
        	        }else if(index_ranker == 3){
        	        	
        	        	file.print("Symmetric Un...->");
        	        	
        	        	System.out.print("Symmetric Un...->");
        	        	filter.setEvaluator(SU);
        	        }else if(index_ranker == 4){
        	        	
        	        	file.print("OneR->");
        	        	
        	        	System.out.print("OneR->");
        	        	
        	        	//Set Seed here
        	        	//OneR.setSeed();
        	        	
        	        	filter.setEvaluator(OneR);
        	        }else if(index_ranker == 5){
        	        	
        	        	file.print("Information Gain->");
        	        	
        	        	System.out.print("Information Gain->");
        	        	filter.setEvaluator(IG);
        	        }
        			
        			filter.setSearch(ranker);
        			filter.setInputFormat(trainData);
        			
        			dataResult = Filter.useFilter(trainData, filter);
        			evaluation = new Evaluation(dataResult);
        			
        			remove.setInputFormat(dataResult);
        			dataResult = Filter.useFilter(dataResult, remove);
        	           
        	     //   rf.buildClassifier(trainData);
        	        evaluation.crossValidateModel(C4_5, dataResult, numFolds, new Random(1));
        	         
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
  
        			if(index_ranker == 1){
        				
        				file.print("Gain Ration->");
        				
        				System.out.print("Gain Ration->");
        				filter.setEvaluator(GR);
        	        }else if(index_ranker == 2){
        	        	
        	        	file.print("ReliefF->");
        	        	
        	        	System.out.print("ReliefF->");
        	        	filter.setEvaluator(RFF);
        	        }else if(index_ranker == 3){
        	        	
        	        	file.print("Symmetric Un...->");
        	        	
        	        	System.out.print("Symmetric Un...->");
        	        	filter.setEvaluator(SU);
        	        }else if(index_ranker == 4){
        	        	
        	        	file.print("OneR->");
        	        	
        	        	System.out.print("OneR->");
        	        	filter.setEvaluator(OneR);
        	        }else if(index_ranker == 5){
        	        	
        	        	file.print("Information Gain->");
        	        	
        	        	System.out.print("Information Gain->");
        	        	filter.setEvaluator(IG);
        	        }
        			
        			filter.setSearch(ranker);
        			filter.setInputFormat(trainData);
        			
        			dataResult = Filter.useFilter(trainData, filter);
        			evaluation = new Evaluation(dataResult);
        			
        			remove.setInputFormat(dataResult);
        			dataResult = Filter.useFilter(dataResult, remove);
        	           
        	     //   rf.buildClassifier(trainData);
        	        evaluation.crossValidateModel(NB, dataResult, numFolds, new Random(1));
        	         
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

        			if(index_ranker == 1){
        				
        				file.print("Gain Ration->");
        				
        				System.out.print("Gain Ration->");
        				filter.setEvaluator(GR);
        	        }else if(index_ranker == 2){
        	        	
        	        	file.print("ReliefF->");
        	        	
        	        	System.out.print("ReliefF->");
        	        	filter.setEvaluator(RFF);
        	        }else if(index_ranker == 3){
        	        	
        	        	file.print("Symmetric Un...->");
        	        	
        	        	System.out.print("Symmetric Un...->");
        	        	filter.setEvaluator(SU);
        	        }else if(index_ranker == 4){
        	        	
        	        	file.print("OneR->");
        	        	
        	        	System.out.print("OneR->");
        	        	filter.setEvaluator(OneR);
        	        }else if(index_ranker == 5){
        	        	
        	        	file.print("Information Gain->");
        	        	
        	        	System.out.print("Information Gain->");
        	        	filter.setEvaluator(IG);
        	        }
        			
        			filter.setSearch(ranker);
        			filter.setInputFormat(trainData);
        			
        			dataResult = Filter.useFilter(trainData, filter);
        			evaluation = new Evaluation(dataResult);
        			
        			remove.setInputFormat(dataResult);
        			dataResult = Filter.useFilter(dataResult, remove);
        	           
        	     //   rf.buildClassifier(trainData);
        	        evaluation.crossValidateModel(RF, dataResult, numFolds, new Random(1));
        	         
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

        			if(index_ranker == 1){
        				
        				file.print("Gain Ration->");
        				
        				System.out.print("Gain Ration->");
        				filter.setEvaluator(GR);
        	        }else if(index_ranker == 2){
        	        	
        	        	file.print("ReliefF->");
        	        	
        	        	System.out.print("ReliefF->");
        	        	filter.setEvaluator(RFF);
        	        }else if(index_ranker == 3){
        	        	
        	        	file.print("Symmetric Un...->");
        	        	
        	        	System.out.print("Symmetric Un...->");
        	        	filter.setEvaluator(SU);
        	        }else if(index_ranker == 4){
        	        	
        	        	file.print("OneR->");
        	        	
        	        	System.out.print("OneR->");
        	        	filter.setEvaluator(OneR);
        	        }else if(index_ranker == 5){
        	        	
        	        	file.print("Information Gain->");
        	        	
        	        	System.out.print("Information Gain->");
        	        	filter.setEvaluator(IG);
        	        }
        			
        			filter.setSearch(ranker);
        			filter.setInputFormat(trainData);
        			
        			dataResult = Filter.useFilter(trainData, filter);
        			evaluation = new Evaluation(dataResult);
        			
        			remove.setInputFormat(dataResult);
        			dataResult = Filter.useFilter(dataResult, remove);
        	           
        	     //   rf.buildClassifier(trainData);
        	        evaluation.crossValidateModel(SVM, dataResult, numFolds, new Random(1));
        	         
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
    }
    

}