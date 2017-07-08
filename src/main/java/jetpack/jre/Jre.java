package jetpack.jre;

import static javax.util.List.*;

import java.net.URL;

import javax.io.File;
import javax.util.List;

public class Jre {
	
	public static String VERSION_1_8 = "1.8.0_131";
	public static List<String> VERSIONS = list(VERSION_1_8);
	
	public static String PLATFORM_LINUX = "linux";
	public static String PLATFORM_WINDOWS = "windows";
	public static String PLATFORM_MACOS = "osx";
	public static List<String> PLATFORMS = list(PLATFORM_LINUX, PLATFORM_WINDOWS, PLATFORM_MACOS);
	
	public static String BIT_32 = "32";
	public static String BIT_64 = "64";
	public static List<String> BITS = list(BIT_32, BIT_64);
	
	protected String version;
	protected String platform;
	protected String bits;
	
	protected URL url;
	protected File path;

	protected Jre(String version, String platform, String bits, URL url, File path) {
		this.version = version;
		this.platform = platform;
		this.bits = bits;
		this.url = url;
		this.path = path;
	}	
	
	public boolean equals(Object object) {
		return this.toString().equals(object.toString());
	}
	
	public boolean equals(String version, String platform, String bits) {
		return this.version.equals(version) && this.platform.equals(platform) && this.bits.equals(bits);
	}
	
	public String toString() {
		return "jre [v" + this.version + " " + this.platform + " " + this.bits + "bits]";
	}

	public String getVersion() {
		return this.version;
	}

	public String getPlatform() {
		return this.platform;
	}

	public String getBits() {
		return this.bits;
	}

	public URL getUrl() {
		return this.url;
	}

	public File getPath() {
		return this.path;
	}
	
	public File getJava() {
		return new File(path, "/bin/java" + (this.platform.equals(Jre.PLATFORM_WINDOWS) ? ".exe" : ""));
	}
}