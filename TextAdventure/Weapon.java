// class weapon subclass of invitem

public class Weapon extends InvItem
{
	public enum AttackType { PUNCH, KICK, BITE, SLASH, STAB, BLUDGEON, SHOOT, POISON, MAGIC }

	//public static AttackType enumAttackType;

	public int damage;
	public AttackType[] attackType;
	public int[] damageMod;
	public int[] toHitMod;

	public Weapon()
	{
		damage = 0;
	}

	public Weapon(String name, String desc, boolean equip, int dmg, int numTypes)
	{
		super(name, desc, equip);
		damage = dmg;
		if (numTypes > 0)
		{
			this.attackType = new AttackType[numTypes];
			damageMod = new int[numTypes];
			toHitMod = new int[numTypes];
		}
	}

	public void addAttack(int index, AttackType atk, int dmgMod, int hitMod)
	{
		attackType[index] = atk;
		damageMod[index] = dmgMod;
		toHitMod[index] = hitMod;
	}

	public void setDamage(int dmg)
	{
		damage = dmg;
	}

	public int getDamage()
	{
		return damage;
	}

	public void equipItem()
	{
		setIsEquip( true);
	}

	public String getTypeDesc(int index)
	{
		return attackType[index].toString().toLowerCase();
	}

	public AttackType getAttackType(int index)
	{
		return attackType[index];
	}

	public int getAttackIndex(String inp)
	{
		int retval = -1;
//		AttackType t = AttackType.valueOf(inp);
//		if (t != null)
//		{
			for (int i = 0; i < attackType.length; i++)
			{
				if (inp.equals(attackType[i].toString()))
				{
					retval = i;
				}
			}
//		}
		return retval;
	}


	public int getDmgMod(int index)
	{
		return damageMod[index];
	}

	public int getHitMod(int index)
	{
		return toHitMod[index];
	}


}