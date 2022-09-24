import java.io.IOException;

public class RCommitTester {

	public static void main(String[] args) throws IOException {
		RCommit original = new RCommit ("i'm tree", "here's the summary", "Emma Miller", "");
		System.out.println ("RCommitTester is running :)");
		RCommit second = new RCommit ("i'm a tree too", "i'm summary", "Emma Miller","d557356b62a05c65503bc45be6b9ca38f8707a94");
		RCommit third = new RCommit ("i'm final tree", "summary", "Emma Miller", "4a2215f634bf545dc23aa3602b9571a7e0fb94dd");

	}

}
