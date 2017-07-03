package jetpack.launch4j;

import java.io.File;

import net.sf.launch4j.Builder;
import net.sf.launch4j.Log;
import net.sf.launch4j.config.Config;
import net.sf.launch4j.config.ConfigPersister;
import net.sf.launch4j.config.Jre;

public class Launch4J {

	public static void main(String[] arguments) throws Exception  {
		ConfigPersister.getInstance().createBlank();
		Config config = ConfigPersister.getInstance().getConfig();

		Jre jre = new Jre();
		jre.setMinVersion("1.8.0");
		
		config.setJar(new File("build/artifacts/jetpack.jar"));
		config.setOutfile(new File("test.exe"));
		config.setJre(jre);
		
		Builder builder = new Builder(Log.getAntLog(), new File("resources/launch4j/linux"));
		builder.build();
	}
}
