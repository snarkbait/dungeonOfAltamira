// Room class
import java.util.ArrayList;
//import java.io.FileReader;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.FileNotFoundException;
import java.util.StringTokenizer;

public class Room
{

	private String roomNum;
	private String keywords;
	private String description;
	private String descOnComplete;
	private boolean complete;
	private int exitToLevel;
	RoomAction action;
	private ArrayList<String> listKeyword;

	public Room()
	{
		roomNum = "";
		keywords = "";
		description = "";
		descOnComplete = "";
		complete = false;
		exitToLevel = -1;
		action = new RoomAction();
		listKeyword = new ArrayList<String>();
	}

	public Room(String cRoomNum,String cKeywords,String cDescription,boolean cComplete, String dComplete, int exit, RoomAction act)
	{
		roomNum = cRoomNum;
		keywords = cKeywords;
		description = cDescription;
		complete = cComplete;
		descOnComplete = dComplete;
		action = act;
		exitToLevel = exit;
		listKeyword = new ArrayList<String>();
	}

	public void setRoomNum(String num)
	{
		roomNum = num;
	}

	public String getRoomNum()
	{
		return roomNum;
	}

	public int intRoomNum(String rmNum)
	{
		return Integer.parseInt(rmNum);
	}

	public void setKeywords(String kw)
	{
		keywords = kw;
	}

	public String getKeywords()
	{
		return keywords;
	}

	public void addListKeyword(String word)
	{
		listKeyword.add(word);
	}

	public ArrayList<String> getListKeyword()
	{
		return listKeyword;
	}


	public void setDesc(String d)
	{
		description = d;
	}

	public String getDesc()
	{
		return description;
	}

	public void setDescComplete(String d)
	{
		descOnComplete = d;
	}

	public String getDescComplete()
	{
		return descOnComplete;
	}

	public void setExitToLevel(int exit)
	{
		exitToLevel = exit;
	}

	public int getExitToLevel()
	{
		return exitToLevel;
	}

	public void setRoomAction(RoomAction act)
	{
		action = act;
	}



	public boolean isComplete()
	{
		return complete;
	}

	public void setComplete(boolean complete)
	{
		this.complete = complete;
	}


	public void tokenKeys(String rKeys)
	{
		StringTokenizer st = new StringTokenizer(rKeys,",");
		while (st.hasMoreTokens())
		{
			listKeyword.add(st.nextToken());
		}
	}

	public int getListSize()
	{
		return listKeyword.size();
	}

	public String getTokenWord(int index)
	{
		if (index < listKeyword.size())
		{
			return listKeyword.get(index);
		}
		else
		{
			return "";
		}
	}
	public int testWord(String inWord)
	{
		int wordIndex = -1;
		for ( String currentString : listKeyword )
		{
			if (currentString.equals(inWord))
			{

				wordIndex = listKeyword.indexOf( currentString );
			}

		}
		return wordIndex;
	}

	public void displayKeys() // temp
	{
		for (String currentWord : listKeyword)
		{
			System.out.println(currentWord);
		}
	}


}

