package jetpack.builder;

import static javax.io.File.*;

import javax.io.File;
import javax.util.List;

import jetpack.Configuration;

public abstract class Builder {
	
	protected File directory;
	protected Configuration configuration;
	
	public Builder(Configuration configuration, File directory) {
		this.configuration = configuration;
		this.directory = file(directory, this.name());
	}

	public Builder clean() throws Exception {
		this.directory.delete();
		return this;
	}
	
	public Builder setup() throws Exception {
		this.directory.mkdirs();
		return this;
	}
	
	abstract public String name();
	abstract public Builder process() throws Exception;
	abstract public List<File> bundle() throws Exception;
}
