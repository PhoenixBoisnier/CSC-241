/**
 * 
 * @author Phoenix Boisier
 * 
 * Change-log:
 * 	10/7/17
 * 	-fixed animal due to abstraction of creature
 * 	-updated unhappy() and happy() methods
 * 	-updated doc comments
 * 	
 *
 */
public class Animal extends Creature{
	
	
	/**
	 * Creates a new animal.
	 * @param N the name of the animal.
	 */
	public Animal (String N, String D) {
		super(N, D);
	}
	
	/**
	 * Determines if the animal is pleased with its room's state.
	 * @return 
	 */
	public boolean isPleased() {
		switch(this.getRoom().getState()) {
			case CLEAN:
				return true;
			case DIRTY:
				return false;
			case HALFDIRTY:
				return true;
		}
		return false;
	}
	
	/**
	 * Used to check if the animal would be pleased in another room.
	 * @param room the nearby room
	 * @return true if CLEAN or HALFDIRTY
	 */
	public boolean isPleasedNearby(Room room) {
		switch(room.getState()) {
			case CLEAN:
				return true;
			case DIRTY:
				return false;
			case HALFDIRTY:
				return true;
		}
		return false;
	}
	
	/**
	 * 
	 * Bumps up the current state of the room one step cleaner.
	 */
	public void modifyRoom() {
		System.out.println(this.getName()+" tidied up the "+this.getRoom().getName());
		switch(this.getRoom().getState()) {
			case CLEAN:
				System.out.println("The room is already clean.");
				break;
			case DIRTY:
				this.getRoom().setState(Room.getHalfDirty());
				break;
			case HALFDIRTY:
				this.getRoom().setState(Room.getClean());
				break;
		}
		System.out.println("It is now "+this.getRoom().getState());
	}
	
	/**
	 * Prints unhappy things to the console.
	 * 
	 */
	public String unhappy() {
		return this.getName()+" is unhappy with the change.\n"
				+this.getName()+" makes an an angry sounding animal noise.";
	}
	
	/**
	 * Prints happy things to the console.
	 * 
	 */
	public String happy() {
		return this.getName()+" is happy with the change.\n"
				+this.getName()+" makes some sort of happy animal noise.";
	}

	/**
	 * Returns false always.
	 */
	public boolean isPlayer() {
		return false;
	}

}
