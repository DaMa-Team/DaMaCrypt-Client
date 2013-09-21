package ClientSide;

import java.math.BigInteger;

public class ChatSessionContainer {
	private long sessionid;
	private BigInteger key;
	private int privateSecret;

	public ChatSessionContainer(long sessionid, int privateSecret) {
		this.sessionid = sessionid;
		this.privateSecret = privateSecret;
	}

	public int getPrivateSecret() {
		return privateSecret;
	}

	public BigInteger getKey() {
		return key;
	}

	public void setKey(BigInteger key) {
		this.key = key;
		System.out.print("KEY lenght:" + key.toByteArray().length + " : [");
		for (byte bla : key.toByteArray()) {
			System.out.print(bla);
		}
		System.out.println();
	}

	public void setSession(long session) {
		this.sessionid = session;
	}

	public long getSession() {
		return sessionid;
	}
}
