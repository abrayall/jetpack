package jetpack.builder;

import static javax.io.File.*;
import static javax.util.List.*;

import javax.io.File;
import javax.util.List;

import cilantro.io.Console;
import jetpack.Configuration;
import jetpack.stork.Stork;

import static jetpack.archive.Archive.*;
import static jetpack.compress.Compress.*;

public class UniversalBuilder extends Builder {
	
	public UniversalBuilder(Configuration configuration, File directory) {
		super(configuration, directory);
	}
	
	public String name() {
		return "universal";
	}

	public Builder process() throws Exception {
		for (String platform : this.platforms())
			this.executable(platform, bin(), configuration.getName());
		
		for (File file : configuration.getClasspath())
			file.copy(file(lib(), file.name()).mkdirs());
		
		return this;
	}

	@Override
	public List<File> bundle() throws Exception {
		String name = configuration.getName() + (configuration.getVersion().equals("") ? "" : "-" + configuration.getVersion());
		return list(
			// turn this into a call to a method artifacts that returns a List<File> that should be used to build artifacts
			platforms().contains("linux") ? gzip(tar(stage(), file(directory, name + ".tar"))) : null,
			platforms().contains("windows") ? zip(stage(), file(directory, name + ".zip")) : null
		).filter(file -> file != null);
	}
	
	protected List<String> platforms() {
		return list("linux", "windows");
	}
	
	protected File executable(String platform, File directory, String name) throws Exception {
		return new Stork().generate(configuration, platform.toUpperCase(), work("stork"), new Console()).copy(file(directory, name + (platform.equalsIgnoreCase("windows") ? ".bat" : "")).mkdirs());
	}
	
	protected File stage() throws Exception {
		return file(this.directory, "stage/" + this.configuration.getName()).mkdirs();
	}
	
	protected File work() {
		return work("");
	}
	
	protected File work(String directory) {
		return file(this.directory, "work" + ((directory != null && directory.equals("") == false) ? "/" + directory : ""));
	}
	
	protected File bin() throws Exception {
		return file(stage(), "bin").mkdirs();
	}
	
	protected File lib() throws Exception {
		return file(stage(), "lib").mkdirs();
	}
	
	public static void main(String[] arguments) throws Exception {
		new UniversalBuilder(new Configuration().setName("jetpack").setVersion("1.0.2.0").setMain("jetpack.Builder").setClasspath(list(file(".").search(".*\\.jar"))), file("/tmp/jetpack/test"))
			.clean()
			.setup()
			.process()
			.bundle();
	}
}
