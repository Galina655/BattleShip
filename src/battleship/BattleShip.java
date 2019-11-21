/*
 * Programmed by:
 * Due Date: 05/10/2018
 * Description: 
 */
package battleship;

import java.util.Scanner;

/**
 *
 * @author Pro.A.Wright,Galina, Bruce
 */
public class BattleShip {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        String resetGame;

        do {
            BattleShipGame game = new BattleShipGame();
            game.Game();
            resetGame = cin.nextLine();
        } while (resetGame.equalsIgnoreCase("y"));
        System.out.println("Bye! Have a nice day!");

    }

}
