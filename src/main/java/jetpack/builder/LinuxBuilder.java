package jetpack.builder;

import static javax.io.File.*;
import static javax.util.List.*;

import javax.io.File;

import jetpack.Configuration;

public class LinuxBuilder extends PlatformBuilder {
	public LinuxBuilder(Configuration configuration, File directory) {
		super(configuration, directory);
	}
	
	public String name() {
		return "linux";
	}
	
	public static void main(String[] arguments) throws Exception {
		Long start = System.currentTimeMillis();
		new LinuxBuilder(new Configuration().setName("jetpack").setVersion("1.0.2.0").setMain("jetpack.Engine").setClasspath(list(file(".").search(".*\\.jar"))), file("/tmp/jetpack/test"))
			.clean()
			.setup()
			.process()
			.bundle();
		
		System.out.println(System.currentTimeMillis() - start);
	}
}
