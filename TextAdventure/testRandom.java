// testrandom

public class testRandom
{
	public static void main( String [] args )
	{
		Player p = new Player();

		for (int i = 1; i<10; i++)
		{
			System.out.println(p.getRandomInt(10,20));
		}

		p.setHitPoints(p.getRandomInt(5,20));
		System.out.println("Hit Points = " + p.getHitPoints());
		p.addHP(-5);
		System.out.println("Hit Points = " + p.getHitPoints());
		p.addInvItem("Bread");
		p.addInvItem("Dagger");
		p.addInvItem("Health Potion");
		p.listInventory();
		p.removeInvItem(2);
		p.listInventory();
		//p.removeInvItem("Dagger");
		p.listInventory();
		System.out.print(p.toString());
		Levels level = new Levels();
		level.loadLevel();
		level.displayDesc();
		Room rm = new Room();
		rm = level.getRoom(0);
		level.tokenKeys(rm.getKeywords());
		level.displayKeys();


	}
}