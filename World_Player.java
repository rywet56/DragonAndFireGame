import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;

public class World_Player extends World_Part {
    World_Part[][] world_dynamic; //contains all kind of World_Part elements
    World_Part[][] world_dynamic_parts;//contains all World_Part elements except fire (as this one can be removed when walking over it)
    World_Part[][] world_static; //contains only hallways, rooms and walls
    HashMap<String, MenuBar.Menu_Element[]> menu_bar_hash_map;
    public boolean game_over = false;


    public World_Player(int x_p, int y_p, World_Part[][] world_d, World_Part[][] world_s, World_Part[][] world_d_parts, HashMap<String, MenuBar.Menu_Element[]> menu_bar_hm){
        this.x_pos = x_p;
        this.y_pos = y_p;
        this.life = 5;
        this.damage = 1;
        this.armor = 0;
        this.world_dynamic = world_d;
        this.part_kind = "player";
        this.world_static = world_s;
        this.world_dynamic_parts = world_d_parts;
        this.file_path = "items/knight.png";
        this.menu_bar_hash_map = menu_bar_hm;
    }

    public void update_world_parts(int old_x, int old_y,
                                   int new_x, int new_y){
        //creates pointer object to the player object
        World_Part player_pointer = world_dynamic[old_x][old_y];
        //changes the position from which player is moving back to its old world_part object
        //the first case is the special case where the player has made his first move
        if (world_dynamic[old_x][old_y].part_kind == "player" && world_dynamic_parts[old_x][old_y].part_kind == "player"){
            world_dynamic[old_x][old_y] = new World_Block(old_x, old_y, "hallway_floor");
            world_dynamic_parts[old_x][old_y] = world_dynamic[old_x][old_y];
        }

        //the case if an item has been dropped from the inventory
        if (world_dynamic[old_x][old_y].part_kind != world_dynamic_parts[old_x][old_y].part_kind
                && world_dynamic[old_x][old_y].part_kind != "player"){
            world_dynamic[new_x][new_y] = player_pointer;
            return;
            //the dynamic world should not yet been set to the dynamic parts world yet. this discrepancy is used in
            //update_visual_world to print things in the correct way
        }

        //the case when the player just moves around, does not drop anything and is not in the starting position and
        //makes his first move
        world_dynamic[old_x][old_y] = world_dynamic_parts[old_x][old_y];
        //change the position of the player to the new position
        world_dynamic[new_x][new_y] = player_pointer;
    }
    public void update_visual_world(int old_x, int old_y,
                                    int new_x, int new_y){
        //is true when an item has been dropped before this next move. the discrepancy between the two world arrays
        //is used as an indicator that an item has been dropped. And only at this point this discrepancy is dealt with
        //and the two worlds are updated again.
        if (world_dynamic[old_x][old_y].part_kind != world_dynamic_parts[old_x][old_y].part_kind
                && world_dynamic[old_x][old_y].part_kind != "player"){
            world_dynamic_parts[old_x][old_y] = world_dynamic[old_x][old_y];
        }

        world_static[old_x][old_y].draw_World_Part();//to make the backround look through when printing pictures
        world_dynamic[old_x][old_y].draw_World_Part();
        world_static[new_x][new_y].draw_World_Part();//to make the backround look through when printing pictures
        world_dynamic[new_x][new_y].draw_World_Part();
        StdDraw.show();
    }

