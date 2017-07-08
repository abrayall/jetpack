package jetpack.compress;

import static javax.io.File.*;
import static javax.io.Streams.*;

import java.util.zip.Deflater;

import javax.io.File;

import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipParameters;

public class Compress {

	public static File gzip(File source) throws Exception {
		return gzip(source, file(source.parent(), source.name() + ".gz"));
	}
	
	public static File gzip(File source, File target) throws Exception {
		copy(source.inputStream(), new GzipCompressorOutputStream(target.outputStream(), parameters()));
		return target;
	}
	
	public static GzipParameters parameters() {
		GzipParameters parameters = new GzipParameters();
		parameters.setCompressionLevel(Deflater.BEST_SPEED);
		return parameters;
	}
}
