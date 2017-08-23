package jetpack.archive;

import static javax.io.File.*;
import static javax.util.List.*;

import java.io.OutputStream;

import javax.io.File;
import javax.io.Streams;

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
		archive(directory, tar(file.outputStream()), pattern);
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
		archive(directory, zip(file.outputStream()), pattern);
		return file;
	}
	
	protected static ZipArchiveOutputStream zip(OutputStream outputStream) {
		ZipArchiveOutputStream zip = new ZipArchiveOutputStream(outputStream);
		zip.setLevel(1);
		return zip;
	}
	
	protected static void archive(File directory, ArchiveOutputStream archive, String pattern) throws Exception {
		for (File child : list(directory.search(pattern))) {
			archive.putArchiveEntry(archive.createArchiveEntry(child.toFile(), child.relative(directory)));
			if (child.isDirectory() == false)
				Streams.copy(child.inputStream(), archive);
			
			archive.closeArchiveEntry();
		}
		
		archive.close();
	}
	
	public static void main(String[] arguments) throws Exception {
		zip(file("/tmp/treehouse"));
	}
}
