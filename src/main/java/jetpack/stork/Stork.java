package jetpack.stork;

import java.util.HashSet;
import java.util.Set;

import javax.util.List;

import org.slf4j.LoggerFactory;

import javax.io.File;

import com.fizzed.stork.launcher.Generator;

import cilantro.io.Console;

import com.fizzed.stork.launcher.Configuration.Platform;
import com.fizzed.stork.launcher.Configuration.Type;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import jetpack.Configuration;

public class Stork {
	static {
		((Logger)LoggerFactory.getLogger("com.fizzed")).setLevel(Level.OFF);
	}
	
	public List<File> generate(Configuration configuration, List<String> platforms, File directory, Console console) throws Exception {
		return platforms.map(platform -> {
			try {
				return generate(configuration, platform, directory, console);
			} catch (Exception e) {
				return null;
			}
		});
	}
	
	public File generate(Configuration configuration, String platform, File directory, Console console) throws Exception {
		console.printlnf("Generating executable ${0} [${1}] for ${2}...", configuration.getName(), configuration.getMain(), platform.toLowerCase());
		new Generator().generate(toStorkConfiguration(configuration, platform), directory.toFile());
		return new File(directory, "bin/" + configuration.getName() + (platform.equalsIgnoreCase("windows") ? ".bat" : ""));
	}
	
	protected Configuration configuration(String name, String main) {
		return new Configuration().setName(name).setMain(main);
	}
	
	protected com.fizzed.stork.launcher.Configuration toStorkConfiguration(Configuration configuration, String platform) {
		com.fizzed.stork.launcher.Configuration stork = new com.fizzed.stork.launcher.Configuration();
		stork.setFile(new File("test.yaml").toFile());
		stork.setName(configuration.getName());
		stork.setType(Type.CONSOLE);
		stork.setMainClass(configuration.getMain());
		stork.setShortDescription("");
		stork.setLongDescription("");

		Set<Platform> platforms = new HashSet<Platform>();
		platforms.add(Platform.valueOf(platform));
		stork.setPlatforms(platforms);
		
		return stork;
	}

}
