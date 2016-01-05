package internetkaufhaus.model;

// TODO: Auto-generated Javadoc
/**
 * The Class NavItem.
 */
public class NavItem {
	
	/** The name. */
	private String name;
	
	/** The link. */
	private String link;
	
	/** The type. */
	private String type;
	
	/**
	 * Instantiates a new nav item.
	 *
	 * @param name the name
	 * @param link the link
	 * @param type the type
	 */
	public NavItem(String name, String link, String type) {
		this.name= name;
		this.link = link;
		this.type = type;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Gets the link.
	 *
	 * @return the link
	 */
	public String getLink() {
		return this.link;
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Sets the link.
	 *
	 * @param link the new link
	 */
	public void setLink(String link) {
		this.link = link;
	}
	
	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}
}