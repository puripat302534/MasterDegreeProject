import weka.core.Attribute;

public class fListClass {
	
		//variable for process add in F variable
	 	private Attribute attribute;
	    private int order;
	    
	    public fListClass(Attribute attribute , int order) throws Exception{
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
		public void setOrder(int order) {
			this.order = order;
		}
	    
}
