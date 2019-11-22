/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

/**
 *
 * @author reece
 */
public class Player {

    private String name;
    private int score, hit, miss;
    protected Board board;
    Ship[] ships = new Ship[5];
    int size;

    public Player() {
        name = "";
        score = miss = hit = 0;
        board = new Board();
        ships[0] = new Ship(ShipsName.DESTROYER, 2, 'D');
        ships[1] = new Ship(ShipsName.SUBMARINE, 3, 'B');
        ships[2] = new Ship(ShipsName.FRIGATE, 3, 'F');
        ships[3] = new Ship(ShipsName.CRUISER, 4, 'C');
        ships[4] = new Ship(ShipsName.AIRCRAFT_CARRIER, 5, 'A');

    }

    public void arrange() {
        if (!getName().equals("Computer")) {
            
            board.arrangeShipsManual(ships);
            
        } else {
            board.arrangeShipsAuto(ships);
        }
    }

    /**
     *
     */
    public void setName(String inName) {
        name = inName;
    }

    /**
     *
     */
    public String getName() {

        return name;
    }

    /**
     *
     */
    public void setScore(int inScore) {
        score = inScore;
    }

    /**
     *
     */
    public int getScore() {
        return score;
    }

    /**
     *
     */
    public void setHit(int inHit) {
        hit = inHit;
    }

    /**
     *
     */
    public int getHit() {
        return hit;
    }

    /**
     *
     */
    public void setMiss(int inMiss) {
        miss = inMiss;
    }

    /**
     *
     */
    public int getMiss() {
        return miss;
    }

    public String toString() {
        String out = "";
        out += "Player's name is: " + getName() + "\nThat's what player's"
                + " board looks like: " + board.toString();
        return out;
    }

}
