package CLI;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter; 
import java.io.IOException; 
import java.io.PrintWriter;
import java.time.LocalDateTime;
class Parser{ 
	private String[] args;
	private String cmd;
	public void Check(String []sent)
    {
    	try {
    	switch(sent[0])
    	{
    	case "cd":  //if the command cd check if it take the right argument and pass it terminal to execute it
    	{
    			Termianl obj=new Termianl();
    			if(sent.length==2)
    			   obj.cd(sent[1]);
    			else
    				System.out.println("The command is taking definitly 1 arguments"); //throw exception if the command take incorrect arguments
    		    break;
    	}
    	case "pwd": //if the command pwd check if it take the right argument and pass it terminal to execute it
    	{
    		Termianl obj=new Termianl();
    		if(sent.length==1)
     			  obj.pwd();
     		else
     			System.out.println("The command doesn't take arguments"); //throw exception if the command take incorrect arguments
    		break;
    	}
    	case "cat": 
    	{
    		Termianl obj=new Termianl();
    		if(sent.length==3)
    		   obj.cat(sent[1],sent[2],false,false);
    		else if(sent.length==4)
    		{
    			if(sent[2].contains(">>"))
    			{
    				 obj.cat(sent[1],sent[3],true,false);
    			}
    			else if(sent[2].contains(">"))
    			{
    				obj.cat(sent[1],sent[3],false,true);
    			}
    			else
    				System.out.println("The command is taking definitly 2 arguments");//throw exception if the command take incorrect arguments
    		}
    		else
    		    System.out.println("The command is taking definitly 2 arguments");
    		break;
    	}
    	case "cp": //if the command cp check if it take the right argument and pass it terminal to execute it
    	{
    		Termianl obj=new Termianl();
    		if(sent.length==3)
    			 obj.cp(sent[1],sent[2]);
    		else
    			 System.out.println("The command is taking definitly 2 arguments"); //throw exception if the command take incorrect arguments
    		break;
    	}
    	case "ls": //if the command ls check if it take the right argument and pass it terminal to execute it
    	{
    		Termianl obj=new Termianl();
			if(sent.length==1)
			   obj.ls(false,false,"");
			else if(sent.length==3)
			{
				if(sent[1].contains(">>"))
				{
					obj.ls(true,false, sent[2]);
				}
				else if(sent[1].contains(">"))
				{
					obj.ls(false,true, sent[2]);
				}
				else
					 System.out.println("The command does't take arguments"); //throw exception if the command take incorrect arguments
			}
			else
			   System.out.println("The command does't take arguments");
			break;
    	}
    	case "clear":  //if the command clear check if it take the right argument and pass it terminal to execute it
    	{
    		Termianl obj=new Termianl();	
    		if(sent.length==1)
 			   obj.clear();
 			else
 				System.out.println("The command does't take arguments"); //throw exception if the command take incorrect arguments
    		break;
    	}
    	case "rm":  //if the command rm check if it take the right argument and pass it terminal to execute it
    	{
    		Termianl obj=new Termianl();  //throw exception if the command take incorrect arguments
    		obj.rm(sent);
    		break;
    	}
    	case "mv":  //if the command mv check if it take the right argument and pass it terminal to execute it
    	{
    			if(sent.length==3)
    			{
    				Termianl obj=new Termianl();
        			obj.mv(sent[1],sent[2]);
    			}
    			else
    				System.out.println("The command is take definitly 2 arguments"); //throw exception if the command take incorrect arguments
    		break;
    	}
    	case "help":  //if the command help check if it take the right argument and pass it terminal to execute it
    	{
    		if(sent.length==1)
			{
    			Termianl obj=new Termianl();
    			obj.help();
			}
			else
				System.out.println("The command doesn't take arguments");  //throw exception if the command take incorrect arguments
    		break;
    	}
    	case "args":   //if the command args check if it take the right argument and pass it terminal to execute it
    	{
    			Termianl obj=new Termianl();
    			if(sent.length==2)
    			  obj.args(sent[1]);
    			else
    				System.out.println("This command is take definitly 1 arguments");  //throw exception if the command take incorrect arguments
    		break;
    	}
    	case "more": //if the command more check if it take the right argument and pass it terminal to execute it
    	{
    			System.out.println(sent.length);
    			if(sent.length==2)
    			{
    				Termianl obj=new Termianl();
    			   System.out.println(sent[1]);
    				obj.more(sent[1],false);
    			}
    			else
    				System.out.println("This command doesn't take arguments");  //throw exception if the command take incorrect arguments
    		break;
    	}
    	case "mkdir":  //if the command mikdir check if it take the right argument and pass it terminal to execute it
    	{
    		Termianl obj=new Termianl();
    		obj.mkdir(sent);
    		break;
    	}
    	case "rmdir":  //if the command rmdir check if it take the right argument and pass it terminal to execute it
    	{	
    			Termianl obj=new Termianl();
    			obj.rmdir(sent);
    		break;
    	}
    	case "date":  //if the command date check if it take the right argument and pass it terminal to execute it
    	{	
    			Termianl obj=new Termianl();
    			if(sent.length==1)
    			   obj.date();
    			else
    				System.out.println("This command isn't take arguments");  //throw exception if the command take incorrect arguments
    		break;
    	}
    	case "exit":   //if the command date check if it take the right argument and pass it terminal to execute it
     	{
    		Termianl obj=new Termianl();
    		if(sent.length==1)
 			   obj.exit();
 			else
 				System.out.println("This command isn't take arguments");  //throw exception if the command take incorrect arguments
    	}
    	default :
    	{
			System.out.println("This command is incorrect"); //throw exception if the command take incorrect arguments
    		break;
    	}
    	}
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
	//to check the command is right or incorrect
    public boolean compare(char b)
    {
    	if(b=='/' || b=='?' || b==':' || b=='<' || b=='|' || b=='"' || b=='*')
    		return true;
    	else
    		return false;
    }
    //to parse the command and pass it to terminal to execute
	public void Parse(String input,boolean pipe) 
    {
		try {
			String args[]=input.split(" "); //split the cmd into arguments 
    	    for(int i=0;i<args.length;i++)
    	    {
    	    	for(int u=0;u<args[0].length();u++)
    	    	{
    	    		char a=input.charAt(u);
    	    	    if(compare(a))
    	    	    {
    	    		    throw new Exception("The system can't recognize this command");
    	    	    }
    	    	}
    	    }
			Check(args); //check if the command is exist 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
	public String getCmd() {
		return cmd;
	} 
	public String[] getArguments()
	{
	    return args;	
	}
}
