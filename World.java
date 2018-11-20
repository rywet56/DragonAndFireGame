
import java.util.HashMap;
import java.util.Random;

public class World {
    private static final int WIDTH_world = 50;
    private static final int HEIGHT_world = 30;
    private static final int WIDTH_canvas = 50;
    private static final int HEIGHT_canvas = 35;
    World_Part[][] world_dynamic = new World_Part[WIDTH_canvas][HEIGHT_canvas];
    World_Part[][] world_dynamic_parts = new World_Part[WIDTH_canvas][HEIGHT_canvas];
    World_Part[][] world_static = new World_Part[WIDTH_canvas][HEIGHT_canvas];
    MenuBar menu_bar_pointer;

    //#################----------------------------------#########################################
    //objects of these classes generate hallways rooms, walls and a status bar
    //#################----------------------------------#########################################
    //objects create hallways depending on checkpoints
    protected class Hallway {
        protected void create_hallways(World_Part starting_World_Block, World_Part[] check_points){
            World_Part start_pointer = starting_World_Block;
            World_Part end_pointer;

            for (int i = 0; i < check_points.length; i += 1){
                end_pointer = check_points[i];

                start_pointer = make_x_pathway(start_pointer, end_pointer);
                start_pointer = make_y_pathway(start_pointer, end_pointer);
            }
        }

        private World_Part make_x_pathway(World_Part start, World_Part end){
            //puts blocks into the x direction towards the x coordinate of the end point
            int x_pos_end = end.x_pos;
            int y_pos = start.y_pos;
            int x_pos;

            if (end.x_pos - start.x_pos > 0){//end is on right side
                for (x_pos = start.x_pos + 1; x_pos <= x_pos_end; x_pos += 1){
                    world_dynamic[x_pos][y_pos] = new_World_Block(x_pos, y_pos, "hallway_floor");
                    world_static[x_pos][y_pos] = new_World_Block(x_pos, y_pos, "hallway_floor");
                    world_dynamic_parts[x_pos][y_pos] = new_World_Block(x_pos, y_pos, "hallway_floor");
                }
                return world_dynamic[x_pos-1][y_pos];
            }else if (end.x_pos - start.x_pos < 0){//end is on left side
                for (x_pos = start.x_pos - 1; x_pos >= x_pos_end; x_pos -= 1) {
                    world_dynamic[x_pos][y_pos] = new_World_Block(x_pos, y_pos, "hallway_floor");
                    world_static[x_pos][y_pos]= new_World_Block(x_pos, y_pos, "hallway_floor");
                    world_dynamic_parts[x_pos][y_pos] = new_World_Block(x_pos, y_pos, "hallway_floor");
                }
                return world_dynamic[x_pos+1][y_pos];
            }
            return start;
            //if not right or left, end has to be above or below, so continue with
            // the next step in create_hallways()
        }

        private World_Part make_y_pathway(World_Part start, World_Part end){
            int y_pos_end = end.y_pos;
            int x_pos = start.x_pos;
            int y_pos;

            if (end.y_pos - start.y_pos > 0){//end up
                for (y_pos = start.y_pos + 1; y_pos <= y_pos_end; y_pos += 1){
                    world_dynamic[x_pos][y_pos] = new_World_Block(x_pos, y_pos, "hallway_floor");
                    world_static[x_pos][y_pos] = new_World_Block(x_pos, y_pos, "hallway_floor");
                    world_dynamic_parts[x_pos][y_pos] = new_World_Block(x_pos, y_pos, "hallway_floor");
                }
                return world_dynamic[x_pos][y_pos-1];
            }else if (end.y_pos - start.y_pos < 0){//end is down
                for (y_pos = start.y_pos - 1; y_pos >= y_pos_end; y_pos -= 1) {
                    world_dynamic[x_pos][y_pos] = new_World_Block(x_pos, y_pos, "hallway_floor");
                    world_static[x_pos][y_pos] = new_World_Block(x_pos, y_pos, "hallway_floor");
                    world_dynamic_parts[x_pos][y_pos] = new_World_Block(x_pos, y_pos, "hallway_floor");
                }
                return world_dynamic[x_pos][y_pos+1];
            }
            return start;
            //in the current immplementation start will only be returned if the end_position is the
            //same as the starting position. This sould not happne, but good that this is covered.
        }
    }
    //objects create rooms depending on checkpoints
    protected class Room{
        protected void create_rooms(World_Part[] check_points){
            for (int i = 0; i < check_points.length; i += 1){
                add_room(check_points[i]);
            }
        }

