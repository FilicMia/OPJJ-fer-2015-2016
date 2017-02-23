package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * Class represents part of domain model. Class implementing one blog entry.
 * 
 * @author mia
 *
 */
@Entity
@Table(name="blog_entries")
@NamedQueries({
	@NamedQuery(name="BlogEntry.upit1",query="select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when",
			hints=@QueryHint(name = "org.hibernate.cacheable", value = "true")),
	@NamedQuery(name="BlogEntry.userEntry", query="select e from BlogEntry as e where e.creator=:user")

})
@Cacheable(true)
public class BlogEntry {

	/** Id of the blog entry. */
	private Long id;
	/** COmments corresponding this blog entry */
	private List<BlogComment> comments = new ArrayList<>();
	/** Time of creation of the blog entry. */
	private Date createdAt;
	/** Last modification of the blog entry. */
	private Date lastModifiedAt;
	/** Title of the blog entry. */
	private String title;
	/** Text corresponding the blog entry. */
	private String text;
	/**Creator of this bog entry.*/
	private BlogUser creator;
	
	/**
	 * Method fetches creator of this blog entry.
	 * @return creator of this blog entry.
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogUser getCreator() {
		return creator;
	}
	

	/**
	 * @return id of the blog entry.
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Sets id of th eblog entry.
	 * 
	 * @param id
	 *            id of the blog entry.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return comments corresponding this blog entry.
	 */
	@OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}

	/**
	 * Sets comments corresponding this blog entry.
	 * 
	 * @param comments
	 *            comments corresponding this blog entry.
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * @return date of creation.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets date of creation.
	 * 
	 * @param createdAt
	 *            date of creation.
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return date of last modification of the blog.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Sets date of last modification.
	 * 
	 * @param lastModifiedAt
	 *            date of last modification.
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * @return title of blog
	 */
	@Column(length = 200, nullable = false)
	public String getTitle() {
		return title;
	}

	/**
	 * Sets title of the blog.
	 * 
	 * @param title
	 *            title of the blog.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return text corresponding this blog entry.
	 */
	@Column(length = 4096, nullable = false)
	public String getText() {
		return text;
	}

	/**
	 * Sets text of this blog entry.
	 * @param text text of this blog entry.
	 */
	public void setText(String text) {
		this.text = text;
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
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	/**Sets creator of blog entry.
	 * @param creator creator of blog entry.
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
	}
}