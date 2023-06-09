/**
 * 
 * @author Phoenix Boisier
 * 
 * Change-log:
 * 	10/7/17
 * 	-thought to myself about using maps, but decided I'm still more familiar with ArrayList 
 * 	-removed characters() method as it was not needed here
 * 	
 *
 */
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;

public class ProjectXMLParser extends DefaultHandler {

	//these variables are the holding cell for the info needed to create the world
	//they are cleansed after each new room
    private String roomState = "clean";
    private String roomName = "MISSING ROOM NAME";
    private String roomDesc = "MISSING ROOM DESCRIPTION";
    private String animalName = "MISSING ANIMAL NAME";
    private String animalDesc = "MISSING ANIMAL DESCRIPTION";
    private String NPCName = "MISSING NPC NAME";
    private String NPCDesc = "MISSING NPC DESCRIPTION";
    private String PCName = "MISSING PC NAME";
    private String PCDesc = "MISSING PC DESCRIPTION";
    private Room roomBuffer;
    
    //these are the variables that handle rooms' neighbors
    private ArrayList<ArrayList<String>> neighbors = new ArrayList<>();
    private String northRoom = "NO SUCH ROOM";
    private String southRoom = "NO SUCH ROOM";
    private String eastRoom = "NO SUCH ROOM";
    private String westRoom = "NO SUCH ROOM";
    private ArrayList<String> neighboringRoomsList = new ArrayList<>();
    
    //this is the variable used to hold creatures until they are placed into a room
    //this is cleansed after they are all placed into the room
    private ArrayList<Creature> creatureBuffer = new ArrayList<>();
    
    //this is the final product
    private ArrayList<Room> rooms = new ArrayList<>();
    
	public void startDocument() throws SAXException {
		System.out.println("Start Document");
		}
 