        protected void add_room(World_Part check_point){
            //5x5 room
            //room_pointer is set to the world_block at which the room should start to be created
            //it is the lower left corner
            World_Part start_pos = world_dynamic[check_point.x_pos-2][check_point.y_pos-2];

            int room_size_x = 5;
            int x_end = start_pos.x_pos + room_size_x;
            int room_size_y = 5;
            int y_end = start_pos.y_pos + room_size_y;

            for (int x_pos = start_pos.x_pos; x_pos < x_end; x_pos += 1){
                for (int y_pos = start_pos.y_pos; y_pos < y_end; y_pos += 1){
                    world_dynamic[x_pos][y_pos] = new_World_Block(x_pos, y_pos, "room_floor");
                    world_static[x_pos][y_pos] = new_World_Block(x_pos, y_pos, "room_floor");
                    world_dynamic_parts[x_pos][y_pos] = new_World_Block(x_pos, y_pos, "room_floor");
                }
            }
        }
    }
    //objects create walls depending on hallway_floors and room_floors
    protected class Wall{
        protected void create_walls(World_Part[][] world, World_Part[][] world_buffer){
            for (int x_pos = 0; x_pos < WIDTH_world; x_pos += 1){
                for (int y_pos = 0; y_pos < HEIGHT_world; y_pos += 1){
                    if (wall_needed(world[x_pos][y_pos], world)){
                        set_wall(world[x_pos][y_pos]);
                        set_wall(world_buffer[x_pos][y_pos]);
                    }
                }
            }
        }

        //this method assumes that the farthest position of hallways and rooms is one block less
        //then the world_dynamic size. That guarantees that there will always be a wall on the outside of every
        //room and hallway. If a more sophisticated algorithm for the hallway and room generation is
        //developed, this imaginary border of size-1 has to be considered.
        //size of the world_dynamic = the places where there are either walls, hallways or rooms
        //size of window = the world_dynamic and the part of the canvas that shows live and buttons
        private boolean wall_needed(World_Part block, World_Part[][] world){
            //in certain cases you should not ask if a wall should be set becasue otherwise
            //you get an indexoutofbounds exception
            if (block.part_kind == "empty"){// checks if it is legal to put a wall at all in its surroundings
                if (block.y_pos == HEIGHT_world - 1){
                    if (block.x_pos == 0){
                        return wall_lower_right(block, world);
                    }else if (block.x_pos == WIDTH_world - 1){
                        return wall_lower_left(block, world);
                    }
                    return wall_down(block, world) | wall_lower_left(block, world) | wall_lower_right(block, world);
                }
                if (block.y_pos == 0){
                    if (block.x_pos == 0){
                        return wall_upper_right(block, world);
                    }else if (block.x_pos == WIDTH_world - 1){
                        return wall_upper_left(block, world);
                    }
                    return wall_up(block, world) | wall_upper_left(block, world) | wall_upper_right(block, world);
                }
                if (block.x_pos == 0){
                    return wall_right(block, world) | wall_upper_right(block, world) | wall_lower_right(block, world);
                }
                if (block.x_pos == WIDTH_world - 1){
                    return wall_left(block, world) | wall_upper_left(block, world) | wall_lower_left(block, world);
                }
                return wall_right(block, world) | wall_left(block, world) |
                        wall_up(block, world) | wall_down(block, world) |
                        wall_upper_right(block, world) | wall_lower_right(block, world) |
                        wall_upper_left(block, world) | wall_lower_left(block, world);

            }
            return false;
        }

        private boolean wall_right(World_Part block, World_Part[][] world) {
            if (world[block.x_pos + 1][block.y_pos].part_kind == "hallway_floor"
                    || world[block.x_pos + 1][block.y_pos].part_kind == "room_floor"
                    || world[block.x_pos + 1][block.y_pos].part_kind == "player") {
                return true;
            }
            return false;
        }
        private boolean wall_left(World_Part block, World_Part[][] world) {
            if (world[block.x_pos - 1][block.y_pos].part_kind == "hallway_floor"
                    || world[block.x_pos - 1][block.y_pos].part_kind == "room_floor"
                    || world[block.x_pos - 1][block.y_pos].part_kind == "player") {
                return true;
            }
            return false;
        }
        private boolean wall_up(World_Part block, World_Part[][] world) {
            if (world[block.x_pos][block.y_pos + 1].part_kind == "hallway_floor"
                    || world[block.x_pos][block.y_pos + 1].part_kind == "room_floor"
                    || world[block.x_pos][block.y_pos + 1].part_kind == "player") {
            return true;
            }
            return false;
        }
        private boolean wall_down(World_Part block, World_Part[][] world){
            if(world[block.x_pos][block.y_pos-1].part_kind == "hallway_floor"
                    || world[block.x_pos][block.y_pos-1].part_kind == "room_floor"
                    || world[block.x_pos][block.y_pos-1].part_kind == "player"){
                return true;
            }
            return false;
        }

