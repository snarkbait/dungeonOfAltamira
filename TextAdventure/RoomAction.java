public class RoomAction
{
	private boolean action;
	private String actionType;
	private String actionLabel;
	private boolean isAfter;

	public RoomAction()
	{
		action = false;
		actionType = "";
		actionLabel = "";
		isAfter = false;
	}

	public RoomAction(boolean a, String type, String label, boolean after)
	{
		action = a;
		actionType = type;
		actionLabel = label;
		isAfter = after;
	}

	public void setAction(boolean a) { action = a;}
	public boolean getAction() { return action;}
	public void setActionType(String type) { actionType = type;}
	public String getActionType() { return actionType; }
	public void setActionLabel(String label) { actionLabel = label;}
	public String getActionLabel() { return actionLabel;}
	public void setIsAfter(boolean after) { isAfter = after; }
	public boolean getIsAfter() { return isAfter;}
}