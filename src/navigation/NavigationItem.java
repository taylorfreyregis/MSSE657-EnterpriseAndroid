package navigation;

/**
 * This is just a POJO (Plain ol' Java Object) for wrapping navigation items.
 * 
 * @author Taylor Frey
 * @version 1.0
 * @since 1.0
 */
public class NavigationItem {
	
	// The title to be displayed
	private String mTitle;
	
	// The Constructor to create a NavigationItem
	public NavigationItem(String title) {
		this.mTitle = title;
		
	}
	
	// Getter to return the title
	public String getTitle() {
		return this.mTitle;
	}
}
