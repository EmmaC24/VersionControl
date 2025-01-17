package Git;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class RTree {
	ArrayList<String> list = new ArrayList<String>();
	String fileName;
	String previousTree;
	public RTree(ArrayList<String> input, String parentTree) throws IOException {
		previousTree = parentTree;
		list = input;
		String temp = "";
		RTreeHelper help = new RTreeHelper();
		for(int k = 0; k < list.size(); k++) {
	
			if (k != list.size()-1)
			{
				temp += list.get(k) + "\n";
			}
			else
			{
				temp += list.get(k);
			}
		}
		
		fileName = help.shaify(temp);
		File f = new File("objects/"+fileName);
		RTreeHelper.writeTo(f,temp);
		System.out.println ("TREE CONTENTS: " + temp);
		
		
	}
	
	public String getParent()
	{
		return previousTree;
	}
	
	public String getSetName() {
		return fileName;
	}
}