        private boolean wall_upper_right(World_Part block, World_Part[][] world) {
            if (world[block.x_pos + 1][block.y_pos + 1].part_kind == "hallway_floor" //upper right
                    || world[block.x_pos + 1][block.y_pos + 1].part_kind == "room_floor"
                    || world[block.x_pos + 1][block.y_pos + 1].part_kind == "player") {
                return true;
            }
            return false;
        }
        private boolean wall_lower_right(World_Part block, World_Part[][] world) {
            if (world[block.x_pos + 1][block.y_pos - 1].part_kind == "hallway_floor" //lower right
                    || world[block.x_pos + 1][block.y_pos - 1].part_kind == "room_floor"
                    || world[block.x_pos + 1][block.y_pos - 1].part_kind == "player") {
                return true;
            }
            return false;
        }
        private boolean wall_upper_left(World_Part block, World_Part[][] world) {
            if (world[block.x_pos - 1][block.y_pos + 1].part_kind == "hallway_floor" //upper left
                    || world[block.x_pos - 1][block.y_pos + 1].part_kind == "room_floor"
                    || world[block.x_pos - 1][block.y_pos + 1].part_kind == "player") {
                return true;
            }
            return false;
        }
        private boolean wall_lower_left(World_Part block, World_Part[][] world){
            if(world[block.x_pos-1][block.y_pos-1].part_kind == "hallway_floor" //lower left
                    || world[block.x_pos-1][block.y_pos-1].part_kind == "room_floor"
                    || world[block.x_pos-1][block.y_pos-1].part_kind == "player"){
                return true;
            }
            return false;
        }

        private void set_wall(World_Part block){
            world_dynamic[block.x_pos][block.y_pos] = new_World_Block(block.x_pos, block.y_pos, "wall");
            world_static[block.x_pos][block.y_pos] = new_World_Block(block.x_pos, block.y_pos, "wall");
            world_dynamic_parts[block.x_pos][block.y_pos] = new_World_Block(block.x_pos, block.y_pos, "wall");
        }
    }

    protected class Items{
        protected void create_items() {
            for (int x_pos = 0; x_pos < WIDTH_world; x_pos += 1) {
                for (int y_pos = 0; y_pos < HEIGHT_world; y_pos += 1) {
                    if (world_dynamic[x_pos][y_pos].part_kind == "hallway_floor" || world_dynamic[x_pos][y_pos].part_kind == "room_floor") {
                        set_item(x_pos, y_pos);
                    }
                }
            }
        }

        private void set_item(int x, int y){
            Random_Probability rand_prob = new Random_Probability();
            if (rand_prob.should_item_be_set()){
                String item = rand_prob.random_item();
                if (item == "sword"){
                    set_sword(x, y);
                }else if(item == "water"){
                    set_water(x, y);
                }else if (item == "fire"){
                    set_fire(x, y);
                }else if (item == "ham") {
                    set_ham(x, y);
                }else if (item == "armor") {
                    set_armor(x, y);
                }else if (item == "enemy") {
                    set_enemy(x, y);
                }
            }
        }

        private void set_water(int x, int y){
            world_dynamic[x][y] = new_World_Block(x, y, "water");
            world_dynamic_parts[x][y] = new_World_Block(x, y, "water");
        }
        private void set_fire(int x, int y){
            world_dynamic[x][y] = new_World_Block(x, y, "fire");
        }
        private void set_sword(int x, int y){
            world_dynamic[x][y] = new_World_Block(x, y, "sword");
            world_dynamic_parts[x][y] = new_World_Block(x, y, "sword");
        }
        private void set_ham(int x, int y){
            world_dynamic[x][y] = new_World_Block(x, y, "ham");
            world_dynamic_parts[x][y] = new_World_Block(x, y, "ham");
        }
        private void set_armor(int x, int y){
            world_dynamic[x][y] = new_World_Block(x, y, "armor");
            world_dynamic_parts[x][y] = new_World_Block(x, y, "armor");
        }
        private void set_enemy(int x, int y){
            world_dynamic[x][y] = new_World_Block(x, y, "enemy");
            world_dynamic_parts[x][y] = new_World_Block(x, y, "enemy");
        }

    }


