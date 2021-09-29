package net.pl3x.map.plugin.task;

import net.pl3x.map.api.MapWorld;
import net.pl3x.map.plugin.configuration.WorldConfig;
import net.pl3x.map.plugin.util.FileUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.GameRule;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdateTime extends BukkitRunnable {

    @Override
    public void run() {
        StringBuilder json = new StringBuilder("{\"times\":{\n");
        boolean fill = false;

        for (World world: Bukkit.getWorlds()) {
            WorldConfig mapworld = WorldConfig.get(world);
            if (fill) {
                json.append(",\n");
            }
            fill = true;
            json.append("\t\t");
            json.append('"').append(world.getName()).append('"');
            json.append(":{\"time\":").append(world.getFullTime());
            json.append(",\"advancing\":").append(world.getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE)).append("}");
        }
        json.append("\n\t}\n}");
        FileUtil.write(json.toString(), FileUtil.WEB_DIR.resolve("worldtimes.json"));
    }
}
