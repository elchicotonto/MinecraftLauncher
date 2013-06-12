package shadowjay1.launcher.commands;

public interface Command
{
	public boolean applies(String command);
	public void run(String command);
}
