import java.util.Random;

public class Random_Probability {
    /*
    objects should be able to return a number in a user defined range, where
    each number in this user defined range has a probability to be returned
     */
    // the above is the end goal, but it is better to only write a class that can be used
    // for two cases with two probabilities
    Random rand = new Random();

    public int random_block_for_hallway(){
        int hit = rand.nextInt(100 + 1);
        if (hit < 2){
            return 0; //corresponds to a door
        }else return 1; //corresponds to a hallway_floor
    }

    public boolean should_item_be_set(){
        int hit = rand.nextInt(90 + 1);
        if (hit < 20){
            return true;
        }
        return false;
    }

    public boolean return_key(){
        int hit = rand.nextInt(90 + 1);
        if (hit < 21){
            return true;
        }
        return false;
    }

    public String random_item(){
        int hit = rand.nextInt(120 + 1);
        if (hit < 15){
            return "water";
        }else if (hit < 50){
            return "fire";
        }else if (hit < 70){
            return "ham";
        }else if (hit < 80){
            return "armor";
        }else if (hit < 100){
            return "enemy";
        }
        return "sword";
    }
}
