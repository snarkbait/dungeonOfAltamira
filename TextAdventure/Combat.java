// class combat
// temporarily has a main class for testing, will be incorporated into the main gui
//
// TODO: compartmentalize the combat with playerTurn and monsterTurn methods
// TODO: allow player to always have punch/kick attacktypes
// TODO: use actual Player instance for the combat
// TODO: cleanup the read xml file code
// TODO: allow multiple-turn lasting damage hits i.e. poison
// TODO: implement magic system

import java.util.ArrayList;
import javax.xml.stream.XMLStreamException;
// class combat

public class Combat
{

	public static int[][] combatMatrix;
	private Monster m;
	private static ArrayList<Monster> mList;
	private Parser par = new Parser();

	public Combat()
	{
		combatMatrix = new int[10][10];
		createCombatMatrix();
	}

	public static void createCombatMatrix()
	{
		for (int row = 0; row < 10; row++)
		{
			for (int col = 0; col < 10; col++)
			{
				combatMatrix[row][col] = ((row * 5) + 50) - (col * 5);
			}
		}
	}

	private static int getAttackRoll()
	{
		return Player.getRandomInt(1,100);
	}

	public static boolean isAttackSuccess(int attackRoll, int playerToHit, int monsterAC, int attackModifier)
	{
		int pctToHit = combatMatrix[playerToHit][monsterAC] + attackModifier;
		//System.out.println("% to hit:" + pctToHit);
		//System.out.println("roll:" + attackRoll);
		if (attackRoll <= pctToHit)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static int getDamage(int pDamage, int pModifier, boolean critical)
	{
		int randInt = Player.getRandomInt(1, pDamage);
		if (critical) { pModifier += randInt; }
		//System.out.println("rolled damage:" + randInt);
		//System.out.println("damage modifier:" + pModifier);
		return randInt + pModifier;
	}

/*	public static ArrayList<Monster> readMonsterFile(String filename, int monsterID) throws XMLStreamException
	{
		Monster m = null;
		ArrayList<Monster> mList = null;
		int ID = 0;
		boolean monsterFound = false;
		String tagContent = null;
		String tagStart = null;
		String tagEnd = null;
		String adesc = null;
		Weapon.AttackType atk = null;
		int adamage = 0;
		int amod = 0;
		int count = 0;
		ReadXML r = new ReadXML(filename);
		while (r.reader.hasNext())
		{
			int event = r.reader.next();
			switch (r.getEventType())
			{
				case 0: //start
					tagStart = r.getTag();
					if (tagStart.equals("root"))
					{
						System.out.println("root");
						mList = new ArrayList<Monster>();
					}

					if (tagStart.equals("monster"))
					{
						ID = Integer.parseInt(r.getAttrValue());
						if (ID == monsterID)
						{
							System.out.println("monster id:" + ID);
							m = new Monster();
							m.id = ID;
						}
					}

					if (ID == monsterID)
					{

						if (tagStart.equals("attacks"))
						{
							m.setNumAttacks(Integer.parseInt(r.getAttrValue()));
							m.initArrays();
							count = 0;
						}
					}
					break;
				case 1:
					if (ID == monsterID)
					{
						tagContent = r.getText();
					}
					break;
				case 2:

					if (ID == monsterID)
					{
						tagEnd = r.getTag();
						switch (tagEnd)
						{
							case "monster":
								mList.add(m);
								break;
							case "name":
								m.setName(tagContent);
								break;
							case "desc":
								m.setDesc(tagContent);
								break;
							case "hpMin":
								m.setHPMin(Integer.parseInt(tagContent));
								break;
							case "hpMax":
								m.setHPMax(Integer.parseInt(tagContent));
								break;
							case "armorClass":
								m.setAC(Integer.parseInt(tagContent));
								break;
							case "tohit":
								m.setToHit(Integer.parseInt(tagContent));
								break;
							case "isDead":
								m.setIsDead(Boolean.parseBoolean(tagContent));
								break;
							case "attackType":
								atk = Weapon.AttackType.valueOf(tagContent);
								break;
							case "attackDesc":
								adesc = tagContent;
								break;
							case "attackDamage":
								adamage = Integer.parseInt(tagContent);
								break;
							case "attackDmgModifier":
								amod = Integer.parseInt(tagContent);
								m.addAttack( count, atk, adesc, adamage, amod);
								count++;
								break;
						}
					}
					break;

				}

		}
		return mList;
	}*/

	public static Monster readMonsterFile(String filename, int monsterID) throws XMLStreamException
	{
		Monster m = null;
		//ArrayList<Monster> mList = null;
		int ID = 0;
		boolean monsterFound = false;
		String tagContent = null;
		String tagStart = null;
		String tagEnd = null;
		String adesc = null;
		Weapon.AttackType atk = null;
		int adamage = 0;
		int amod = 0;
		int count = 0;
		ReadXML r = new ReadXML(filename);
		while (r.reader.hasNext())
		{
			int event = r.reader.next();
			switch (r.getEventType())
			{
				case 0: //start
					tagStart = r.getTag();

					if (tagStart.equals("monster"))
					{
						ID = Integer.parseInt(r.getAttrValue());
						if (ID == monsterID)
						{
							System.out.println("monster id:" + ID);
							m = new Monster();
							m.id = ID;
						}
					}

					if (ID == monsterID)
					{

						if (tagStart.equals("attacks"))
						{
							m.setNumAttacks(Integer.parseInt(r.getAttrValue()));
							m.initArrays();
							count = 0;
						}
					}
					break;
				case 1:
					if (ID == monsterID)
					{
						tagContent = r.getText();
					}
					break;
				case 2:

					if (ID == monsterID)
					{
						tagEnd = r.getTag();
						switch (tagEnd)
						{
							case "monster":
								//mList.add(m);
								break;
							case "name":
								m.setName(tagContent);
								break;
							case "desc":
								m.setDesc(tagContent);
								break;
							case "hpMin":
								m.setHPMin(Integer.parseInt(tagContent));
								break;
							case "hpMax":
								m.setHPMax(Integer.parseInt(tagContent));
								break;
							case "armorClass":
								m.setAC(Integer.parseInt(tagContent));
								break;
							case "tohit":
								m.setToHit(Integer.parseInt(tagContent));
								break;
							case "isDead":
								m.setIsDead(Boolean.parseBoolean(tagContent));
								break;
							case "expToPlayer":
								m.setExp(Integer.parseInt(tagContent));
								break;
							case "attackType":
								atk = Weapon.AttackType.valueOf(tagContent);
								break;
							case "attackDesc":
								adesc = tagContent;
								break;
							case "attackDamage":
								adamage = Integer.parseInt(tagContent);
								break;
							case "attackDmgModifier":
								amod = Integer.parseInt(tagContent);
								m.addAttack( count, atk, adesc, adamage, amod);
								count++;
								break;
						}
					}
					break;

				}

		}
		return m;
	}


	public void combatInit(int monsterID, Player p)
	{
		try
		{
			Monster m = readMonsterFile("monster",monsterID);
		}
		catch (XMLStreamException xse)
		{
			System.out.println("Bad error, man");
		}

		Combat c = new Combat();
	}




	public int combatPlayerTurn(Player p)
	{
		int mAttack = -1;

		while (!m.isDead)
		{
			// TODO
		}
		return 0;
	}

	public int combatMonsterTurn(Player p)
	{
		// TODO
		return 0;
	}


	public static void main(String args []) ///temp for testing
	{

		Parser par = new Parser();
		Weapon w = new Weapon("sword","long sword",true,10,2);
		w.addAttack(0,Weapon.AttackType.SLASH, 1, 0);
		w.addAttack(1,Weapon.AttackType.STAB, 1, 5);
		Monster m = null;



		Combat c = new Combat();
		try
		{
			m = readMonsterFile("monster", 101);
		}
		catch (XMLStreamException e)
		{
			System.out.println("some kinda error");
		}
		createCombatMatrix();

		//player
		int damage = w.getDamage();
		//int dModifier = w.getDmgMod();
		int pToHit = 3;
		//int pAMod = 5;
		int pAC = 3;
		int pHP = 100;
		//Monster m;
		boolean attack;
		int roll;
		boolean critical;
		boolean auto = false;
		int mAttack = -1;
		int currentDmg;
		String inline = "";
	while (!inline.equalsIgnoreCase("quit"))
	{

		//m = mList.get(0);//Player.getRandomInt(0, mList.size()-1));
		m.setIsDead(false);
		m.calcHP();
		pHP = 100;

		System.out.println("You encounter a " + m.getName() + ", " + m.getDesc());


		while (!m.isDead)
		{
				// player turn
				mAttack = -1;
				if (!auto)
				{
					while (mAttack < 0)
					{
						inline = par.getInputLine(true, ">").toUpperCase();
						mAttack = w.getAttackIndex(inline);
						if (inline.equalsIgnoreCase("quit")) { break;}
						if (inline.equalsIgnoreCase("auto"))
						{
							auto = true;
							mAttack = Player.getRandomInt(0,1);
						}
					}
				}
				else
				{
					mAttack = Player.getRandomInt(0,1);
				}
				if (inline.equalsIgnoreCase("quit")) { break; }
				System.out.println(">>You " + w.getTypeDesc(mAttack) + " the " + m.getName() + " with your " + w.getName());
				critical = false;
				roll = getAttackRoll();
				if (roll <=5)
				{
					System.out.println(">>CRITICAL HIT!!!!!!");
					critical= true;
				}
				attack = isAttackSuccess(roll,pToHit, m.getAC(), w.getHitMod(mAttack));
				if (attack)
				{
					if (!critical) {System.out.println(">>A HIT!!");}
					currentDmg = getDamage(damage,w.getDmgMod(mAttack),critical);
					System.out.println(">>Player hits the " + m.getName() + " for " + currentDmg + " points!");
					m.addHP( -currentDmg );
				}
				else
				{
					System.out.println(">>You missed.");
				}
				if (!m.isDead)
				{
					// monster turn
					critical = false;
					roll = getAttackRoll();
					mAttack = Player.getRandomInt(0,m.getNumAttacks()-1);
					System.out.println("\t" + m.getAttackDesc(mAttack));
					if (roll <=5)
					{
						System.out.println("\tCRITICAL HIT!!!!!!");
						critical= true;
					}
					attack = isAttackSuccess(roll,m.getToHit(), pAC, 0);
					if (attack)
					{
						if (!critical) {System.out.println("\tA HIT!!");}
						currentDmg = getDamage(m.getAttackDmg(mAttack),m.getAttackDmgMod(mAttack),critical);
						System.out.println("\tThe " + m.getName() + " hits you for " + currentDmg + " points!");
						pHP -= currentDmg;
						if (pHP <=0)
						{
							System.out.println("You were killed DEAD!!");
							inline = "quit";
							//m = null;
							break;
						}
					}
					else
					{
						System.out.println("\tThe monster missed.");
					}
				}
			}
				if (auto) { auto = false;}
				m = null;
		}



/*		for (int i = 0; i < 25; i++)
		{
			roll = getAttackRoll();
			//dModifier = 0;
			critical = false;
			if (roll <= 5)
			{
				System.out.println("CRITICAL HIT!!!");
				critical = true;
			}
			attack = isAttackSuccess(roll,7,4,0);
			if (attack)
			{
				System.out.println("A HIT!!!");
				System.out.println("damage: " + getDamage(damage,dModifier,critical));

			}
			else
			{
				System.out.println("You missed.");
			}
		} */
	}
}
