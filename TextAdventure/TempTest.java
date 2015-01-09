public class TempTest
{
	public static void main( String [] args )
	{
		Player p = new Player("Bob", 18, 1000, 0, 5, 0, 100);

		p.calcToHit();


		System.out.println(p.toString());

		p.addHP(-10);
		p.setPlayerLevel(14);

		System.out.println(p.toString());



	}

}
