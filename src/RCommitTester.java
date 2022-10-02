import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RCommitTester {

	public static void main(String[] args) throws IOException {
		Path p1 = Paths.get("remy'sCommit1.txt");
        try {
            Files.writeString(p1, "testing deleting file", StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Path p2 = Paths.get("remy'sCommit2.txt");
        try {
            Files.writeString(p2, "testing editing file", StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Path p3 = Paths.get("remy'sCommit3.txt");
        try {
            Files.writeString(p3, "some random content", StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Path p4 = Paths.get("remy'sCommit4.txt");
        try {
            Files.writeString(p4, "some random content sdjflw", StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Path p5 = Paths.get("editedremy'sBlob.txt");
        try {
            Files.writeString(p5, "some random content!!!", StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		System.out.println ("RCommitTester is running :)");
		RIndex i = new RIndex();
		i.generateHeadFile();
		i.resetRIndexArrayList();
		i.add("remy'sIndex1.txt");
		i.add("remy'sCommit1.txt");
        
		RCommit original = new RCommit ("here's the summary", "Emma Miller");
		i.resetRIndexArrayList();
		i.add("remy'sIndex2.txt");
		i.add("remy'sCommit3.txt");
		i.add("remy'sCommit2.txt");
		RCommit second = new RCommit ("i'm summary", "Emma Miller");
		i.resetRIndexArrayList();
		i.add("remy'sBlob.txt");
	
		
		//System.out.println ("FOUND FILE" + second.checkTreeForFile(second.getCommitTreeSHA("65bc694de99683f4901c198d27a3a5f0ad7cadba"),"remy'sIndex1.txt"));
		//System.out.println ("FOUND FILE" + second.checkTreeForFile(second.getCommitTreeSHA("65bc694de99683f4901c198d27a3a5f0ad7cadba"),"remy'sIndex2.txt"));
		
		RCommit third = new RCommit ("summary", "Emma Miller");
		i.resetRIndexArrayList();
		
		i.delete("remy'sIndex2.txt");
	
		RCommit fourth = new RCommit ("finalSummary", "Emma Miller");
		i.resetRIndexArrayList();
		
		i.add("remy'sCommit4.txt");
		RCommit fifth = new RCommit ("summ", "Emma Miller");
		i.resetRIndexArrayList();
		
		i.add("editedremy'sBlob.txt");
		i.edit("remy'sBlob.txt");
		
		RCommit sixth = new RCommit ("summs", "Emma Miller");
		i.resetRIndexArrayList();
	}

}
