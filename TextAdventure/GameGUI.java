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

	public void appendConsole(String iString)
	{
		console.append(iString + newline);
	}

	private class TextFieldHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String response = "";
			inputLine = inputArea.getText();
			console.append(">" + inputLine + newline);
			inputArea.setText("");
			Parser par = new Parser(inputLine);
			par.tokenize(inputLine);
			int wordIndex = aw.testWord(par.getWord(0));
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
						for (int i = 1; i < par.getListSize(); i++)
						{
							int getitem = p.findGettableItem(par.getWord(i));
							if (getitem >= 0)
							{
								System.out.println("found item: " + getitem);
								if (p.getInvRoomID(getitem).equals(Levels.getRoomIndex()))
								{
									p.setInvGettable(getitem);
								}
							}
						}
						break;
					default:
						response = par.getResponse(wordIndex);
						console.append(response + newline);
						if (wordIndex != -1)
						{
							int roomIndex = level.processAction(par, p);
							if (roomIndex != -1)
							{
								console.append(level.getRoomDesc(roomIndex));
								//p.checkGettableItems(level.getRoom(roomIndex));
							}
						}

				}
			//}

		}
	}

	public static void main(String[] args) throws XMLStreamException
	{
		String inString = "";
		//Parser par = new Parser();
		//Actions aw = new Actions();
//		Player p = new Player();
//		Levels level = new Levels();
		level.loadLevelXML("level1", 1);


		//int wordIndex = -1;

		//aw.loadActions(); // read action words from text file actions.txt

		//p.setName(par.getInputLine(false,"Please enter your character's name:"));
		p.setName(JOptionPane.showInputDialog(null,"Please enter your character's name"));
		p.setHitPoints(Player.getRandomInt(10,15));
		p.setPlayerLevel(1);
		p.setHunger(100);


		GameGUI gameGUI = new GameGUI();
		gameGUI.appendConsole(level.getRoomDesc(0));
		//gameGUI.appendConsole(p.listInvGUI());
		gameGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
