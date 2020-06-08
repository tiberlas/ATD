package common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import dto.AclDTO;
import dto.UserArgDTO;
import model.ACL;
import model.AID;
import model.AgentType;

public abstract class AclAdapter {

	public static ACL fromDTOtoModel(AclDTO dto) {
		
		Set<AID> receiverAIDs = new HashSet<AID>();
		if(dto.getReceiverAIDs() != null) {
			for(String aid : dto.getReceiverAIDs()) {
				receiverAIDs.add(fromStringToAID(aid));
			}			
		}
		
		Map<String, Object> userArgs = new HashMap<String, Object>();
		if(dto.getUserArgs() != null) {
			for(UserArgDTO userArg : dto.getUserArgs()) {
				userArgs.put(userArg.getKey(), userArg.getVal());
			}			
		}
		
		return new ACL(
				dto.getPerformative(),
				fromStringToAID(dto.getSenderAID()),
				receiverAIDs,
				fromStringToAID(dto.getReplayToAID()),
				dto.getContent(),
				dto.getContentObj(),
				userArgs,
				dto.getLanguage(),
				dto.getOntology(),
				dto.getEncoding(),
				dto.getProtocol(),
				dto.getConversationId(),
				dto.getReplayWith(),
				dto.getInReplyTo(),
				dto.getReplayBy());
	}
	
	public static AclDTO fromModeltoDTO(ACL acl) {
		
		String[] receivers = new String[acl.getReceiverAIDs().size()];
		int i = 0;
		for(AID aid : acl.getReceiverAIDs()) {
			receivers[i++] = fromAIDToString(aid);
		}
		
		UserArgDTO[] userArgs = new UserArgDTO[acl.getUserArgs().size()];
		int ii = 0;
		for(String key : acl.getUserArgs().keySet()) {
			userArgs[ii++] = new UserArgDTO(key, acl.getUserArgs().get(key));
		}
		
		return new AclDTO(
				acl.getPerformative(),
				fromAIDToString(acl.getSenderAID()),
				receivers,
				fromAIDToString(acl.getReplayToAID()),
				acl.getContent(),
				acl.getContentObj(),
				userArgs,
				acl.getLanguage(),
				acl.getOntology(),
				acl.getEncoding(),
				acl.getProtocol(),
				acl.getConversationId(),
				acl.getReplayWith(),
				acl.getInReplyTo(),
				acl.getReplayBy());
	}
	
	private static AID fromStringToAID(String str) {
		if(str == null || str.trim().isEmpty()) {
			return null;
		}
		
		String[] agTy = str.split(" type:");
		String[] ag = agTy[0].split("@");
		String[] ty = agTy[1].split(":");
		
		return new AID(ag[0], ag[1], new AgentType(ty[0], ty[1]));
	}
	
	private static String fromAIDToString(AID aid) {
		if(aid == null) {
			return null;
		}
		
		return aid.getName()+"@"+aid.getHostAlias()+" type:"+aid.getType().getName()+":"+aid.getType().getModule();
	}
}
