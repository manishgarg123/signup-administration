package org.company.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@XmlRootElement
public class SignupRequest implements Serializable {
   /** Default value included to remove warning. Remove or modify at will. **/
   private static final long serialVersionUID = 1L;

   public SignupRequest(String firstName, String lastName, String email, String company, String phoneNumber, String referer){
	   this.firstName = firstName;
	   this.lastName = lastName;
	   this.email = email;
	   this.company = company;
	   this.phoneNumber = phoneNumber;
	   this.referer = referer;
   }
   
   public SignupRequest() {
   }
   
   public SignupRequest(String formParam) {
   }
   
   @Size(min = 1, max = 25)
   @Pattern(regexp = "[A-Za-z ]*", message = "must contain only letters and spaces")
   private String firstName;
   
   @Size(min = 1, max = 25)
   @Pattern(regexp = "[A-Za-z ]*", message = "must contain only letters and spaces")
   private String lastName;

   @NotNull
   @NotEmpty
   @Email
   private String email;
   
   private String company;
   
   private String referer;
   
   private Date datetime;
   
   private String status;

   @NotNull
   @Size(min = 10, max = 12)
   @Digits(fraction = 0, integer = 12)
   private String phoneNumber;

   @XmlElement
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	   @XmlElement
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@XmlElement
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@XmlElement
	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	@XmlElement
	public String getReferer() {
		return referer;
	}
	
	public void setReferer(String referer) {
		this.referer = referer;
	}
	
	public Date getDatetime() {
		return datetime;
	}
	
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@Override
	public String toString() {
		return "firstName="+firstName+", lastName="+lastName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
   
}