    public void next_action(){
        //aks if the user presses keyes or mouse, saves the corresponding variant and
        //calls the corresponding method on the World_Player object that has been created
        //it does this in an endless loop for now
        if (StdDraw.isMousePressed()){
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            if (mouse_around_player(x, y)){
                World_Part world_part = mouse_press_to_block(x, y);
                switch (world_part.part_kind){
                    case "door" :
                        if (red_diamond_selected() && key_selected() && this.life > 0){
                            StdDraw.setPenColor(Color.orange);
                            StdDraw.filledRectangle(25.0,21.0, 9.0, 4);
                            StdDraw.setPenColor(Color.darkGray);
                            StdDraw.filledRectangle(25.0,21.0, 8.5, 3.5);
                            StdDraw.setPenColor(Color.orange);
                            StdDraw.text(25.0, 20.0, "YOU WON!!!!!");
                            StdDraw.text(25.0, 22.0, "CONGRATULATION!");
                            StdDraw.show();
                            this.game_over = true;
                        }
                        break;
                    case "sword" :
                        if (inventory_size() < inventory_length()){
                            world_dynamic_parts[world_part.x_pos][world_part.y_pos] = world_static[world_part.x_pos][world_part.y_pos];
                            world_dynamic[world_part.x_pos][world_part.y_pos] = world_dynamic_parts[world_part.x_pos][world_part.y_pos];
                            world_dynamic[world_part.x_pos][world_part.y_pos].draw_World_Part();
                            StdDraw.show();
                            menu_bar_hash_map.get("inventory")[inventory_size()].add_item_to_inventory(world_part.part_kind);
                        }
                        break;
                    case "ham" :
                        if (inventory_size() < inventory_length()){
                            world_dynamic_parts[world_part.x_pos][world_part.y_pos] = world_static[world_part.x_pos][world_part.y_pos];
                            world_dynamic[world_part.x_pos][world_part.y_pos] = world_dynamic_parts[world_part.x_pos][world_part.y_pos];
                            world_dynamic[world_part.x_pos][world_part.y_pos].draw_World_Part();
                            StdDraw.show();
                            menu_bar_hash_map.get("inventory")[next_free_inventory_place()].add_item_to_inventory(world_part.part_kind);
                        }
                        break;
                    case "armor" :
                        if (inventory_size() < inventory_length()){
                            world_dynamic_parts[world_part.x_pos][world_part.y_pos] = world_static[world_part.x_pos][world_part.y_pos];
                            world_dynamic[world_part.x_pos][world_part.y_pos] = world_dynamic_parts[world_part.x_pos][world_part.y_pos];
                            world_dynamic[world_part.x_pos][world_part.y_pos].draw_World_Part();
                            StdDraw.show();
                            menu_bar_hash_map.get("inventory")[next_free_inventory_place()].add_item_to_inventory(world_part.part_kind);
                        }
                        break;
                    case "key" :
                        if (inventory_size() < inventory_length()){
                            world_dynamic_parts[world_part.x_pos][world_part.y_pos] = world_static[world_part.x_pos][world_part.y_pos];
                            world_dynamic[world_part.x_pos][world_part.y_pos] = world_dynamic_parts[world_part.x_pos][world_part.y_pos];
                            world_dynamic[world_part.x_pos][world_part.y_pos].draw_World_Part();
                            StdDraw.show();
                            menu_bar_hash_map.get("inventory")[next_free_inventory_place()].add_item_to_inventory(world_part.part_kind);
                        }
                        break;
                    case "red_diamond" :
                        if (inventory_size() < inventory_length()){
                            world_dynamic_parts[world_part.x_pos][world_part.y_pos] = world_static[world_part.x_pos][world_part.y_pos];
                            world_dynamic[world_part.x_pos][world_part.y_pos] = world_dynamic_parts[world_part.x_pos][world_part.y_pos];
                            world_dynamic[world_part.x_pos][world_part.y_pos].draw_World_Part();
                            StdDraw.show();
                            menu_bar_hash_map.get("inventory")[next_free_inventory_place()].add_item_to_inventory(world_part.part_kind);
                        }
                        break;
                    case "enemy" :
                        int damage = attack_damage();
                        world_part.life -= damage;
                        this.action_to_block(world_part);

                        if (world_part.life < 1){
                            Random_Probability rand_prob = new Random_Probability();
                            boolean drop_key = rand_prob.return_key();
                            if (drop_key){
                                world_dynamic[world_part.x_pos][world_part.y_pos] = new World_Block(world_part.x_pos, world_part.y_pos, "key");
                                world_dynamic_parts[world_part.x_pos][world_part.y_pos] = new World_Block(world_part.x_pos, world_part.y_pos, "key");

                                world_static[world_part.x_pos][world_part.y_pos].draw_World_Part();
                                world_dynamic[world_part.x_pos][world_part.y_pos].draw_World_Part();
                                StdDraw.show();
                            }else {
                                world_dynamic[world_part.x_pos][world_part.y_pos] = world_static[world_part.x_pos][world_part.y_pos];
                                world_dynamic_parts[world_part.x_pos][world_part.y_pos] = world_dynamic[world_part.x_pos][world_part.y_pos];

                                world_dynamic[world_part.x_pos][world_part.y_pos].draw_World_Part();
                                StdDraw.show();
                            }
                        }
                        break;
                }
            }
            else if (mouse_on_inventory(x, y) && StdDraw.isKeyPressed(KeyEvent.VK_R)) {
                StdDraw.pause(400);
                MenuBar.Menu_Element element = mouse_press_to_inventory_element(x, y);
                if (!element.equipped &&
                        (world_dynamic_parts[x_pos][y_pos].part_kind == "hallway_floor"
                        || world_dynamic_parts[x_pos][y_pos].part_kind == "room_floor")) {
                    String part_kind = element.kind;
                    element.remove_item_from_inventory();
                    world_dynamic_parts[x_pos][y_pos] = new World_Block(x_pos, y_pos, part_kind);

                }
            }
            else if (mouse_on_inventory(x, y) && StdDraw.isKeyPressed(KeyEvent.VK_E)) {
                MenuBar.Menu_Element element = mouse_press_to_inventory_element(x, y);
                if (element == null){
                    return;
                }
                if (element.kind == "ham"){
                    MenuBar.Menu_Element life_element = next_empty_heart();
                    if (life_element != null){
                        this.life += 1;
                        life_element.add_life();
                        element.remove_item_from_inventory();
                    }else {
                        return;
                    }
                }else if (element.equipped){
                    if (element.kind == "armor" && armor_started_to_be_used()){
                        return;
                    }else {
                        enforce_equipment_status(element.kind, element.equipped);
                        element.unequip_item(element.kind);
                    }
                }else if (!element.equipped && equipment_possible(element.kind)){
                    enforce_equipment_status(element.kind, element.equipped);
                    element.equip_with_item(element.kind);
                }
            }else {
                return;
            }
            StdDraw.pause(600);
        }


        if (StdDraw.isKeyPressed(KeyEvent.VK_W )){
            if (move_up_possible()){
                World_Part new_block = move_up();
                action_to_block(new_block);
            }
        }
        if (StdDraw.isKeyPressed(KeyEvent.VK_S )){
            if (move_down_possible()){
                World_Part new_block = move_down();
                action_to_block(new_block);
            }
        }
        if (StdDraw.isKeyPressed(KeyEvent.VK_D )){
            if (move_right_possible()){
                World_Part new_block = move_right();
                action_to_block(new_block);
            }
        }
        if (StdDraw.isKeyPressed(KeyEvent.VK_A )){
            if (move_left_possible()){
                World_Part new_block = move_left();
                action_to_block(new_block);
            }
        }
    }
    //returns the damage made by the player depending on the number of sorts equipped
    private int attack_damage(){
        int damage = 0;
    for (int i = 0; i < menu_bar_hash_map.get("inventory").length; i += 1) {
        if (menu_bar_hash_map.get("inventory")[i].kind == "sword"
                && menu_bar_hash_map.get("inventory")[i].equipped) {
            damage += 1;
        }
    }
    return damage;
    }

