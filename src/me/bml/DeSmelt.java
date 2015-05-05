package me.bml;


//import java.util.ArrayList;
//import java.util.List;
import java.util.logging.Logger;





import net.md_5.bungee.api.ChatColor;

import org.bukkit.GameMode;
//import org.bukkit.ChatColor;
//import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
//import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
//import org.bukkit.inventory.meta.ItemMeta;
//import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;



public class DeSmelt extends JavaPlugin implements Listener{
	public final Logger logger = Logger.getLogger("Minecraft");
	public static DeSmelt plugin;
	
	
	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been disabled!");
	}
	
	@Override
	public void onEnable(){
		this.getConfig().options().copyDefaults(true);
		this.saveDefaultConfig();
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info("[" + pdfFile.getName() + "] Version " + pdfFile.getVersion() + " has been enabled!");
		getServer().getPluginManager().registerEvents(this, this);
		
		for(String key : getConfig().getKeys(false)){
			int amount = getConfig().getInt(getConfig().getConfigurationSection(key).getName() + ".amount");
			Material outputItem = Material.getMaterial(getConfig().getString(getConfig().getConfigurationSection(key).getName() + ".type"));
			Material input = Material.getMaterial(getConfig().getConfigurationSection(key).getName());
			ItemStack output = new ItemStack(outputItem);
			output.setAmount(amount);
			FurnaceRecipe derp = new FurnaceRecipe(output, input);
			getServer().addRecipe(derp);
			
		}
		
		ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
		int enchantLvl = 10;
		Enchantment unbreaking = Enchantment.DURABILITY;

		pickaxe.addUnsafeEnchantment(unbreaking, enchantLvl);
		pickaxe.addUnsafeEnchantment(Enchantment.DIG_SPEED, 10);
		
		ShapedRecipe pick = new ShapedRecipe (pickaxe);
		pick.shape("odo"," s "," s ");
		pick.setIngredient('o', Material.OBSIDIAN);
		pick.setIngredient('d', Material.DRAGON_EGG);
		pick.setIngredient('s', Material.STICK);
		
		getServer().addRecipe(pick);
		
	}
	
	
	
	//FurnaceRecipe derp = new FurnaceRecipe(new ItemStack(Material.CARROT_ITEM), Material.DIAMOND);
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player player = (Player) sender;
		
		if(commandLabel.equalsIgnoreCase("gamemode") || commandLabel.equalsIgnoreCase("gm")){		
		   if (sender.hasPermission("pasta")){
			if(args.length == 1){
				if(args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("1")){
					player.setGameMode(GameMode.CREATIVE);
				} else if(args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("0")) {
					player.setGameMode(GameMode.SURVIVAL);
					//player.sendMessage(player.getGameMode().toString());
				} 
			} else if(args.length == 0){
				player.sendMessage(ChatColor.RED + "You are currently in " + ChatColor.DARK_AQUA + player.getGameMode().toString() + ChatColor.RED + " mode.");
			} 
			
		   } else {
			   sender.sendMessage(ChatColor.RED + "*waves hand*" + ChatColor.DARK_AQUA + "This is not the command that you are looking for... " + ChatColor.RED + "*end hand wave*");
		   }
		}
		return false;
		
	}
	

}

