package jetpack;

import static javax.io.File.*;
import static javax.util.List.*;

import java.net.URL;
import java.net.URLClassLoader;

import javax.io.File;
import javax.net.Urls;
import javax.util.List;
import javax.util.Map;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import jetpack.stork.Stork;

public class Engine extends cilantro.Main {
	
	static {
		((Logger)LoggerFactory.getLogger("com.fizzed")).setLevel(Level.OFF);
	}
	
	public Integer execute(List<String> parameters, Map<String, String> options) throws Exception {
		println("------------------------------------------");
		println("${format(Jetpack - Native Executable Builder, blue, bold)} ${format(v" + Version.getVersion() + ", yellow)}");
		println("------------------------------------------");

		if (parameters.size() < 2)
			return help();
		
		return build(parameters.get(0, "sample"), parameters.get(1, "sample.Main"), list(parameters.get(2, "linux,windows").toUpperCase().split(",")), new File(parameters.get(3, "build")));
	}
	
	public Integer build(String name, String main, List<String> platforms, File directory) throws Exception {
		return new Stork().generate(configuration(name, main), platforms, directory, console).filter(file -> file != null).size();
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

	
	protected Configuration configuration(String name, String main) throws Exception {
		return configuration(name, main, List.list(file(".").search(".*\\.jar")));
	}
	
	protected Configuration configuration(String name, String main, List<File> classpath) throws Exception {
		try (URLClassLoader classloader = new URLClassLoader(urls(classpath))) {
			return configuration(name, main, classloader.loadClass(main).getProtectionDomain().getCodeSource().getLocation().toString(), classpath);
		}
	}
	
	protected Configuration configuration(String name, String main, String jar, List<File> classpath) {
		return new Configuration().setName(name).setMain(main).setJar(jar).setClasspath(classpath);
	}
	
	protected URL[] urls(List<File> classpath) {
		return classpath.map(file -> Urls.url(file.toFile().toURI())).toArray(new URL[0]);
	}
	
	public static void main(String[] arguments) throws Exception {
		main(Engine.class, arguments);
	}
}
