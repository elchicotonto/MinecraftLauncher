package shadowjay1.launcher.commands;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import shadowjay1.launcher.CommandUtil;
import shadowjay1.launcher.Launcher;

public class LoginCommand implements Command
{
	@Override
	public boolean applies(String command)
	{
		if(command.matches("login(?: .*)?"))
		{
			return true;
		}

		return false;
	}

	@Override
	public void run(String command)
	{
		String[] args = CommandUtil.getArguments(command);

		if(args.length == 1)
		{
			Console console = System.console();

			char[] password = console.readPassword("Password: ");

			try
			{
				URLConnection connection = new URL("https://login.minecraft.net").openConnection();
				connection.setDoOutput(true);

				connection.connect();

				connection.getOutputStream().write(("user=" + args[0] + "&password=" +new String(password) + "&version=" + Launcher.getVersion()).getBytes());

				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

				String line = reader.readLine();

				System.out.println(line);

				String[] split = line.split(":");
				
				if(split.length == 5)
				{
					String username = split[3];
					String session = split[4];
					
					Process proc = Runtime.getRuntime().exec(new String[]{"java", "-Xms512m", "-Xmx1g", "-Djava.library.path=" + Launcher.getMinecraftLocation().replaceAll(" ", "\\ ") +"natives/", "-cp", "\"" + Launcher.getMinecraftLocation().replaceAll(" ", "\\ ") +"minecraft.jar:" + Launcher.getMinecraftLocation().replaceAll(" ", "\\ ") + "lwjgl.jar:" + Launcher.getMinecraftLocation().replaceAll(" ", "\\ ") + "lwjgl_util.jar\"", "net.minecraft.client.Minecraft", username, session});
					
					JarClassLoader loader = new JarClassLoader(new File(Launcher.getMinecraftLocation() + "minecraft.jar"));
					
					BufferedReader br = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
					
					String l;
					
					while((l = br.readLine()) != null)
					{
						System.err.println(l);
					}
				}			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("Usage: login <username>");
		}
	}
}
