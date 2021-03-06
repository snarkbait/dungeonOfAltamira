/* inventory item superclass

extended by Weapon, Armor, Food, Potion classes, and more possible

this class is used by the Inventory class as an arraylist. itemName should be same as Inventory.item


by snarkbait 2014
*/

public class InvItem
{
	public String itemName;	 // same as Inventory.item
	public String itemDesc;	// short description of item, not used yet
	public boolean isEquip; // is equippable, i.e. weapon or armor, not 'is equipped' which is in inventory class

	public InvItem()
	{
		itemName = "";
		itemDesc = "";
		isEquip = false;
	}

	public InvItem(String name, String desc, boolean equip)
	{
		itemName = name;
		itemDesc = desc;
		isEquip = equip;
	}

	public void setName(String name)
	{
		itemName = name;
	}

	public String getName()
	{
		return itemName;
	}

	public void setDesc(String desc)
	{
		itemDesc = desc;
	}

	public String getDesc()
	{
		return itemDesc;
	}

	public void setIsEquip(boolean equip)
	{
		isEquip = equip;
	}

	public boolean getIsEquip()
	{
		return isEquip;
	}

	public String toString()
	{
		return "Item:" + itemName + " : " + itemDesc +
			" : Can be equipped : " + isEquip + "\n";
	}
}
