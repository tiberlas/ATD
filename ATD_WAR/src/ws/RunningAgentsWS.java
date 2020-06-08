package ws;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import common.JsonObjMapper;
import dto.RunningAgentDTO;
import dto.WSRunningAgentDTO;
import model.AID;

@Stateless
@ServerEndpoint(value = "/ws/agents")
@LocalBean
public class RunningAgentsWS {
	
private static Set<Session> activeSessions = new HashSet<Session>();
	
	@EJB
	private JsonObjMapper jsonMapper;

	public RunningAgentsWS() {
		super();
	}
	
	@OnOpen
	public void onOpen(Session session) {
		activeSessions.add(session);
	}
	
	public void sendActiveAgent(AID aid) {
		sendAgent(new WSRunningAgentDTO(new RunningAgentDTO(aid), true));
	}
	
	public void sendInactiveAgent(AID aid) {
		sendAgent(new WSRunningAgentDTO(new RunningAgentDTO(aid), false));
	}
	
	public void sendActiveAgentSet(Set<AID> aids) {
		aids.forEach(aid -> {
			sendAgent(new WSRunningAgentDTO(new RunningAgentDTO(aid), true));
		});
	}
	
	public void sendInactiveAgentSet(Set<AID> aids) {
		aids.forEach(aid -> {
			sendAgent(new WSRunningAgentDTO(new RunningAgentDTO(aid), false));
		});
	}
	
	private void sendAgent(WSRunningAgentDTO agent) {
		activeSessions.forEach(session -> {
			if(session.isOpen()) {
				try {
					session.getBasicRemote().sendText(jsonMapper.objToJson(agent));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@OnClose
	public void close(Session session) {
		activeSessions.remove(session);
	}
	
	@OnError
	public void error(Session session, Throwable t) {
		close(session);
		t.printStackTrace();
	}

}
