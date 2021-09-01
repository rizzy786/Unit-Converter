public class Currency {

	private String name, symbol;
	private double factor;
	
	public Currency(String name, double factor, String symbol) {
	  this.name = name;
	  this.factor = factor;
	  this.symbol = symbol;
	}

	public String getName() {
	  return name;
	}
	
	public String getSymbol() {
	  return symbol;
	}
	
	public double getFactor() {
	  return factor;
	}
}
