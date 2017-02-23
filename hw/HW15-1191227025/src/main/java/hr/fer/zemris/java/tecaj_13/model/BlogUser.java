package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * Class BlogUser models a single user of the blog. For each blog
 * user,properties: id, firstName, lastName, nick, email and passwordHash are
 * tracked.
 * 
 * @author mia
 *
 */
@Entity
@Table(name = "blog_users")
@NamedQueries({
		@NamedQuery(name = "BlogUser.ulist", query = "select u from BlogUser as u", hints = @QueryHint(name = "org.hibernate.cacheable", value = "true")),
		@NamedQuery(name = "BlogUser.byNick", query = "select u from BlogUser as u where u.nick=:nick", hints = @QueryHint(name = "org.hibernate.cacheable", value = "true")) })
@Cacheable(true)
public class BlogUser {
	/** Id of the blog user. */
	private Long id;
	/** User's first name. */
	private String firstName;
	/** User's last name. */
	private String lastName;
	/** User's nick name. */
	private String nick;
	/** User's email. */
	private String email;
	/** User's last passwordHash. */
	private String passwordHash;
	/** Entries of user. */
	private List<BlogEntry> entries;

	/**
	 * Default constructor.
	 */
	public BlogUser() {
		this.firstName = "";
		this.lastName = "";
		this.nick = "";
		this.email = "";
		this.entries = new ArrayList<BlogEntry>();
	}

	/**
	 * Constructor.
	 * 
	 * @param firstName
	 *            name of user.
	 * @param lastName
	 *            last name of user.
	 * @param nick
	 *            nickname which has to be unique.
	 * @param email
	 *            email of user
	 * @param passwordHash
	 *            hashed password; digest
	 */
	public BlogUser(String firstName, String lastName, String nick,
			String email, String passwordHash) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.nick = nick;
		this.email = email;
		this.passwordHash = passwordHash;
		this.entries = new ArrayList<BlogEntry>();
	}

	/**
	 * @return id of the user.
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Sets id of the user.
	 * 
	 * @param id
	 *            id of the user
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return first name of the user.
	 */
	@Column(length = 25, nullable = false)
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets first name of the user.
	 * 
	 * @param firstName
	 *            of the user.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return last name of the user.
	 */
	@Column(length = 80, nullable = false)
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets last name of the user.
	 * 
	 * @param lastName
	 *            last name of the usr.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return nick name of the user.
	 */
	@Column(length = 80, nullable = false, unique = true)
	public String getNick() {
		return nick;
	}

	/**
	 * Set nick for the user. It must be unique.
	 * 
	 * @param nick
	 *            nick of the user.
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * @return email of the user.
	 */
	@Column(length = 100, nullable = false)
	public String getEmail() {
		return email;
	}

	/**
	 * Sets email of the user.
	 * 
	 * @param email
	 *            email of the user.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return passwordhash of the user.
	 */
	@Column(length = 200, nullable = false)
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Sets password hash of the user.
	 * 
	 * @param passwordHash
	 *            password hash of the user.
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * Method gets entries of all users.
	 * 
	 * @return entries of users
	 */
	@OneToMany(mappedBy = "creator", cascade = CascadeType.PERSIST, orphanRemoval = true)
	public List<BlogEntry> getEntries() {
		return entries;
	}

	/**
	 * Setting the entries of user.
	 * 
	 * @param entries
	 *            entries of users
	 */
	public void setEntries(List<BlogEntry> entries) {
		this.entries = entries;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogUser other = (BlogUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
