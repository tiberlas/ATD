package dto;

public class WsTypeDTO {
	
	private TypeDTO content;
	private boolean  status;

	public WsTypeDTO() {
		super();
	}
	
	public WsTypeDTO(TypeDTO content, boolean status) {
		super();
		this.content = content;
		this.status = status;
	}
	
	public TypeDTO getContent() {
		return content;
	}
	public void setContent(TypeDTO content) {
		this.content = content;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
}