	/**
	 * Locates and assigns information to the variables used to create the world in endElement().
	 */
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		System.out.println("Start Element: " + qName);
		if(qName.equals("room")) {
			roomName = atts.getValue("name");
			roomDesc = atts.getValue("description");
			roomState = atts.getValue("state");
			northRoom = atts.getValue("north");
			southRoom = atts.getValue("south");
			eastRoom = atts.getValue("east");
			westRoom = atts.getValue("west");
			neighboringRoomsList.add(northRoom);
			neighboringRoomsList.add(southRoom);
			neighboringRoomsList.add(eastRoom);
			neighboringRoomsList.add(westRoom);
		}
		if(qName.equals("animal")) {
			animalName = atts.getValue("name");
			animalDesc = atts.getValue("description");
		}
		if(qName.equals("NPC")) {
			NPCName = atts.getValue("name");
			NPCDesc = atts.getValue("description");
		}
		if(qName.equals("PC")) {
			PCName = atts.getValue("name");
			PCDesc = atts.getValue("description");
		}
    }
 
	/**
	 * Builds the individual rooms and creatures in the world, and adds the creatures to the rooms.
	 * This is also where the rooms get their neighboring rooms assigned.
	 */
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        System.out.println("End Element: " + qName);
        if(qName.equals("room")) {
        	//creates the new room
        	roomBuffer = new Room(roomName, roomDesc);
        	//this sets the room to the appropriate cleanliness level
        	switch (roomState) {
        		case "clean":{ 
        			roomBuffer.setState(State.CLEAN);
        		}
        		case "dirty":{
        			roomBuffer.setState(State.DIRTY);
        		}
        		default:{
        			roomBuffer.setState(State.HALFDIRTY);
        		}
        	}
        	//then resets the state string to half-dirty
        	roomState = "half-dirty";
        	//puts each creature into the room then removes it from the buffer
        	for(int i = 0; i<creatureBuffer.size(); i = 0) {
        		roomBuffer.addCreature(creatureBuffer.get(i));
        		creatureBuffer.remove(i);
        	}
        	//adds the neighboringRooms array for later assignment
			neighbors.add(neighboringRoomsList);
        	//removes the strings in neighboringRoomsList
        	neighboringRoomsList = new ArrayList<>();
        	//adds the room into the room list
        	rooms.add(roomBuffer);
        }
        if(qName.equals("animal")) {
        	//creates the animal
        	creatureBuffer.add(new Animal(animalName, animalDesc));
        	//resets the animal strings
        	animalName = "MISSING ANIMAL NAME";
        	animalDesc = "MISSING ANIMAL DESCRIPTION";
        }
        if(qName.equals("NPC")) {
        	//creates the NPC
        	creatureBuffer.add(new NPC(NPCName, NPCDesc));
        	//resets the NPC strings
        	NPCName = "MISSING NPC NAME";
        	NPCDesc = "MISSING NPC DESCRIPTION";
        }
        if(qName.equals("PC")) {
        	//creates the PC
        	creatureBuffer.add(new PC(PCName, PCDesc));
        	//resets the PC strings
        	PCName = "MISSING PC NAME";
        	PCDesc = "MISSING PC DESCRIPTION";
        }
        if(qName.equals("xml")) {
    		//this is where all the neighboring rooms are assigned to each other
    		for(int i = 0; i<rooms.size(); i++) {
    			//for all of the rooms
    			for(int p = 0; p<4; p++) {
    				//and for all of the corresponding neighbor sets
    				if(neighbors.get(i).get(p)!=null) {
    					//if the neighbor at index p exists
    					if(p==0) {
    						//where p=0 is the north room
    						for(int t = 0; t<rooms.size(); t++) {
    							//finding the room located at p in neighboring rooms is now found in rooms
    							if(rooms.get(t).getName().equals(neighbors.get(i).get(p))) {
    								//now that we've found that room
    								rooms.get(i).setRoomNorth(rooms.get(t));
    								//this room is now set to rooms at i as the north room
    								rooms.get(t).setRoomSouth(rooms.get(i));
    								//the original room is now set to room at t's south neighbor
    							}
    						}
    					}
    					if(p==1) {
    						//where p=1 is the south room
    						for(int t = 0; t<rooms.size(); t++) {
    							//finding the room located at p in neighboring rooms is now found in rooms
    							if(rooms.get(t).getName().equals(neighbors.get(i).get(p))) {
    								//now that we've found that room
    								rooms.get(i).setRoomSouth(rooms.get(t));
    								//this room is now set to rooms at i as the south room
    								rooms.get(t).setRoomNorth(rooms.get(i));
    								//the original room is now set to room at t's north neighbor
    							}
    						}
    					}
    					if(p==2) {
    						//where p=2 is the east room
    						for(int t = 0; t<rooms.size(); t++) {
    							//finding the room located at p in neighboring rooms is now found in rooms
    							if(rooms.get(t).getName().equals(neighbors.get(i).get(p))) {
    								//now that we've found that room
    								rooms.get(i).setRoomEast(rooms.get(t));
    								//this room is now set to rooms at i as the east room
    								rooms.get(t).setRoomWest(rooms.get(i));
    								//the original room is now set to room at t's West neighbor
    							}
    						}
    					}
    					if(p==3) {
    						//where p=3 is the west room
    						for(int t = 0; t<rooms.size(); t++) {
    							//finding the room located at p in neighboring rooms is now found in rooms
    							if(rooms.get(t).getName().equals(neighbors.get(i).get(p))) {
    								//now that we've found that room
    								rooms.get(i).setRoomWest(rooms.get(t));
    								//this room is now set to rooms at i as the west room
    								rooms.get(t).setRoomEast(rooms.get(i));
    								//the original room is now set to room at t's east neighbor
    							}
    						}
    					}
    				}
    			}
    		}
        }
    }

    /**
     * This doesn't do much.
     */
	public void endDocument() throws SAXException {	
		System.out.println("End Document\n\n\n");
		}
 
	/**
	 * This returns the world to be used as the game world.
	 * @return The ArrayList of rooms that is build by the parser.
	 */
	public ArrayList<Room> getRooms(){
		return rooms;
		}
	}	