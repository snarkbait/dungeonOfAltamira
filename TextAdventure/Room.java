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
	private boolean action;
	private String actionType;
	private String actionLabel;
	private ArrayList<String> listKeyword;

	public Room()
	{
		roomNum = "";
		keywords = "";
		description = "";
		descOnComplete = "";
		complete = false;
		action = false;
		actionType = "";
		actionLabel = "";
		listKeyword = new ArrayList<String>();
	}

	public Room(String cRoomNum,String cKeywords,String cDescription,boolean cComplete, boolean cAction, String cType, String dComplete, String label)
	{
		roomNum = cRoomNum;
		keywords = cKeywords;
		description = cDescription;
		complete = cComplete;
		action = cAction;
		actionType = cType;
		descOnComplete = dComplete;
		actionLabel = label;
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

	public void setActionAll(boolean a, String type, String label)
	{
		action = a;
		actionType = type;
		actionLabel = label;
	}

	public boolean getAction()
	{
		return action;
	}

	public String getActionType()
	{
		return actionType;
	}

	public String getActionLabel()
	{
		return actionLabel;
	}

	public boolean isComplete()
	{
		return complete;
	}

	public void setComplete(boolean complete)
	{
		this.complete = complete;
	}

/*	public int getRoomIndex(String rmNum)
	{
		return this.indexOf(rmNum);
	}*/
	public int processAction()  // if 'room' has an action that needs to be performed before 2nd description is displayed
	{
		int retval = -1;
		if (action)
		{
			switch (actionType)
			{
				case "ITEM":
					retval = 0;
					break;
				case "HP":
					retval = 1;
					break;
				case "MONSTER":
					retval = 2;
					break;
				case "roomID":
					retval = 3;
					break;
			}
		}
		return retval;
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

