package me.regos.armorstandgui.events;

import me.regos.armorstandgui.ArmorStandGUI;
import me.regos.armorstandgui.commands.ArmorStandCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MenuHandler implements Listener {

    ArmorStandGUI plugin;

    public MenuHandler(ArmorStandGUI plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMenuClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();


        if(e.getClickedInventory().equals(ArmorStandGUI.main_menu)){
            switch (e.getCurrentItem().getType()){
                case ARMOR_STAND:
                    player.sendMessage("Opened Armor Stand Create Menu");
                    player.closeInventory();
                    plugin.openCreateMenu(player);
                    break;
                case BARRIER:
                    player.sendMessage("Closing Main Menu");
                    player.closeInventory();
                    break;
            }
            e.setCancelled(true);
        }else if(e.getClickedInventory().equals(ArmorStandGUI.create_menu)){

            if(!plugin.armorstands.containsKey(player)) {
                ArmorStand stand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
                plugin.armorstands.put(player, stand);
                stand.setInvisible(true);
            }

            switch(e.getCurrentItem().getType()){
                case ARMOR_STAND:
                    player.sendMessage("Add arms?");
                    plugin.openConfirmMenu(player, Material.ARMOR_STAND);
                    break;
                case GLOWSTONE:
                    player.sendMessage("Add glow?");
                    plugin.openConfirmMenu(player, Material.GLOWSTONE);
                    break;
                case IRON_CHESTPLATE:
                    player.sendMessage("Choose Armor?");
                    plugin.openArmorMenu(player);
                    break;
                case STONE_SLAB:
                    player.sendMessage("Add base?");
                    plugin.openConfirmMenu(player, Material.STONE_SLAB);
                    break;
                case GREEN_WOOL:
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("create-message")));
                    if(plugin.armorstands.containsKey(player)){
                        ArmorStand stand = plugin.armorstands.get(player);
                        stand.setInvisible(false);
                        plugin.armorstands.remove(player);
                    }
                    player.closeInventory();
                    break;
                case RED_WOOL:
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("delete-message")));
                    if(plugin.armorstands.containsKey(player)){
                        ArmorStand stand = plugin.armorstands.get(player);
                        stand.remove();
                        plugin.armorstands.remove(player);
                    }
                    break;


            }
            e.setCancelled(true);
        }else if(e.getClickedInventory().equals(ArmorStandGUI.confirm_menu)){
            if(e.getClickedInventory().contains(Material.ARMOR_STAND)) {
                switch (e.getCurrentItem().getType()) {
                    case GREEN_WOOL:
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("confirm-option")));
                        if(plugin.armorstands.containsKey(player)) {
                            ArmorStand stand = plugin.armorstands.get(player);
                            stand.setArms(true);
                        }
                        plugin.openCreateMenu(player);
                        break;
                    case RED_WOOL:
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("cancel-option")));
                        if(plugin.armorstands.containsKey(player)) {
                            ArmorStand stand = plugin.armorstands.get(player);
                            stand.setArms(false);
                        }
                        plugin.openCreateMenu(player);
                        break;
                }
            } else if (e.getClickedInventory().contains(Material.GLOWSTONE)){
                switch (e.getCurrentItem().getType()) {
                    case GREEN_WOOL:
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("confirm-option")));
                        if(plugin.armorstands.containsKey(player)) {
                            ArmorStand stand = plugin.armorstands.get(player);
                            stand.setGlowing(true);
                        }
                        plugin.openCreateMenu(player);
                        break;
                    case RED_WOOL:
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("cancel-option")));
                        if(plugin.armorstands.containsKey(player)) {
                            ArmorStand stand = plugin.armorstands.get(player);
                            stand.setGlowing(false);
                        }
                        plugin.openCreateMenu(player);
                        break;
                }
            } else if (e.getClickedInventory().contains(Material.STONE_SLAB)){
                switch (e.getCurrentItem().getType()) {
                    case GREEN_WOOL:
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("confirm-option")));
                        if(plugin.armorstands.containsKey(player)) {
                            ArmorStand stand = plugin.armorstands.get(player);
                            stand.setBasePlate(true);
                        }
                        plugin.openCreateMenu(player);
                        break;
                    case RED_WOOL:
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("cancel-option")));
                        if(plugin.armorstands.containsKey(player)) {
                            ArmorStand stand = plugin.armorstands.get(player);
                            stand.setBasePlate(false);
                        }
                        plugin.openCreateMenu(player);
                        break;
                }
            }
            e.setCancelled(true);
        }else if(e.getClickedInventory().equals(ArmorStandGUI.armor_menu)){
            if(plugin.armorstands.containsKey(player)) {
                ArmorStand stand = plugin.armorstands.get(player);
                switch (e.getCurrentItem().getType()) {
                    case DIAMOND_HELMET:
                        if(stand.getHelmet().getType() == Material.DIAMOND_HELMET){
                            stand.setHelmet(null);
                            player.sendMessage("Removed");
                        }else {
                            stand.setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                            player.sendMessage("Added");
                        }
                        break;
                    case DIAMOND_CHESTPLATE:
                        if(stand.getChestplate().getType() == Material.DIAMOND_CHESTPLATE){
                            stand.setChestplate(null);
                            player.sendMessage("Removed");
                        }else {
                            stand.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                            player.sendMessage("Added");
                        }
                        break;
                    case DIAMOND_LEGGINGS:
                        if(stand.getLeggings().getType() == Material.DIAMOND_LEGGINGS){
                            stand.setLeggings(null);
                            player.sendMessage("Removed");
                        }else {
                            stand.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                            player.sendMessage("Added");
                        }
                        break;
                    case DIAMOND_BOOTS:
                        if(stand.getBoots().getType() == Material.DIAMOND_BOOTS){
                            stand.setBoots(null);
                            player.sendMessage("Removed");
                        }else {
                            stand.setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                            player.sendMessage("Added");
                        }
                        break;
                    case GREEN_WOOL:
                        player.sendMessage("Armor confirmed");
                        plugin.openCreateMenu(player);
                }
            }
            e.setCancelled(true);
        }
    }
}
