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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
	
	
public class RCommit {
	private String author = "";
	private String parent = "";
	private String next = "";
	private String fileLocation = "";
	private ArrayList <String> indexArrayList;
	private ArrayList <String> deleting;
	private ArrayList <String> editing;
	private ArrayList <String> pointingTrees;
	private String previousTree = "";
	private RTree tree;
	public String date = "";
	public String summary = "";
	private String parentTree ="";
	private String treeToPoint = "";
	private ArrayList <String> allBlobs = new ArrayList <String> ();
		
		public RCommit (String sum, String auth, String par) throws IOException
		{
			parent = par;
			deleting = new ArrayList <String> ();
			editing = new ArrayList <String> ();
			pointingTrees = new ArrayList <String> ();
			indexArrayList = new ArrayList <String> ();
			
			System.out.println("EMPTY indexArrayList: " + indexArrayList);
			indexArrayList = convertIndexToArrayList();
			System.out.println("indexArrayList before tree added: " + indexArrayList);
			System.out.println ("parentTreeName: " + getParentTree(par));

			if (!parent.equals("") && !indexArrayList.contains("*deleted*") && !indexArrayList.contains("*edited*"))
			{
				System.out.println ("IGOTHROUGHTHIS");
				indexArrayList.add("tree : " + getParentTree(par));
				tree = new RTree (indexArrayList, getParentTree(par));
			}
			else
			{
				indexArrayList = generateUpdatedTreeContents (indexArrayList);
				tree = new RTree (indexArrayList, previousTree);
			}
			System.out.println("indexArrayList after tree added: " + indexArrayList);
			
			
			clearIndex();
			indexArrayList.clear();
			System.out.println("indexArrayList after clear(): " + indexArrayList);
			//do we need to create the objects folder?
			summary = sum;
			author = auth;
			
			date = getDate();
			//fileLocation = getLocation();
			createFile();
			updateParent (par);
			
		}
		
		public String getParentString (String commit) throws IOException
		{
			String pString = "";
			if (!parent.equals(""))
			{
				BufferedReader buff = new BufferedReader (new FileReader ("objects/" + commit));
				buff.readLine();
				pString += buff.readLine().substring(8);
				buff.close();

			}
			return pString;
			
		}
		
		public String getCommitTreeSHA (String commit) throws IOException
		{
			String cTree = "";
			{
				BufferedReader buff = new BufferedReader (new FileReader ("objects/" + commit));
				cTree = buff.readLine().substring(8);
				buff.close();

			}
			return cTree;
		}
		
		public ArrayList<String> generateUpdatedTreeContents (ArrayList <String> indexContents) throws IOException
		{
			
			for (int k = 0; k < indexContents.size(); k++)
			{
				if (indexContents.get(k).contains ("*deleted*"))
				{
					deleting.add(indexContents.get(k).substring(10));
					checkTreeForFile (getParentTree (parent), indexContents.get(k).substring(10));
					
				}
				else if (indexContents.get(k).contains ("*edited*"))
				{
					editing.add(indexContents.get(k).substring (9));
				}
			}
			for (int k = 0; k < indexContents.size(); k++)
			{
				if (indexContents.get(k).contains ("*deleted*"))
				{
					checkTreeForFile (getParentTree (parent), indexContents.get(k).substring(10));
					pointingTrees.add(treeToPoint);
				}
				if (indexContents.get(k).contains ("*edited*"))
				{
					checkTreeForFile (getParentTree (parent), indexContents.get(k).substring(9));
					pointingTrees.add(treeToPoint);
				}
			}
			
			previousTree = "";
			String currentCommit = parent;
			while (!currentCommit.equals(""))
			{
				if (pointingTrees.contains(getCommitTreeSHA(currentCommit)))
				{
					previousTree = getCommitTreeSHA (currentCommit);
				}
				currentCommit = getParentString (currentCommit);
			}
			
			allBlobs.add("tree : " + previousTree);
			
			return allBlobs;
		}
		
		
		public String checkTreeForFile (String tree, String fileName)
		{
			File file = new File("objects/" + tree);

			try {
			    Scanner scanner = new Scanner(file);

			    while (scanner.hasNextLine()) {
			        String line = scanner.nextLine();
			        if (line.substring (0,4).equals ("blob"))
			        {
			        	if (!line.contains (fileName)&& !deleting.contains (fileName) && !editing.contains(fileName))
			        	allBlobs.add(line);
			        }
			        if(line.contains(fileName)) { 
			            return line.substring(7,48);
			        }
			        else if (line.substring(0,4).equals("tree"))
			        {
			        	treeToPoint = line.substring(7);
			        	return checkTreeForFile (line.substring(7), fileName);
			        }
			        
			    }
			} catch(FileNotFoundException e) { 
			    System.out.println ("no file found in checkTreeForFile method");
			}
			String error = "this file never existed: " + fileName;
			return error;
		}
		public ArrayList <String> generateArrayListTreeContents(String c) throws IOException
		{
			ArrayList <String> treeContents = new ArrayList <String> ();
			if (getParentString(c).equals(""))
			{
				
			}

			return treeContents;
		}
		
		public ArrayList <String> getindexArray()
		{
			return indexArrayList;
		}
	
		public void clearIndex() throws IOException
		{
			File clearedIndex = new File ("index");
			clearedIndex.delete();
			clearedIndex.createNewFile();
		}
		
		
		
		public String getParentTree (String p) throws IOException
		{
			String parentT = "";
			if (!parent.equals(""))
			{
				BufferedReader b = new BufferedReader (new FileReader ("objects/" + p));
				parentT += b.readLine().substring(8);
				b.close();

			}
			return parentT;
		}
		
		public ArrayList <String> convertIndexToArrayList () throws IOException
		{
			BufferedReader br = new BufferedReader (new FileReader ("index"));
			ArrayList <String> indexContents = new ArrayList <String>();
			String currentLine = br.readLine();
			while (currentLine != null)
			{
				if (!currentLine.equals(""))
				{
					if (currentLine.contains ("*deleted*"))
					{
						deleting.add(currentLine.substring (9));
					}
					else if (currentLine.contains(("*edited*")))
					{
						editing.add(currentLine.substring(8));
					}
					else
					{
					indexContents.add(currentLine);
					}
				}
				currentLine = br.readLine();
			}
			br.close();
			return indexContents;
		}
		
		
		public void updateParent(String p) throws IOException
		{
			String parentContents = "";
//			String firstLine = "";
			if (!parent.equals(""))
			{
				BufferedReader buffy = new BufferedReader (new FileReader ("objects/" + p));
//				firstLine = buffy.readLine();
//				parentTree = firstLine.substring (8);
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
		
		public RTree getTree()
		{
			return tree;
		}
		
		
		public String getFileContents()
		{
			String contents = "";
			contents += "objects/" + tree.getSetName() + "\n";
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
			contents += "objects/" + tree.getSetName() + "\n";
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
