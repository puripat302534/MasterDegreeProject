//Library from java
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//Library From Weka
import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.ChiSquaredAttributeEval;
import weka.attributeSelection.CorrelationAttributeEval;
import weka.attributeSelection.FilteredAttributeEval;
import weka.attributeSelection.GainRatioAttributeEval;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.LatentSemanticAnalysis;
import weka.attributeSelection.OneRAttributeEval;
import weka.attributeSelection.PrincipalComponents;
import weka.attributeSelection.Ranker;
import weka.attributeSelection.ReliefFAttributeEval;
import weka.attributeSelection.SymmetricalUncertAttributeEval;
import weka.core.Attribute;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.attribute.Reorder;

public class featureSelectionCo {
	
	//Create Attribute Selection object
	private AttributeSelection filter ;
	
	//For method in all feature selection
	private Ranker ranker;

	//Variable for all evaluator process (Filter Ranking)
	private InfoGainAttributeEval infoGain;
	private GainRatioAttributeEval gainRatio;
	private SymmetricalUncertAttributeEval symmetri;
	private ReliefFAttributeEval reliefF;
	
	//Research Methodology
	private ChiSquaredAttributeEval chiSquare;
	private FilteredAttributeEval filtered;
	private CorrelationAttributeEval co_re;
	
	
	//Variable for all evaluator process (Wraper Ranking)
	private OneRAttributeEval oneR;
	
	//For input dataset,all result from each process.
	private  Instances dataset,resultInfoGain,resultGainRatio,resultSymmetri
						,resultreliefF,resultOneR,resultChiSquare,resultFiltered,resultCo_Re;
	public static Instances resultData;
	
	//For calculate the average score
	Map<Attribute,Double> score_average = new LinkedHashMap<Attribute, Double>();
	Map<Attribute,Double> average_score = new LinkedHashMap<Attribute, Double>();
	Map<Attribute,Double> average_ranking = new LinkedHashMap<Attribute, Double>();
	
	//Temp values
	Map<Attribute,Double> sumOfSelecting = new LinkedHashMap<Attribute, Double>();
	private int select_num = 0;
	private int seed_set = 0;
	private int num_att = 0;
	private int neight = 0;
	
	public featureSelectionCo(Instances dataset,int seed,int attr_num,int neight) throws Exception{
		this.dataset = dataset;
		select_num = attr_num;
		seed_set = seed;
		this.neight = neight;
		config();
	}
	
	//Config variable before process
	private void config() throws Exception{

		//Create Attribute Selection object
		 filter = new AttributeSelection();
		 ranker = new Ranker();
		 ranker.setGenerateRanking(true);

		 //Set up for all ranking evaluator
		 informationGainConfig();
		 GainRatioConfig();
		 SymmetriConfig();
		 ReliefFConfig();
		 OneRConfig();
		 
		 //Tesis Research
		 //ChiSquareConfig();
		 //FilteredAttributeConfig();
		 CorrelationConfig();
		 
		 //Process ensemble of multiple feature Selection Techniques
		 resultData = ensembleFeatureSelection();
		 //resultData = resultreliefF;
		 
	}
	
