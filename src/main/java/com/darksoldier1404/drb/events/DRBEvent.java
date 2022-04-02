package com.darksoldier1404.drb.events;

import com.darksoldier1404.dppc.api.inventory.DInventory;
import com.darksoldier1404.dppc.utils.NBT;
import com.darksoldier1404.dppc.utils.Tuple;
import com.darksoldier1404.drb.RandomBox;
import com.darksoldier1404.drb.functions.DRBFunction;
import com.darksoldier1404.drb.functions.SettingType;
import net.minecraft.world.entity.player.PlayerInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class DRBEvent implements Listener {
    private final RandomBox plugin = RandomBox.getInstance();

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        if (e.getInventory() instanceof DInventory) {
            DInventory inv = (DInventory) e.getInventory();
            if (inv.isValidHandler(plugin)) {
                Tuple<String, SettingType> tpl = (Tuple<String, SettingType>) inv.getObj();
                if (tpl.getB() == SettingType.COUPON) {
                    DRBFunction.saveCouponSetting(p, inv);
                }
                if (tpl.getB() == SettingType.PRIZE) {
                    DRBFunction.savePrizeSetting(p, inv);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory() instanceof DInventory) {
            DInventory inv = (DInventory) e.getInventory();
            if (inv.isValidHandler(RandomBox.getInstance())) {
                Tuple<String, SettingType> tpl = (Tuple<String, SettingType>) inv.getObj();
                if (tpl.getB() == SettingType.COUPON) {
                    if (e.getClickedInventory() instanceof DInventory && e.getSlot() != 13) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getItem() != null) {
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (NBT.hasTagKey(e.getItem(), "DRB")) {
                    String name = NBT.getStringTag(e.getItem(), "DRB");
                    DRBFunction.givePrize(e.getPlayer(), name, e.getItem());
                    e.setCancelled(true);
                }
            }
        }
    }
}
