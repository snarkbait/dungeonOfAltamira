// monster class

public class Monster
{
	public int id;
	private String name;
	private String desc;
	private int hitPoints;
	private int armorClass;
	private int toHit;
	public boolean isDead;
	private int hpMin;
	private int hpMax;
	private int numAttacks;

/*
 Using multiple arrays with the same index instead of another class, true OOP nerds would probably have a heart attack, but it was just quicker.

*/
	private Weapon.AttackType[] attackType; // enum from Weapon class
	private String[] attackDesc;
	private int[] attackDamage;
	private int[] attackDmgModifier;

	public Monster()
	{
		id = 0;
		name = "";
		desc = "";
		hitPoints = 0;
		armorClass = 0;
		toHit = 0;
		isDead = false;
		hpMin = 0;
		hpMax = 0;
		numAttacks = 0;
	}

	public Monster(String n, String d, int min, int max, int ac, int tohit, int num)
	{
		name = n;
		desc = d;
		hpMin = min;
		hpMax = max;
		armorClass = ac;
		toHit = tohit;
		isDead = false;
		numAttacks = num;

		if (numAttacks > 0)
		{
			attackType = new Weapon.AttackType[numAttacks];
			attackDesc = new String[numAttacks];
			attackDamage = new int[numAttacks];
			attackDmgModifier = new int[numAttacks];
		}

		hitPoints = Player.getRandomInt(hpMin,hpMax);
	}

	public void initArrays()
	{
		if (numAttacks > 0)
		{
			attackType = new Weapon.AttackType[numAttacks];
			attackDesc = new String[numAttacks];
			attackDamage = new int[numAttacks];
			attackDmgModifier = new int[numAttacks];
		}
	}


	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public void setHPMin(int min)
	{
		this.hpMin = min;
	}

	public int getHPMin()
	{
		return hpMin;
	}

	public void calcHP()
	{
		hitPoints = Player.getRandomInt(hpMin, hpMax);
	}

	public void setHPMax(int max)
	{
		this.hpMax = max;
	}

	public int getHPMax()
	{
		return hpMax;
	}

	public String getDesc()
	{
		return desc;
	}

	public int getHP()
	{
		return hitPoints;
	}

	public void addHP(int num)
	{
		hitPoints += num;
		if (hitPoints <=0)
		{
			isDead = true;
			System.out.println("The " + name + " is dead!");
			System.out.println("\n" + "----------------------------\n\n");
		}
	}

	public void setAC(int ac)
	{
		armorClass = ac;
	}

	public int getAC()
	{
		return armorClass;
	}

	public void setToHit(int hit)
	{
		toHit = hit;
	}

	public int getToHit()
	{
		return toHit;
	}

	public void setNumAttacks(int num)
	{
		numAttacks = num;
	}

	public int getNumAttacks()
	{
		return numAttacks;
	}

	public void setIsDead(boolean ded)
	{
		isDead = ded;
	}

	public boolean getIsDead()
	{
		return isDead;
	}

	public void addAttack(int index, Weapon.AttackType type, String desc, int dmg, int dmgMod)
	{
		attackType[index] = type;
		attackDesc[index] = desc;
		attackDamage[index] = dmg;
		attackDmgModifier[index] = dmgMod;
	}

	public int getAttackDmg(int index)
	{
		return attackDamage[index];
	}

	public int getAttackDmgMod(int index)
	{
		return attackDmgModifier[index];
	}

	public String getAttackDesc(int index)
	{
		return attackDesc[index];
	}

	public String getAttackType(int index)
	{
		return attackType[index].toString().toLowerCase();
	}


}


