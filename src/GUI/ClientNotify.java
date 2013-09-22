/*
Copyright (C) 2013  Marcel Hollerbach, Daniel Haß

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

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
