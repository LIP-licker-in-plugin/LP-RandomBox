package com.darksoldier1404.drb.functions;

import com.darksoldier1404.dppc.api.inventory.DInventory;
import com.darksoldier1404.dppc.utils.*;
import com.darksoldier1404.drb.RandomBox;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@SuppressWarnings("all")
public class DRBFunction {
    private static final RandomBox plugin = RandomBox.getInstance();

    public static void createRandomBox(Player p, String name) {
        if (isExistRandomBox(name)) {
            p.sendMessage(plugin.prefix + "이미 존재하는 랜덤박스입니다.");
            return;
        }
        plugin.config.set("RandomBoxs." + name + ".Drop", 1);
        plugin.config.set("RandomBoxs." + name + ".CustomModelData", 0);
        p.sendMessage(plugin.prefix + "랜덤박스가 생성되었습니다.");
        saveConfig();
    }

    public static void deleteRandomBox(Player p, String name) {
        if (!isExistRandomBox(name)) {
            p.sendMessage(plugin.prefix + "존재하지 않는 랜덤박스입니다.");
            return;
        }
        plugin.config.set("RandomBoxs." + name, null);
        p.sendMessage(plugin.prefix + "랜덤박스가 삭제되었습니다.");
        saveConfig();
    }

    public static void openCouponSetting(Player p, String name) {
        if (!isExistRandomBox(name)) {
            p.sendMessage(plugin.prefix + "존재하지 않는 랜덤박스입니다.");
            return;
        }
        DInventory inv = new DInventory(null, "랜덤박스 쿠폰 설정", 27, plugin);
        inv.setObj(Tuple.of(name, SettingType.COUPON));
        ItemStack pane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, pane);
        }
        if (plugin.config.get("RandomBoxs." + name + ".Coupon") != null) {
            inv.setItem(13, plugin.config.getItemStack("RandomBoxs." + name + ".Coupon"));
        } else {
            inv.setItem(13, new ItemStack(Material.AIR));
        }
        p.openInventory(inv);
    }

    public static void openPrizeSetting(Player p, String name) {
        if (!isExistRandomBox(name)) {
            p.sendMessage(plugin.prefix + "존재하지 않는 랜덤박스입니다.");
            return;
        }
        DInventory inv = new DInventory(null, "랜덤박스 상자 설정", 54, plugin);
        inv.setObj(Tuple.of(name, SettingType.PRIZE));
        if (plugin.config.get("RandomBoxs." + name + ".Prize") != null) {
            for (String key : plugin.config.getConfigurationSection("RandomBoxs." + name + ".Prize").getKeys(false)) {
                inv.setItem(Integer.parseInt(key), plugin.config.getItemStack("RandomBoxs." + name + ".Prize." + key));
            }
        }
        p.openInventory(inv);
    }

    public static void setDrop(Player p, String name, String sDrop) {
        if (!isExistRandomBox(name)) {
            p.sendMessage(plugin.prefix + "존재하지 않는 랜덤박스입니다.");
            return;
        }
        try {
            plugin.config.set("RandomBoxs." + name + ".Drop", Integer.parseInt(sDrop));
            p.sendMessage(plugin.prefix + "랜덤박스의 드롭이 설정되었습니다.");
        } catch (NumberFormatException e) {
            p.sendMessage(plugin.prefix + "잘못된 값입니다.");
            return;
        }
        saveConfig();
    }

    public static void setCustomModelData(Player p, String name, String sCustomModelData) {
        if (!isExistRandomBox(name)) {
            p.sendMessage(plugin.prefix + "존재하지 않는 랜덤박스입니다.");
            return;
        }
        try {
            plugin.config.set("RandomBoxs." + name + ".CustomModelData", Integer.parseInt(sCustomModelData));
            p.sendMessage(plugin.prefix + "랜덤박스의 커스텀 모델 데이터가 설정되었습니다.");
        } catch (NumberFormatException e) {
            p.sendMessage(plugin.prefix + "잘못된 값입니다.");
            return;
        }
        saveConfig();
    }

    public static void saveCouponSetting(Player p, DInventory inv) {
        String name = ((Tuple<String, SettingType>) inv.getObj()).getA();
        plugin.config.set("RandomBoxs." + name + ".Coupon", inv.getItem(13));
        p.sendMessage(plugin.prefix + "쿠폰이 저장되었습니다.");
        saveConfig();
    }

    public static void savePrizeSetting(Player p, DInventory inv) {
        String name = ((Tuple<String, SettingType>) inv.getObj()).getA();
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) != null) {
                plugin.config.set("RandomBoxs." + name + ".Prize." + i, inv.getItem(i));
            }
        }
        p.sendMessage(plugin.prefix + "보상이 저장되었습니다.");
        saveConfig();
    }

    public static void getCoupon(Player p, String name) {
        if (!isExistRandomBox(name)) {
            p.sendMessage(plugin.prefix + "존재하지 않는 랜덤박스입니다.");
            return;
        }
        if (plugin.config.get("RandomBoxs." + name + ".Coupon") == null) {
            p.sendMessage(plugin.prefix + "쿠폰이 없습니다.");
            return;
        }
        ItemStack coupon = plugin.config.getItemStack("RandomBoxs." + name + ".Coupon");
        if (plugin.config.get("RandomBoxs." + name + ".CustomModelData") != null) {
            ItemMeta meta = coupon.getItemMeta();
            meta.setCustomModelData(plugin.config.getInt("RandomBoxs." + name + ".CustomModelData"));
            coupon.setItemMeta(meta);
        }
        p.getInventory().addItem(NBT.setStringTag(coupon, "DRB", name));
        p.sendMessage(plugin.prefix + "쿠폰을 발급하였습니다.");
    }

    public static void getCoupon(CommandSender p, String name, Player target) {
        if (!isExistRandomBox(name)) {
            p.sendMessage(plugin.prefix + "존재하지 않는 랜덤박스입니다.");
            return;
        }
        if (plugin.config.get("RandomBoxs." + name + ".Coupon") == null) {
            p.sendMessage(plugin.prefix + "쿠폰이 없습니다.");
            return;
        }
        ItemStack coupon = plugin.config.getItemStack("RandomBoxs." + name + ".Coupon");
        if (plugin.config.get("RandomBoxs." + name + ".CustomModelData") != null) {
            ItemMeta meta = coupon.getItemMeta();
            meta.setCustomModelData(plugin.config.getInt("RandomBoxs." + name + ".CustomModelData"));
            coupon.setItemMeta(meta);
        }
        if (target.getInventory().firstEmpty() == -1) {
            p.sendMessage(plugin.prefix + "대상 인벤토리가 가득 찼습니다.");
            return;
        }
        target.getInventory().addItem(NBT.setStringTag(coupon, "DRB", name));
        p.sendMessage(plugin.prefix + "쿠폰을 지급하였습니다.");
    }

    public static void givePrize(Player p, String name, ItemStack cp) {
        if (!isExistRandomBox(name)) {
            p.sendMessage(plugin.prefix + "존재하지 않는 랜덤박스입니다.");
            return;
        }
        if (plugin.config.get("RandomBoxs." + name + ".Prize") == null) {
            p.sendMessage(plugin.prefix + "보상이 없습니다.");
            return;
        }
        int drop = plugin.config.getInt("RandomBoxs." + name + ".Drop");
        List<ItemStack> items = new ArrayList<>();
        for (String key : plugin.config.getConfigurationSection("RandomBoxs." + name + ".Prize").getKeys(false)) {
            items.add(plugin.config.getItemStack("RandomBoxs." + name + ".Prize." + key));
        }
        if (items.size() == 0) {
            p.sendMessage(plugin.prefix + "보상이 없습니다.");
            cp.setAmount(cp.getAmount() - 1);
            return;
        }
        List<ItemStack> item = new ArrayList<>();
        for (int i = 0; i < drop; i++) {
            item.add(items.get(new Random().nextInt(items.size())));
        }
        ItemStack[] playerItems = p.getInventory().getStorageContents();
        Inventory inv = Bukkit.createInventory(null, 36, "보상");
        inv.setContents(playerItems);
        Map<Integer, ItemStack> leftOver = new HashMap<>();
        for (ItemStack i : item) {
            leftOver.putAll(inv.addItem(i));
        }
        if (!leftOver.isEmpty()) {
            p.sendMessage(plugin.prefix + "인벤토리 공간이 부족합니다.");
            return;
        } else {
            for (ItemStack i : item) {
                p.getInventory().addItem(i);
            }
            p.sendMessage(plugin.prefix + "보상을 수령하였습니다.");
            cp.setAmount(cp.getAmount() - 1);
        }
    }

    public static void listRandomBoxs(Player p) {
        if (plugin.config.getConfigurationSection("RandomBoxs") == null) {
            p.sendMessage(plugin.prefix + "랜덤박스가 없습니다.");
            return;
        }
        p.sendMessage(plugin.prefix + "<<< 랜덤박스 목록 >>>");
        for (String key : plugin.config.getConfigurationSection("RandomBoxs").getKeys(false)) {
            p.sendMessage(plugin.prefix + key);
        }
    }

    public static boolean isExistRandomBox(String name) {
        return plugin.config.get("RandomBoxs." + name) != null;
    }

    public static void saveConfig() {
        ConfigUtils.savePluginConfig(plugin, plugin.config);
    }

    public static void reloadConfig() {
        plugin.config = ConfigUtils.reloadPluginConfig(plugin, plugin.config);
        plugin.prefix = ColorUtils.applyColor(plugin.config.getString("Settings.prefix"));
    }
}
