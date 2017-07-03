package jetpack.stork;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.util.List;

import com.fizzed.stork.launcher.Generator;

import cilantro.io.Console;

import com.fizzed.stork.launcher.Configuration.Platform;
import com.fizzed.stork.launcher.Configuration.Type;

import jetpack.Configuration;

public class Stork {
	public Integer build(String name, String main, List<String> platforms, File directory, Console console) throws Exception {
		return build(configuration(name, main), platforms, directory, console);
	}
	
	public Integer build(Configuration configuration, List<String> platforms, File directory, Console console) throws Exception {
		for (String platform : platforms)
			build(configuration, platform, directory, console);
		
		console.println("Build complete.");
		return 0;
	}
	
	protected Integer build(Configuration configuration, String platform, File directory, Console console) throws Exception {
		console.printlnf("Building executable ${0} [${1}] for ${2}...", configuration.getName(), configuration.getMain(), platform.toLowerCase());
		return new Generator().generate(toStorkConfiguration(configuration, platform), directory);
	}
	
	protected Configuration configuration(String name, String main) {
		return new Configuration().setName(name).setMain(main);
	}
	
	protected com.fizzed.stork.launcher.Configuration toStorkConfiguration(Configuration configuration, String platform) {
		com.fizzed.stork.launcher.Configuration stork = new com.fizzed.stork.launcher.Configuration();
		stork.setFile(new File("test.yaml"));
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
