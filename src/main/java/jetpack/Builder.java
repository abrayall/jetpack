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
		return build(
			configuration(parameters.get(0, "sample"), parameters.get(1, "sample.Main")),
			list(parameters.get(2, "linux,windows").toUpperCase().split(",")), 
			new File(parameters.get(3, "build"))
		);
	}
	
	public Integer build(Configuration configuration, List<String> platforms, File directory) throws Exception {
		for (String platform : platforms)
			build(configuration, platform, directory);
		
		return 0;
	}
	
	public Integer build(Configuration configuration, String platform, File directory) throws Exception {
		return new Generator().generate(toStorkConfiguration(configuration, platform), directory);
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
