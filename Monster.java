package dankest_dungeon;

public class Monster {
    int HP;
    int strength;
    int points;
    String name;
    
    public Monster(){

    }
    
    public void genMon(int HPtemp, int strengthtemp, int pointstemp, String nametemp){
        HP = HPtemp;
        strength = strengthtemp;
        points = pointstemp;
        name = nametemp;
    }
    
    public void die(){
        HP = 0;
        strength = 0;
        points = 0;
        name = "";
    }
}
