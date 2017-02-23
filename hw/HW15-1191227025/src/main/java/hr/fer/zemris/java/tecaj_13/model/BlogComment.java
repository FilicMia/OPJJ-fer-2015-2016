package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;

import javax.persistence.*;

/**
 * Class represents part of domain model. Class implementing one blog entry
 * comment.
 * 
 * @author mia
 *
 */
@Entity
@Table(name = "blog_comments")
public class BlogComment {
	/**Id of the user.*/
	private Long id;
	/**Blog entry on which this comment is posted.*/
	private BlogEntry blogEntry;
	/**E-mail of the user whose has posted the comment.*/
	private String usersEMail;
	/**Message of the comment.*/
	private String message;
	/**Date when the comment has been poseted on.*/
	private Date postedOn;

	/**
	 * @return id of the comment
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Sets id of the comment.
	 * @param id id of the comment.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return blog entry on which this comment has been posted.
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}

	/**
	 * Sets blog entry on which this comment has been posted.
	 * @param blogEntry blog entry on which this comment has been posted.
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * @return email of the user who has posted the comment.
	 */
	@Column(length = 100, nullable = false)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Sets email of the user who has posted the comment.
	 * @param usersEMail email of the user who has posted the comment.
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * @return message of the comment.
	 */
	@Column(length = 4096, nullable = false)
	public String getMessage() {
		return message;
	}

	/**
	 * Sets message of the comment.
	 * @param message message of the comment.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return date when the comment has been posted on.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Sets date when the comment has been posted on. 
	 * @param postedOn date when the comment has been posted on.
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
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
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}