import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RBlobTester {

	public static void main(String[] args) throws IOException {
		
		Path p = Paths.get("remy'sBlob.txt");
        try {
            Files.writeString(p, "testing if blob works", StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            e.printStackTrace();
        }
		Blob test = new Blob("remy'sBlob.txt");
		
		
	}

}