    private void action_to_block(World_Part former_block) {
        String former_block_kind = former_block.part_kind;
        int damage = former_block.damage;

        boolean armor_removed = false;

        if (former_block_kind == "fire" || former_block_kind == "enemy") {
            if (armor_exist()){
                armor_removed = true;
                int armor_before = this.armor;
                this.armor = this.armor - damage;
                int armor_after = this.armor;

                if (armor_after >= 0){
                    int shields_to_be_removed = armor_before - armor_after;
                    remove_armor(shields_to_be_removed);
                    StdDraw.show();
                }else {
                    int shields_to_be_removed = armor_before;
                    remove_armor(shields_to_be_removed);
                    int lifes_to_be_removed = - armor_after;
                    this.life = this.life - lifes_to_be_removed;
                    remove_lifes(lifes_to_be_removed);
                    if (this.life <= 0){
                        print_GameOver_message();
                    }
                }
            }else {
                int life_before = this.life;
                this.life = this.life - damage;
                int life_after = this.life;
                int lifes_to_be_removed = life_before - life_after;
                remove_lifes(lifes_to_be_removed);
                if (this.life <= 0){
                    print_GameOver_message();
                }
            }
        }
        else {
            return;
        }

        if (former_block_kind == "fire"){
            drop_red_diamond();
        }
        //if the armor is less than zero, unequip armor and remove it from inventory for ever. In other words,
        //the armor is destroyed
        //BUT this should only happen if a removal of the last shield led to the fact armor < 1. It should not
        //happen when the armor is zero, as this can also be the case when not armor equipped at all
        if (this.armor < 1 && armor_removed){
            MenuBar.Menu_Element armor_element_to_be_removed = return_armor_element_to_be_removed();
            if (armor_element_to_be_removed == null){
                return;
            }
            armor_element_to_be_removed.unequip_item("armor");
            armor_element_to_be_removed.remove_item_from_inventory();
        }
    }

