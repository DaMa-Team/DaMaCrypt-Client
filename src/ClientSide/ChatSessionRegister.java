package ClientSide;

import java.util.ArrayList;

public class ChatSessionRegister {
	private ArrayList<ChatSessionContainer> list;

	public ChatSessionRegister() {
		list = new ArrayList<ChatSessionContainer>();
	}

	public void addChatSession(ChatSessionContainer container) {
		list.add(container);
	}

	public void removeChatSession(ChatSessionContainer container) {
		list.remove(container);
	}

	public ChatSessionContainer getChatSessionContainer(long id) {
		for (ChatSessionContainer container : list) {
			if (container.getSession() == id) {
				return container;
			}
		}
		return null;
	}
}
