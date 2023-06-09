/**
 * 
 * @author Phoenix Boisnier
 * 
 * Change-log: 
 * 	10/7/17
 * 	-began logging changes
 * 	-made creature abstract
 * 	-added throws NullPointerException for getRoom()
 * 	-updated doc comments
 */
public abstract class Creature {

	private String name;
	private String desc;
	private Room r;
	
	/**
	 * Creates a new animal.
	 * @param N the name of the animal.
	 */
	public Creature (String N, String D) {
		name = N;
		desc = D;
		r = null;
	}

	/**
	 * 
	 * @return The creature's name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the name and description of the creature in a pleasing format.
	 */
	public String toString() {
		return (this.name + ", " + this.desc);
	}
	
	/**
	 * 
	 * @return The description of the creature.
	 */
	public String getDesc() {
		return desc;
	}
	
	/**
	 * 
	 * @return The room the creature is in.
	 */
	public Room getRoom() throws NullPointerException{
		return r;
	}
	
	/**
	 * Sets the creature's room to a new room.
	 * @param newR the room the creature is now in
	 */
	public void setRoom(Room newR) {
		r = newR;
	}
	
	/**
	 * Animals like CLEAN and HALFDIRTY rooms and will return true for either.
	 * NPCs like DIRTY and HALFDIRTY rooms and will return true for either.
	 * The player does not care and therefore always returns true.
	 * @return true or false
	 */
	public abstract boolean isPleased();
	
	/**
	 * Checks to see if a nearby room is suitable for the creature to move into.
	 * @param A nearby room to be checked.
	 */
	public abstract boolean isPleasedNearby(Room r);
	
	/**
	 * Animals will always try to clean the room.
	 * NPCs will always try to dirty the room.
	 * PCs use a different method.
	 * 
	 */
	public void modifyRoom() {
		System.out.println("Nothing happens. Wrong modifyRoom method used by player: "+this.getName());
	}
	
	/**
	 * This is the method that the PC uses to change the room's state.
	 * The input is currently a String as the planned implementation will use a scanner for input.
	 */
	public void modifyRoom(String s) {
		System.out.println("Wrong method used for modify room for " + this.getName());
	}
	
	/**
	 * Pretty self-explanatory...
	 * 
	 */
	public abstract boolean isPlayer();
	
	/**
	 * Prints out a string describing that the creature is unhappy.
	 * 
	 */
	public abstract String unhappy();
	
	/**
	 * Prints out a string describing that the creature is happy.
	 * 
	 */
	public abstract String happy();
	
}
