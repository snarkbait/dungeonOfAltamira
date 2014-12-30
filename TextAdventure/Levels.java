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
//	private ArrayList<Inventory> inv;

	public Levels()
	{
		levelNum = 0;
		currentRoomIndex = 0;
		room = new ArrayList<Room>();
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

	public static String getRoomIndex()
	{
		return String.valueOf(currentRoomIndex);
	}


	public int processAction(Parser par,Player p) // returns index of room
	{
		int index = -1;
		int count = 0;
		boolean hasBoth = false;
		int fIndex = -1; // index of found set
		int roomIDX = -1; // room index within arraylist, not field 'roomNum'
		for (Room rm : room)
		{
			String currentWord = par.getWord(0); // get action word
			index = rm.testWord(currentWord);
			if (index != -1)
			{
				count++;
				roomIDX = room.indexOf(rm);
				//System.out.println("index of occurance " + currentWord + count + ": " + roomIDX); //:DEBUG:
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
							currentRoomIndex = Integer.parseInt(rm.getRoomNum());
							//rm.setComplete(true);
							hasBoth = true;
						}
					}
				}

			}
		}
		return fIndex;
	}

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
			if (!rm.isComplete())
			{
				desc = rm.getDesc();
			}
			else
			{
				desc = rm.getDescComplete();
			}
		}
		return desc;
	}

	public void setRoomComplete(int index)
	{
		room.get(index).setComplete(true);
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
		Weapon.AttackType atk = null;
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
		int iHealth = 0;
		int iHunger = 0;
		int iDamage = 0;
		int aNum = 0;
		int aCount = 0;
		int dMod = 0;
		int hit = 0;
		String idesc = null;


		while (r.reader.hasNext())
		{
			int event = r.reader.next();
			switch (r.getEventType())
			{
				case 0: //start
					tagStart = r.getTag();
					//System.out.println(tagStart);
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
							if (subclass == "")
							{
									subclass = r.getAttrValue();
									//System.out.println("sc:" + subclass);
							}
						}

						if (tagStart.equals("attacks"))
						{
							aNum = Integer.parseInt(r.getAttrValue());
							//System.out.println("anum:" + aNum);
							aCount = 0;
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
								break;
							case "equip":
								equip = Boolean.parseBoolean(tag);
								break;
							case "hunger":
								iHunger = Integer.parseInt(tag);
								break;
							case "desc":
								idesc = tag;
								if (subclass.equalsIgnoreCase("food"))
								{
									InvItem temp = new Food(iName, idesc, false, iHunger);
									Player.addInvItem(iName, iQty, equip, true, troom.getRoomNum(), temp);
									subclass = "";
								}
								else
								{
								}
								break;
							case "damage":
								iDamage = Integer.parseInt(tag);
								break;
							case "attackType":
								atk = Weapon.AttackType.valueOf(tag);
								break;
							case "dmgMod":
								dMod = Integer.parseInt(tag);
								break;
							case "toHitMod":
								hit = Integer.parseInt(tag);
								if (subclass.equalsIgnoreCase("weapon"))
								{
									//System.out.println("c:" + aCount);
									//System.out.println("an:" + aNum);
									Weapon wtemp = new Weapon(iName, idesc, false, iDamage, aNum);
									wtemp.addAttack(aCount, atk, dMod, hit);
									aCount++;
									Player.addInvItem(iName, iQty, equip, true, troom.getRoomNum(), wtemp);
									subclass = "";
								}
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

