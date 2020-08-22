import java.io.Serializable;

public class User  implements Serializable{
	private String id;
	private String chatLocation;
	
	public User(String id,String chatLocation) {
		this.id=id;
		this.chatLocation=chatLocation;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChatLocation() {
		return chatLocation;
	}

	public void setChatLocation(String chatLocation) {
		this.chatLocation = chatLocation;
	}
	@Override
	public boolean equals(Object src) {
		if(src == null || !(src instanceof User)) {
			return false;
		}
		User user = (User) src;
		return id.equals(user.getId());
	}
	@Override
	public String toString() {
		return "id : "+ id + " / location: " + chatLocation ;
	}
}
