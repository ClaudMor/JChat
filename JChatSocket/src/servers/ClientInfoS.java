package servers;

import java.net.InetAddress;

public class ClientInfoS {
	


		private int ClientPort;
		private InetAddress ClientInetAddress;
		private String ClientName;

		public ClientInfoS(InetAddress IPaddress, int port, String name) {
			this.ClientInetAddress = IPaddress;
			this.ClientPort = port;
			this.ClientName = name;
		}

		public String toString() {
			return this.ClientName;
		}

		public int getClientPort() {
			return ClientPort;
		}

		public InetAddress getClientInetAddress() {
			return ClientInetAddress;
		}

		public String getClientName() {
			return ClientName;
		}
}
