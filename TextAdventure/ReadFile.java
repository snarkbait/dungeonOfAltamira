// Java file test

public class ReadFile
{
	public static void main( String [] args )
	{
		String inString = "";
		Parser par = new Parser();
		Actions aw = new Actions();
		Player p = new Player();
		Levels level = new Levels();
		level.loadLevel();


		int wordIndex = -1;

		aw.loadActions(); // read action words from text file actions.txt

		p.setName(par.getInputLine(false,"Please enter your character's name:"));
		p.setHitPoints(Player.getRandomInt(10,15));
		p.setPlayerLevel(1);


		System.out.println(level.getRoomDesc(0));

		while (!inString.equals("quit"))
		{

			inString = par.getInputLine(true,">");

			par.tokenize( inString );

			wordIndex = aw.testWord(par.getWord(0)); // see if first input word matches action word

			if ( wordIndex != -1 )
			{
				switch (par.checkMenuCommands(par.getWord(0)))
				{
					case 0:			// quit
						inString = "quit";
						break;
					case 1:			// stats
						System.out.print(p.toString());
						break;
					case 2:
					case 3:			// inventory
						p.listInventory();
						break;
					default:		// commands that get a response
						System.out.print( "You " + par.getWord(0) + " ");
						if ( par.getListSize() > 1 )
						{
							for (int i = 1;i<par.getListSize();i++)
							{
								if (i == 1 && !par.testArticle(par.getWord(1)))
								{
									System.out.print("the ");
								}
								System.out.print(par.getWord(i) + " ");
							}
							System.out.print("\n");
						}
						else
						{
							System.out.print("\n");
						}
						wordIndex = -1;
				}
			}
			else
			{
				System.out.println("Word not found.");
			}
		}
	}
}

