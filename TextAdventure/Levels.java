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
	private ArrayList<Inventory> inv;
	//private ArrayList<String> listKeyword;

	public Levels()
	{
		levelNum = 0;
		currentRoomIndex = 0;
		room = new ArrayList<Room>();
		//listKeyword = new ArrayList<String>();
	}

	public ArrayList<Room> getRoomList()
	{
		return room;
	}

	public static void setRoomIndex(int index)
	{
		currentRoomIndex = index;
	}

	public int getLevelNum()
	{
		return levelNum;
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
							//p.checkGettableItems(room);
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

	public void loadLevelXML(String filename, int areaID) throws XMLStreamException
	{
		ReadXML r = new ReadXML(filename);
		ArrayList<Room> rList = null;
		//ArrayList<String> words = null;
		Room troom = null;
		String tag = null;
		String tagStart = null;
		String tagEnd = null;
		String adesc = null;
		boolean areaFound = false;
		String subclass = "";
		int ID = 0;
		String iName = "";
		int iQty = 0;
		boolean equip = false;

		while (r.reader.hasNext())
		{
			int event = r.reader.next();
			switch (r.getEventType())
			{
				case 0: //start
					tagStart = r.getTag();
					if (tagStart.equals("area"))
					{
						ID = Integer.parseInt(r.getAttrValue());
						if (ID == areaID)
						{
							levelNum = ID;
							areaFound = true;
							rList = new ArrayList<Room>();

						}
					}


					if (ID == areaID)
					{
						if (tagStart.equals("room"))
						{
							troom = new Room();
							troom.setRoomNum(r.getAttrValue());
						}

						if (tagStart.equals("keywords"))
						{
							troom.setKeywords(r.getAttrValue());
						}

						if (tagStart.equals("item"))
						{
							subclass = r.getAttrValue();
						}

					}
					break;
				case 1: //characters
					if (ID == areaID)
					{
						tag = r.getText();
					}
					break;
				case 2:
					if(ID == areaID)
					{
						tagEnd = r.getTag();
						switch (tagEnd)
						{
							case "room":
								rList.add(troom);
								break;
							case "description":
								troom.setDesc(tag);
								break;
							case "descOnComplete":
								troom.setDescComplete(tag);
								break;
							case "word":
								troom.addListKeyword(tag);
								break;
						// inventory items (if they exist)
							case "name":
								iName = tag;
								break;
							case "qty":
								iQty = Integer.parseInt(tag);
							case "equip":
								equip = Boolean.parseBoolean(tag);
								break;
							case "desc":
								String idesc = tag;
								InvItem temp = new InvItem(iName, idesc, false);
								Player.addInvItem(iName, iQty, equip, true, troom.getRoomNum(), temp);
								break;


						}
					}
					break;
			}
		}
		if ((rList != null) && (!rList.isEmpty()))
		{
			room.addAll(rList);
		}
		/*if ((listKeyword != null) && (!listKeyword.isEmpty()));
		{
			listKeyword.addAll(words);
		}*/
	}



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