    private void remove_lifes(int lifes_to_be_removed){
        //determine lifes that the player has left
        int length = menu_bar_hash_map.get("life").length;
        int lifes_left = 0;
        for (int i = length-1; i >= 0; i -= 1){
            if (menu_bar_hash_map.get("life")[i].used){
                lifes_left += 1;
            }
        }

        int lifes_to_be_left = lifes_left - lifes_to_be_removed;

        //update the appropriate number of lifes depending on the lifes_to_be_removed variable
        for (int i = lifes_left - 1; i >= lifes_to_be_left; i -= 1) {
            if (i >= 0){
                menu_bar_hash_map.get("life")[i].remove_life();
            }
        }
    }
    private void remove_armor(int shields_to_be_removed){
        for (int i = 1; i <= shields_to_be_removed; i += 1){
            MenuBar.Menu_Element shield_to_be_removed = next_shield_to_be_removed();
            shield_to_be_removed.remove_one_shield();
        }

    }

    //it returns the next inventory place that is not occupied
    private int next_free_inventory_place(){
        int i = 0;
        while (menu_bar_hash_map.get("inventory")[i].used){
            i += 1;
        }
        return i;
    }
    //it returns the size of the inventory
    private int inventory_size(){
        int part_number = 0;
        for (int i = 0; i < 10; i += 1){
            if (menu_bar_hash_map.get("inventory")[i].kind != "empty"){
                part_number += 1;
            }
        }
        return part_number;
    }
    private int inventory_length(){
        return menu_bar_hash_map.get("inventory").length;
    }

    private void print_GameOver_message(){
        StdDraw.setPenColor(Color.orange);
        StdDraw.filledRectangle(25.0,21.0, 9.0, 4);
        StdDraw.setPenColor(Color.darkGray);
        StdDraw.filledRectangle(25.0,21.0, 8.5, 3.5);
        StdDraw.setPenColor(Color.orange);
        StdDraw.text(25.0, 20.0, "YOU ARE DEAD!!!!!");
        StdDraw.text(25.0, 22.0, "GAME OVER!");
        StdDraw.show();
        world_dynamic[this.x_pos][this.y_pos] = world_static[this.x_pos][this.y_pos];
    }

    private boolean move_up_possible(){
        int new_x = x_pos;
        int new_y = y_pos + 1;
        if (world_dynamic[new_x][new_y].part_kind == "empty" || world_dynamic[new_x][new_y].part_kind == "wall"){
            return false;
        }
        return true;
    }
    private boolean move_down_possible(){
        int new_x = x_pos;
        int new_y = y_pos - 1;
        if (world_dynamic[new_x][new_y].part_kind == "empty" | world_dynamic[new_x][new_y].part_kind == "wall"){
            return false;
        }
        return true;
    }
    private boolean move_right_possible(){
        int new_x = x_pos + 1;
        int new_y = y_pos;
        if (world_dynamic[new_x][new_y].part_kind == "empty" | world_dynamic[new_x][new_y].part_kind == "wall"){
            return false;
        }
        return true;
    }
    private boolean move_left_possible(){
        int new_x = x_pos - 1;
        int new_y = y_pos;
        if (world_dynamic[new_x][new_y].part_kind == "empty" | world_dynamic[new_x][new_y].part_kind == "wall"){
            return false;
        }
        return true;
    }

