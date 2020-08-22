import java.io.Serializable;

public class SendData implements Serializable  {
	private int code;
	private Object[] obj;
	 
	public SendData(int code, Object... obj) {
		this.code=code;
		this.obj=obj;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object[] getObj() {
		return obj;
	}

	public void setObj(Object[] obj) {
		this.obj = obj;
	}
	
}
