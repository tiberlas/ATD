package dto;

public class UserArgDTO {

	private String key;
	private Object val;

	public UserArgDTO() {
		super();
	}
	
	public UserArgDTO(String key, Object val) {
		super();
		this.key = key;
		this.val = val;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Object getVal() {
		return val;
	}
	public void setVal(Object val) {
		this.val = val;
	}
}
