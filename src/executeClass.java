
import weka.attributeSelection.OneRAttributeEval;
import weka.attributeSelection.Ranker;
import weka.attributeSelection.ReliefFAttributeEval;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.attribute.Remove;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class executeClass {
	
	//For test
	//private static final String INPUT_FILE_PATH = "/Users/admin/Desktop/General/Master Degree/Tesis/Experiment/contact-lenses.arff";
	
	//For research
	//private static final String INPUT_FILE_PATH =  "/Users/Puripat.T/Desktop/General/Master Degree/Tesis/Experiment/Lymphoma96x4026+9classes.arff";
	//private static final String INPUT_FILE_PATH = "/Users/Puripat.T/Desktop/General/Master Degree/Tesis/Experiment/Data/BreastCancer/breastCancer-train.arff";
	
	//private static final String INPUT_FILE_PATH = "/Users/Puripat.T/Desktop/General/Master Degree/Tesis/Experiment/Data/Ovarian_Cancer/Ovarian.arff";
	private static final String INPUT_FILE_PATH = "/Users/Puripat.T/Desktop/General/Master Degree/Tesis/Experiment/Data/Leukemia/leukemia_train_38x7129.arff";
	private static final String OUTPUT_FILE_PATH = "/Users/Puripat.T/Desktop/General/Master Degree/Tesis/Experiment/Result/result.arff";
	
	public static void main(String[] args) throws Exception {
		// load data source
		long startTime = System.nanoTime();
		System.out.println("Processing.....");
		DataSource source = new DataSource(INPUT_FILE_PATH);
		Instances dataset = source.getDataSet();
		
		//feature Selection Process
		featureSelectionProcessClass fSP = new featureSelectionProcessClass(dataset,1,400);//seed,num attribute
		
		//featureSelectionNewMethod fSP = new featureSelectionNewMethod(dataset,1,400,2);//seed,num attribute,neighbor
		//featureSelectionCo fSP = new featureSelectionCo(dataset,78,20,2);
		
		/*//Test
		int k=1,neighbor = 10,seed=1,feature = 35;
		featureSelectionNewMethod fSP1;
        Remove remove = new Remove();
		remove.setInvertSelection(false);
        Evaluation evaluation ;
        IBk rf = new IBk();
        Instances dataResult;
        int numFolds = 10;
        
		while(k<=5000){
			System.out.print("Round : " +k+" --> ");
			fSP1 = new featureSelectionNewMethod(dataset,seed,feature,k);
			int i = fSP1.getResult().classIndex();
			String strI = "" + i;
			String S = "36-"+strI;
			remove.setAttributeIndices(S);
			
			evaluation = new Evaluation(fSP1.getResult());
			
			remove.setInputFormat(fSP1.getResult());
			dataResult = Filter.useFilter(fSP1.getResult(), remove);
	           
	     //   rf.buildClassifier(trainData);
	        evaluation.crossValidateModel(rf, dataResult, numFolds, new Random(1));
	        double check = round(evaluation.weightedAreaUnderROC(),3);
	        System.out.println(check);
	        if(check >= 0.790){
	        	//System.out.println(evaluation.toSummaryString("\nResults\n======\n", true));
	            //System.out.println(evaluation.toClassDetailsString());
	            System.out.println(evaluation.weightedAreaUnderROC());
	            System.out.println("And K is "+k);
	        	break;
	        }
	        k++;
		}
		
		//int i = fSP.getResult().classIndex();
		  //System.out.println(i);
		featureSelectionNewMethod fSP = new featureSelectionNewMethod(dataset,seed,feature,k);
		//end Test*/
		
		 
		
		//Save to .arff file
		ArffSaver saver = new ArffSaver();
		saver.setInstances(fSP.getResult());
		saver.setFile(new File(OUTPUT_FILE_PATH));
		saver.writeBatch();
		long endTime = System.nanoTime();
		System.out.println("Process Finish ( "+((endTime - startTime)/1000000000.0)+"ns )");
	}
	
	 public static double round(double value, int places) {
	        if (places < 0) throw new IllegalArgumentException();

	        BigDecimal bd = new BigDecimal(value);
	        bd = bd.setScale(places, RoundingMode.HALF_UP);
	        return bd.doubleValue();
	    }

}
