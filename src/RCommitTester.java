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
		RCommit second = new RCommit ("i'm summary", "Emma Miller","a6f6a37fcd925e9f81b819a5ed992bdcac4cd31f");
		i.resetRIndexArrayList();
		i.add("remy'sBlob.txt");
		RCommit third = new RCommit ("summary", "Emma Miller", "68c30019e6cb3dab8343d1359031cf1ab5f291b0");
		i.resetRIndexArrayList();
		//System.out.println (original.convertIndexToArrayList());
	}

}
