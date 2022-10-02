import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.util.Formatter;
import java.util.HashMap;

public class RIndex {
	
	private  String sha1Contents;
	private HashMap<String, String> blobList;
	
	public RIndex() {
		blobList= new HashMap<String, String>();
	}
	
	public void init() {
		new File("./objects").mkdirs();
		File f = new File("index");
		
	}
	
	public void generateHeadFile()
	{
		Path p1 = Paths.get("HEAD");
        try {
            Files.writeString(p1, "", StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void delete(String fileName) throws IOException
	{
		blobList.put(fileName + "deleted", "*deleted* " + fileName);
		
		FileWriter myWriter = new FileWriter("index");
	      
	       
        blobList.forEach((k,v) -> {
			try {
				//myWriter.write(k+" : "+v+"\n");
				myWriter.write(v+"\n");
				System.out.println (v+"\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
        myWriter.close(); 
	}
	
	public void edit(String fileName) throws IOException
	{
		blobList.put(fileName + "edited", "*edited* " + fileName);
		
		FileWriter myWriter = new FileWriter("index");
	      
	       
        blobList.forEach((k,v) -> {
			try {
				//myWriter.write(k+" : "+v+"\n");
				myWriter.write(v+"\n");
				System.out.println (v+"\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
        myWriter.close(); 
	}
	
	//adds the blob of the file contents to the object folder
	//also adds to the index file: the key value pair of file name and the corresponding sha1(of contents)
	public void add(String fileName) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get("./"+fileName));
        String contents=  new String(encoded, StandardCharsets.UTF_8); 
		
		sha1Contents=getSha1Name(contents);
		
		RBlob b = new RBlob (fileName);
//		
//		File sha1File = new File("./object/"+sha1Code);//is it ok to add it here?
//        sha1File.createNewFile();
  
        
       // blobList.put(fileName, sha1Code);
       // Blob blob= new blob(m_testDirectory +filename, false , true);
		
//		File sha1File = new File("./objects/"+sha1Contents);
//        sha1File.createNewFile();
        
        blobList.put(fileName, ("blob : " + sha1Contents + " " + fileName));
        
        FileWriter myWriter = new FileWriter("index");
      
       
        blobList.forEach((k,v) -> {
			try {
				//myWriter.write(k+" : "+v+"\n");
				myWriter.write(v+"\n");
				System.out.println (v+"\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
        myWriter.close();  
		
	}
	
	public void resetRIndexArrayList()
	{
		blobList.clear();
	}
	
	public void remove(String fileToRemove) throws IOException {
		String sha1OfFileToRemove= blobList.get(fileToRemove).substring(0,40);
		blobList.remove(fileToRemove);
		
		new FileWriter("index", false).close();
        FileWriter myWriter = new FileWriter("index");
      
       //myWriter.write(blobList.toString()+"\n"); //does this work or do i need to for loop thru it
        blobList.forEach((k,v) -> {
			try {
				myWriter.write(v+"\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
        myWriter.close();
		
        
        String path= "./objects/"+ sha1OfFileToRemove;
		Files.delete(Paths.get(path));
        
        
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
