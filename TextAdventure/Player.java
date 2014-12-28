// Player class
import java.util.Random;
import java.util.ArrayList;

public class Player
{
	private String playerName;
	private int hitPoints;
	private int expPoints;
	private int gold;
	private int playerLevel;
	private int armorClass;
	private int hunger;

	public static Random rndgen = new Random();

	private ArrayList<Inventory> playerInventory;

	public Player()
	{
		playerName = "";
		hitPoints = 0;
		expPoints = 0;
		gold = 0;
		playerLevel = 0;
		armorClass = 0;
		hunger = 0;

		playerInventory = new ArrayList<Inventory>();
	}

	public Player(String name,int hp,int exp,int g,int lvl,int ac, int hr)
	{
		playerName = name;
		hitPoints = hp;
		expPoints = exp;
		gold = g;
		playerLevel = lvl;
		armorClass = ac;
		hunger = hr;
		playerInventory = new ArrayList<Inventory>();
	}


	public void setName(String name)
	{
		playerName = name;
	}

	public String getName()
	{
		return playerName;
	}

	public void setHitPoints(int hp)
	{
		hitPoints = hp;
	}

	public int getHitPoints()
	{
		return hitPoints;
	}

	public void addHP(int amt)
	{
		hitPoints = hitPoints + amt;
	}

	public void setExpPoints(int exp)
	{
		expPoints = exp;
	}

	public int getExpPoints()
	{
		return expPoints;
	}

	public void addEXP(int amt)
	{
		expPoints = expPoints + amt;
	}

	public void setGold(int g)
	{
		gold = g;
	}

	public int getGold()
	{
		return gold;
	}

	public void setPlayerLevel(int level)
	{
		playerLevel = level;
	}

	public int getPlayerLevel()
	{
		return playerLevel;
	}

	public void setArmorClass(int ac)
	{
		armorClass = ac;
	}

	public int getArmorClass()
	{
		return armorClass;
	}

	public void setHunger(int hr)
	{
		hunger = hr;
	}

	public int getHunger()
	{
		return hunger;
	}

	public void addInvItem(String invItem, int qty)
	{
		Inventory temp = new Inventory(invItem, qty, false, false, null);
		playerInventory.add(temp);
	}

	public void addInvItem(String invItem, int qty,boolean eq, boolean gettable,String roomID)
	{
		Inventory temp = new Inventory(invItem, qty, false, false, null);
		playerInventory.add(temp);
	}


	public void removeInvItem(int index)
	{
		if (index < playerInventory.size())
		{
			playerInventory.remove(index);
		}
	}

	public void removeInvItem(String invItem)
	{
		for (Inventory currentItem : playerInventory)
		{
			if (currentItem.getItem().equals(invItem))
			{
				playerInventory.remove(playerInventory.indexOf(currentItem));
			}
		}
	}

	public void checkGettableItems(ArrayList<Room> room)
	{
		int count = 0;
		for (Room rm : room)
		{
			if (rm.getDesc().equals("items"))
			{
				rm.tokenKeys(rm.getKeywords());
				for (int i = 0; i < rm.getListSize(); i++)
				{
					if (!rm.isComplete())
					{
						count ++;
						System.out.println("item:" + rm.getTokenWord(i));
						this.addInvItem(rm.getTokenWord(i), 1, false, true, rm.getRoomNum());
						//rm.setComplete( true );
					}
				}
				if (count > 0) { rm.setComplete( true ); }
			}
		}
	}

/*	public void listInventory()
	{
		System.out.println("==========INVENTORY=================");
		if (playerInventory.size() > 0)
		{
			for (String currentItem : playerInventory)
			{
				System.out.println(currentItem);
			}
		}
		else
		{
			System.out.println("You aren't carrying anything.");
		}
		System.out.println("====================================");
	}*/

	public String listInvGUI()
	{
		String invList=("===========Inventory============" + "\n");
		if (playerInventory.size() > 0)
		{
			for (Inventory currentItem : playerInventory)
			{
				invList += currentItem.toString();
			}
		}
		else
		{
			invList+= "You aren't carrying anything." + "\n";
		}
		return invList;
	}


	public static int getRandomInt(int low, int high)
	{
		return rndgen.nextInt(high - low + 1) + low;
	}

	public String toString()
	{
		String stats = "==========PLAYER STATS==============\n";
		stats = stats + playerName + "\n";
		stats = stats +"====================================\n";
		stats = stats +"Hit Points: " + hitPoints + "\n" + "Experience Points:" + expPoints + "\n";
		stats = stats +"Gold Pieces: " + gold + "\n" + "Level: " + playerLevel + "\n";
		stats = stats +"Armor Class: " + armorClass + "\n" + "Hunger: " + hunger + "\n";
		stats = stats +"====================================\n";
		return stats;
	}
}

