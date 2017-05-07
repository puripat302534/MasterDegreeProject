
import java.util.Comparator;

import weka.core.Attribute;

public class eListClass{
	//variable for process add in F variable
 	private Attribute attribute;
    private int order;
    private int no_att = 0;
    private double average = 0.0;
    private double average_range = 0.0;
    private double score_avergae = 0.0;
    
    public eListClass (Attribute attribute , int order) throws Exception{
    	setAttribute(attribute);
    	setOrder(order);
	} 
	public Attribute getAttribute() {
		return attribute;
	}
	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}
	public int getOrder() {
		return order;
	}
	
	//Temporary
	/*public void setNo_Att(int no_att) {
		this.no_att = no_att;
	}*/
	
	public void setOrder(int order) {
		this.order = order;
	}
	public int getNo_att() {
		return no_att;
	}
	public void update_no_att(){
		this.no_att++;
	}
	public double getAverage() {
		return average;
	}
	//Average score
	public void setAverage(double average) {
		this.average = average;
	}
	public double getAverage_range() {
		return average_range;
	}
	//Frequency 
	public void setAverage_range(double average_range) {
		this.average_range = average_range;
	}
	//Average Range
	public double getScore_avergae() {
		return score_avergae;
	}
	public void setScore_average(double score_avergae) {
		this.score_avergae = score_avergae;
	}
	public boolean checkForCount(Attribute attribute , int order){
		boolean isTrue = false;
		
		if (this.attribute == attribute && this.order == order){
			isTrue = true;
		}
		
		return isTrue;
	}
	@Override
    public boolean equals(final Object obj) {
		if(this == obj)
				return true;
			if((obj == null) || (obj.getClass() != this.getClass()))
			return false;
			// object must be Test at this point
			eListClass test = (eListClass)obj;
			return order == test.order &&
			(attribute == test.attribute|| (attribute != null && attribute.equals(test.attribute)));
    }
	
	public static Comparator<eListClass> SortAttribute = new Comparator<eListClass>() {

			@Override
			public int compare(eListClass o1, eListClass o2) {
				
				//-->start_Frequency
				Double num1 = o1.getAverage_range();
				Double num2 = o2.getAverage_range();
				int resultCom_order = num2.compareTo(num1);
				//System.out.print("Compare: "+ o1.getAttribute().name()+"->Order :"+o1.getOrder()+"("+o1.getNo_att()+") "+",Avg: "
				//+o1.getAverage()+" With "+o2.getAttribute().name()+"->Order: "+o2.getOrder()+"("+o2.getNo_att()+") "+"
				//,Avg: "+o2.getAverage()+" Result is "+resultCom_order);
				
				if(o1.getAttribute() == o2.getAttribute())
				{
					//System.out.println("-->So case 1");
					//Order by frequency
					return resultCom_order ;
				}else
				{
					//System.out.println("-->So case 2");
					if (resultCom_order != 0){
						//Order by frequency
						return resultCom_order;
					}else{
						
						Double check1 = o1.getScore_avergae();
						Double check2 = o2.getScore_avergae();
						resultCom_order = check1.compareTo(check2);
						
						if(resultCom_order != 0){
							//order in average rank.
							return check1.compareTo(check2);
						}else
						{
							//order by average score
							Double check_1 = o1.getAverage();
							Double check_2 = o2.getAverage();
							return check_2.compareTo(check_1);
						}
						
						
					}
				}
				//--->end	
			}
		};
		
		public static Comparator<eListClass> SortAttribute_Weight_Distance = new Comparator<eListClass>() {

			@Override
			public int compare(eListClass o1, eListClass o2) {
				
				//-->start_Frequency
				Double num1 = o1.getAverage_range();
				Double num2 = o2.getAverage_range();
				
				int resultCom_order = num2.compareTo(num1);
				//System.out.print("Compare: "+ o1.getAttribute().name()+"->Order :"+o1.getOrder()+"("+o1.getNo_att()+") "+",Avg: "
				//+o1.getAverage()+" With "+o2.getAttribute().name()+"->Order: "+o2.getOrder()+"("+o2.getNo_att()+") "+"
				//,Avg: "+o2.getAverage()+" Result is "+resultCom_order);
				
				if(o1.getAttribute() == o2.getAttribute())
				{
					//System.out.println("-->So case 1");
					//Order by frequency
					return resultCom_order ;
				}else
				{			
					if (resultCom_order != 0){
						//Order by frequency
						return resultCom_order;
					}
					else{
						
					//order by average score
					Double check_1 = o1.getAverage();
					Double check_2 = o2.getAverage();
					return check_2.compareTo(check_1);
					}
				}
				//--->end	
			}
		};
}
