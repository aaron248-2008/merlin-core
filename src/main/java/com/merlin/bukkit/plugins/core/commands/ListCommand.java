package com.merlin.bukkit.plugins.core.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.merlin.bukkit.plugins.core.commands.libraries.possibilities.CommandPossibility;
import com.merlin.bukkit.plugins.core.commands.pieces.CommandPiece;
import com.merlin.bukkit.plugins.core.path.Path;

public class ListCommand extends Command {

	protected Path title;
	protected String indent = ChatColor.GREEN+"/";
	protected List<CommandPossibility> commandPossibilities;
	
	@Override
	public boolean execute(CommandSender sender) {

		try {
			StringBuilder builder = new StringBuilder();
			builder.append(title.getPath()).append("\n");
			
			for(int i = 0;i<commandPossibilities.size();i++) {
				
				builder.append(indent);
				CommandPossibility possibility = commandPossibilities.get(i);
				List<CommandPiece<?>> pattern = possibility.getCommandPattern();

				for(int j = 0;j<pattern.size();j++) {
					builder.append(pattern.get(j).getDisplay()).append(" ");
				}
				
				
				String description = possibility.getCommand().getDescription();
				if(description==null || description.length()==0) {
					description = "No Description";
				}
				builder.append(ChatColor.WHITE).append("- ").append(description).append("\n");
			}
			
			sender.sendMessage(builder.toString());
			return true;
		} catch(Exception e) {
			sender.getServer().getLogger().warning("Could not execute command: " + e.getMessage());
			return false;
		}

	}

	public Path getTitle() {
		return title;
	}

	public void setTitle(Path title) {
		this.title = title;
	}

	public List<CommandPossibility> getCommandPossibilities() {
		return commandPossibilities;
	}

	public void setCommandPossibilities(
			List<CommandPossibility> commandPossibilities) {
		this.commandPossibilities = commandPossibilities;
	}

	public ListCommand(Path title,
			List<CommandPossibility> commandPossibilities) {
		super();
		this.title = title;
		this.commandPossibilities = commandPossibilities;
	}
	
	public ListCommand(Path title) {
		super();
		this.title = title;
		this.commandPossibilities = new ArrayList<CommandPossibility>();
	}
	
	public void addPossibility(CommandPossibility possibility) {
		commandPossibilities.add(possibility);
	}

	public void addPossibility(Command command, List<CommandPiece<?>> pattern) {
		CommandPossibility possibility = new CommandPossibility();
		possibility.setCommand(command);
		possibility.setCommandPattern(pattern);
		commandPossibilities.add(possibility);
	}

	public String getIndent() {
		return indent;
	}

	public void setIndent(String indent) {
		this.indent = indent;
	}
	
	
}
