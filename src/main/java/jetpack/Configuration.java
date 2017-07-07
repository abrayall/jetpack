package jetpack;

import javax.io.File;
import javax.util.List;

public class Configuration {
	
	protected String name;
	protected String main;
	protected String type = "console";
	
	protected String jar;
	protected List<File> classpath;
	
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
	
	public String getJar() {
		return this.jar;
	}

	public Configuration setJar(String jar) {
		this.jar = jar;
		return this;
	}

	public List<File> getClasspath() {
		return this.classpath;
	}

	public Configuration setClasspath(List<File> classpath) {
		this.classpath = classpath;
		return this;
	}
}
