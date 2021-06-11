package CLI;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.time.LocalDateTime;   
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
class Termianl {
	private String ans="";

    /**
     * set variable ans with the user answer from cp function
     * @param s user answer
     */
    public void setAns(String s){
        ans=s;
    }

    /**
     * get the answer of the user from cp function
     * @return ans(user answer)
     */
    public String getAns(){
        return ans;
    }

    /**
     * copy file from source to destination
     * @param sourcePath the source of the file to copy
     * @param destinationPath the destination where file copying to
     */
    public  void cp(String sourcePath, String destinationPath){
        Scanner scan=new Scanner(System.in);
        String fileName=""; // to store file name
        String destPath=""; // to store the destination path
        File destinationFile=new File(destinationPath);
        if(destinationFile.isDirectory()) { // in case of the destination is directory
            for (int i = sourcePath.length() - 1; i > -1; i--) {
                if (sourcePath.charAt(i) == '\\') {
                    fileName = sourcePath.substring(i + 1);  //get the name of the file to name it in destination
                    break;
                }
            }
            destPath=destinationPath;
            destinationPath=destinationPath + "\\" + fileName; //adding file name to destination
        }else{
            for (int i = destinationPath.length() - 1; i > -1; i--) {
                if (destinationPath.charAt(i) == '\\') {
                    fileName = destinationPath.substring(i + 1);
                    destPath = destinationPath.substring(0,i);
                    break;
                }
            }
        }
        try {
            File fileDestination = new File(destPath);
            File[] filesList = fileDestination.listFiles();
            if (filesList != null) {
                for (int i = 0; i < filesList.length; i++) {
                    if (fileName.equals(filesList[i].getName())) {
                        System.out.println("there is file with the same name.\n" +
                                "do you want to override the file(yes/no)");  // ask user if he want's to override the file if exist
                        String userAns = scan.nextLine();
                        this.setAns(userAns);    // set ans
                        if (ans.equalsIgnoreCase("no"))
                            return;
                    }
                }
            }

            FileReader source = new FileReader(sourcePath);
            FileWriter destination = new FileWriter(destinationPath);

            int i;          // store the byte of the file
            while ((i = source.read()) != -1) {
                destination.write(i);           // copying the file into destination
            }
            source.close();
            destination.close();
        } catch (Exception e) {
            System.out.println("Incorrect file name or directory");
        }

    }

    /**
     * moving from source to distination
     * @param sourcePath the source of the file to move
     * @param destinationPath the destination where file moving to
     */
    public void mv(String sourcePath, String destinationPath){
        try{
        	this.setAns("");
            File Source=new File(sourcePath);
            cp(sourcePath,destinationPath);
            if(this.getAns().equalsIgnoreCase("yes")||this.getAns().equalsIgnoreCase(""))// if user choose to override the file
                Source.delete();   
        }
        catch(Exception e){
            System.out.println("Incorrect file name or directory");
        }

    }

    /**
     * list all files and folders in current directory
     * @throws IOException 
     */
    public void ls(boolean isdirect_append,boolean isdirect_overwrite,String path) throws IOException{
    	
    	List<String> list=new LinkedList<String>();  
    	String dir=new File("").getAbsolutePath();
        File directory=new File(dir); // dir = directory
        File[] filesList = directory.listFiles();   // putting all files in this directory into list.
        SimpleDateFormat filesDate=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        if (filesList != null) {
            for(int i=0;i<filesList.length;i++)
            {
                if(isdirect_overwrite==true || isdirect_append==true)
                {
					list.add(filesDate.format(filesList[i].lastModified())+ "     " + filesList[i].getName());
                }
                else
                    System.out.println(filesDate.format(filesList[i].lastModified())
                        + "     " + filesList[i].getName());
            }
        }
        if(isdirect_overwrite==true)
        	Redirection_overwrite(list,path);
        else if(isdirect_append==true)
        	Redirection_append(list,path);
       // System.out.println("File(s)"+filesList.length); // number of files

    }

