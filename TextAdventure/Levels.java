// Levels class
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.StringTokenizer;
import javax.xml.stream.XMLStreamException;

public class Levels
{

	private int levelNum;
	private static int currentRoomIndex;
	private ArrayList<Room> room;
	//private ArrayList<String> listKeyword;

	public Levels()
	{
		levelNum = 0;
		currentRoomIndex = 0;
		room = new ArrayList<Room>();
		//listKeyword = new ArrayList<String>();
	}

	public static void setRoomIndex(int index)
	{
		currentRoomIndex = index;
	}

	public static int getRoomIndex()
	{
		return currentRoomIndex;
	}

	public int processAction(Parser par,Player p) // returns index of room
	{
		int index = -1;
		int count = 0;
		boolean hasBoth = false;
		int fIndex = -1; // index of found set
		int roomIDX = -1;
		for (Room rm : room)
		{
			String currentWord = par.getWord(0); // get action word
			index = rm.testWord(currentWord);
			if (index != -1)
			{
				count++;
				roomIDX = room.indexOf(rm);
				System.out.println("index of occurance " + currentWord + count + ": " + roomIDX);
				// look for next words
				for (int i = 1; i < par.getListSize(); i++)
				{
					currentWord = par.getWord(i);
					index = rm.testWord(currentWord);
					if (index != -1)
					{
						if (!hasBoth)
						{
							fIndex = roomIDX;
							hasBoth = true;
							p.checkGettableItems(room);
							//System.out.println("next word found : " + currentWord);
						}
					}
				}

			}
		}
		return fIndex;
	}


/*
	public int checkKeywords(Parser par)
	{

		boolean hasAction = false;
		boolean hasOther = false;
		int index = -1;
		int roomID = -1;
		int roomID2 = -1;

		for (Room rm: room)
		{
			for (int i = 0; i < par.getListSize(); i++)
			{
				String currentWord = par.getWord(i);
				index = rm.testWord(currentWord);
				if (index != -1)
				{
					if (i == 0)
					{
						if (!hasAction)
						{
							hasAction = true;
							//roomID = rm.intRoomNum(rm.getRoomNum());
							roomID = room.indexOf(rm);
							System.out.println("roomid 1:" + roomID + ":" + roomID2);

						}
					}
					else
					{
						if (hasAction == true)
						{
							hasOther = true;
							roomID2 = room.indexOf(rm);
							System.out.println("Roomid 2:" + roomID2+ ":" + roomID);
							hasAction = false;
							hasOther = false;
						}
					}
					System.out.println(roomID + ":" + roomID2);
					//if (roomID != roomID2)
					//{
					//	roomID = -1;
					//}
				}
			}
		}
		//System.out.println(room.indexOf(Integer.toString(roomID)));
		return roomID;
	}*/

	public Room getRoom(int index)
	{
		return room.get(index);
	}



	public String getRoomDesc(int index)
	{
		String desc = "";
		Room rm = new Room();
		if (index < room.size())
		{
			rm = room.get(index);
			desc = rm.getDesc();
		}
		return desc;
	}


	public void displayDesc() //temporary just for testing
	{
		for ( Room currentRoom : room)
		{
			System.out.println(currentRoom.getDesc());
		}
	}

/*	public String [] getArray(int max)
	{
		String [] thisArray = new String [max];
		return thisArray;
	}*/

/*	public void loadLevelXML(String filename) throws XMLStreamException
	{
		ReadXML r = new ReadXML(filename);
		while (r.reader.hasNext())
		{
			r.reader.next();
			if (r.getTag().equals("room")
			{
*/


	public void loadLevel()
	{
		try
		{
			FileReader fr = new FileReader("dungeon.txt");
			BufferedReader br = new BufferedReader(fr);
			String stringRead = br.readLine();

			while (stringRead != null)
			{
				StringTokenizer stok = new StringTokenizer(stringRead,":");
				String roomNum = stok.nextToken();
				String keywords = stok.nextToken();
				String description = stok.nextToken();

				Room temp = new Room(roomNum, keywords, description, false,"");
				temp.tokenKeys(keywords);

				room.add(temp);

				stringRead = br.readLine();
			}

			br.close();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}


}

