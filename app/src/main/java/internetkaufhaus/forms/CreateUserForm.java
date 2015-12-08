package internetkaufhaus.forms;

public class CreateUserForm extends StandardUserForm{

	private String rolename;


	public CreateUserForm()
	{
		
	}
	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
}
