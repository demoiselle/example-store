package org.demoiselle.jee7.example.store.product.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Cacheable
@Table(name = "categoria")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c"),
		@NamedQuery(name = "Category.findById", query = "SELECT c FROM Category c WHERE c.id = :id"),
		@NamedQuery(name = "Category.findByDescricao", query = "SELECT c FROM Category c WHERE c.description = :description") })
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@NotNull
	@Column(nullable = false)
	private Integer id;
	@Size(max = 45)
	@Column(length = 45)
	private String description;

	public Category() {
	}

	public Category(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Category)) {
			return false;
		}
		Category other = (Category) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Category[ id=" + id + " ]";
	}

}
