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
import dto.TypeDTO;
import dto.WsTypeDTO;
import model.AgentType;

@Stateless
@ServerEndpoint(value = "/ws/type")
@LocalBean
public class TypeWS {

private static Set<Session> activeSessions = new HashSet<Session>();
	
	@EJB
	private JsonObjMapper jsonMapper;

	public TypeWS() {
		super();
	}
	
	@OnOpen
	public void onOpen(Session session) {
		System.out.println("SESSION STARTED");
		activeSessions.add(session);
	}
	
	public void sentActiveTypeSet(Set<AgentType> types) {
		types.forEach(type -> {
			sendTypeActive(type);
		});
	}
	
	public void sentInactiveTypeSet(Set<AgentType> types) {
		types.forEach(type -> {
			sendTypeInactive(type);
		});
	}
	
	public void sendTypeActive(AgentType type) {
		System.out.println("WS" + type);
		sendType(new WsTypeDTO(new TypeDTO(type), true));
	}
	
	public void sendTypeInactive(AgentType type) {
		sendType(new WsTypeDTO(new TypeDTO(type), false));
	}
	
	private void sendType(WsTypeDTO type) {
		activeSessions.forEach(session -> {
			if(session.isOpen()) {
				try {
					session.getBasicRemote().sendText(jsonMapper.objToJson(type));
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
