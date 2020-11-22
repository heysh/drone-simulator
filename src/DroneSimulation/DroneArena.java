package DroneSimulation;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Harshil Surendralal bf000259
 */
public class DroneArena {

    private int arenaX, arenaY, arenaId;
    private static int arenaIdCounter = 0;
    private ArrayList<Drone> drones;

    /**
     *
     * @param arenaX The width of the drone arena.
     * @param arenaY The height of the drone arena.
     */
    public DroneArena(int arenaX, int arenaY) {
        this.arenaX = arenaX;
        this.arenaY = arenaY;
        this.arenaId = arenaIdCounter++;
        this.drones = new ArrayList<Drone>();
    }

    /**
     * @return The width of the drone arena.
     */
    public int getArenaX() {
        return this.arenaX;
    }

    /**
     * @return The height of the drone arena.
     */
    public int getArenaY() {
        return this.arenaY;
    }

    /**
     * @return The ID of the drone arena.
     */
    public int getArenaId() {
        return this.arenaId;
    }

    /**
     * @return The list of all drones in the drone arena.
     */
    public ArrayList<Drone> getDrones() {
        return this.drones;
    }

    /**
     * Add a drone in a random, empty location in the drone arena.
     */
    public void addDrone() {
        // if the drone arena is full, return
        if (this.drones.size() == this.arenaX * this.arenaY) {
            return;
        }

        Random randomGenerator;
        randomGenerator = new Random();
        int randomX, randomY;
        Drone drone;

        // generate a random direction
        Direction randomDir = Direction.North;
        randomDir = randomDir.getRandomDirection();

        // generate random x and y co-ordinates until the co-ordinates point to an empty space
        do {
            randomX = randomGenerator.nextInt(this.arenaX);
            randomY = randomGenerator.nextInt(this.arenaY);
            drone = getDroneAt(randomX, randomY);
        } while (drone != null);

        // create a new drone with the random direction and random x and y co-ordinates and add this drone to the list
        // of all drones in the drone arena
        drone = new Drone(randomX, randomY, randomDir);
        this.drones.add(drone);
    }

    @Override
    public String toString() {
        String output = "Arena " + this.arenaId + " has a width of " + this.arenaX + " and a height of " + this.arenaY + "\n";

        for (Drone d : this.drones) output += d.toString();

        return output;
    }

    /**
     * @param x The x co-ordinate.
     * @param y The y co-ordinate.
     * @return The drone located at the provided x and y co-ordinates, otherwise null if empty.
     */
    public Drone getDroneAt(int x, int y) {
        for (Drone d : this.drones) {
            if (d.isHere(x, y)) {
                return d;
            }
        }
        return null;
    }

    /**
     * Iterate through each drone and add it to the console canvas.
     * @param c The console canvas that the drones are added to.
     */
    public void showDrones(ConsoleCanvas c) {
        for (Drone d : this.drones) d.displayDrone(c);
    }

    /**
     * Checks whether or not a drone can move a single unit in the direction it's facing.
     * @param x The x co-ordinate that is to be checked.
     * @param y The y co-ordinate that is to be checked.
     * @return True if the drone can move to the new co-ordinates, otherwise false.
     */
    public boolean canMoveHere(int x, int y) {
        Drone d = getDroneAt(x, y);

        // if the x and y co-ordinates are within the width and height of the drone arena
        if (x >= 0 && x < this.arenaX && y >= 0 && y < this.arenaY) {

            // and if there is no drone at the new co-ordinates, return true
            if (d == null) {
                return true;
            }
        }

        // otherwise the new co-ordinates are outside of the drone arena, or they are occupied by another drone
        return false;
    }

    /**
     * Move all drones a single unit in the direction they are facing if possible, otherwise change the direction
     * to the next cardinal direction (north, east, south and west) when moving in a clockwise fashion.
     */
    public void moveAllDrones() {
        for (Drone d : this.drones) d.tryToMove(this);
    }
}