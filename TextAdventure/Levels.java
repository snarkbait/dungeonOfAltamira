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

	public int findRoomByNum(String num)
	{
		for (Room rm : room)
		{
			if (rm.getRoomNum().equals(num))
			{
				return room.indexOf(rm);
			}
		}
		return -1;
	}

	public boolean testExitRoom(int index) throws XMLStreamException
	{
		int e = room.get(index).getExitToLevel();
		if (e > 0)
		{
			if (room.get(index).isComplete())
			{
				room.clear();
				loadLevelXML("level1",e);
				return true;
			}
		}
		return false;
	}



	public String getRoomDesc(int index)
	{
		return room.get(index).getDesc();
	}

	public String handleSpecialAction(Player p, int index) // and get description line
	{
		String desc = "";
		String t = null;
		boolean after = false;
		Room rm = room.get(index);
		t = rm.action.getActionType(); // get type of action
		after = rm.action.getIsAfter(); // does action take place before or after second description

		if (!rm.isComplete())
		{
			desc = rm.getDesc(); // get initial description
		}
		else
		{
			desc = rm.getDescComplete();
		}

		if (rm.action.getAction()) // if action = true, room has special action
		{

			switch (t)
			{
				case "ITEM": // ITEM - second desc won't show until items are picked up
					if (!rm.isComplete())
					{
						int itemCount = p.findGettableByRoomID(rm.getRoomNum());
						System.out.println("room:" + itemCount);
						if (itemCount == 0)
						{
							System.out.println("complete?");
							rm.setComplete(true);
							desc = rm.getDescComplete();
						}
					}
					break;
				case "HP": // HP (room adds or subtracts health)
					if (!after)
					{
						if (!rm.isComplete())
						{
							System.out.println("hp subtracted");
							p.addHP(Integer.parseInt(rm.action.getActionLabel()));
							rm.setComplete(true);
							desc = rm.getDescComplete();
						}
					}
					else
					{
						System.out.println("after");
						if (!rm.isComplete())
						{
							desc = rm.getDesc();
							rm.setComplete(true);
						}
						else
						{
							System.out.println("hp hit!");
							p.addHP(Integer.parseInt(rm.action.getActionLabel()));
							desc = rm.getDescComplete();
						}
					}
					break;
				case "MONSTER": // MONSTER - initiate combat, player must defeat befor moving on

					break;
				case "roomID": // RoomID - requires a different room to be 'complete'
					if (!rm.isComplete())
					{
						String checkRoom = rm.action.getActionLabel();
						if (this.getRoomComplete(this.findRoomByNum(checkRoom)))
						{
							System.out.println("completed!");
							rm.setComplete(true);
							desc = rm.getDescComplete();
						}
					}
					break;
				default: // other

					break;
			}
		}
		else // room has no special action
		{
			if (!rm.isComplete())
			{
				rm.setComplete(true);
			}
			else
			{
				desc = rm.getDescComplete();
			}
		}
		return desc;
	}



/*
	public String getRoomDesc(Player p, int index) // also does some action handling
	{
		String desc = "";
		Room rm = new Room();
		if (index < room.size())
		{
			rm = room.get(index);
			if (!rm.isComplete())
			{
				desc = rm.getDesc();
				int t = this.roomProcessAction(p, index);
				if (t == 0) // item
				{
					int itemCount = p.findGettableByRoomID(rm.getRoomNum());
					System.out.println("room:" + itemCount);
					if (itemCount == 0)
					{
						System.out.println("complete?");
						rm.setComplete(true);
						desc = rm.getDescComplete();

					}
				}
				if (t == 1)
				{
					if (rm.action.getIsAfter())
					{
						rm.setComplete(true);
					}
				}
				if (t == 3) // room id
				{
					String checkRoom = rm.action.getActionLabel();
					if (this.getRoomComplete(this.findRoomByNum(checkRoom)))
					{
						System.out.println("completed!");
						rm.setComplete(true);
						desc = rm.getDescComplete();
					}
				}
				else
				{
					//rm.setComplete(true);
				}

			}
			else
			{
				desc = rm.getDescComplete();
				int t = roomProcessAction(p, index);
			}
		}
		return desc;
	}

	public int roomProcessAction(Player p, int index)
	{
		return room.get(index).processAction(p);
	}*/

	public void setRoomComplete(int index)
	{
		room.get(index).setComplete(true);
	}

	public boolean getRoomComplete(int index)
	{
		return room.get(index).isComplete();
	}


	public void displayDesc() //temporary just for testing
	{
		for ( Room currentRoom : room)
		{
			System.out.println(currentRoom.getDesc());
		}
	}


	public void loadLevelXML(String filename, int areaID) throws XMLStreamException
	{
		ReadXML r = new ReadXML(filename);
		ArrayList<Room> rList = null;
		Weapon.AttackType atk = null;
		RoomAction act = null;
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
		boolean rAction = false;
		String rType = null;


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

						if (tagStart.equals("descOnComplete"))
						{
							rAction = Boolean.parseBoolean(r.getAttrValue());
							act = new RoomAction();
							act.setAction(rAction);
							act.setIsAfter(Boolean.parseBoolean(r.getAttrValue(1)));
						}
						if (tagStart.equals("action"))
						{
							act.setActionType(r.getAttrValue());
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
							case "action":
								act.setActionLabel(tag);
								troom.setRoomAction(act);
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
							case "health":
								iHealth = Integer.parseInt(tag);
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
							case "item":
								if (subclass.equalsIgnoreCase("potion"))
								{
									Potion ptemp = new Potion(iName, idesc, false, iHealth, iHunger);
									Player.addInvItem(iName, iQty, equip, true, troom.getRoomNum(), ptemp);
								}
								break;
							case "exit":
								troom.setExitToLevel(Integer.parseInt(tag));
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
	}


/* deprecated
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
	*/

}

