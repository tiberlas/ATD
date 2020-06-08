package dto;

public class WSRunningAgentDTO {

	private RunningAgentDTO content;
	private boolean status;
	
	public WSRunningAgentDTO() {
		super();
	}
	
	public WSRunningAgentDTO(RunningAgentDTO content, boolean status) {
		super();
		this.content = content;
		this.status = status;
	}
	
	public RunningAgentDTO getContent() {
		return content;
	}
	public void setContent(RunningAgentDTO content) {
		this.content = content;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
}
