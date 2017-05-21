package us.tastybento.bskyblock.database;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import us.tastybento.bskyblock.config.Settings;
import us.tastybento.bskyblock.database.flatfile.FlatFileDatabase;
import us.tastybento.bskyblock.database.mysql.MySQLDatabase;
import us.tastybento.bskyblock.database.objects.Island;
import us.tastybento.bskyblock.database.objects.Players;
import us.tastybento.bskyblock.database.sqlite.SQLiteDatabase;

public abstract class ASBDatabase {
    
    public static ASBDatabase getDatabase(){
        for(DatabaseType type : DatabaseType.values()){
            if(type == Settings.databaseType) return type.database;
        }
        return DatabaseType.FLATFILE.database;
    }
    
    public abstract Players loadPlayerData(UUID uuid);
    public abstract void savePlayerData(Players player);
    
    public abstract Island loadIslandData(String location);
    public abstract void saveIslandData(Island island);
    
    public abstract HashMap<UUID, List<String>> loadOfflineHistoryMessages();
    public abstract void saveOfflineHistoryMessages(HashMap<UUID, List<String>> messages);
    
    public enum DatabaseType{
        FLATFILE(new FlatFileDatabase()),
        MYSQL(new MySQLDatabase()),
        SQLITE(new SQLiteDatabase());
        
        ASBDatabase database;
        
        DatabaseType(ASBDatabase database){
            this.database = database;
        }
    }

    /**
     * Checks in database whether the player is known by the plugin or not
     * @param uniqueID
     * @return true or false
     */
    public abstract boolean isPlayerKnown(UUID uniqueID);

    /**
     * Gets the UUID for player with name. If adminCheck is true, the search will be more extensive
     * @param name
     * @param adminCheck
     * @return UUID of player with name, or null if it cannot be found
     */
    public abstract UUID getUUID(String name, boolean adminCheck);

    /**
     * Associates this name with the UUID
     * @param name
     * @param uuid
     */
    public abstract void savePlayerName(String name, UUID uuid);
    
}