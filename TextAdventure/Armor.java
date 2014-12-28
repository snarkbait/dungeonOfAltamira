// class armor extend invitem

public class Armor extends InvItem
{
	public int armorBonus;

	public Armor()
	{
		armorBonus = 0;
	}

	public Armor(String name, String desc, boolean equip, int armor)
	{
		super( name, desc, equip);
		armorBonus = armor;
	}

	public void setArmor(int armor)
	{
		armorBonus = armor;
	}

	public int getArmor()
	{
		return armorBonus;
	}

	public void equipItem()
	{
		setIsEquip(true);
	}
}
