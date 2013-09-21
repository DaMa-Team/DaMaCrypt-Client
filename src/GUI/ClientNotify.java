package GUI;

import Client.UserHandling.ChatSession;
import Client.UserHandling.User;

public interface ClientNotify {

	public void notifyOnlineUsers(User[] users);

	public void notifyNewChat(String chatpartnernick);

	public void notifyChatConnectionEstablished(ChatSession s);

	public void notifyNewMessage(ChatSession session, String msg);

	public String notifyInvalidNickName(String invalidName);

	public void notifyClose();

	public void notifyChatSessionEnded(ChatSession s);
}
