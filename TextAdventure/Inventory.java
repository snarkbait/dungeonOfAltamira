// Inventory class

public class Inventory
{

	private String item;
	private int qty;
	private boolean isEquipped; // is equipped
	private boolean gettable;
	private String roomID;
	public InvItem itemDetail;

	public Inventory()
	{
		item = "";
		qty = 0;
		isEquipped = false;
		gettable = false;
		roomID = "";
		itemDetail = new InvItem();
	}

	public Inventory(String item, int qty, boolean equip, boolean gettable,String roomID) //fix
	{
		this.item = item;
		this.qty = qty;
		this.isEquipped = equip;
		this.gettable = gettable;
		this.roomID = roomID;
		itemDetail = new InvItem(); // fix
	}


	public Inventory(String item, int qty, boolean equip, boolean gettable,String roomID, InvItem detail)
	{
		this.item = item;
		this.qty = qty;
		this.isEquipped = equip;
		this.gettable = gettable;
		this.roomID = roomID;
		this.itemDetail = detail;
	}

	public void setItem( String item)
	{
		this.item = item;
	}

	public String getItem()
	{
		return item;
	}

	public void setQty(int qty)
	{
		this.qty = qty;
	}

	public int getQty()
	{
		return qty;
	}

	public void setEquip(boolean isEquipped)
	{
		this.isEquipped = isEquipped;
	}

	public boolean getEquip()
	{
		return isEquipped;
	}

	public void setGettable(boolean gettable)
	{
		this.gettable = gettable;
	}

	public boolean getGettable()
	{
		return gettable;
	}


	public void setRoomID(String roomID)
	{
		this.roomID = roomID;
	}

	public String getRoomID()
	{
		return roomID;
	}
	public String toString()
	{
		String outString= qty + " " + item;
		if (qty > 1) { outString += "(s)";}
		outString += "  : " + itemDetail.getDesc();
		outString += "\n";
		return outString;
	}



}
