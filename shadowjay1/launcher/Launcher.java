package shadowjay1.launcher;

import java.io.Console;
import java.util.ArrayList;

import shadowjay1.launcher.commands.Command;
import shadowjay1.launcher.commands.LoginCommand;

public class Launcher
{
	private static ArrayList<Command> registeredCommands = new ArrayList<Command>();
	
	public static void main(String[] args)
	{
		registerCommand(new LoginCommand());
		
		requestCommand();
	}
	
	public static void registerCommand(Command command)
	{
		registeredCommands.add(command);
	}
	
	public static void requestCommand()
	{
		Console console = System.console();
		
		String command = console.readLine("> ");
		
		boolean ran = false;
		
		for(Command c : registeredCommands)
		{
			if(c.applies(command))
			{
				c.run(command);
				
				ran = true;
				
				break;
			}
		}
		
		if(!ran)
		{
			System.out.println("Command not found!");
		}
	}
	
	public static String getMinecraftLocation()
	{
		String osname = System.getProperty("os.name").toLowerCase();
		
		if(osname.contains("linux"))
		{
			return System.getProperty("user.home") + "/.minecraft/bin/";
		}
		else if(osname.contains("windows"))
		{
			throw new RuntimeException("Operating system not supported.");
		}
		else if(osname.contains("mac"))
		{
			return System.getProperty("user.home") + "/Library/Application Support/minecraft/bin/";
		}
		
		return ".";
	}
	
	public static int getVersion()
	{
		return 13;
	}
}
