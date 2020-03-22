package me.CraftDream.mcdm;

import cn.nukkit.plugin.*;
import me.CraftDream.mcdm.command.*;
import me.CraftDream.mcdm.command.framework.*;

public class mcdm extends PluginBase {
    public static mcdm snowkPlugin;
    public static final String version = "1.0.0";

    @Override
    public void onEnable() {
        snowkPlugin = this;
        getLogger().info("MCdataMining已加载 - 重制By: 梦幻星空(CraftDream) - 原作者: Bear");
        getLogger().info("源码于: https://github.com/CraftDream/MCdataMining 敬请关注更新!");
        CommandFramework.register(this, new CommandHandler("dm"));
    }

    @Override
    public void onDisable() {
        getLogger().info("MCdataMining已卸载");
    }
}
