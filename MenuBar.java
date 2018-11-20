import java.awt.*;
import java.util.HashMap;

public class MenuBar {
    private HashMap<String, Menu_Element[]> menu_contents = new HashMap<>();

    public MenuBar(){
        create_menu_HashMap();
    }
    private void create_menu_HashMap(){
        menu_contents.put("life", new Menu_Element[5]);
        menu_contents.put("armor", new Menu_Element[5]);
        menu_contents.put("inventory", new Menu_Element[10]);
    }

    //########################################
    public class Menu_Element {
        public boolean used; //refers to the space inventory used or whether a heart or shield is used
        public boolean equipped; //refers to whether the item in an inventory space is being equipped
        public String kind;
        public double x_pos;
        public double y_pos;
        private String heart;
        private String empty_heart;
        private String armor;
        private String empty_armor;

        public Menu_Element(double x, double y, String k, boolean u){
            this.kind = k;
            this.used = u;
            this.x_pos = x;
            this.y_pos = y;
            this.equipped = false;

            if (this.kind == "life"){
                this.heart = "items/heart.png";
                this.empty_heart = "items/empty_heart.png";
            }else if (this.kind == "armor"){
                this.armor = "items/shield.png";
                this.empty_armor = "items/empty_shield.png";
            }
        }

        //used by the next_action() method of the player object as a result of interaction with the world
        public void remove_life(){
            used = false;
            StdDraw.setPenColor(Color.darkGray);
            StdDraw.filledSquare(x_pos + 0.5, y_pos + 0.5, 0.5);
            StdDraw.picture(x_pos + 0.5, y_pos + 0.5, empty_heart, 1.0, 1.0);
            StdDraw.show();
        }
        public void add_life(){
            used = true;

            StdDraw.setPenColor(Color.darkGray);
            StdDraw.filledSquare(x_pos + 0.5, y_pos + 0.5, 0.5);
            StdDraw.picture(x_pos + 0.5, y_pos + 0.5, heart, 1.0, 1.0);
            StdDraw.show();
        }
        public void add_full_armor(){
            //draw all full shields
            //more armor is added in player class
            int size = menu_contents.get("armor").length;
            double x;
            double y;
            String file_path;

            for (int i = 0; i < size; i += 1){
                menu_contents.get("armor")[i].used = true;
                x = menu_contents.get("armor")[i].x_pos;
                y = menu_contents.get("armor")[i].y_pos;
                file_path = menu_contents.get("armor")[i].armor;
                StdDraw.picture(x + 0.5, y + 0.5, file_path, 1.0, 1.0);
            }

        }
        public void remove_full_armor(){
            int size = menu_contents.get("armor").length;
            double x;
            double y;
            String file_path;

            for (int i = 0; i < size; i += 1){
                menu_contents.get("armor")[i].used = false;
                x = menu_contents.get("armor")[i].x_pos;
                y = menu_contents.get("armor")[i].y_pos;
                file_path = menu_contents.get("armor")[i].empty_armor;
                StdDraw.setPenColor(Color.darkGray);
                StdDraw.filledSquare(x + 0.5, y + 0.5, 0.5);
                StdDraw.picture(x + 0.5, y + 0.5, file_path, 1.0, 1.0);
            }

        }
        public void remove_one_shield(){
            this.used = false;
            StdDraw.setPenColor(Color.darkGray);
            StdDraw.filledSquare(x_pos + 0.5, y_pos + 0.5, 0.5);
            StdDraw.picture(x_pos + 0.5, y_pos + 0.5, empty_armor, 1.0, 1.0);
        }

        public void add_item_to_inventory(String part_kind){
            this.used = true;
            this.kind = part_kind;
            this.equipped = false;

            StdDraw.picture(x_pos, y_pos, part_kind_to_picture(part_kind), 1.0, 1.0);
            StdDraw.show();
        }
        public void remove_item_from_inventory(){
            this.used = false;
            this.kind = "empty";
            this.equipped = false;
            StdDraw.setPenColor(Color.lightGray);
            StdDraw.filledSquare(x_pos, y_pos, 0.5);
            StdDraw.show();
        }
        public void equip_with_item(String part_kind){
            this.equipped = true;
            if (part_kind == "armor"){
                add_full_armor();
            }
            StdDraw.setPenColor(Color.red);
            StdDraw.filledSquare(x_pos, y_pos, 0.7);
            StdDraw.setPenColor(Color.lightGray);
            StdDraw.filledSquare(x_pos, y_pos, 0.5);
            StdDraw.picture(x_pos, y_pos, part_kind_to_picture(part_kind), 1.0, 1.0);
            StdDraw.show();
        }
        public void unequip_item(String part_kind){
            if (part_kind == "armor"){
                remove_full_armor();
            }

            this.equipped = false;
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.filledSquare(x_pos, y_pos, 0.7);
            StdDraw.setPenColor(Color.lightGray);
            StdDraw.filledSquare(x_pos, y_pos, 0.5);
            StdDraw.picture(x_pos, y_pos, part_kind_to_picture(part_kind), 1.1, 1.1);
            StdDraw.show();

        }


