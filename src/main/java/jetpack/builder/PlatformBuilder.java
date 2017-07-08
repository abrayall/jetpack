package jetpack.builder;

import static javax.io.File.*;
import static javax.util.List.*;

import java.util.zip.ZipEntry;

import javax.io.File;
import javax.util.List;

import jetpack.Configuration;
import jetpack.jre.Jre;
import jetpack.jre.JreManager;

public abstract class PlatformBuilder extends UniversalBuilder {

	public PlatformBuilder(Configuration configuration, File directory) {
		super(configuration, directory);
	}
	
	public List<String> platforms() {
		return list(this.name());
	}
	
	public Builder process() throws Exception {
		Jre jre = runtimes().get(Jre.VERSION_1_8, this.name(), Jre.BIT_64);
		if (jre.getJava().exists() == false)
			runtimes().download(jre, this::downloaded, this::extracted);
		
		jre.getPath().copy(this.jre());
		return super.process();
	}
	
	protected File jre() throws Exception {
		return file(stage(), "jre").mkdirs();
	}
	
	protected JreManager runtimes() throws Exception {
		return new JreManager(file(directory.parent(), "java"));
	}
	
	protected void downloaded(Long downloaded, Long total) {
		System.out.println(downloaded);
	}
	
	protected void extracted(ZipEntry entry, File file) {
		System.out.println(entry);
	}
}