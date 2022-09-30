import java.io.IOException;

public class RCommitTester {

	public static void main(String[] args) throws IOException {
		System.out.println ("RCommitTester is running :)");
		RIndex i = new RIndex();
		i.resetRIndexArrayList();
		i.add("remy'sIndex1.txt");
        
		RCommit original = new RCommit ("here's the summary", "Emma Miller", "");
		i.resetRIndexArrayList();
		i.add("remy'sIndex2.txt");
		RCommit second = new RCommit ("i'm summary", "Emma Miller","b2daf7ba3f47ad7091f1b7ab8e6848b69fab9b15");
		i.resetRIndexArrayList();
		i.add("remy'sBlob.txt");
	
		
		//System.out.println ("FOUND FILE" + second.checkTreeForFile(second.getCommitTreeSHA("f192add36c7f229eed88e90e90e035b7380dfa49"),"remy'sIndex1.txt"));
		//System.out.println ("FOUND FILE" + second.checkTreeForFile(second.getCommitTreeSHA("f192add36c7f229eed88e90e90e035b7380dfa49"),"remy'sIndex2.txt"));
		
		RCommit third = new RCommit ("summary", "Emma Miller", "f192add36c7f229eed88e90e90e035b7380dfa49");
		i.resetRIndexArrayList();
		
		i.delete("remy'sIndex2.txt");
		
		RCommit fourth = new RCommit ("finalSummary", "Emma Miller", "69da6dfa30487841613aa72f7b016492417bb26a");
		i.resetRIndexArrayList();
	}

}
