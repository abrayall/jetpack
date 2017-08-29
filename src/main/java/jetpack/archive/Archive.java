package jetpack.archive;

import static javax.io.File.*;
import static javax.util.List.*;
import static javax.util.Map.*;

import java.io.OutputStream;

import javax.io.File;
import javax.io.Streams;
import javax.util.Map;

import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

public class Archive {

	public static File tar(File directory) throws Exception {
		return tar(directory, file(directory.parent(), directory.name() + ".tar"));
	}
	
	public static File tar(File directory, File file) throws Exception {
		return tar(directory, file, ".*");
	}
	
	public static File tar(File directory, File file, String pattern) throws Exception {
		archive(directory, new TarArchiveOutputStream(file.outputStream()), "", pattern).close();
		return file;
	}
	
	public static File tar(File file, Map<String, File> layout) throws Exception {
		return tar(file, layout, ".*");
	}
	
	public static File tar(File file, Map<String, File> layout, String pattern) throws Exception {
		archive(new TarArchiveOutputStream(file.outputStream()), layout, pattern).close();
		return file;
	}
	
	protected static TarArchiveOutputStream tar(OutputStream outputStream) {
		TarArchiveOutputStream tar = new TarArchiveOutputStream(outputStream);
		tar.setLongFileMode(TarArchiveOutputStream.LONGFILE_POSIX);
		return tar;
	}
	
	public static File zip(File directory) throws Exception {
		return zip(directory, file(directory.parent(), directory.name() + ".zip"));
	}
	
	public static File zip(File directory, File file) throws Exception {
		return zip(directory, file, ".*");
	}
	
	public static File zip(File directory, File file, String pattern) throws Exception {
		archive(directory, zip(file.outputStream()), "", pattern).close();
		return file;
	}
	
	public static File zip(File file, Map<String, File> layout) throws Exception {
		return zip(file, layout, ".*");
	}
	
	public static File zip(File file, Map<String, File> layout, String pattern) throws Exception {
		archive(zip(file.outputStream()), layout, pattern).close();
		return file;
	}
	
	protected static ZipArchiveOutputStream zip(OutputStream outputStream) {
		ZipArchiveOutputStream zip = new ZipArchiveOutputStream(outputStream);
		zip.setLevel(1);
		return zip;
	}
	
	protected static ArchiveOutputStream archive(ArchiveOutputStream archive, Map<String, File> layout, String pattern) throws Exception {
		for (String entry : layout.keySet())
			archive(layout.get(entry), archive, entry.endsWith("/") ? entry : entry + "/", pattern);
		
		return archive;
	}
	
	protected static ArchiveOutputStream archive(File directory, ArchiveOutputStream archive, String prefix, String pattern) throws Exception {
		for (File child : list(directory.search(pattern))) {
			archive.putArchiveEntry(archive.createArchiveEntry(child.toFile(), prefix + child.relative(directory)));
			if (child.isDirectory() == false)
				Streams.copy(child.inputStream(), archive);
			
			archive.closeArchiveEntry();
		}
		
		return archive;
	}
	
	public static void main(String[] arguments) throws Exception {
		zip(file("/tmp/treehouse"));
		tar(file("/tmp/treehouse"));

		tar(file("/tmp/test.tar"), map(
			entry("lib", file("/tmp/treehouse")),
			entry("simpliprotected", file("/tmp/simpliprotected"))
		));
		
		zip(file("/tmp/test.zip"), map(
			entry("lib", file("/tmp/treehouse")),
			entry("simpliprotected", file("/tmp/simpliprotected"))
		));
		
	}
}
