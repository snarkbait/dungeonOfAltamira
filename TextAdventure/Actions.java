/* Actions class

*/
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Actions
{
	private ArrayList<String> actionWords;

	public Actions()
	{
		actionWords = new ArrayList<String>();
	}

	public String getAction(int iindex)
	{
		return actionWords.get(iindex);
	}

	public void loadActions()
	{
		try
		{
			FileReader fr = new FileReader( "actions.txt");
			BufferedReader br = new BufferedReader( fr );

			String stringRead = br.readLine();

			while (stringRead !=null)
			{

				actionWords.add(new String( stringRead ));
				stringRead = br.readLine();
			}
			br.close();
		}

		catch (FileNotFoundException fnfe)
		{
			System.out.println("file not found");
		}

		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}

	}

	public int testWord(String inWord)
	{
		int wordIndex = -1;
		for ( String currentString : actionWords )
		{
			if (currentString.equals(inWord))
			{

				wordIndex = actionWords.indexOf( currentString );
			}

		}
		return wordIndex;
	}

}