	//Main Algorithm: Ensemble of Multiple Feature Selection Techniques
	private Instances ensembleFeatureSelection() throws Exception{
		
		//Variable for add all data map
		List<fListClass> F_list = new ArrayList<fListClass>();
		
		//Variable for finish ordering attribute
		List<eListClass> E_List = new ArrayList<eListClass>();
		Map<Attribute,Integer> E_List_Final = new LinkedHashMap<Attribute, Integer>();
		
		//Variable for number of all instance ,Final instance & average ranking score
		Instances E = dataset;
		
		
		Instances[] dataProcess = new Instances[5];
		
		dataProcess[0] = resultInfoGain;//-->
		dataProcess[1] = resultGainRatio;//-->
		//dataProcess[2] = resultSymmetri;//-->
		dataProcess[2] = resultreliefF;//
		dataProcess[3] = resultOneR;//-->
		dataProcess[4] = resultCo_Re;
		
		
		num_att = dataset.numAttributes() - 1;
		int numDataProcess = dataProcess.length;
		
		//Temp-start 
		int numAttr = select_num;
		//System.out.println("Select num : "+select_num+"\n");
		//Temp-end
		
		//For each dataset result in each algorithm-->Start Loop[Add all data to F List]
		for(int i=0 ; i < numDataProcess; i++ ){
			
			//set class index .. as the last attribute
			if (dataProcess[i].classIndex() == -1) {
				dataProcess[i].setClassIndex(dataProcess[i].numAttributes() - 1);
				
			 }
			
			//For build ranking value in each algorithm
			if(i==0){
				infoGain.buildEvaluator(dataProcess[i]);
				
			}else if(i==1){
				gainRatio.buildEvaluator(dataProcess[i]);
				
			}else if(i==99){
				symmetri.buildEvaluator(dataProcess[i]);
				
			}else if(i==2){
				reliefF.buildEvaluator(dataProcess[i]);
				
			}else if(i==3){
				//oneR.setSeed(101);
				oneR.buildEvaluator(dataProcess[i]);
				
			}else if(i==4){
				co_re.buildEvaluator(dataProcess[i]);
			}
			
			//For each attribute in E with 0 number count
			for(int j=0 ; j < numAttr ;j++){//Start Inner Loop
				
				//add all attr for prepare next process in E list
				if(j < select_num){
					if (E_List.size() == 0){
						
						E_List.add(new eListClass(dataProcess[i].attribute(j), j+1) );
						
					}else{
						eListClass check_contain = new eListClass(dataProcess[i].attribute(j), j+1);
						
						if(!E_List.contains(check_contain) ){
							E_List.add(check_contain);
						}	
					}
				}
				
				//set Average score of each attribute
				if(i==0){
					setAverageOfAttribute(dataProcess[i],j,infoGain.evaluateAttribute(j));				
				}else if (i==1){
					setAverageOfAttribute(dataProcess[i],j,gainRatio.evaluateAttribute(j));
				}else if (i==99){
					setAverageOfAttribute(dataProcess[i],j,symmetri.evaluateAttribute(j));
				}else if (i==2){
					setAverageOfAttribute(dataProcess[i],j,reliefF.evaluateAttribute(j));
				}else if (i==3){
					setAverageOfAttribute(dataProcess[i],j,oneR.evaluateAttribute(j));
				}else if(i==4){
					setAverageOfAttribute(dataProcess[i],j,co_re.evaluateAttribute(j));
				}
				
				// set for average ranking score
				setAverageRankingOfAttribute(dataProcess[i],j,j+1,i);
				
				if(j < select_num){
				
				//Add All data to F list
				F_list.add(new fListClass(dataProcess[i].attribute(j), j+1));
				}
				
			}//End Inner loop
			
		}
		//-->End Loop
		
		//Process of clearing Issue of data and new ordering of attribute 
		for(int j =0 ; j<  F_list.size() ; j++){	
			eListClass check_contain = new eListClass(F_list.get(j).getAttribute() ,F_list.get(j).getOrder());
			int indexToUpdate = E_List.indexOf(check_contain);
			E_List.get(indexToUpdate).update_no_att();
			
			//Set Score
			E_List.get(indexToUpdate).setAverage(average_score.get(F_list.get(j).getAttribute())/5.0);
			//E_List.get(indexToUpdate).setAverage(average_score.get(F_list.get(j).getAttribute())/ sumOfSelecting.get(F_list.get(j).getAttribute()));
			
			//Set Frequency
			E_List.get(indexToUpdate).setAverage_range(average_ranking.get(F_list.get(j).getAttribute()));
			
			
			//E_List.get(indexToUpdate).setScore_average(score_average.get(F_list.get(j).getAttribute())/5.0);
			
			//Set Order
			E_List.get(indexToUpdate).setScore_average(score_average.get(F_list.get(j).getAttribute())/sumOfSelecting.get(F_list.get(j).getAttribute()));
		}
		
		//Temp-->
		/*for (Map.Entry<Attribute, Double> entry : average_score.entrySet()) {
	    System.out.println("Key = " + entry.getKey().name() + ", Value = " + entry.getValue());
		}
		System.out.println("------");*/
		
		/*System.out.println("-----Before Sort------");
		for(int j =0 ; j<E_List.size() ; j++){
		System.out.println(E_List.get(j).getAttribute().name() + "  " + E_List.get(j).getOrder()+"  " +E_List.get(j).getNo_att()+" "+E_List.get(j).getAverage() );
		}*/	
		//End temp
			
		//Sort E List
		Collections.sort(E_List, eListClass.SortAttribute_Weight_Distance);
		
		/*System.out.println("------After Sort--------");
		for(int j =0 ; j<E_List.size() ; j++){
			System.out.println(E_List.get(j).getAttribute().name() + "  " + E_List.get(j).getOrder()+"  Att_Num:" 
					+E_List.get(j).getNo_att()+" Score: "+E_List.get(j).getAverage()+" Freqency :"+ E_List.get(j).getAverage_range()+" Rank: "+E_List.get(j).getScore_avergae());
		}*/
		
		//System.out.println("-----------------");
		
		//check for duplicate data
		for(int j =0 ; j<  E_List.size() ; j++){
			//System.out.println(E_List.get(j).getAttribute().name() + "  " + E_List.get(j).getOrder()+"  " +E_List.get(j).getNo_att() );
			
			//Case for attribute is not in the list
			if(!E_List_Final.containsKey(E_List.get(j).getAttribute())){
				
				E_List_Final.put(E_List.get(j).getAttribute() ,E_List.get(j).getNo_att());
				//System.out.println( E_List_Final.get(E_List.get(j).getAttribute()));
			}else{
				//if attribute is in the list already we would check for 
				
				//List<Attribute> indexes = new ArrayList<Attribute>(E_List_Final.keySet());
				
				//update the frequent of att
				if( E_List_Final.get(E_List.get(j).getAttribute()) < E_List.get(j).getNo_att()) {
					//System.out.println(E_List_Final.get(E_List.get(j).getAttribute())+" , "+E_List.get(j).getNo_att());
					
					E_List_Final.put(E_List.get(j).getAttribute(),E_List.get(j).getNo_att());
				}
			}
			
		}
		
		/*for (Map.Entry<Attribute, Integer> entry : E_List_Final.entrySet()) {
		    System.out.println("Key = " + entry.getKey().name() + ", Value = " + entry.getValue());
		}*/
		
		E = reorderAtts( dataset, E_List_Final);
		
		
		//System.out.println("----------");
		/*for (int i=0 ;i<E.numAttributes();i++){
			System.out.println(E.attribute(i).name());
		}*/
		
		return E;
			
	}
	
