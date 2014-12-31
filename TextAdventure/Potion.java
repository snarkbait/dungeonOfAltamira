// potion extends invitem

public class Potion extends InvItem
{
	private int healthBonus;
	private int hungerBonus;
	private int damageBonus;
	private int hitBonus;

	public Potion(String name, String desc, boolean equip, int health, int hunger)
	{
		super( name, desc, equip);
		healthBonus = health;
		hungerBonus = hunger;
	}

	public int getHealthBonus()
	{
		return healthBonus;
	}

	public int getHungerBonus()
	{
		return hungerBonus;
	}

	public void drinkPotion(Player p)
	{
		if (healthBonus > 0) { p.addHP(healthBonus);}
		if (hungerBonus > 0) { p.addHunger(hungerBonus);}
	}
}

