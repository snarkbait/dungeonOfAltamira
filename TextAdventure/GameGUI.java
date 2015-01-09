import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import java.awt.Container;
import javax.swing.BoxLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import javax.xml.stream.XMLStreamException;


public class GameGUI extends JFrame
{
	public final String newline = "\n";
	private Container contents;
	private JLabel prompt;
	private JTextField inputArea;
	private JTextArea console;
	private	Actions aw = new Actions();
	private static Player p = new Player();
	private static Levels level = new Levels();
	private static Parser par = new Parser();
	private boolean melee = false;
//	aw.loadActions();

	private String inputLine;

	public GameGUI()
	{
		super("The Dungeon of Altamira");
		contents = getContentPane();
		BoxLayout fl = new BoxLayout(contents,BoxLayout.Y_AXIS);
		contents.setLayout(fl);
		//fl.setAlignment(FlowLayout.CENTER);

		prompt = new JLabel("Enter Text Here>");
		inputArea = new JTextField("",25);
		inputArea.setMaximumSize(new Dimension (300,20));

		console = new JTextArea("Welcome to the Dungeon of Altamira!\n" + "Enjoy your....death!! I mean, stay!\n");
		JScrollPane areaScrollPane = new JScrollPane(console);
		areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setMaximumSize(new Dimension(600, 400));
		areaScrollPane.setAutoscrolls(true);
		console.setEditable(false);
		console.setLineWrap(true);
		console.setWrapStyleWord(true);

		contents.add(prompt);
		contents.add(inputArea);
		contents.add(areaScrollPane);

		TextFieldHandler tfh = new TextFieldHandler();
		inputArea.addActionListener(tfh);

		setSize(800,600);
		setVisible(true);
		aw.loadActions();
	}

	public Parser sendToCombat(Parser par)
	{
		return par;
	}

	public void appendConsole(String iString)
	{
		console.append(iString + newline);
	}

	private class TextFieldHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String response = "";

			// read input line
			inputLine = inputArea.getText();

			// push to 'console'
			console.append(">" + inputLine + newline);

			// clear text box
			inputArea.setText("");

			// push to parser
			par.setInputLine(inputLine);

			// tokenize
			par.tokenize(inputLine);

			// test first word as valid action
			int wordIndex = aw.testWord(par.getWord(0));

			// test if action word is menu command
			int command = par.checkMenuCommands(par.getWord(0));
				switch (command)
				{
					case 0:	//quit
						System.exit(0);
					case 1:	//stats
						console.append(p.toString());
						break;
					case 2:
					case 3:	//inventory
						console.append(p.listInvGUI(false));
						break;
					case 4:
					case 5: // take item
						response = par.getResponse(wordIndex);
						console.append(response + newline);

						// loop through parsed input words, starting after first word
						for (int i = 1; i < par.getListSize(); i++)
						{
							// see if item is in inventory list with status 'gettable' meaning, available in area but not 'picked up' by player yet
							int getitem = p.findGettableItem(par.getWord(i));

							if (getitem >= 0)
							{
								//System.out.println("found item: " + getitem); //:DEBUG:

								// check if player is in the proper place to pick up the item
								if (p.getInvRoomID(getitem).equals(Levels.getRoomIndex()))
								{
									p.setInvGettable(getitem);
								}
								else
								{
									console.append("You cannot do that here." + newline);
								}
							}
							else
							{
								console.append("You cannot take that item." + newline);
							}
						}
						break;
					case 6:
					case 7: // use, equip
						response = par.getResponse(wordIndex);
						console.append(response + newline);

						// loop through parsed input words, starting after first word
						for (int i = 1; i < par.getListSize(); i++)
						{
							int getitem = p.findItem(par.getWord(i));
							System.out.println("item:" + getitem);
							if (getitem >=0)
							{
								Inventory item = p.getInvItem(getitem);
								if (item.itemDetail.getIsEquip())
								{
									if (!item.getEquip())
									{
										item.setEquip(true);
										console.append("Your " + item.getItem() + " is equipped" + newline);
									}
									else
									{
										console.append("Item is already equipped." + newline);
									}
								}
								else
								{
									console.append("That item cannot be equipped." + newline);
								}
							}
						}
						break;
					default:

						if (!melee)
						{

							// not a menu command, return response like "you _____ the ______"
							response = par.getResponse(wordIndex);
							console.append(response + newline);
							if (wordIndex != -1)
							{

								// take the action (go to next 'room')
								int roomIndex = level.processAction(par, p);
								if (roomIndex != -1)
								{
									console.append(level.handleSpecialAction(p, roomIndex) + newline);
									try
									{
										if(level.testExitRoom(roomIndex))
										{
											console.append(level.getRoomDesc(0));
										}
									}
									catch(XMLStreamException xse){ }





								}
							}
						}
						else
						///////// combat ///////
						{
							sendToCombat(par);
						}

				}
			//}

		}
	}

	public static void main(String[] args) throws XMLStreamException
	{
		String inString = "";
		level.loadLevelXML("level1", 1);




		p.setName(JOptionPane.showInputDialog(null,"Please enter your character's name"));

		// temporary settings for testing
		p.setHpMax(Player.getRandomInt(10,15));
		p.setHitPoints(p.getHpMax());
		p.setPlayerLevel(1);
		p.setHunger(100);


		GameGUI gameGUI = new GameGUI();
		gameGUI.appendConsole(level.getRoomDesc(0));
		gameGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
