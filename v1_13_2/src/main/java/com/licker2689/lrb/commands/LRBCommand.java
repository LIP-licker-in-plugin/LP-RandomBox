package com.licker2689.lrb.commands;

import com.licker2689.lrb.RandomBox;
import com.licker2689.lrb.functions.LRBFunction;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("all")
public class LRBCommand implements CommandExecutor, TabCompleter {
    private final RandomBox plugin = RandomBox.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("권한이 없습니다.");
            return false;
        }
        Player p;
        if (args.length == 0) {
            sender.sendMessage(plugin.prefix + "/lrb 생성 <이름> - 랜덤 박스를 생성합니다.");
            sender.sendMessage(plugin.prefix + "/lrb 보상 <이름> - 랜덤 박스 보상을 설정합니다.");
            sender.sendMessage(plugin.prefix + "/lrb cmd <이름> <CustomModalData> - 랜덤 박스 쿠폰 아이템의 커스텀 모델 데이터를 설정합니다.");
            sender.sendMessage(plugin.prefix + "0으로 설정하면 삭제됩니다.");
            sender.sendMessage(plugin.prefix + "/lrb 드랍 <이름> <수량> - 랜덤 박스 오픈시 지급할 보상 갯수를 설정합니다.");
            sender.sendMessage(plugin.prefix + "1 = 1슬롯, 2 = 2개의 슬롯의 아이템 지급");
            sender.sendMessage(plugin.prefix + "1개 2개의 아이템이 아닌 슬롯에 지정된 보상을 지급합니다.");
            sender.sendMessage(plugin.prefix + "/lrb 쿠폰 <이름> - 랜덤 박스 쿠폰 아이템을 설정합니다.");
            sender.sendMessage(plugin.prefix + "/lrb 쿠폰발급 <이름> (닉네임) - 자신 또는 대상에게 랜덤 박스 쿠폰 아이템을 발급합니다.");
            sender.sendMessage(plugin.prefix + "/lrb 목록 - 랜덤 박스 목록을 표시합니다.");
            sender.sendMessage(plugin.prefix + "/lrb 삭제 <이름> - 랜덤 박스를 삭제합니다.");
            sender.sendMessage(plugin.prefix + "/lrb reload - config 설정 파일을 다시 불러옵니다.");
            return false;
        }
        if (args[0].equals("생성")) {
            if (args.length == 1) {
                sender.sendMessage(plugin.prefix + "생성할 랜덤 박스의 이름을 입력해주세요.");
                return false;
            }
            try{
                p = (Player) sender;
            }catch (Exception e){
                sender.sendMessage(plugin.prefix + "플레이어만 사용 가능한 명령어 입니다.");
                return false;
            }
            LRBFunction.createRandomBox(p, args[1]);
            return false;
        }
        if (args[0].equals("보상")) {
            if (args.length == 1) {
                sender.sendMessage(plugin.prefix + "보상을 설정할 랜덤 박스의 이름을 입력해주세요.");
                return false;
            }
            try{
                p = (Player) sender;
            }catch (Exception e){
                sender.sendMessage(plugin.prefix + "플레이어만 사용 가능한 명령어 입니다.");
                return false;
            }
            LRBFunction.openPrizeSetting(p, args[1]);
            return false;
        }
        if (args[0].equals("cmd")) {
            if (args.length == 1) {
                sender.sendMessage(plugin.prefix + "커스텀 모델 데이터를 설정할 랜덤 박스의 이름을 입력해주세요.");
                return false;
            }
            if (args.length == 2) {
                sender.sendMessage(plugin.prefix + "커스텀 모델 데이터를 입력해주세요.");
                return false;
            }
            try{
                p = (Player) sender;
            }catch (Exception e){
                sender.sendMessage(plugin.prefix + "플레이어만 사용 가능한 명령어 입니다.");
                return false;
            }
            LRBFunction.setCustomModelData(p, args[1], args[2]);
            return false;
        }
        if (args[0].equals("드랍")) {
            if (args.length == 1) {
                sender.sendMessage(plugin.prefix + "지급 갯수를 설정할 랜덤 박스의 이름을 입력해주세요.");
                return false;
            }
            if (args.length == 2) {
                sender.sendMessage(plugin.prefix + "지급 갯수를 입력해주세요.");
                return false;
            }
            try{
                p = (Player) sender;
            }catch (Exception e){
                sender.sendMessage(plugin.prefix + "플레이어만 사용 가능한 명령어 입니다.");
                return false;
            }
            LRBFunction.setDrop(p, args[1], args[2]);
            return false;
        }
        if (args[0].equals("쿠폰")) {
            if (args.length == 1) {
                sender.sendMessage(plugin.prefix + "쿠폰을 설정할 랜덤 박스의 이름을 입력해주세요.");
                return false;
            }
            try{
                p = (Player) sender;
            }catch (Exception e){
                sender.sendMessage(plugin.prefix + "플레이어만 사용 가능한 명령어 입니다.");
                return false;
            }
            LRBFunction.openCouponSetting(p, args[1]);
            return false;
        }
        if (args[0].equals("쿠폰발급")) {
            if (args.length == 1) {
                sender.sendMessage(plugin.prefix + "발급할 랜덤 박스의 이름을 입력해주세요.");
                return false;
            }
            if (args.length == 2) {
                try{
                    p = (Player) sender;
                }catch (Exception e){
                    sender.sendMessage(plugin.prefix + "플레이어만 사용 가능한 명령어 입니다.");
                    return false;
                }
                LRBFunction.getCoupon(p, args[1]);
                return false;
            }
            if (args.length == 3) {
                try {
                    Player target = Bukkit.getPlayer(args[2]);
                    LRBFunction.getCoupon(sender, args[1], target);
                    return false;
                } catch (Exception e) {
                    sender.sendMessage(plugin.prefix + "잘못된 대상입니다.");
                    return false;
                }
            }
            return false;
        }
        if (args[0].equals("삭제")) {
            if (args.length == 1) {
                sender.sendMessage(plugin.prefix + "삭제할 랜덤 박스의 이름을 입력해주세요.");
                return false;
            }
            try{
                p = (Player) sender;
            }catch (Exception e){
                sender.sendMessage(plugin.prefix + "플레이어만 사용 가능한 명령어 입니다.");
                return false;
            }
            LRBFunction.deleteRandomBox(p, args[1]);
            return false;
        }
        if (args[0].equals("reload")) {
            LRBFunction.reloadConfig();
            sender.sendMessage(plugin.prefix + "콘피그 설정 파일이 리로드 되었습니다.");
            return false;
        }
        if(args[0].equals("목록")) {
            LRBFunction.listRandomBoxs(sender);
            return false;
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (sender.isOp()) {
            if (args.length == 1) {
                return Arrays.asList("생성", "보상", "쿠폰", "쿠폰발급", "cmd", "드랍", "삭제", "reload", "목록");
            }
            if (args.length == 2) {
                if (plugin.config.get("RandomBoxs") != null) {
                    return plugin.config.getConfigurationSection("RandomBoxs").getKeys(false).stream().collect(Collectors.toList());
                }
            }
        }
        return null;
    }
}