    /**
     * clear the console screen
     */
    public void clear(){
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
	/**
	 * this function is for making directories
	 * @param args is a string array may contain the Path where to make the directory or flags
	 * @return a boolean that is true if it succeed to make the directory or false if it fails
	 */
	public static boolean mkdir(String args[]) throws Exception{
		if(args[1].equals("-p"))			// incase the function gets -p flag
		{
			/**
			 * count for counting the number of '\' in the directory Path 
			 * start to determine the starting of '{....}' (the '{' ) in case it's exist
			 * end to determine the ending of '{....}' (the '}' ) in case it's exist
			 */
			int count=0,start = -1 , end;				
			for(int i=0;i<args[2].length();i++)			//loop to determine the number of subdirectories by counting the '\' characters in the path
			{
				if(args[2].charAt(i)=='\\')
					{
						count++;
					}
			}
			
			String[] files=args[2].split("\\\\");		//array of strings contain all the sub directories paths
			String filePath=files[0];					//string to point to one subdirectory
			String[] inner = null;						//array of string contains all the subdirectory for the case "{...}"
			for(int i=1;i<count+1;i++)					//loop to point at at one subdirectory at time
			{
				if (files[i].charAt(0)=='{')			//check the case of "{....}" and set the start and the end
				{
					//inner=true;
					start=i;
					for(int j=0;j<files[i].length();j++)		//looping till the end of brackets in the cases of "{...}"
					{
						if(files[i].charAt(j)=='}')
						{
							end=j;
						}
					}
					inner=files[i].split(",");				//set the sub directories in the inner array
					break;
					
				}
				
			}
			String[] newArgs= {" ",files[0]};		// updating the args to call the function with
			
			mkdir(newArgs);							//call the function to make the parent directory  
			for(int i=1;i<count+1;i++) {			//looping to all the inner path
				filePath+='\\';						//adding \ to separate the last path to jump to the next inner one
				if (start==i)						//checking on the case of "{...}"
				{
					for(String looptemp:inner)		//looping at all the inner sub directories in that case 
					{
						String temp=filePath;		//string pointing on the current path
						if(looptemp.equals(inner[0])&&looptemp.charAt(0)=='{')		//checking if the '{' is at the first characters 
							for(int j=1;j<looptemp.length();j++)
							{
								temp+=looptemp.charAt(j);							//ignoring the '{' and add the path to be ..\current sub directory
							}
						else if(looptemp.charAt(looptemp.length()-1)=='}')			//checking if the '}' is at the last characters
							for(int j=0;j<looptemp.length()-1;j++)
							{
								temp+=looptemp.charAt(j);							//ignoring it and add the path to be ..\current sub directory
							}
						else 
							for(int j=0;j<looptemp.length();j++)					
							{
								temp+=looptemp.charAt(j);						//adding the full path name without ignoring any character  to be ..\current sub directory
							}
						File innerFile= new File(temp);							//pointing to this file
						if(!innerFile.mkdir())									//check if it can be created
						{
							throw new Exception("A subdirectory or file "+temp+" already exists.");		//if it cannot throw the exception
						}						
					}
					return true;								//if it's created then return true
				}
				for(int j=0;j<files[i].length();j++)				//incase if it has inner sub directories
					filePath+=files[i].charAt(j);					//adding the name to the path to be ..\current sub directory
				newArgs=new String[] {" ",filePath};				//updating the args with the new path value
				mkdir(newArgs);										//calling the function recursively till creating all the sub directories
			}
			 return true;											//returning true if it created successfully
		}
		else {														//in the case that it has no flags
			File thisFile=new File(args[1]);						//pointing on the file with the current path
			
			if(thisFile.mkdir())
			{
				return true;										//returning true if it created successfully
			}
			else{													
				if(thisFile.exists())
					System.out.println("A subdirectory or file "+args[1]+" already exists.");
				else 
					System.out.println("cannot make such directory.");
				return false;
			}
		}
		
	}//display all commands to help user
	public static void help()
	{
		System.out.println("args\t\tManipulate and output command arguments.\n"+
							"cat\\t\\tConcatenate files and print on the standard output.\n"+
							"cd\t\tDisplays the name of or changes the current directory.\n"+
							"clear\t\tClears the screen.\n"+
							"cp\t\tCopies one or more files to another location.\n"+
							"date\t\tDisplays or sets the date.\n"+
							"exit\t\tQuits the CMD.EXE program (command interpreter).\n"+
							"help\t\tProvides Help information for Windows commands.\n"+
							"ls\t\tList directory contents.\n"+
							"mkdir\t\tCreates a directory.\n"+
							"more\t\tDisplays output one screen at a time.\n"+
							"mv\t\tMoves one or more files from one directory to another directory.\n"+
							"pipe\t\tCreate pipe.\n"+
							"pwd\t\tPrint the name of the current working directory.\n"+
							"rm\t\tRemove files or directories.\n"+
							"rmdir\t\tRemoves a directory.\n");
	}//to move in directory deeply
	static void cd( String dir  )
	{
                if( dir.equalsIgnoreCase("..") ) //get the parent 
                {
                    File temp = new File( System.getProperty("user.dir")); //get user directory
                    String new_dir = temp.getParent() ; 
                    System.setProperty("user.dir", new_dir);    //set the new directory
                }
                else{
		String current_dir= System.getProperty("user.dir");
		char input_partion=dir.charAt(0),curr_partion=current_dir.charAt(0);
		if( input_partion == curr_partion) {
		File file = new File(dir);
		if(! file.exists() )System.out.println(" File Not Found "); 
		else{String new_dir=dir;
		System.setProperty( "user.dir" , new_dir );
	    }
		}
		else
                {
                    String tem = System.getProperty("user.dir") ;
                    tem+="\\";
                    tem+=dir;
                    File check = new File(tem);
                    if( !check.exists() )
                         System.out.println("the system can't execute this command"); //if the file is not exist
                    else 
                         System.setProperty( "user.dir" , tem );

                    
        }
        }
    }
	//display the current path
    static void pwd (  )
    {
        System.out.println( "current directory :- " + System.getProperty( "user.dir" ) );
    }
    //display the date
    static void date( )
    {
         Date date = new Date( );
         System.out.println( date.toString() );
    }
    //concatenate two files and display it
    static void cat(String first_file, String second_file,boolean isdirect_append,boolean isdirect_overwrite) throws IOException {
        
    	File fil_1 = new File(first_file);
        File fil_2 = new File(second_file);
        if (!fil_1.exists()) // file existance 
            System.out.println(first_file + " wrong path or file name ");
        else if (!fil_1.canRead()) // first file readable ?
            System.out.println("Can't read the file");
        else if (!fil_2.exists()) //file existance 
            System.out.println(second_file + " wrong path or file name "); //if the files is not exist or unreadable
        else if (!fil_2.canRead()) //first file readable ?
            System.out.println("Can't read the file");
        else {
            if(isdirect_append!=true && isdirect_overwrite!=true)
            {
        	try {
                Scanner read = new Scanner(fil_1); //Scanner to read form the first file.
                while (read.hasNextLine()) { //Util the end of the first file.
                    String data = read.nextLine(); //Take the data line by line.
                    System.out.println(data);
                }
                read.close(); //Close the file.
                read = new Scanner(fil_2); //Scanner to read from the second file.
                while (read.hasNextLine()) { //Until the end of the second file.
                    String data = read.nextLine(); //take the data line by line.
                    System.out.println(data);
                }
                read.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error has occurred...\n"); //throw exception that error occurred while execute
                e.printStackTrace();
            }
            }
            else
            {
            	List<String> list=new LinkedList<String>();  
            	Scanner read = new Scanner(fil_1); //Scanner to read form the first file.
                 while (read.hasNextLine()) { //Util the end of the first file.
                     String data = read.nextLine(); //Take the data line by line.
                    list.add(data);
                 }
                 if(isdirect_append==true)
                	 Redirection_append(list,second_file);
                 else if(isdirect_overwrite==true)
                	 Redirection_overwrite(list,second_file);
            }
        }
    }
    //display a definite number of lines in file in one direction with handling the short path and full path 
    public void more(String arg,boolean pipe)
    {
    	try
    	{
        String List_Of_Lines="";
        File file = new File(arg);
        Scanner scan=new Scanner(System.in);
    	if(file.exists())
    	{
    		BufferedReader reader = new BufferedReader(new FileReader(file)); //read buffer if the text is large
    	    int counter=1;
    		Scanner Read_File=new Scanner(reader);
    		while(Read_File.hasNextLine())
    		{
    			if(counter%10==0)
    			{
    				String ans;
    				while(true)
    				{
    				    System.out.println("Continue or Quit?"); //press continue or quit to stop display
    				    ans=scan.nextLine();
    				    if(ans.toLowerCase().equals("continue") || ans.toLowerCase().equals("quit"))
    				    	break;
    				}
    				if(ans.toLowerCase().equals("continue"))
    					System.out.println('\n');
    				else
    					break;
    			}
    			String line= Read_File.nextLine();
    			if(pipe==true)
    			{
    				if(List_Of_Lines.equals(""))
    				   List_Of_Lines+=line+"\n";
    			}
    			else
    				System.out.print(line);
    			counter++;
    		}
    	}
    	else
    	{
    		String path=new File("").getAbsolutePath();
    		path=path+"\\"+arg;
    		File f2=new File(path);
    		if(f2.exists())
    		{
    			BufferedReader reader = new BufferedReader(new FileReader(f2));
        	    int counter=1;
        		Scanner Read_File=new Scanner(reader);
        		while(Read_File.hasNextLine())
        		{
        			if(counter%10==0)
        			{
        				String ans;
        				while(true)
        				{
        				    System.out.println("Continue or Quit?");
        				    ans=scan.nextLine();
        				    if(ans.toLowerCase().equals("continue") || ans.toLowerCase().equals("quit"))
        				    	break;
        				}
        				if(ans.toLowerCase().equals("continue"))
        					System.out.println('\n');
        				else
        					break;
        			}
        			String line= Read_File.nextLine();
        			if(pipe==true)
        			{
        				if(List_Of_Lines.equals(""))
        				   List_Of_Lines+=line+"\n";
        			}
        			else
        				System.out.print(line);
        			counter++;
        		}	
    		}
    		else
    			 System.out.println("This file isn't exist");
    	}
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
	public void args(String command){
        try {
            switch (command) {
                case "ls":
                case "clear":
                case "pwd":
                case "date":
                case "help":
                    System.out.println("No arg");
                    break;
                case "cp":
                case "mv":
                    System.out.println("arg1: SourcePath, arg2: DestinationPath");
                    break;
                case "rm":
                case "cat":
                case "mkdir":
                case "rmdir":
                    System.out.println("arg1: SourcePath");
                    break;
                case "cd":
                    System.out.println("arg1: directory");
                    break;
                default:
                    System.out.println("'" + command + "'" + " is not recognized as an internal or external command");
                    break;
            }
        }
        catch (Exception e){
            System.out.println(e+" is not recognized as an internal or external command");
        }
    }
	/**
	 * this function for removing a directories
	 * @param args  is a string array may contain the Path where to make the directory or flags
	 * @return a boolean that is true if it succeed to remove the directory or false if it fails
	 */
	public static boolean rmdir (String[] args) {
		if(args[1].equals("-p")||args[1].equals("-pv"))						//in the case it called with the flag "-p" or "-pv"
		{
			int count=0;													//count that count times of '\' repeated in the path to get how many times to removes parent directories
			for(int i=0;i<args[2].length();i++)								//looping to all the path characters to set the count
			{
				if(args[2].charAt(i)=='\\')
					{
						count++;
					}
			}
			
			String[] files=args[2].split("\\\\");						//array of string contain all the parents in the path
			String currLocation=args[2];								//pointing at the current path
					
			String newArgs[]= {" ",currLocation};						//array to update the args to call the function with on the upcoming steps
			//System.out.println(currLocation);
			rmdir(newArgs);												//remove the last directory

			for(int i=count;i>0;i--)									//looping on all the parents paths
			{
				if(args[1].equals("-pv"))								//incase the flag is "-pv" printing what's deleting
					System.out.println("rmdir: removing directory,'\t"+currLocation+"'");
				currLocation=currLocation.substring(0, currLocation.length()- files[i].length()-1);			//removing the last directory from the path because it's deleted 
				newArgs=new String[] {" ",currLocation};		//updating the args with the new path
				rmdir(newArgs);
			}
			//rmdir(newArgs);
			if(args[1].equals("-pv"))								//incase the flag is "-pv" printing what's deleting the last parent directory
				System.out.println("rmdir: removing directory,'\t"+currLocation+'\'');
			return true;											//returning true if it's removed successfully
		}
		
		else {														//incase no flags
			
			File thisFile=new File(args[1]);
			if(thisFile.isDirectory()) {							//trying to delete the directory if it's deleted return true
				if(thisFile.delete())
					return true;
				
				else {												
					System.out.println("The directory is not empty.");
				}
			}
			
			if(!thisFile.exists())
				System.out.println("The system cannot find the file specified.");
			
			else System.out.println("rmdir: failed to remove "+args[1]+": No such file or directory.");
			return false;
		}
		
	}
	/**
	 * this function is for deleting files or directories
	 * @param args is a string array may contain the Path where to make the directory or flags
	 * @return a boolean that is true if it succeed to remove the directory or false if it fails
	 */
	public static boolean rm(String[] args)  {

		if(args[1].equals("-r"))						//incase of getting flag "-r"
		{
			File thisFile = new File(args[2]);			//the current file
			File[] contents;							//file array to get the contains file
			String newArgs[];							//array to update the args to call the function with on the upcoming steps
			
			while (true)								//looping on all the inner files
			{	
				if(thisFile.isDirectory())				//if it's a directory it gets all its directory recursively repeat its selfe 
				{	
					contents= thisFile.listFiles();
					for(File checker:contents)
					{
						if(checker.isDirectory())
						{
							newArgs=new String[]{" ","-r",checker.getAbsolutePath()};			//calling its self on the inner directory
							rm(newArgs);
							checker.delete();													//deleting this directory
						}
						checker.delete();														//deleting it's contents (not a directories)
					}
				}
				thisFile.delete();																// deleting the parent directory
				break;
					
			}return true;
		}
		else {										//incase no flags sent
			File thisFile = new File(args[1]);
	        if (thisFile.delete()) return true;			//if it's deleted returning  true
	        
	        else {										//throws exception otherwise
	        	System.out.println("cannot delete this file, direcory");
	        }
	        return false;
		}
	}//overwrite file
	public static void Redirection_overwrite(List<String> list,String arg) 
	{
		 try {
		 File f1=new File(arg);
		 if(f1.exists()) //if the file is exist with short path
		 {
			 FileWriter fw=new FileWriter(arg);
			 for(int i=0;i<list.size();i++)
			 {
				 fw.write(list.get(i)+'\n');  //write into it and close it
			 }
			 fw.close();
		 }
		 else
		 {
			 String path=new File("").getAbsolutePath(); ////if the file is exist with full path
			 path=path+"\\"+arg;
			 File f2=new File(path);
			 if(f2.exists())
			 {
				 FileWriter fw=new FileWriter(path);
				 for(int i=0;i<list.size();i++)
				 {
					 fw.write(list.get(i)+'\n');  
				 }
				 fw.close();
			 }
			 else //if the file isn't exist so create new file and push it into
			 {
				f2.createNewFile();
				FileWriter fw=new FileWriter(path);
				for(int i=0;i<list.size();i++)
				 {
					 fw.write(list.get(i)+'\n');  
				 }
				 fw.close();
			 }
		 }
		 }catch(Exception e)
		 {
			 e.printStackTrace();
		 }
	}
	// terminate the program
	public void exit()
	{
		System.exit(0);
	}
	//append at the end in file
	public static void Redirection_append(List<String> list,String arg) throws IOException
	{
		
		try {
			 File f1=new File(arg);
			 if(f1.exists()) //if the file is exist in short path we use output stream to write in the file
			 {
			     PrintWriter out = null;
		         out = new PrintWriter(new FileOutputStream(arg, true));
		         for(int i=0;i<list.size();i++)
		            out.append(list.get(i)+'\n');
		         out.close(); //close the file
			 }
			 else
			 {
				 String path=new File("").getAbsolutePath(); //if the file is exist in full path we use output stream to write in the file
				 path=path+"\\"+arg;
				 File f2=new File(path);
				 if(f2.exists())
				 {
					  PrintWriter out = null;
		              out = new PrintWriter(new FileOutputStream(path, true)); 
		              for(int i=0;i<list.size();i++)
		                 out.append(list.get(i)+'\n');
		              out.close(); //close the file
				 }
				 else
				 {
					 PrintWriter out = null; 
		             out = new PrintWriter(path); //if it isn't exist create file and append the list in it
		             for(int i=0;i<list.size();i++)
		                out.append(list.get(i)+'\n');
		             out.close(); //close the file
				 }
			 }
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
}

