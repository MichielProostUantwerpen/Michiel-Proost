package dankest_dungeon;
import static dankest_dungeon.Dankest_Dungeon.get;
import java.sql.ResultSet;
import java.util.concurrent.ThreadLocalRandom;

public class Player {
    int hp;
    int score;
    int difficulty;
    int strength;
    
    public Player(){
        hp = 100;
        score = 0;
        difficulty = 0;
        strength =0;
    }
    
    public void move(int[] coordnew, Room room) throws Exception{
        String query = "SELECT Id FROM rooms WHERE X=" + coordnew[0] + " AND Y=" + coordnew[1] +"";
        ResultSet result = get(query);
        if(!result.next()){
            room.GenRoom(coordnew, 0);
        }else{
            room.x=coordnew[0];
            room.y=coordnew[1];
        }
    }
    
    public int attack(){
        int hit = ThreadLocalRandom.current().nextInt(1, 20 + 1);
        int damage = 0;
        if(hit >= 6){
            damage = ThreadLocalRandom.current().nextInt(1, 10 + 1);
        }
        return damage;
    }
}