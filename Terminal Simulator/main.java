package CLI;
import java.io.File;
import java.util.Scanner;
import java.time.LocalDateTime;
//get command and pass it to parser to verify it
public class main {
		public static void main(String[] args) 
		{
			while(true)
			{
			System.out.print(System.getProperty("user.dir")+">");//get user directory
			Scanner scan=new Scanner(System.in); 
			String cmd = scan.nextLine();//get cmd as input
			if(cmd.contains("|")) //if cmd contains pipe operator
			{
				String[] cmd_split = cmd.split("\\|"); //split cmd with pipe operator 
				for(int i=0;i<cmd_split.length;i++)
				{
				if(cmd_split[i].toLowerCase()=="exit") //if the input is exit
			    {
					System.exit(0);
				}
				else
				{
				   Parser obj=new Parser();
				   obj.Parse(cmd_split[i].toLowerCase(),true);
				}
			    }
			}
			else
			{
				Parser obj=new Parser();  //parse commands
				cmd=cmd.toLowerCase();
				obj.Parse(cmd,false);
			}
			}
			
		}
}