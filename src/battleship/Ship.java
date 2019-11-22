/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

/**
 *
 * @author Galina and Bruce
 */
public class Ship {

    ShipsName name;
    Orientation direction;
    private int size, coordinateX, coordinateY;
    protected int hitCount;
    private boolean sunk;
    private char symbol;

    public Ship() {
        
        coordinateX = coordinateY = 0;
        sunk = false;

    }

    public Ship(ShipsName n, int s, char sym) {
        name = n;
        size = s;
        symbol = sym;

    }

    public void setDirection(Orientation direc) {
        direction = direc;
    }

    public Orientation getDirection() {
        return direction;
    }

    public void setShipsName(ShipsName n) {
        name = n;
    }

    public ShipsName getShipsName() {
        return name;
    }

    public void setSize(int s) {
        size = s;
    }

    public int getSize() {
        return size;
    }

    public void setSunk(boolean sink) {
        sunk = sink;
    }

    public boolean getSunk() {
        return sunk;
    }

    public void setSymbol(char sym) {
        symbol = sym;
    }

    public char getSymbol() {
        return symbol;
    }

    public boolean sunkOrNot() {
        if (hitCount == size) {
            hitCount = 0;
            sunk = true;
        } else {
            sunk = false;
        }
        return sunk;
    }

    public void setCoordinates(Orientation ori, int x, int y) {
        direction = ori;
        coordinateX = x;
        coordinateY = y;
    }

    public void setXCoordinate(int x) {
        coordinateX = x;
    }

    public int getXCoordinate() {
        return coordinateX;
    }

    public void setYCoordinate(int y) {
        coordinateY = y;
    }

    public int getYCoordinate() {
        return coordinateY;
    }

    public String toString() {
        String out = "";
        out += "Name of the ship: " + name + "\nSize: " + size + "\nOrientation: "
                + direction;
        return out;

    }

}
