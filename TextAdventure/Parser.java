/* Parser class
*/
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Parser
{
	private String inLine;
	private ArrayList<String> inList;

	// words that do not require the response to use 'the' like 'look down'
	private final String [] articleList = {"the","a","at","up","down","over","around","with","in","to","from","all"};

	// menu words that can be used anywhere
	private final String [] menuCommands = {"quit","stats","inventory","inv","take","grab"};
	/*				QUIT = 0
					STATS = 1
					INVENTORY = 2
					INV = 3
					TAKE = 4
					GRAB = 5
	*/

	// default constructor

	public Parser()
	{
		inLine = "";
	}

	public Parser( String inputLine )
	{
		inLine = inputLine;
	}

/* method getInputLine
used only for console version of application

@params boolean lower
				convert all text to lowercase
		String prompt
				text to display to the left of input are ('>')
*/
	public String getInputLine(boolean lower,String prompt)
	{

		String inString = "";
		try
		{

			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader ibr = new BufferedReader( isr);
			while (inString.equals(""))
			{
				System.out.print(prompt);
				inString = ibr.readLine();
			}

		}

		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		if (lower)
		{
			return inString.toLowerCase();
		}
		else
		{
			return inString;
		}
	}

	public void setInputline(String inLine)
	{
		this.inLine = inLine;
	}

/* tokenize input string into arraylist inList
.
*/
	public void tokenize(String inString)
	{
		inList = new ArrayList<String>();
		StringTokenizer stok = new StringTokenizer(inString);
		String nextWord = "";
		while (stok.hasMoreTokens())
		{
			nextWord = stok.nextToken();
			if (!nextWord.equals(""))
			{
				inList.add(nextWord);
			}
		}
	}

	public String getWord(int index)
	{
		if (index < inList.size())
		{
			return inList.get( index );
		}
		else
		{
			return "";
		}
	}

	public String getResponse(int wordIndex)
	{
		String response = "";
		if (wordIndex < 0)
		{
			response = "I don't understand.";
		}
		else
		{
			int mc = this.checkMenuCommands(this.getWord(0));
			if (mc != -1)
			{
				response = this.getWord(0);
			}
			else
			{
				response = "You " + this.getWord(0) + " ";
				if (this.getListSize() > 1)
				for (int i = 1; i < this.getListSize(); i++)
				{
					if (i == 1 && !this.testArticle(this.getWord(1)))
					{
						response += "the ";
					}
					response += this.getWord(i) + " ";
				}
			}
		}
		return response;
	}

	public int getListSize()
	{
		return inList.size();
	}

	public ArrayList<String> getWordList()
	{
		return inList;
	}

	public int testWord(String inWord)
	{
		int wordIndex = -1;
		for (String currentWord : inList)
		{
			if (currentWord.equalsIgnoreCase(inWord))
			{
				wordIndex = inList.indexOf(currentWord);
			}
		}
		return wordIndex;
	}

	public boolean testArticle(String iString)
	{
		boolean test = false;
		for (int i=0;i<articleList.length;i++)
		{
			if (iString.equals(articleList[i]))
			{
				test = true;
			}
		}
		return test;
	}

	public int checkMenuCommands(String iString)
	{
		int index = -1;
		for (int i=0; i < menuCommands.length; i++)
		{
			if (iString.equalsIgnoreCase(menuCommands[i]))
			{
				index = i;
			}
		}
		return index;
	}


}


