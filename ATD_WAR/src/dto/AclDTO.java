package dto;

import model.PerformativeENUM;

public class AclDTO {

	private PerformativeENUM performative;
    private String senderAID;
    private String[] receiverAIDs;
    private String replayToAID;
    private String content;
    private Object contentObj;
    private UserArgDTO[] userArgs;
    private String language;
    private String ontology;
    private String encoding;
    private String protocol;
    private String conversationId;
    private String replayWith;
    private String inReplyTo;
    private String replayBy;
    
    public AclDTO() {
    	super();
    }
    
	public AclDTO(PerformativeENUM performative, String senderAID, String[] receiverAIDs, String replayToAID, String content,
			Object contentObj, UserArgDTO[] userArgs, String language, String ontology, String encoding,
			String protocol, String conversationId, String replayWith, String inReplyTo, String replayBy) {
		super();
		this.performative = performative;
		this.senderAID = senderAID;
		this.receiverAIDs = receiverAIDs;
		this.replayToAID = replayToAID;
		this.content = content;
		this.contentObj = contentObj;
		this.userArgs = userArgs;
		this.language = language;
		this.ontology = ontology;
		this.encoding = encoding;
		this.protocol = protocol;
		this.conversationId = conversationId;
		this.replayWith = replayWith;
		this.inReplyTo = inReplyTo;
		this.replayBy = replayBy;
	}
	public PerformativeENUM getPerformative() {
		return performative;
	}
	public void setPerformative(PerformativeENUM performative) {
		this.performative = performative;
	}
	public String getSenderAID() {
		return senderAID;
	}
	public void setSenderAID(String senderAID) {
		this.senderAID = senderAID;
	}
	public String[] getReceiverAIDs() {
		return receiverAIDs;
	}
	public void setReceiverAIDs(String[] receiverAIDs) {
		this.receiverAIDs = receiverAIDs;
	}
	public String getReplayToAID() {
		return replayToAID;
	}
	public void setReplayToAID(String replayToAID) {
		this.replayToAID = replayToAID;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Object getContentObj() {
		return contentObj;
	}
	public void setContentObj(Object contentObj) {
		this.contentObj = contentObj;
	}
	public UserArgDTO[] getUserArgs() {
		return userArgs;
	}
	public void setUserArgs(UserArgDTO[] userArgs) {
		this.userArgs = userArgs;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getOntology() {
		return ontology;
	}
	public void setOntology(String ontology) {
		this.ontology = ontology;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getConversationId() {
		return conversationId;
	}
	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}
	public String getReplayWith() {
		return replayWith;
	}
	public void setReplayWith(String replayWith) {
		this.replayWith = replayWith;
	}
	public String getInReplyTo() {
		return inReplyTo;
	}
	public void setInReplyTo(String inReplyTo) {
		this.inReplyTo = inReplyTo;
	}
	public String getReplayBy() {
		return replayBy;
	}
	public void setReplayBy(String replayBy) {
		this.replayBy = replayBy;
	}
}
