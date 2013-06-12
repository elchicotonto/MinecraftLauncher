package shadowjay1.launcher;

public class CommandUtil
{
	public static String[] getArguments(String command)
	{
		String[] split = command.split(" ");
		
		String[] arguments = new String[split.length - 1];
		
		for(int i = 1; i < split.length; i++)
		{
			arguments[i - 1] = split[i];
		}
		
		return arguments;
	}
}