        private String part_kind_to_picture(String part_kind){

            if (part_kind == "sword"){
                return "items/sword.png";
            }else if (part_kind == "ham"){
                return "items/ham.png";
            }else if (part_kind == "armor"){
                return "items/armor.png";
            }else if (part_kind == "key"){
                return "items/key.png";
            }else if (part_kind == "red_diamond"){
                return "items/red_diamond.png";
            }
            return "something_went_wrong";
        }
    }
    //########################################


    //used by the world instance to draw the initial menu bar with full life
    public HashMap<String, Menu_Element[]> establish_Menu_Bar(){
        //life boxes are drawn in the same way as unit world_blocks
        double life_bar_start_x = 3;
        double y = 31;
        double life_bar_end_x = 8;
        int array_position = 0;
        for (double x = life_bar_start_x; x < life_bar_end_x; x += 1){
            menu_contents.get("life")[array_position] = add_life_element(x, y, true);
            array_position += 1;
        }

        //armor boxes are drawn in the same way as unit world_blocks
        double armor_bar_start_x = 12;
        double armor_y = 31;
        double armor_bar_end_x = 17;
        array_position = 0;
        for (double x = armor_bar_start_x; x < armor_bar_end_x; x += 1){
            menu_contents.get("armor")[array_position] = add_armor_element(x, armor_y, false);
            array_position += 1;
        }

        //inventory boxes are drawn starting from the middle
        int size = menu_contents.get("inventory").length;
        double x = 35.5;
        y = 31.5;
        for (int i = 0; i < size; i += 1){
            menu_contents.get("inventory")[i] = add_inventory_element(x, y,false);
            x += 1.2; //0.2 is space between inventory objects
        }

        return menu_contents;
    }
    //helper methods to fill the Menu_Bar arrays of the HashMap
    private Menu_Element add_life_element(double x, double y, boolean used){
        return new Menu_Element(x, y, "life", used);
    }
    private Menu_Element add_armor_element(double x, double y, boolean used){
        return new Menu_Element(x, y, "armor", used);
    }
    private Menu_Element add_inventory_element(double x, double y, boolean used){
        return new Menu_Element(x, y, "empty", used);
    }


    //wrapped by a display method in World.java and executed in Game
    public void draw_Menu_Bar(){
        StdDraw.setPenColor(Color.orange);
        StdDraw.setPenRadius(0.01);
        StdDraw.line(1.0,30, 49, 30);

        draw_life_status();
        draw_armor_status();
        draw_inventory();
    }
    //helper methods to display all three kinds of status bars on the screen
    private void draw_life_status(){
        int size = menu_contents.get("life").length;
        double x;
        double y;
        String file_path;

        StdDraw.setPenColor(Color.ORANGE);
        StdDraw.text(5.5, 31 + 1.7, "life");

        for (int i = 0; i < size; i += 1){
            x = menu_contents.get("life")[i].x_pos;
            y = menu_contents.get("life")[i].y_pos;
            file_path = menu_contents.get("life")[i].heart;
            draw_heart(x, y, file_path);
        }

    }
    private void draw_armor_status(){
        int size = menu_contents.get("armor").length;
        double x;
        double y;
        String file_path;

        StdDraw.setPenColor(Color.ORANGE);
        StdDraw.text(14.5, 31 + 1.7, "armor");

        for (int i = 0; i < size; i += 1){
            x = menu_contents.get("armor")[i].x_pos;
            y = menu_contents.get("armor")[i].y_pos;
            file_path = menu_contents.get("armor")[i].empty_armor;
            draw_empty_armor(x, y, file_path);
        }

    }
    private void draw_inventory(){
        int size = menu_contents.get("inventory").length;

        double x_start = menu_contents.get("inventory")[0].x_pos - 0.7;
        double x_end = menu_contents.get("inventory")[size - 1].x_pos + 0.7;
        double x_medium = x_start + (0.5 * (x_end - x_start));
        double y_medium = menu_contents.get("inventory")[0].y_pos;

        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(x_medium, y_medium,0.5 * (x_end - x_start), 0.7 );

        StdDraw.setPenColor(Color.ORANGE);
        StdDraw.text(x_medium, y_medium + 1.3, "inventory");

        double x;
        double y;
        for (int i = 0; i < size; i += 1){
            x = menu_contents.get("inventory")[i].x_pos;
            y = menu_contents.get("inventory")[i].y_pos;
            draw_inventory_box(x, y);
        }
    }
    //helper methods that draws individual fields for all three status bars
    private void draw_heart(double x_pos, double y_pos, String file_path){
        StdDraw.picture(x_pos + 0.5, y_pos + 0.5, file_path, 1.0, 1.0);
    }
    private void draw_empty_armor(double x_pos, double y_pos, String file_path){
        StdDraw.picture(x_pos + 0.5, y_pos + 0.5, file_path, 1.0, 1.0);
    }
    private void draw_inventory_box(double x_pos, double y_pos){
        StdDraw.setPenColor(Color.lightGray);
        StdDraw.filledSquare(x_pos, y_pos, 0.5);
    }
}
