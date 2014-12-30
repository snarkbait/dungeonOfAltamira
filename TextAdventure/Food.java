// food class extends invitem

public class Food extends InvItem
{
	public int hungerBonus;

	public Food()
	{
		hungerBonus = 0;
	}

	public Food(String name, String desc, boolean equip, int hunger)
	{
		super( name, desc, equip);
		hungerBonus = hunger;
	}

	public void setHunger(int hunger)
	{
		hungerBonus = hunger;
	}

	public int getHunger()
	{
		return hungerBonus;
	}

}