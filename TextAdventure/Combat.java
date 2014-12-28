import java.util.ArrayList;
import javax.xml.stream.XMLStreamException;
// class combat

public class Combat
{

	public static int[][] combatMatrix;
	private Monster m;
	private static ArrayList<Monster> mList;
	//private Weapon.AttackType[] atk = null;

	public Combat()
	{
		combatMatrix = new int[10][10];
	}

	public static void createCombatMatrix()
	{
		for (int row = 0; row < 10; row++)
		{
			for (int col = 0; col < 10; col++)
			{
				combatMatrix[row][col] = ((row * 5) + 50) - (col * 5);
				//System.out.print(combatMatrix[row][col]);
				//System.out.print(" ");
			}
			System.out.print("\n");
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

	public static ArrayList<Monster> readMonsterFile(String filename) throws XMLStreamException
	{
		Monster m = null;
		ArrayList<Monster> mList = null;
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
					//System.out.println("start:" + tagStart);
					if (tagStart.equals("monster"))
					{
						m = new Monster();
					}
					if (tagStart.equals("root"))
					{
						mList = new ArrayList<Monster>();
					}

					if (tagStart.equals("attacks"))
					{
						m.setNumAttacks(Integer.parseInt(r.getAttrValue()));
						m.initArrays();
						count = 0;
					}
					break;
				case 1:
					tagContent = r.getText();
					//System.out.println("Text:" + tagContent);
					break;
				case 2:
					tagEnd = r.getTag();
					//System.out.println("End:" + tagEnd);

					//if (!tagContent.isEmpty())
					//{
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
				//}
					break;

				}

		}
		return mList;
	}

	public static void main(String args []) ///temp for testing
	{

		Parser par = new Parser();
		Weapon w = new Weapon("dagger","Basic dagger",true,5,2);
		w.addAttack(0,Weapon.AttackType.SLASH, 0, 0);
		w.addAttack(1,Weapon.AttackType.STAB, 1, 5);



		Combat c = new Combat();
		try
		{
			mList = readMonsterFile("monster");
		}
		catch (XMLStreamException e)
		{
			System.out.println("some kinda error");
		}

		Monster m = mList.get(2);//Player.getRandomInt(0, mList.size()-1));
		m.calcHP();
		System.out.println("Attacks:" + m.getNumAttacks());
		System.out.println("monster HP:" + m.getHP());
		createCombatMatrix();

		//player
		int damage = w.getDamage();
		//int dModifier = w.getDmgMod();
		int pToHit = 1;
		//int pAMod = 5;
		int pHP = 10;
		int pAC = 0;


		boolean attack;
		int roll;
		boolean critical;
		int mAttack = -1;
		int currentDmg;
		String inline;

		System.out.println("You encounter a " + m.getName() + ", " + m.getDesc());


		while (!m.isDead)
		{
			// player turn
			mAttack = -1;
			while (mAttack < 0)
			{
				inline = par.getInputLine(true, ">").toUpperCase();
				mAttack = w.getAttackIndex(inline);
			}
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
						break;
					}
				}
				else
				{
					System.out.println("\tThe monster missed.");
				}
			}


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
