package dankest_dungeon;
import static dankest_dungeon.Dankest_Dungeon.post;
import java.util.concurrent.ThreadLocalRandom;

public class Room {
    int x = 0;
    int y = 0;
    int mon1 = 0;
    int mon2 = 0;
    int mon3 = 0;
    
    public Room()throws Exception{       
        int[] coordnew = {0,0};
        GenRoom(coordnew, 1);
    }

    public void GenRoom(int[] coordnew, int firstRun) throws Exception{
        String[][] doors = new String[4][2];
        
        // Haal de buren op in de database
        // !PLACEHOLDER!
        int[][] neighbours = {{-1,0,0},{0,1,0},{1,0,0},{-1,0,0}};

        // For elke plaats waar een deur kan zijn in de kammer
        for (int i=0; i<4; i++){
            // Genereer de deuren in de kamer
            int randomNum = ThreadLocalRandom.current().nextInt(1, 4 + 1);
            if (doors[i][0] == null && randomNum >= 3){
                int xtemp = coordnew[0];
                int ytemp = coordnew[1];
                switch(i){
                    case 0:     xtemp--;
                                break;
                    case 1:     ytemp++;
                                break;
                    case 2:     xtemp++;
                                break;
                    case 3:     ytemp--;
                                break;
                }    
                doors[i][0] = Integer.toString(xtemp);
                doors[i][1] = Integer.toString(ytemp);
            }

            // Correcties voor als er buren zijn
            // Zijn er al buren met deuren naar hier?
            if (neighbours[i][2] == 1){
                int xtemp = neighbours[i][0];
                int ytemp = neighbours[i][1];
                doors[i][0] = Integer.toString(xtemp);
                doors[i][1] = Integer.toString(ytemp);

            // Zijn er buren zonder deur naar hier?
            } else if (neighbours[i][2] == -1){
                doors[i][0] = null;
                doors[i][1] = null;
            }
        }

        // Zeker zijn dat in first run er minimaal 1 deur is.
        if (firstRun == 1 && doors[0][0] == null && doors[1][0] == null
            && doors[2][0] == null && doors[3][0] == null){
                doors[2][0] = "1";
                doors[2][1] = "0";
        }

        // Genereer monsters
        int[] monsters = new int[3];
        int monstNum = ThreadLocalRandom.current().nextInt(1, 3 + 1);
        for (int i=0; i<monstNum && firstRun != 1 ; i++){
            int monsterID = 0;
            int monstlvl = ThreadLocalRandom.current().nextInt(1, 100 + 1);
            if (monstlvl <= 50){
                monsterID = 1;
            } else if(monstlvl > 50 && monstlvl <= 100){
                monsterID = 2;
            }
            monsters[i] = monsterID;
        }

        // Save new room to database
        String query  = "INSERT INTO rooms (X,Y,Mon1,Mon2,Mon3) VALUES (" + coordnew[0] + ", " + coordnew[1] + ", " + monsters[0] + ", " + monsters[1] + ", " + monsters[2] + ")";
        post(query);
        
        // Move player to new room
        x = coordnew[0];
        y = coordnew[1];
        mon1 = monsters[0];
        mon2 = monsters[1];
        mon3 = monsters[2];               
    }    
}