import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RIndexTester {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		RIndex test = new RIndex();
		test.init();
		
		test.add("remy'sBlob.txt");
		
		
		Path p1 = Paths.get("remy'sIndex1.txt");
        try {
            Files.writeString(p1, "testing adding index", StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Path p2 = Paths.get("remy'sIndex2.txt");
        try {
            Files.writeString(p2, "testing adding index again", StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        test.add("remy'sIndex1.txt");
        test.add("remy'sIndex2.txt");
        test.remove("remy'sBlob.txt");
        test.remove ("remy'sIndex1.txt");
        test.remove("remy'sIndex2.txt");
      
		//test.remove("foo.txt");
		
		
	}

}