    private World_Part move_up(){
        int old_x = x_pos;
        int old_y = y_pos;
        int new_x = old_x;
        int new_y = old_y + 1;
        //part object of new position moving into
        World_Part next_part = world_dynamic[new_x][new_y];
        //update player object
        y_pos = new_y;
        //update world_dynamic array
        update_world_parts(old_x, old_y, new_x, new_y);
        //update visual world_dynamic
        update_visual_world(old_x, old_y, new_x, new_y);
        //displays the visual changes of the world_dynamic
        StdDraw.show();
        //pauses. during this short time now keyboard input is processed
        StdDraw.pause(200);

        return next_part;
    }
    private World_Part move_down(){
        int old_x = x_pos;
        int old_y = y_pos;
        int new_x = old_x;
        int new_y = old_y - 1;
        World_Part next_part = world_dynamic[new_x][new_y];
        this.y_pos = new_y;
        update_world_parts(old_x, old_y, new_x, new_y);
        update_visual_world(old_x, old_y, new_x, new_y);
        StdDraw.show();
        StdDraw.pause(200);

        return next_part;
    }
    private World_Part move_right(){
        int old_x = x_pos;
        int old_y = y_pos;
        int new_x = old_x + 1;
        int new_y = old_y;
        World_Part next_part = world_dynamic[new_x][new_y];
        this.x_pos = new_x;
        update_world_parts(old_x, old_y, new_x, new_y);
        update_visual_world(old_x, old_y, new_x, new_y);
        StdDraw.show();
        StdDraw.pause(200);

        return next_part;
    }
    private World_Part move_left(){
        int old_x = x_pos;
        int old_y = y_pos;
        int new_x = old_x - 1;
        int new_y = old_y;
        World_Part next_part = world_dynamic[new_x][new_y];
        this.x_pos = new_x;
        update_world_parts(old_x, old_y, new_x, new_y);
        update_visual_world(old_x, old_y, new_x, new_y);
        StdDraw.show();
        StdDraw.pause(200);

        return next_part;
    }

    private void enforce_equipment_status(String element_kind, boolean equip_status){
        if (element_kind == "armor"){
            if (equip_status){
                this.armor -= 5;
            }else {
                this.armor += 5;
            }
        }else if (element_kind == "sword"){
            if (equip_status){
                this.damage -= 3;
            }else {
                this.damage += 3;
            }
        }
    }
    private boolean equipment_possible(String part_kind){
        int number_of_parts = 0;
        int allowed_number_of_items = 0;

        if (part_kind == "sword"){
            allowed_number_of_items = 2;
        }else if (part_kind == "armor"){
            allowed_number_of_items = 1;
        }else if (part_kind == "key"){
            allowed_number_of_items = 1;
        }else if (part_kind == "red_diamond"){
            allowed_number_of_items = 1;
        }

        for (int i = 0; i < 10; i += 1){
            if (menu_bar_hash_map.get("inventory")[i].kind == part_kind){
                if (menu_bar_hash_map.get("inventory")[i].equipped) {
                    number_of_parts += 1;
                }
            }
        }

        if (number_of_parts < allowed_number_of_items){
            return true;
        }
        return false;
    }

    private boolean mouse_around_player(double x, double y){
        World_Part part_pointer = mouse_press_to_block(x, y);
        int x_p = part_pointer.x_pos;
        int y_p = part_pointer.y_pos;

        if (y_p == y_pos+1 && x_p == x_pos){//mouse above
            return true;
        }else if (y_p == y_pos-1 && x_p == x_pos){//mouse below
            return true;
        }else if (y_p == y_pos && x_p == x_pos+1){//mouse on the right
            return true;
        }else if (y_p == y_pos && x_p == x_pos-1){//mouse on the left
            return true;
        }
        return false;
    }
    private World_Part mouse_press_to_block(double x, double y){
        //returns the world_dynamic part that the mouse is over depending on the x and y coordinates of the cursor
        int x_position = (int) x;
        int y_position = (int) y;
        return world_dynamic[x_position][y_position];
    }

