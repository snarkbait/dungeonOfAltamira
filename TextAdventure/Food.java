// food class extends invitem

public class Food extends InvItem
{
	public int healthBonus;

	public Food()
	{
		healthBonus = 0;
	}

	public Food(String name, String desc, boolean equip, int health)
	{
		super( name, desc, equip);
		healthBonus = health;
	}

	public void setHealth(int health)
	{
		healthBonus = health;
	}

	public int getHealth()
	{
		return healthBonus;
	}

}