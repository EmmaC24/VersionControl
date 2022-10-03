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
import java.util.Formatter;


public class RBlob {
	
	private String sha1Code; 
	
	public RBlob(String path) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
        String contents=  new String(encoded, StandardCharsets.UTF_8); 
        String fileString = new String(encoded);
        System.out.println (fileString);
        sha1Code=getSha1Name(contents);
        
        File sha1File = new File("objects/"+sha1Code);
        sha1File.createNewFile();
        
        FileWriter myWriter = new FileWriter(sha1File);
        //FileWriter myWriter = new FileWriter(sha1File.getName());
        myWriter.write(fileString);
        myWriter.close();
        
	}
	
	public String getSha1FileName() {
		return sha1Code;
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
