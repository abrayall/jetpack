package jetpack;

public class Configuration {
	
	protected String name;
	protected String main;
	protected String type = "console";
	
	public String getName() {
		return this.name;
	}
	
	public Configuration setName(String name) {
		this.name = name;
		return this;
	}
	
	public String getMain() {
		return this.main;
	}
	
	public Configuration setMain(String main) {
		this.main = main;
		return this;
	}
	
	public String getType() {
		return this.type;
	}
	
	public Configuration setType(String type) {
		this.type = type;
		return this;
	}
	
}
