import javax.xml.stream.XMLStreamException;
import java.util.ArrayList;

public class TestXML
{

	public static void main( String [] args ) throws XMLStreamException
	{
		Player p = new Player();
		Levels level = new Levels();
		ArrayList<Room> room = new ArrayList<Room>();
		ArrayList<String> words = new ArrayList<String>();
		level.loadLevelXML("level1", 1);
		System.out.println("level:" + level.getLevelNum());
		room = level.getRoomList();
		for (Room curr : room)
		{
			System.out.println(curr.getDesc());
			words = curr.getListKeyword();
			for (String currword : words)
			{
				System.out.println(currword);
			}
		}
		System.out.println(p.listInvGUI(true));
	}

}