	private Instances reorderAtts(Instances data,Map<Attribute,Integer> E_List_Final) throws Exception {
		  Reorder r = new Reorder();
		  //Temp
		  int[] attributes= new int[E_List_Final.size()+1];
		  
		  //int[] attributes= new int[dataset.numAttributes()];
		  int indexUpdate = 0;
		  
		  //-->Temp-start
		  //int up = 0;
		  //Attribute[] a = new  Attribute[E_List_Final.size()];
		  //-->Temp-End
		 
		  data.setClassIndex(data.numAttributes() - 1);
		  
		  for (Map.Entry<Attribute, Integer> entry : E_List_Final.entrySet()) {
			  //System.out.println(data.attribute(indexUpdate).name());
			 attributes[indexUpdate++] = data.attribute(entry.getKey().name()).index();
		    //System.out.println("Key = " + entry.getKey().name() + ", Value = " + entry.getValue());
			 
			 //temp->start
			 //a[up++] = entry.getKey();  
			 //temp->end
		  }
		  
		  //Temp->start
		 /* for (int i=0;i<data.numAttributes()-1;i++){
			  data.deleteAttributeAt(i);
			 
		  }
			for (int j = 0;j<26;j++){
				data.insertAttributeAt(a[j], j);
			}
		  //Temp->end  
		  */
		  
		  
		  attributes[indexUpdate++] = data.attribute(data.classAttribute().name()).index();
		  
		  //System.out.println(indexUpdate+"  "+E_List_Final.size()+1);
		  
		  r.setAttributeIndicesArray(attributes); 
		  r.setInputFormat(data);
		  data = Filter.useFilter(data, r);
		  
		  
		  return data;
		}
	
	//Calculate the average score ranking method
	private void setAverageOfAttribute(Instances dataProcess,int att_no,double ranking_score){
		
		if(!average_score.containsKey( dataProcess.attribute(att_no) )){
			average_score.put(dataProcess.attribute(att_no) ,ranking_score);
			//gainRatio.evaluateAttribute(j);
			
		}else{
			
			//Update average score 
			average_score.put(dataProcess.attribute(att_no),
			average_score.get(dataProcess.attribute(att_no))+ranking_score);
		}
	}
	
	
	private void setAverageRankingOfAttribute(Instances dataProcess,int att_no,int rank,int index){
		
		
		double priority = num_att - rank;
		
		//Average frequency
		if(!average_ranking.containsKey( dataProcess.attribute(att_no) )){
			if(att_no < select_num){
				
				
			average_ranking.put(dataProcess.attribute(att_no) ,priority);	
			//average_ranking.put(dataProcess.attribute(att_no) ,(double)(num_att - rank));
			}
			
		}else{
			
			//Update average frequency 
			if(att_no < select_num){
				
			average_ranking.put(dataProcess.attribute(att_no),
			average_ranking.get(dataProcess.attribute(att_no))+priority);	
				
			//average_ranking.put(dataProcess.attribute(att_no),
			//average_ranking.get(dataProcess.attribute(att_no))+(num_att - rank));
			}
		}
		
		/*if(!score_average.containsKey( dataProcess.attribute(att_no) )){
			score_average.put(dataProcess.attribute(att_no) ,(double)rank);
			
		}else{	
			//Update average rank 
			score_average.put(dataProcess.attribute(att_no),
			score_average.get(dataProcess.attribute(att_no))+(double)rank);
		}*/
		
		//Temp-start
		//Average ranking number
		if(!score_average.containsKey( dataProcess.attribute(att_no) )){
			
			//if(att_no <= select_num){
			score_average.put(dataProcess.attribute(att_no) ,(double)rank);
			sumOfSelecting.put(dataProcess.attribute(att_no) ,1.00);
			
			//}
			
		}else{	
			//Update average rank 
			//if(att_no <= select_num){
			score_average.put(dataProcess.attribute(att_no),
			score_average.get(dataProcess.attribute(att_no))+(double)rank);
			
			sumOfSelecting.put(dataProcess.attribute(att_no),
			sumOfSelecting.get(dataProcess.attribute(att_no))+1.0);
			//}
		}
		//Temp-end
	}
	
