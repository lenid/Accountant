package messagesservice.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.GenerationTime;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="USERS")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank
	@Column(name = "SSO_ID", unique = true, nullable = false)
	private String ssoId;

	@NotBlank
	@Column(name="PASSWORD")
	private String passwd;

	@Transient
	private String oldPasswd = "";

	@Transient
	private String newPasswd = "";

	@NotBlank
	@Column(name = "FIRST_NAME", nullable = true)
	private String firstName;

	@NotBlank
	@Column(name = "LAST_NAME", nullable = true)
	private String lastName;

	@NotBlank
	@Column(name = "EMAIL", nullable = true)
	private String email;
	
    @Temporal( value = TemporalType.TIMESTAMP ) 
    @org.hibernate.annotations.Generated(value=GenerationTime.INSERT) 
    @Column(name = "CREATED", nullable = false, insertable=false, updatable=false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date created;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USERS_USER_PROFILES", 
             joinColumns = { @JoinColumn(name = "USER_ID") }, 
             inverseJoinColumns = { @JoinColumn(name = "USER_PROFILE_ID") })
	private Set<UserProfile> profiles = new HashSet<UserProfile>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSsoId() {
		return ssoId;
	}

	public void setSsoId(String ssoId) {
		this.ssoId = ssoId;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getOldPasswd() {
		return oldPasswd;
	}

	public void setOldPasswd(String oldPasswd) {
		this.oldPasswd = oldPasswd;
	}

	public String getNewPasswd() {
		return newPasswd;
	}

	public void setNewPasswd(String newPasswd) {
		this.newPasswd = newPasswd;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Set<UserProfile> getProfiles() {
		return profiles;
	}

	public void setProfiles(Set<UserProfile> profiles) {
		this.profiles = profiles;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (id != user.id) return false;
		return ssoId.equals(user.ssoId);

	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + ssoId.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", ssoId='" + ssoId + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", created=" + created +
				", profiles=" + profiles +
				'}';
	}
	
}