    public MenuBar.Menu_Element next_empty_heart(){
        for (int i = 0; i < menu_bar_hash_map.get("life").length ; i += 1) {
            if (!menu_bar_hash_map.get("life")[i].used){
                return menu_bar_hash_map.get("life")[i];
            }
        }
        return null;
    }

    //in case that the player still has armor return true
    private boolean armor_exist(){
        int length = menu_bar_hash_map.get("armor").length;
        for (int i = length-1; i >= 0; i -= 1) {
            if (menu_bar_hash_map.get("armor")[i].used){
                return true;
            }
        }
        return false;
    }
    private MenuBar.Menu_Element next_shield_to_be_removed(){
        int length = menu_bar_hash_map.get("armor").length;
        for (int i = length-1; i >= 0; i -= 1) {
            if (menu_bar_hash_map.get("armor")[i].used){
                return menu_bar_hash_map.get("armor")[i];
            }
        }
        return null;
    }
    private MenuBar.Menu_Element return_armor_element_to_be_removed(){
        int length = menu_bar_hash_map.get("inventory").length;
        for (int i = 0; i < length; i += 1) {
            if (menu_bar_hash_map.get("inventory")[i].kind == "armor") {
                if (menu_bar_hash_map.get("inventory")[i].equipped = true) {
                    return menu_bar_hash_map.get("inventory")[i];
                }
            }
        }
        return null;
    }
    private boolean armor_started_to_be_used(){
        if (menu_bar_hash_map.get("armor")[4].used){
            return false;
        }
        return true;
    }

    private boolean mouse_on_inventory(double x, double y){
        if (x > inv_x(0) - 0.7 && x < inv_x(9) + 0.7) {
            if (y > inv_y(0) - 0.7 && y < inv_y(0) + 0.7) {
                return true;
            }
        }
        return false;
    }
    private MenuBar.Menu_Element mouse_press_to_inventory_element(double x, double y) {
        for (int i = 0; i < 10; i += 1) {
            if (x > inv_x(i) - 0.5 && x < inv_x(i) + 0.5) {
                if (y > inv_y(i) - 0.5 && y < inv_y(i) + 0.5) {
                    return return_inv_element(i);
                }
            }
        }
        return null;
    }
    //return the x value of the respective inventory element i that they are asked for
    private double inv_x(int i){
        return menu_bar_hash_map.get("inventory")[i].x_pos;
    }
    private double inv_y(int i){
        return menu_bar_hash_map.get("inventory")[i].y_pos;
    }
    //you give it a number of the ith element in the inventory array and it gives you back the corresponding object
    private MenuBar.Menu_Element return_inv_element(int i){
        return menu_bar_hash_map.get("inventory")[i];
    }

    private boolean red_diamond_selected(){
        int length = menu_bar_hash_map.get("inventory").length;
        for (int i = 0; i < length; i += 1){
            if (menu_bar_hash_map.get("inventory")[i].kind == "red_diamond"
            && menu_bar_hash_map.get("inventory")[i].equipped){
                return true;
            }
        }
        return false;
    }
    private boolean key_selected(){
        int length = menu_bar_hash_map.get("inventory").length;
        for (int i = 0; i < length; i += 1){
            if (menu_bar_hash_map.get("inventory")[i].kind == "key"
                    && menu_bar_hash_map.get("inventory")[i].equipped){
                return true;
            }
        }
        return false;
    }

    private void drop_red_diamond(){
        Random_Probability rand_prob = new Random_Probability();
        boolean drop_red_diamond = rand_prob.return_key();
        if (drop_red_diamond){
            world_dynamic_parts[x_pos][y_pos] = new World_Block(x_pos, y_pos, "red_diamond");
        }
    }
}
