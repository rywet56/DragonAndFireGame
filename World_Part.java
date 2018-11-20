import java.awt.*;

public class World_Part {
    int x_pos;
    int y_pos;
    boolean destructable;
    int damage;
    int life;
    int armor;
    String part_kind;
    Color background_color;
    Color text_color;
    String part_character;
    String file_path;

    public void draw_World_Part(){
        if (part_kind == "water") {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.filledSquare(x_pos + 0.5, y_pos + 0.5, 0.5);
        } else if (part_kind == "empty") {
            StdDraw.setPenColor(this.background_color);
            StdDraw.filledSquare(x_pos + 0.5, y_pos + 0.5, 0.5);
        } else if (part_kind == "player") {
            StdDraw.picture(x_pos + 0.5, y_pos + 0.5, file_path, 1, 1);
        } else if (part_kind == "hallway_floor") {
            StdDraw.setPenColor(this.background_color);
            StdDraw.filledSquare(x_pos + 0.5, y_pos + 0.5, 0.5);
            StdDraw.setPenColor(this.text_color);
            StdDraw.text(x_pos + 0.5, y_pos + 0.5, this.part_character);
        }else if (part_kind == "room_floor") {
            StdDraw.setPenColor(this.background_color);
            StdDraw.filledSquare(x_pos + 0.5, y_pos + 0.5, 0.5);
            StdDraw.setPenColor(this.text_color);
            StdDraw.text(x_pos + 0.5, y_pos + 0.5, this.part_character);
        }else if (part_kind == "wall") {
            StdDraw.setPenColor(this.background_color);
            StdDraw.filledSquare(x_pos + 0.5, y_pos + 0.5, 0.5);
            StdDraw.setPenColor(this.text_color);
            StdDraw.text(x_pos + 0.5, y_pos + 0.5, this.part_character);
        }else if (part_kind == "fire") {
            StdDraw.setPenColor(this.background_color);
            StdDraw.filledSquare(x_pos + 0.5, y_pos + 0.5, 0.5);
            StdDraw.setPenColor(this.text_color);
            StdDraw.text(x_pos + 0.5, y_pos + 0.5, this.part_character);
        }else if (part_kind == "sword") {
            StdDraw.picture(x_pos + 0.5, y_pos + 0.5, this.file_path, 1.1, 1.1);
        }else if (part_kind == "ham") {
            StdDraw.picture(x_pos + 0.5, y_pos + 0.5, this.file_path, 1.1, 1.1);
        }else if (part_kind == "armor") {
            StdDraw.picture(x_pos + 0.5, y_pos + 0.5, this.file_path, 1.1, 1.1);
        }else if (part_kind == "enemy") {
            StdDraw.picture(x_pos + 0.5, y_pos + 0.5, this.file_path, 1.1, 1.1);
        }else if (part_kind == "key") {
            StdDraw.picture(x_pos + 0.5, y_pos + 0.5, this.file_path, 1.0, 1.0);
        }else if (part_kind == "red_diamond") {
            StdDraw.picture(x_pos + 0.5, y_pos + 0.5, this.file_path, 1.0, 1.0);
        }else if (part_kind == "door") {
            StdDraw.picture(x_pos + 0.5, y_pos + 0.8, this.file_path, 2.0, 2.0);
        }
    }

}
