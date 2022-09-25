import java.io.IOException;

public class RCommitTester {

	public static void main(String[] args) throws IOException {
		RIndex i = new RIndex();
		i.add("remy'sIndex1.txt");
        
		RCommit original = new RCommit ("here's the summary", "Emma Miller", "");
		System.out.println ("cleared index list:" + original.getindexArray());
		System.out.println ("RCommitTester is running :)");
		i.add("remy'sIndex2.txt");
		RCommit second = new RCommit ("i'm summary", "Emma Miller","d0dc93c857db8d52847ee70f1d5487bc92485955");
//		i.add("remy'sBlob.txt");
//		RCommit third = new RCommit ("summary", "Emma Miller", "2f6e256fb1b04c7a70fb63e3d879b254274726b1");
//		System.out.println (original.convertIndexToArrayList());
	}

}
