import java.awt.*;

public class World_Block extends World_Part {

    public World_Block(int x_pos, int y_pos, String block_kind){
        specifier_to_World_Block(x_pos, y_pos, block_kind);
    }

    public void specifier_to_World_Block(int x_pos, int y_pos, String block_kind){
        if (block_kind == "water"){
            water_block(x_pos, y_pos);
        }else if (block_kind == "empty"){
            empty_block(x_pos, y_pos);
        }else if (block_kind == "hallway_floor"){
            hallway_floor(x_pos, y_pos);
        }else if (block_kind == "door"){
            door(x_pos, y_pos);
        }else if (block_kind == "room_floor") {
            room_floor(x_pos, y_pos);
        }else if (block_kind == "wall") {
            wall(x_pos, y_pos);
        }else if (block_kind == "fire") {
            fire(x_pos, y_pos);
        }else if (block_kind == "sword") {
            sword(x_pos, y_pos);
        }else if (block_kind == "ham") {
            ham(x_pos, y_pos);
        }else if (block_kind == "armor") {
            armor(x_pos, y_pos);
        }else if (block_kind == "enemy") {
            enemy(x_pos, y_pos);
        }else if (block_kind == "key") {
            key(x_pos, y_pos);
        }else if (block_kind == "red_diamond") {
            red_diamond(x_pos, y_pos);
        }
        else if (block_kind == "door") {
            door(x_pos, y_pos);
        }
    }

    public void water_block(int x_pos, int y_pos){
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.destructable = false;
        this.damage = 1;
        this.part_kind = "water";
        this.background_color = StdDraw.BOOK_LIGHT_BLUE;
        this.text_color = Color.BLUE;
        this.part_character = "≈";
    }
    public void empty_block(int x_pos, int y_pos){
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.destructable = false;
        this.damage = 0;
        this.part_kind = "empty";
        this.background_color = Color.darkGray;
    }
    public void hallway_floor(int x_pos, int y_pos){
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.destructable = false;
        this.damage = 0;
        this.part_kind = "hallway_floor";
        this.background_color = Color.GRAY;
        this.text_color = Color.BLACK;
        this.part_character = "+";
    }
    public void room_floor(int x_pos, int y_pos){
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.destructable = false;
        this.damage = 0;
        this.part_kind = "room_floor";
        this.background_color = Color.DARK_GRAY;
        this.text_color = Color.GRAY;
        this.part_character = "+";

    }
    public void wall(int x_pos, int y_pos){
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.destructable = false;
        this.damage = 0;
        this.part_kind = "wall";
        this.background_color = StdDraw.PINK;
        this.text_color = StdDraw.BOOK_RED;
        this.part_character = "#";
    }
    public void fire(int x_pos, int y_pos){
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.destructable = false;
        this.damage = 1;
        this.part_kind = "fire";
        this.background_color = StdDraw.RED;
        this.text_color = StdDraw.YELLOW;
        this.part_character = "%";
    }
    public void sword(int x_pos, int y_pos){
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.destructable = false;
        this.damage = 0;
        this.part_kind = "sword";
        this.file_path = "items/sword.png";
    }
    public void ham(int x_pos, int y_pos){
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.destructable = false;
        this.damage = 0;
        this.part_kind = "ham";
        this.file_path = "items/ham.png";
    }
    public void armor(int x_pos, int y_pos){
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.destructable = false;
        this.damage = 0;
        this.part_kind = "armor";
        this.file_path = "items/armor.png";
    }
    public void enemy(int x_pos, int y_pos){
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.destructable = false;
        this.damage = 2;
        this.life = 2;
        this.part_kind = "enemy";
        this.file_path = "items/enemy.png";
    }
    public void key(int x_pos, int y_pos){
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.destructable = false;
        this.damage = 0;
        this.part_kind = "key";
        this.file_path = "items/key.png";
    }
    public void red_diamond(int x_pos, int y_pos){
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.destructable = false;
        this.damage = 0;
        this.part_kind = "red_diamond";
        this.file_path = "items/red_diamond.png";
    }
    public void door(int x_pos, int y_pos){
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.destructable = false;
        this.damage = 0;
        this.part_kind = "door";
        this.file_path = "items/door.png";
    }
}
