public class Information{
	private String date;
	private String title;
	private String body;
	private String hash;
	
	public Information(String date, String title, String body, String hash) {
		// TODO Auto-generated constructor stub
		setDate(date);
		setTitle(title);
		setBody(body);
		setHash(hash); 
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	@Override
	public String toString() {
		return "Info [date=" + getDate() + ", title=" + getTitle() + " , body="+ getBody()+ ", hash=" + getHash() + "]";
	}
	
}
