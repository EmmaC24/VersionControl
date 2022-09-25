import java.io.IOException;
import java.util.ArrayList;


public class RTreeTester {

	public static void main(String[] args) throws IOException {
		ArrayList <String> bt= new ArrayList <String> ();
		bt.add("blob : 22395720752039");
		bt.add("tree: 29847937030");
		bt.add("blob : 7937459834");
		
		RTree tree = new RTree (bt, "");

	}

}
