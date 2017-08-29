package jetpack.launch4j;

import static javax.io.File.*;

import javax.io.File;
import javax.lang.System;

import jetpack.Configuration;
import net.sf.launch4j.Builder;
import net.sf.launch4j.Log;
import net.sf.launch4j.config.ClassPath;
import net.sf.launch4j.config.Config;
import net.sf.launch4j.config.ConfigPersister;
import net.sf.launch4j.config.Jre;

public class Launch4J {

	public static void main(String[] arguments) throws Exception  {
		Launch4J launch4j = new Launch4J();
		System.out.println(launch4j.build(new Configuration()
			.setName("jetpack")
			.setMain("jetpack.Main")
			.setJar("build/artifacts/jetpack.jar"), 
		file("build")));
	}
	
	public File build(Configuration configuration, File directory) throws Exception {
		Logger logger = new Logger();
		Config config = config(configuration, directory);
		File output = file(config.getOutfile()).mkdirs();

		Builder builder = builder(config, logger);
		builder.build();
		
		return output.exists() && logger.results().contains("Successfully created") ?  output : null;
	}
	
	public Builder builder(Config config, Logger logger) {
		return new Builder(logger, new File("resources/launch4j/" + (System.isWindows() ? "windows" : "linux")).toFile().getAbsoluteFile());
	}
	
	public Config config(Configuration configuration, File directory) {
		ConfigPersister.getInstance().createBlank();
		Config config = ConfigPersister.getInstance().getConfig();

		Jre jre = new Jre();
		jre.setMinVersion("1.8.0");
		//jre.setPath("jre");
		
		ClassPath classpath = new ClassPath();
		classpath.setMainClass(configuration.getMain());
		classpath.setPaths(configuration.getClasspath().map(file -> file.relative(file("."))));
		
		config.setClassPath(classpath);
		config.setJar(file(configuration.getJar()).toFile());
		config.setOutfile(file(directory, "windows/" + configuration.getName() + ".exe").toFile());
		config.setJre(jre);
		return config;
	}
	
	public static class Logger extends Log {
		
		protected StringBuilder output = new StringBuilder();

		public void append(String message) {
			this.output.append(message + "\n");
		}

		public void clear() {
			this.append("\n");
		}	
		
		public String results() {
			return this.output.toString();
		}
	}
}
