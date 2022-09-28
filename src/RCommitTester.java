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
		RCommit second = new RCommit ("i'm summary", "Emma Miller","c907291573d0fcbec859627991f459d6e8e4e6ce");
		i.resetRIndexArrayList();
		i.add("remy'sBlob.txt");
		RCommit third = new RCommit ("summary", "Emma Miller", "6d7ea47802882c1f167d3d8aab0e425cd3e01fb5");
		i.resetRIndexArrayList();
		//System.out.println (original.convertIndexToArrayList());
	}

}
