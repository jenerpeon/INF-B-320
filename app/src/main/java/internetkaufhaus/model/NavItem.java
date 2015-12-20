package internetkaufhaus.model;

public class NavItem {
	
	private String name;
	private String link;
	private String type;
	
	public NavItem(String name, String link, String type) {
		this.name= name;
		this.link = link;
		this.type = type;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getLink() {
		return this.link;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}