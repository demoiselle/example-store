/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.demoiselle.jee7.entity;

import java.io.Serializable;

/**
 *
 * @author 70744416353
 */
//@Entity
//@Cacheable
//@Table(name = "categoria")
//@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "Categoria.findAll", query = "SELECT c FROM Categoria c"),
//    @NamedQuery(name = "Categoria.findById", query = "SELECT c FROM Categoria c WHERE c.id = :id"),
//    @NamedQuery(name = "Categoria.findByDescricao", query = "SELECT c FROM Categoria c WHERE c.descricao = :descricao")})
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;
//    @Id
//    @Basic(optional = false)
//    @NotNull
//    @Column(nullable = false)
    private Integer id;
//    @Size(max = 45)
//    @Column(length = 45)
    private String descricao;

    public Categoria() {
    }

    public Categoria(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categoria)) {
            return false;
        }
        Categoria other = (Categoria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.demoiselle.jee7.entity.Categoria[ id=" + id + " ]";
    }

}
