package Git;
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
		
		public RCommit (String sum, String auth) throws IOException
		{
			
			if (getHead()!= null)
			{
				parent = getHead();
			}
			else
			{
				parent = "";
			}
			deleting = new ArrayList <String> ();
			editing = new ArrayList <String> ();
			pointingTrees = new ArrayList <String> ();
			indexArrayList = new ArrayList <String> ();
			
			System.out.println("EMPTY indexArrayList: " + indexArrayList);
			indexArrayList = convertIndexToArrayList();
			System.out.println("indexArrayList before tree added: " + indexArrayList);
			System.out.println ("parentTreeName: " + getParentTree(parent));//changed to parent
			
			if (editing.size() == 0 && deleting.size() == 0)
			{
				System.out.println ("i go through regular");
				if (!parent.equals(""))
				{
					indexArrayList.add("tree : " + getParentTree(parent));//changed to parent
				}
				//System.out.println ("IGOTHROUGHTHIS");
				
				tree = new RTree (indexArrayList, getParentTree(parent));//changed to parent
				//System.out.println("indexArrayList after tree added: " + indexArrayList);
			}
			else
			{
				System.out.println ("i go through edited/deleted");
				ArrayList <String> beforeAdded = new ArrayList <String>();
				beforeAdded = generateUpdatedTreeContents();
				for (int k = 0; k < indexArrayList.size(); k++)
				{
					beforeAdded.add(0,(indexArrayList.get (k)));
				}
				//indexArrayList = generateUpdatedTreeContents();
				System.out.println ("updated arrayList w/ extra blobs: " + indexArrayList);
				tree = new RTree (beforeAdded, previousTree);
				
			}
			
			clearIndex();
			indexArrayList.clear();
			System.out.println("indexArrayList after clear(): " + indexArrayList);
			//do we need to create the objects folder?
			summary = sum;
			author = auth;
			
			date = getDate();
			//fileLocation = getLocation();
			createFile();
			updateParent (parent);//changed to parent for par
			updateHead (generateSHA1 (getFileNameContents()));
			
		}
		
		
		public void updateHead(String c) throws IOException
		{
			File resetHead = new File ("HEAD");
			resetHead.delete();
			resetHead.createNewFile();
			PrintWriter printWriter3 = new PrintWriter ("HEAD");
			printWriter3.print(c);
			printWriter3.close();
		}
		
		public String getHead () throws IOException
		{
			BufferedReader buff2 = new BufferedReader (new FileReader ("HEAD"));
			return buff2.readLine();
		}
		
		
		public String getParentString (String commit) throws IOException
		{
			String pString = "";
			if (!parent.equals(""))
			{
				BufferedReader buff = new BufferedReader (new FileReader ("objects/" + commit));
				buff.readLine();
				
				pString += buff.readLine();
				if (!pString.equals(""))
				{
					pString = pString.substring (8);
				}
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
		
		public ArrayList<String> generateUpdatedTreeContents()  throws IOException
		{
			//(ArrayList <String> indexContents)
			
			for (int k = 0; k < deleting.size(); k++)
			{
				checkTreeForFile (getParentTree (parent), deleting.get(k));
				addLeftOverFiles (treeToPoint);
				pointingTrees.add(getTreeBefore(treeToPoint));
			}
			
			for (int k = 0; k < editing.size(); k++)
			{
				System.out.println ("i got into editing");
				checkTreeForFile (getParentTree(parent), editing.get(k));
				addLeftOverFiles (treeToPoint);
				System.out.println ("treeat: " + treeToPoint);
				System.out.println ("treebefor: " + getTreeBefore(treeToPoint));
				pointingTrees.add(getTreeBefore(treeToPoint));
			}
			System.out.println("Pointing trees array list: "+ pointingTrees);
			if (!pointingTrees.contains(""))
			{	
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
				System.out.println ("previousTree: " + previousTree);
				System.out.println ("All Blobs " + allBlobs);
				allBlobs.add("tree : " + previousTree);
				System.out.println ("All Blobs " + allBlobs);
			}
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
			        	if ((!line.contains (fileName)) && (!deleting.contains (line.substring(48))) && (!editing.contains(line.substring(48))))
			        	{
			        		System.out.println ("------ blob being added" + line);
			        		allBlobs.add(line);
			        	}
			        }
			        if(line.contains(fileName)) { 
			            return line.substring(7,48);
			        }
			        else if (line.substring(0,4).equals("tree"))
			        {
			        	treeToPoint = line.substring(7);
			        	System.out.println ("TreeToPoint: " + treeToPoint);
			        	return checkTreeForFile (line.substring(7), fileName);
			        }
			        
			    }
			} catch(FileNotFoundException e) { 
			    System.out.println ("no file found in checkTreeForFile method");
			}
			String error = "this file never existed: " + fileName;
			return error;
		}
		
		public void addLeftOverFiles (String currentTree) throws IOException
		{
			System.out.println ("this is current tree: " + currentTree);
			String c = parent;
			while (!c.equals(""))
			{
				if (getCommitTreeSHA (c).equals(currentTree))
				{
					File file = new File("objects/" + currentTree);
					try {
					Scanner scanner = new Scanner(file);

				    while (scanner.hasNextLine()) {
				        String line = scanner.nextLine();
				        System.out.println ("this is current line: " +line);
				        if (line.substring (0,4).equals ("blob"))
				        {
				        	if (!deleting.contains (line.substring(48)) && !editing.contains(line.substring(48)) && !allBlobs.contains(line))
				        	{
				        		allBlobs.add(line);
				        	}
				        }
				        
				    }
				} catch(FileNotFoundException e) { 
				    System.out.println ("no file found in addLeftOverFiles method");
				}
				}
				c = getParentString (c);
			}
		}
		public String getTreeBefore (String currentTree) throws IOException
		{
			String treeBefore = "";
			BufferedReader buffy = new BufferedReader (new FileReader ("objects/" + currentTree));
			String line = buffy.readLine();
			while (line != null)
			{
				if (line.contains("tree"))
				{
					treeBefore = line.substring (7);
				}
				line = buffy.readLine();
			}
			return treeBefore;
//			String c = parent;
//			String treeBefore = "";
//			while (!c.equals(""))
//			{
//				if (getCommitTreeSHA (c).equals(currentTree))
//				{
//					return getCommitTreeSHA(getParentString (c));
//				}
//				c = getParentString (c);
//			}
//			return "";
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
						deleting.add(currentLine.substring (10));
					}
					else if (currentLine.contains(("*edited*")))
					{
						editing.add(currentLine.substring(9));
					}
					else
					{
					indexContents.add(currentLine);
					}
				}
				currentLine = br.readLine();
			}
			
			System.out.println ("deleting arrayList: " + deleting);
			System.out.println ("editing arrayList: " + editing);
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
		
		public void createFile() throws IOException
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
