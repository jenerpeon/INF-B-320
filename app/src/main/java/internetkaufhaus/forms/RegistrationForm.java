/*
 * Copyright 2013-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package internetkaufhaus.forms;

import org.hibernate.validator.constraints.NotEmpty;

// (｡◕‿◕｡)
// Manuelle Validierung ist mühsam, Spring bietet hierfür auch Support.
// Um die Registrierung auf korrekte Eingaben zu checken, schreiben eine POJO-Klasse, welche den Inputfelder der Registrierung entspricht
// Diese werden annotiert, damit der Validator weiß, worauf geprüft werden soll
// Via message übergeben wir einen Resourcekey um die Fehlermeldungen auch internationalisierbar zu machen.
// Die ValidationMessages.properties Datei befindet sich in /src/main/resources
// Lektüre: 
// http://docs.oracle.com/javaee/6/tutorial/doc/gircz.html
// http://docs.jboss.org/hibernate/validator/4.2/reference/en-US/html/

public class RegistrationForm {

	@NotEmpty(message = "username is mandatory")
	private String username;

	@NotEmpty(message = "password field is mandatory")
	private String password;

	@NotEmpty(message = "password-repeat field is mandatory")
	private String passwordrepeat;

	@NotEmpty(message = "email field is mandatory")
	private String email;
	
	@NotEmpty(message = "address field is mandatory")
	private String address;
	
	private String firstname;
	private String lastname;
	
	public String getFirstname(){
		return this.firstname;
	}
	
	public String getLastname(){
		return this.lastname;
	}
	
	public void setFirstname(String firstname){
		this.firstname = firstname;
	}
	
	public void setLastname(String lastname){
		this.lastname = lastname;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordrepeat() {
		return passwordrepeat;
	}

	public void setPasswordrepeat(String passwordrepeat) {
		this.passwordrepeat = passwordrepeat;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getAddress(){
		return this.address;
	}
	
    public void setAddress(String address){
    	this.address = address;
    }
}
