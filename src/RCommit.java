import java.io.BufferedReader;
	import java.io.File;
	import java.io.FileNotFoundException;
	import java.io.FileReader;
	import java.io.IOException;
	import java.io.PrintWriter;
	import java.math.BigInteger;
	import java.nio.charset.StandardCharsets;
	import java.nio.file.Files;
	import java.nio.file.Path;
	import java.nio.file.Paths;
	import java.security.MessageDigest;
	import java.security.NoSuchAlgorithmException;
	import java.sql.Date;
	import java.text.DateFormat;
	import java.text.SimpleDateFormat;
	import java.util.Calendar;
	
	
public class RCommit {
	private String author = "";
	private String parent = "";
	private String next = "";
	private String fileLocation = "";
	public String pTree = "";
	public String date = "";
	public String summary = "";
		
		public RCommit (String tree, String sum, String auth, String par) throws IOException
		{
			
			//do we need to create the objects folder?
			pTree = tree;
			summary = sum;
			author = auth;
			parent = par;
			date = getDate();
			//fileLocation = getLocation();
			createFile();
			updateParent (par);
			
			
		}
		
		public void updateParent(String p) throws IOException
		{
			String parentContents = "";
			if (!parent.equals(""))
			{
				BufferedReader buffy = new BufferedReader (new FileReader ("objects/" + p));
				parentContents += buffy.readLine() + "\n";
				parentContents += buffy.readLine() + "\n";
				parentContents += "objects/" + generateSHA1 (getFileNameContents()) + "\n";
				buffy.readLine();
				parentContents += buffy.readLine() + "\n";
				parentContents += buffy.readLine() + "\n";
				parentContents += buffy.readLine();
				
				File newParent = new File ("objects/" + p);
				
				PrintWriter pw = new PrintWriter (newParent);
				pw.print(parentContents);
				pw.close();
			}
		}
		
		public String getTree()
		{
			return pTree;
		}
		
		
		public String getFileContents()
		{
			String contents = "";
			contents += "objects/" + pTree + "\n";
			if (!parent.equals (""))
			{
				contents += "objects/" + parent + "\n";
			}
			else
			{
				contents += "\n";
			}
			
			if (!next.equals(""))
			{
				contents += "objects/" + next + "\n";
			}
			else
			{
				contents += "\n";
			}
			contents += author + "\n";
			contents += date + "\n";
			contents += summary;
			
			return contents;
		}
		
		public String getFileNameContents()
		{
			String contents = "";
			contents += "objects/" + pTree + "\n";
			contents += author + "\n";
			contents += date + "\n";
			contents += summary;
			
			return contents;
		}
		
		public void createFile()
		{
			Path p = Paths.get("objects/" + generateSHA1 (getFileNameContents()));
	        try {
	            Files.writeString(p, getFileContents(), StandardCharsets.ISO_8859_1);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		public String getDate ()
		{
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Calendar cal = Calendar.getInstance();
			String d = dateFormat.format(cal.getTime());
			return d;
		}
		public String generateSHA1 (String input)
		{
			try {
	            // getInstance() method is called with algorithm SHA-1
	            MessageDigest md = MessageDigest.getInstance("SHA-1");
	 
	            // digest() method is called
	            // to calculate message digest of the input string
	            // returned as array of byte
	            byte[] messageDigest = md.digest(input.getBytes());
	 
	            // Convert byte array into signum representation
	            BigInteger no = new BigInteger(1, messageDigest);
	 
	            // Convert message digest into hex value
	            String hashtext = no.toString(16);
	 
	            // Add preceding 0s to make it 32 bit
	            while (hashtext.length() < 32) {
	                hashtext = "0" + hashtext;
	            }
	 
	            // return the HashText
	            return hashtext;
	        }
	        catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException(e);
	        }
		}

	}
