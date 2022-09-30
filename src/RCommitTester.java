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
//		RCommit second = new RCommit ("i'm summary", "Emma Miller","a8b505106a310e746b99c29c42c213d6f068543e");
//		i.resetRIndexArrayList();
//		i.add("remy'sBlob.txt");
//	
//		
//		//System.out.println ("FOUND FILE" + second.checkTreeForFile(second.getCommitTreeSHA("c3ecc7806ef2bfee954ef96a91223f0d6a7a964"),"remy'sIndex1.txt"));
//		//System.out.println ("FOUND FILE" + second.checkTreeForFile(second.getCommitTreeSHA("c3ecc7806ef2bfee954ef96a91223f0d6a7a964"),"remy'sIndex2.txt"));
//		
//		RCommit third = new RCommit ("summary", "Emma Miller", "c3ecc7806ef2bfee954ef96a91223f0d6a7a964");
//		i.resetRIndexArrayList();
////		
//		i.delete("remy'sBlob.txt");
//		
//		RCommit fourth = new RCommit ("finalSummary", "Emma Miller", "69da6dfa30487841613aa72f7b016492417bb26a");
//		i.resetRIndexArrayList();
	}

}
