package battleship;

/**
 *
 * @author Galina & Bruce's helper
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.awt.Toolkit;

public class Board {

    Scanner scan = new Scanner(System.in);
    private final int SIZE = 10;
    char[][] board = new char[SIZE][SIZE]; // 10 x 10 board
    private List<Ship> shipList;
    protected char symbol;

    // boolean for if ships are arranged automatically
    private boolean auto = false;

    // Constructor
    // Creates a 10 X 10 seaBoard of ^ open water symbols
    public Board() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = '^';
            }
        }
        shipList = new ArrayList<>();
        //index = 1;
//        checkSide1=checkSide2=checkSide3=checkSide4=
//                bestMove=hitCount=missCount=0;
        //stillHit = false;
    }

    /**
     * Mutator: arrangeShipsManual()
     *
     * @param ship // arranges all ships in the array preconditions: an array of
     * ships has been created postconditions: arranges ships manually or
     * automatically depending on user's answer Calls: manualOrientation() or
     * arrangeShipsAuto() based on user's answer
     */
    public void arrangeShipsManual(Ship[] ship) {
        String ans = "";
        do {
            System.out.println("\nWould you like to manually arrange your "
                    + "ships? Enter 'y' for yes and 'n' for no.");
            ans = scan.next();
        } while (!ans.toLowerCase().equals("y")
                && !ans.toLowerCase().equals("n"));
        if (ans.equals("y")) {
            for (Ship boat : ship) {
                manualOrientation(boat);
                shipList.add(boat);
            }
        } else if (ans.equals("n")) {
            arrangeShipsAuto(ship);
        }
    }

    /**
     * Mutator: manualOrientation()
     *
     * @param ship // reports its name preconditions: a ship object has been
     * created postconditions: sets the orientation of the ship Calls:
     * manualCoordinate()
     */
    private void manualOrientation(Ship ship) {
        String ans = "";
        do {
            System.out.println("Enter the Orientation of the "
                    + ship.getShipsName() + ". Enter 'v' for vertical and"
                    + " 'h' for horizontal");
            ans = scan.next();
        } while (!ans.toLowerCase().equals("v")
                && !ans.toLowerCase().equals("h"));
        if (ans.equals("v")) {
            ship.setDirection(Orientation.vertical);
        } else {
            ship.setDirection(Orientation.horizontal);
        }
        manualCoordinate(ship); // user can now enter the starting coordinates
    }

    /**
     * Mutator: manualCoordinate()
     *
     * @param s // reports its orientation preconditions: user enters an
     * orientation for the ship postconditions: if x and y values entered are
     * valid, they are passed to placedOnBoard() and setCoordinates() Calls:
     * desiredPlacement()
     */
    private void manualCoordinate(Ship s) {
        int x, y, size = s.getSize();
        Orientation orien;
        String ans = "";
        boolean valid; // true or false depending on validity of x and y
        do {
            // valid always set to true here so that it does not remain
            // false from the previous iteration of the loop
            valid = true;
            // gets x coordinate value from user
            System.out.println("Enter the the X-value of the ship's"
                    + " starting coordinate");
            x = scan.nextInt();

            // gets y coordinate value from user
            System.out.println("Enter the the Y-value of the ship's"
                    + " starting coordinate");
            y = scan.nextInt();

            while (x > 10 || y > 10 || x < 1 || y < 1) {
                System.out.println();
                System.out.println("Invalid coordinates. Please try again.");
                System.out.println();
                System.out.println("Enter the the X-value of the ship's"
                        + " starting coordinate");
                x = scan.nextInt();
                System.out.println("Enter the the Y-value of the ship's"
                        + " starting coordinate");
                y = scan.nextInt();
            }
            System.out.println();
            System.out.println();
            // subtracts 1 from x so array index is not out of bounds
            x -= 1;

            // subtracts 1 from x so array index is not out of bounds
            y -= 1;

            orien = s.getDirection(); // gets orientation of boat

            // if vertical, y has to be within valid range of the ship
            // and x has to be between 0 and 9 inclusive
            if (orien == Orientation.vertical && !(y <= (9 - size + 1)
                    && y >= 0 && x <= 9 && x >= 0)) {
                // prints that ship placement is invalid
                System.out.println("Invalid Ship Placement. Please enter"
                        + " a valid starting location");
                valid = false;
            } // if horizontal, x has to be within valid range of the ship
            // and y has to be between 0 and 9 inclusive
            else if (orien == Orientation.horizontal
                    && !(x <= (9 - size + 1) && x >= 0 && y <= 9 && y >= 0)) {
                // prints that ship placement is invalid
                System.out.println("Invalid Ship Placement. Please enter"
                        + " a valid starting location");
                valid = false;
            }
        } // keeps asking for new starting coordinates until valid coordinates
        // are given by user
        while (valid == false || !placedOnBoard(x, y, s));
        s.setCoordinates(orien, x, y);
        s.setXCoordinate(x);
        s.setYCoordinate(y);
        System.out.println(toString()); // prints current state of the board
        desiredPlacement(x, y, s);
    }

    /**
     * Mutator: manualCoordinate()
     *
     * @param x // starting x coord
     * @param y // starting y coord
     * @param s // reports its size and orientation preconditions: a ship has
     * been placed on the board postconditions: clears previously entered ship
     * if the current placement is not desired Calls: manualOrientation() if
     * ship isn't in desired location
     */
    private void desiredPlacement(int x, int y, Ship s) {
        String ans = "";
        // asks user if the previously placed ship was placed as desired
        do {
            System.out.println("Is this the desired ship placement?"
                    + " Enter 'y' for yes and 'n' for no.");
            ans = scan.next();
        } while (!ans.toLowerCase().equals("y")
                && !ans.toLowerCase().equals("n"));

        // if ship placement is not desired, the ship is removed from the board
        // and the user can reenter the orientation and starting coordinates
        if (ans.equals("n")) {
            clearShip(x, y, s); // clears ship

            // prints board without previous ship
            //System.out.println(toString());
            manualOrientation(s); // user can enter orientation and
            // coordinates of ship again
        }
    }

    /**
     * Mutator: arrangeShipsAuto Description: for each ship, determine size to
     * find possible valid position range, then generate random # for row and
     * col based on orientation;repeat placedOnBoard() until successful
     * preconditions: Ships[] are constructed postconditions: Ships[] are
     * arranged onto the board via random Calls: placedOnBoard()
     *
     * @param ships
     */
    public void arrangeShipsAuto(Ship[] ships) {
        // class knows when ships are arranged automatically
        // auto assigned true
        auto = true;
        Random rand = new Random(); // new random object
        // generates correct starting coordinates for all ships
        for (Ship boat : ships) {
            int tileX, tileY;// random x, y coordinate to place the ship

            int howBig = boat.getSize();
            int range = SIZE - howBig + 1; // sets correct range of 
            // random numbers to be generated for this ship

            int oddEven = rand.nextInt(2); // generates random num
            // (either 0 or 1) and determines orientation based on this num
            Orientation way = (oddEven == 0) ? Orientation.horizontal
                    : Orientation.vertical;
            boat.setDirection(way);

            do {
                // ship is horizontal 
                if (boat.getDirection() == Orientation.horizontal) {
                    tileX = rand.nextInt(range);// x coordinated limited to range
                    tileY = rand.nextInt(SIZE);// can have any valid y coordinate
                } // ship is vertical
                else {
                    tileX = rand.nextInt(SIZE);// can have any valid x coordinate
                    tileY = rand.nextInt(range);// y coordinate limited to range
                }
            } // checks if tiles are available
            while (!placedOnBoard(tileX, tileY, boat));
            boat.setCoordinates(way, tileX, tileY);
            boat.setXCoordinate(tileX);
            boat.setYCoordinate(tileY);
            shipList.add(boat);
        }
       //System.out.println(toString());
    }

    /**
     * Mutator: placedOnBoard()
     *
     * @param x // starting x coord
     * @param y // starting y coord
     * @param boat // reports its size and orientation
     * @return success // yes, or no; Ship could be placed in the requested //
     * location preconditions: x and y starting locations are valid for the ship
     * postconditions: if tiles are open ("^") ship is placed on board and
     * method returns true; otherwise returns false
     */
    private boolean placedOnBoard(int x, int y, Ship boat) {
        boolean success = false;    // ship placed on seaBoard
        boolean clear = true;       // seaBoard tile is available
        // ship orientation is horizontal
        if (boat.getDirection() == Orientation.horizontal) {
            for (int i = x, j = 0; j < boat.getSize() && clear; i++, j++) {
                //**********************************************
                // WHEN CHECKING 2 DIMENSIONAL ARRAY
                // X & Y ARE INVERTED
                // i.e. board[y][x] 
                // X is the horizontal dimension & X is incremented
                // Y is the vertical dimension
                //**********************************************
                if (board[y][i] != '^') {
                    clear = false;
                }
            }
            if (clear) // place ship on board, then set success to true
            {
                success = true;
                for (int i = x, j = 0; j < boat.getSize(); i++, j++) {
                    board[y][i] = boat.getSymbol();

                }
            }
        } else // follows same logic for horizontal placement of ship except
        // y is incremented instead of x
        {
            for (int i = y, j = 0; j < boat.getSize() && clear; i++, j++) {
                if (board[i][x] != '^') {
                    clear = false;
                }
            }
            if (clear) // place ship on board, then set success to true
            {
                success = true;
                for (int i = y, j = 0; j < boat.getSize(); i++, j++) {
                    board[i][x] = (boat.getSymbol());

                }
            }
        }
        if (!success && !auto) {
            // Prints statement that you can't overlap ships if the user tries to
            // overlap ships
            // also only prints this statement if the ships are manually arranged
            // ships are manually arranged if auto == false
            System.out.println("You cannot overlap ships. Please enter"
                    + " a valid positioning of the ship.");
        }
        return success;
    }

    /**
     * Mutator: clearShip()
     *
     * @param x // starting x coord
     * @param y // starting y coord
     * @param ship // reports its size and orientation preconditions: x and y
     * starting locations are valid for the ship postconditions: replaces all
     * tiles of the previous ship placed on the board with "^"
     */
    public void clearShip(int x, int y, Ship ship) {
        // ship orientation is vertical
        if (ship.getDirection() == Orientation.vertical) {
            for (int i = y, j = 0; j < ship.getSize(); i++, j++) {
                // sets all spots of the previously placed ship to "^"
                board[i][x] = '^';
            }
        } else {
            // ship orientation is horizontal
            for (int i = x, j = 0; j < ship.getSize(); i++, j++) {
                // sets all tiles of the previously placed ship to "^"
                board[y][i] = '^';
            }
        }
    }

    public void setSymbol(int x, int y, char symbol) {
        board[x][y] = symbol;
    }

    public char getSymbol(int x, int y) {

        return board[x - 1][y - 1];
    }

    public void sinkShip(int x, int y, int size, Orientation orientation) {
        int i = 0;
        int row = x;
        int col = y;
        while (i < size) {
            board[col][row] = 'S';

            if (orientation == Orientation.horizontal) {
                row++;
            } else {
                col++;
            }
            i++;
        }

    }

    // returns string representation of the seaBoard created
    public String toString() {
        String board1 = "\n"; // string representing board
        int y = 1; // int to display y values of board
        for (int x = 0; x <= 10; x++) {
            board1 += "" + x + "\t"; // // displays x values of board
        }
        board1 += "\n\n";
        for (int i = 0; i < SIZE; i++, y++) {
            board1 += "" + y + "\t"; // displays y value of board
            for (int j = 0; j < SIZE; j++) {
                board1 = board1.concat(board[i][j] + "\t");
            }
            board1 = board1.concat("\n\n");
        }
        return board1; // returns completed string representation of board
    }

    public String compBoard() {
        String cBoard = "Computer Board\n ";

        int y = 1; // int to display y values of board
        for (int x = 0; x <= 10; x++) {
            cBoard += "" + x + "\t"; // // displays x values of board
        }
        cBoard += "\n\n";
        for (int i = 0; i < SIZE; i++, y++) {
            cBoard += "" + y + "\t"; // displays y value of board
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 'A' || board[i][j] == 'B'
                        || board[i][j] == 'C' || board[i][j] == 'D'
                        || board[i][j] == 'F') {
                    cBoard += "^" + "\t";
                } else {
                    cBoard += board[i][j] + "\t";
                }

            }
            cBoard = cBoard.concat("\n\n");
        }
        return cBoard; // returns completed string representation of board

    }

    /**
     * Mutator: checkFor user's guess is a hit of a ship Updates board with M
     * for a Miss, beeps and updates board with H for hit
     *
     * @param x retrieve the x coordinate of the ship
     * @param y retrieve the y coordinate of the ship
     * @return true if Hit otherwise false
     */
    public boolean checkForHit(int x, int y) {
        boolean imHit = false;
        char whatsThere = getSymbol(x, y);

        if (whatsThere != '^' && whatsThere != 'S'
                && whatsThere != 'H' && whatsThere != 'M') { // must be a ship if not one of these

            imHit = true;
            board[x - 1][y - 1] = 'H';
            Toolkit.getDefaultToolkit().beep();

        } else if (whatsThere == '^') {
            board[x - 1][y - 1] = 'M';
        }
        return imHit;
    }

}
