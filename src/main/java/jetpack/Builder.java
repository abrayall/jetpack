package jetpack;

import static javax.util.List.*;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.util.List;
import javax.util.Map;

import org.slf4j.LoggerFactory;

import com.fizzed.stork.launcher.Configuration.Platform;
import com.fizzed.stork.launcher.Configuration.Type;
import com.fizzed.stork.launcher.Generator;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class Builder extends cilantro.Main {
	
	static {
		((Logger)LoggerFactory.getLogger("com.fizzed")).setLevel(Level.OFF);
	}
	
	public Integer execute(List<String> parameters, Map<String, String> options) throws Exception {
		if (parameters.size() < 2)
			return help();
		
		return build(parameters.get(0, "sample"), parameters.get(1, "sample.Main"), list(parameters.get(2, "linux,windows").toUpperCase().split(",")), new File(parameters.get(3, "build")));
	}
	
	public Integer build(String name, String main, List<String> platforms, File directory) throws Exception {
		return build(configuration(name, main), platforms, directory);
	}
	
	public Integer build(Configuration configuration, List<String> platforms, File directory) throws Exception {
		for (String platform : platforms)
			build(configuration, platform, directory);
		
		return 0;
	}
	
	public Integer build(Configuration configuration, String platform, File directory) throws Exception {
		return new Generator().generate(toStorkConfiguration(configuration, platform), directory);
	}
	
	protected Integer help() {
		println();
		println("Usage: jetpack [name] [main] [platforms] [directory]");
		println("  - name:       name of executable to generate");
		println("  - main:       name of class with main() that should be launched");
		println("  - platforms:  plaforms to generate executables (linux|window default both)");
		println("  - directory:  directory that executables should be built");
		println();
		return 0;
	}
	
	protected Configuration configuration(String name, String main) {
		return new Configuration().setName(name).setMain(main);
	}
	
	protected com.fizzed.stork.launcher.Configuration toStorkConfiguration(Configuration configuration, String platform) {
		com.fizzed.stork.launcher.Configuration stork = new com.fizzed.stork.launcher.Configuration();
		stork.setFile(new File("test.yaml"));
		stork.setName(configuration.name);
		stork.setType(Type.CONSOLE);
		stork.setMainClass(configuration.main);
		stork.setShortDescription("");
		stork.setLongDescription("");

		Set<Platform> platforms = new HashSet<Platform>();
		platforms.add(Platform.valueOf(platform));
		stork.setPlatforms(platforms);
		
		return stork;
	}
	
	public static void main(String[] arguments) throws Exception {
		main(Builder.class, arguments);
	}
}
