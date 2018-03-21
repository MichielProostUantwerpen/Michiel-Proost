package dankest_dungeon;
import java.sql.*;
import java.util.*;

public class Dankest_Dungeon {

    public static void main(String[] args) throws Exception{
        // Setup Db
        createDB();
        
        // SETUP ROOMS
        String query = "CREATE TABLE IF NOT EXISTS rooms(id INT(10) AUTO_INCREMENT PRIMARY KEY, X INT(10), Y INT(10), Mon1 INT(10), Mon2 INT(10), Mon3 INT(10))";
        createTable(query);
        
        // SETUP GAME
        FrameDungeon frame = new FrameDungeon();
        frame.setVisible(true);
        Room room = new Room();
        Player player = new Player();
        Monster mon1 = new Monster();
        Monster mon2 = new Monster();
        Monster mon3 = new Monster();
        int HP = 20;
        int strength = 5;
        int points = 5;
        String name = "Dietertje";
        
        int menu = 100;
        while(menu != 0){
            System.out.println("HP: " + player.hp);
            System.out.println("Current room " + room.x + "/" + room.y);
            System.out.println("Monsters: " + room.mon1 + " " +  room.mon2 + " " + room.mon3);
            
            // Combat mode
            if(room.mon1 != 0 || room.mon2 != 0 || room.mon3 != 0){
                // Generate monsters
                if(room.mon1 != 0){
                    mon1.genMon(HP, strength, points, name);
                }
                if(room.mon2 != 0){
                    mon2.genMon(HP, strength, points, name);
                }
                if(room.mon3 != 0){
                    mon3.genMon(HP, strength, points, name);
                }
                // Initiate combat
                while(room.mon1 != 0 || room.mon2 != 0 || room.mon3 != 0){
                    if(room.mon1 != 0){
                        System.out.println("1)" + mon1.name + " Hp: " + mon1.HP);
                    }
                    if(room.mon2 != 0){
                        System.out.println("2)" + mon2.name + " Hp: " + mon2.HP);
                    }
                    if(room.mon3 != 0){
                        System.out.println("3)" + mon3.name + " Hp: " + mon3.HP);
                    }
//                    System.out.println("Kill monsters?");
//                    Scanner sc = new Scanner(System.in);
//                    int kill = sc.nextInt();
//                    switch(kill){
//                        default:room.mon1 = 0;
//                                room.mon2 = 0;
//                                room.mon3 = 0;
//                                break;
//                    }
                    // Player turn
                    System.out.println("Attack: monster 1 (1); monster 2 (2); monster 3 (3)");
                    Scanner sc = new Scanner(System.in);
                    menu = sc.nextInt();
                    int damage = 0;
                    switch(menu){
                        case 1: damage = player.attack();
                                if(damage > 0){
                                    System.out.println("-" + damage);
                                    mon1.HP = mon1.HP - damage;
                                    if(mon1.HP <= 0){
                                        room.mon1 = 0;
                                        mon1.die();
                                    }
                                }else{
                                    System.out.println("-" + damage);
                                }
                                break;
                        case 2: damage = player.attack();
                                if(damage > 0){
                                    System.out.println("-" + damage);
                                    mon2.HP = mon2.HP - damage;
                                    if(mon2.HP <= 0){
                                        room.mon2 = 0;
                                        mon2.die();
                                    }
                                }else{
                                    System.out.println("Miss!");
                                }
                                break;
                        case 3: damage = player.attack();
                                if(damage > 0){
                                    System.out.println("Hit!");
                                    mon3.HP = mon3.HP - damage;
                                    if(mon3.HP <= 0){
                                        room.mon3 = 0;
                                        mon3.die();
                                    }
                                }else{
                                    System.out.println("Miss!");
                                }
                                break;
                        default:break;
                    }
                }
            }
            
            // Exploration mode
            int[] coordnew = {room.x,room.y};
            System.out.println("What direction? (left = 1, up = 2, right = 3, down = 4): ");
            Scanner sc = new Scanner(System.in);
            menu = sc.nextInt();
            switch(menu){
                case 1: coordnew[0] = (room.x-1);
                        break;
                case 2: coordnew[1] = (room.y+1);
                        break;
                case 3: coordnew[0] = (room.x+1);
                        break;
                case 4: coordnew[1] = (room.y-1);
                        break;
                default:menu = 0;
                        break;
            }
            System.out.println("----------------------------------------------");
            player.move(coordnew ,room);
        }
    }

    // Connection method
    public static Connection DBconnection() throws Exception{
       try{
           String driver = "com.mysql.jdbc.Driver";
           // AANPASSEN VOOR ANDERE SERVER????
           String url = "jdbc:mysql://localhost:3306/dankest_dungeon?useSSL=false";
           String username = "root";
           String password = "Pwd35979";
           Class.forName(driver);
           
           Connection con = DriverManager.getConnection(url,username,password);
           return con;
       }catch(Exception ex){
           System.out.println(ex);
       }
       return null;
    }
    
    // insertion method
    public static void post(String query) throws Exception{
        try{
            Connection con = DBconnection();
            PreparedStatement posted = con.prepareStatement(query);
            posted.executeUpdate();
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    
    public static ResultSet get(String query) throws Exception{
       try{
           Connection con = DBconnection();
           PreparedStatement statement = con.prepareStatement(query);
           ResultSet result = statement.executeQuery();
           return result;
       }catch(Exception ex){
           System.out.println(ex);
       }
       return null;
    }
    
    public static void createTable(String query) throws Exception{
        try{
            Connection con = DBconnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.executeUpdate();
        }catch(Exception ex){
           System.out.println(ex);
       }
    }
    
    public static void createDB() throws Exception{
        try{
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/?useSSL=false";
            String username = "root";
            String password = "Pwd35979";
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url,username,password);
            PreparedStatement statement = con.prepareStatement("CREATE DATABASE dankest_dungeon");
            statement.executeUpdate();
        }catch(Exception ex){
           System.out.println(ex);
       }
    }
}
