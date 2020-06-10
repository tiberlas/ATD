package common;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ContractNet.IniatorAgent;
import ContractNet.IniatorAgentRemote;
import ContractNet.ParticipantAgent;
import ContractNet.ParticipantAgentRemote;
import agents.TestAgent;
import agents.TestAgentRemote;

public abstract class JNDILookup {

	public static final String JNDIPath = "ejb:ATD_EAR/ATD_JAR//";
	public static final String TestAgentLookup = JNDIPath + TestAgent.class.getSimpleName() + "!" + TestAgentRemote.class.getName() + "?stateful";
	public static final String IniatorAgentLookup = JNDIPath + IniatorAgent.class.getSimpleName() + "!" + IniatorAgentRemote.class.getName() + "?stateful";
	public static final String ParticipantAgentLookup = JNDIPath + ParticipantAgent.class.getSimpleName() + "!" + ParticipantAgentRemote.class.getName() + "?stateful";
	
	@SuppressWarnings("unchecked")
	public static <T> T lookUp(String name, Class<T> c) {
		T bean = null;
		try {
			Context context = new InitialContext();

			System.out.println("Looking up: " + name);
			bean = (T) context.lookup(name);

			context.close();

		} catch (NamingException e) {
			e.printStackTrace();
		}
		return bean;
	}
}
