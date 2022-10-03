package Git;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.Instant;
import java.util.Formatter;
import java.util.LinkedList;



public class Commit {
	
	private String pTree; 
	//will be null or will point to a Filename or Path or String of a SHA1 file inside the objects folder
	//initialize in the contrustor
	private String summary;
	private String authorC;
	private String date;
	private String sha1ContentsCode;
	private String pathAndSummarySha1;
	
	public Commit(String pTreePath, String recentChangesSummary, String author, ListNode2 parent ) throws IOException {
		//is this how the pointer param would be ^^^
		
		pTree= pTreePath;
		summary= recentChangesSummary;
		authorC=author;
		
		LinkedList<ListNode2> commitList=new LinkedList<ListNode2>();
		commitList.add(parent);
		
		byte[] encoded = Files.readAllBytes(Paths.get(pTree));
        String contents=  new String(encoded, StandardCharsets.UTF_8); 
        String fileString = new String(encoded); //the og file in string form, not in sha form
        sha1ContentsCode=getSha1Name(contents);
       
        pathAndSummarySha1= getSha1Name(summary+date+authorC+pTree );
        
        File sha1File = new File(".\\objects\\"+pathAndSummarySha1);
        sha1File.createNewFile();
        
        FileWriter myWriter = new FileWriter(sha1File.getName());
        myWriter.write(pTree+"\n");
//        myWriter.write();//2nd line is the file location of the previous commit (can be blank / null)
        myWriter.write(parent+"\n");
//        myWriter.write();//file location of next commit
        
        myWriter.write(author+"\n");
        myWriter.write(getDate()+"\n");
        myWriter.write(summary+"\n");
        myWriter.close();
		
	}
	
	public static String getDate() { //revisit
		return Instant.now().toString();
	}
	
	private static String getSha1Name(String password)
	{
	    String sha1 = "";
	    try
	    {
	        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        crypt.update(password.getBytes("UTF-8"));
	        sha1 = byteToHex(crypt.digest());
	    }
	    catch(NoSuchAlgorithmException e)
	    {
	        e.printStackTrace();
	    }
	    catch(UnsupportedEncodingException e)
	    {
	        e.printStackTrace();
	    }
	    return sha1;
	}

	private static String byteToHex(final byte[] hash)
	{
	    Formatter formatter = new Formatter();
	    for (byte b : hash)
	    {
	        formatter.format("%02x", b);
	    }
	    String result = formatter.toString();
	    formatter.close();
	    return result;
	}
	
	
}
