package jetpack.jre;

import static javax.io.File.*;
import static javax.util.Map.*;
import static javax.util.zip.ZipFile.*;

import java.io.PrintStream;
import java.net.URL;
import java.util.function.BiConsumer;
import java.util.zip.ZipEntry;

import javax.io.File;
import javax.lang.Strings;
import javax.net.Urls;
import javax.util.List;

public class JreManager {
	
	private File work;
	
	public JreManager(File work) {
		this.work = work;
	}
	
	public List<Jre> list() throws Exception {
		List<Jre> jres = List.list();
		for (String version : Jre.VERSIONS) {
			for (String platform : Jre.PLATFORMS) {
				for (String bits : Jre.BITS) {
					if (platform.equals(Jre.PLATFORM_MACOS) && bits.equals(Jre.BIT_64))
						continue;
					
					jres.add(new Jre(version, platform, bits,
						url(version, platform, bits),
						path(version, platform, bits)
					));
				}
			}
		}
	
		return jres;
	}

	public Jre get(String version, String platform) throws Exception {
		return this.get(platform, version, Jre.BIT_64);
	}
	
	public Jre get(String version, String platform, String bits) throws Exception {
		for (Jre jre : this.list()) {
			if (jre.equals(version, platform, bits)) 
				return jre;
		}
		
		return null;
	}
	
	public Jre download(Jre jre) throws Exception {
		return this.download(jre, (bytes, total) -> {}, (entry, file) -> {});
	}
	
	public Jre download(Jre jre, BiConsumer<Long, Long> download, BiConsumer<ZipEntry, File> extract) throws Exception {
		unzip(Urls.download(jre.url, file(jre.path, "jre.zip"), download), file(jre.path, "jre"), (entry, file) -> {
			File resolved = resolve(jre, entry, file);
			extract.accept(entry, resolved);
			return resolved;
		});
		
		return jre;
	}

	protected String name(Jre jre) {
		return name(jre.version, jre.platform, jre.bits);
	}
	
	protected String name(String version, String platform, String bits) {
		return "jre" + version + "-" + platform + (bits.equals(Jre.BIT_64) ? "-x64" : "");
	}
	
	protected URL url(String version, String platform, String bits) {
		return Urls.url("https://installbuilder.bitrock.com/java/" + name(version, platform, bits) + ".zip");
	}
	
	protected File path(String version, String platform, String bits) {
		return file(work, platform + "/" + version + "/" + bits + "bit");
	}
	
	protected File resolve(Jre jre, ZipEntry entry, File file) {
		return file(file.path().replace(name(jre) + "/", "").replace("java-" + jre.platform + (jre.bits.equals(Jre.BIT_64) ? "-x64" : ""), ""));
	}
	
	public static void main(String[] arguments) throws Exception {
		JreManager manager = new JreManager(new File("/tmp/java"));
		Jre jre = manager.get(Jre.VERSION_1_8, Jre.PLATFORM_MACOS, Jre.BIT_32);
		if (jre.path.exists() == false)
			manager.download(jre, JreManager::downloaded, JreManager::extracted);
	}
	
	public static BiConsumer<Long, Long> downloaded(String format) {
		return downloaded(System.out, format);
	}

	public static BiConsumer<Long, Long> downloaded(PrintStream stream, String format) {
		return (bytes, size) -> stream.println(Strings.format(format, map(entry("bytes", bytes), entry("size", size))));		
	}
			
	public static void downloaded(Long bytes, Long size) {
		System.out.println("Downloaded " + bytes + " bytes out of " + size + " bytes [" + String.format("%.2f", ((bytes.doubleValue() / size.doubleValue()) * 100.0)) + "%]");
	}
	
	public static void extracted(ZipEntry entry, File file) {
		System.out.println("Extracting " + entry + " to " + file);
	}
}