	//Set up ranking filter
	private void setUPfilterAlgorithm(ASEvaluation evaluator,ASSearch method,int option) throws Exception{
		Instances dataResult;
		
		/*if(evaluator instanceof OneRAttributeEval){
			System.out.println(option);
		}*/
		
		filter.setEvaluator(evaluator);
		filter.setSearch(method);
		filter.setInputFormat(dataset);
		
		dataResult = Filter.useFilter(dataset, filter);
		
		//For set result in each variable of each algorithm 
		switch(option){
		case 1 : resultInfoGain = dataResult; 
				 break;
		case 2 : resultGainRatio = dataResult; 
			     break;
		case 3 : resultSymmetri = dataResult; 
				 break;
		case 4 : resultreliefF = dataResult; 
				 break;
		case 5 : resultOneR = dataResult; 
		 		 break;
	  //case 8 : resultChiSquare = dataResult; 
		 	  // break;
		default : resultCo_Re = dataResult; 
		 		 break;
		}
		
	}
	
	//return instance of final result
	public Instances getResult() throws Exception{
		return resultData;	
	}
	
	private void informationGainConfig() throws Exception{
		//Create evaluator and search algorithm objects by information Gain
		 infoGain = new InfoGainAttributeEval (); 
		//set filter to use the evaluator and search algorithm by information Gain
	    setUPfilterAlgorithm(infoGain,ranker,1);
	}
	
	private void GainRatioConfig() throws Exception{
		//Create evaluator and search algorithm objects by Gain Ratio
		gainRatio = new GainRatioAttributeEval ();
		//set filter to use the evaluator and search algorithm by Gain Ratio
	    setUPfilterAlgorithm(gainRatio,ranker,2);
	}
	
	private void SymmetriConfig() throws Exception{
		//Create evaluator and search algorithm objects by Symmetri
		 symmetri = new SymmetricalUncertAttributeEval ();
		//set filter to use the evaluator and search algorithm by Symmetri
	    setUPfilterAlgorithm(symmetri,ranker,3);
		
	}
	
	private void ReliefFConfig() throws Exception{
		//Create evaluator and search algorithm objects by ReliefF
		 reliefF = new ReliefFAttributeEval ();
		reliefF.setNumNeighbours(neight);
		//set filter to use the evaluator and search algorithm by ReliefF
	    setUPfilterAlgorithm(reliefF,ranker,4);
	}
	
	private void OneRConfig() throws Exception{
		//Create evaluator and search algorithm objects by OneR
		 oneR = new OneRAttributeEval ();
		 oneR.setMinimumBucketSize(6);
		 oneR.setSeed(seed_set);
		//set filter to use the evaluator and search algorithm by OneR
	    setUPfilterAlgorithm(oneR,ranker,5);
	}
	
	//Addition for research
	private void ChiSquareConfig() throws Exception{
		//Create evaluator and search algorithm objects by OneR
		 chiSquare = new ChiSquaredAttributeEval ();
		//set filter to use the evaluator and search algorithm by OneR
	    setUPfilterAlgorithm(chiSquare,ranker,6);
	}
	
	private void FilteredAttributeConfig() throws Exception{
		//Create evaluator and search algorithm objects by OneR
		filtered = new FilteredAttributeEval ();
		//set filter to use the evaluator and search algorithm by OneR
	    setUPfilterAlgorithm(filtered,ranker,7);
	}
	
	private void CorrelationConfig() throws Exception{
		//Create evaluator and search algorithm objects by OneR
		co_re = new CorrelationAttributeEval ();
		//set filter to use the evaluator and search algorithm by OneR
	    setUPfilterAlgorithm(co_re,ranker,6);
	}
	
	
}