    //#################----------------------------------#########################################
    // creates an empty world_dynamic containing the player
    //#################----------------------------------#########################################
    // fills the world_dynamic array with empty World_Blocks
    public World_Player create_empty_world(HashMap<String, MenuBar.Menu_Element[]> menu_bar_hash_map) {
        //create the empty world_dynamic
        for (int width = 0; width < WIDTH_canvas; width += 1) {
            for (int height = 0; height < HEIGHT_canvas; height += 1) {
                world_dynamic[width][height] = new_World_Block(width, height, "empty");
                world_static[width][height] = new_World_Block(width, height, "empty");
                world_dynamic_parts[width][height] = new_World_Block(width, height, "empty");
            }
        }
        //add the player instance
        int start_x = 10;
        int start_y = 2;
        world_dynamic[start_x][start_y] = create_new_World_Player(start_x, start_y, menu_bar_hash_map);
        world_dynamic_parts[start_x][start_y] = world_dynamic[start_x][start_y];
        world_static[start_x][start_y] = new_World_Block(start_x, start_y, "hallway_floor");//only for visual purposes
        return (World_Player) world_dynamic[start_x][start_y];
    }
    //creates and returns a player instance
    public World_Player create_new_World_Player(int x_pos, int y_pos, HashMap<String, MenuBar.Menu_Element[]> menu_bar_hash_map){
        //creates a new player instance and returns it
        return new World_Player(x_pos, y_pos, world_dynamic, world_static, world_dynamic_parts, menu_bar_hash_map);
    }
    //creates and returns a new World_Block object
    public World_Block new_World_Block(int x_pos, int y_pos, String block_kind) {
        return new World_Block(x_pos, y_pos, block_kind);
    }


    //#################----------------------------------#########################################
    // this part only consist of one function
    // it fills the world_dynamic array with all kinds of World_Blocks to generate a beautiful playable world_dynamic
    // it also uses the methods in the section above to do that
    public World_Player create_world(){

        MenuBar Menu_Bar = new MenuBar();
        menu_bar_pointer = Menu_Bar;
        HashMap<String, MenuBar.Menu_Element[]> menu_bar_hash_map = Menu_Bar.establish_Menu_Bar();

        //creates empty world_dynamic and returns the player object that is in it
        World_Player player = create_empty_world(menu_bar_hash_map);

        World_Part[] check_points = new World_Part[9];
        check_points[0] = world_dynamic[12][5];
        check_points[1] = world_dynamic[14][10];
        check_points[2] = world_dynamic[24][19];
        check_points[3] = world_dynamic[5][13];
        check_points[4] = world_dynamic[44][25];//
        check_points[5] = world_dynamic[42][7];
        check_points[6] = world_dynamic[12][13];
        check_points[7] = world_dynamic[18][4];
        check_points[8] = world_dynamic[5][24];

        //fills in hallways
        Hallway taxis = new Hallway();
        taxis.create_hallways(player, check_points);

        //fills in rooms
        Room room = new Room();
        room.create_rooms(check_points);

        //fill in walls
        Wall wall =  new Wall();
        wall.create_walls(world_dynamic, world_static);

        //fill in items like swords or fire and water blocks
        Items item = new Items();
        item.create_items();

        //fill in exit door to end game
        world_dynamic[44][28] = new_World_Block(44, 28, "door");
        world_dynamic_parts[44][28] = new_World_Block(44, 28, "door");
        world_static[44][28] = new_World_Block(44, 28, "door");

        return player;
    }


    //#################----------------------------------#########################################
    //displays the world_dynamic in the array visually
    //#################----------------------------------#########################################
    //creates and empty window with appropriate size.
    public void initialize_world() {
        StdDraw.setCanvasSize(WIDTH_canvas * 16, HEIGHT_canvas * 16);
        StdDraw.setXscale(0, WIDTH_canvas);
        StdDraw.setYscale(0, HEIGHT_canvas);
        StdDraw.enableDoubleBuffering();
    }
    // displays the contents of world_dynamic by using the draw method of every world_dynamic block, which displays
    //the corresponding object on the canvas
    public void render_world() {
        for (int x = 0; x < WIDTH_canvas; x += 1) {
            for (int y = 0; y < HEIGHT_canvas; y += 1) {
                world_static[x][y].draw_World_Part();
                world_dynamic[x][y].draw_World_Part();
            }
        }
        world_dynamic[44][28].draw_World_Part();
        StdDraw.show();

    }
    //displays menu bar
    public void display_menu_bar(){
        menu_bar_pointer.draw_Menu_Bar();
        StdDraw.show();
    }
}

