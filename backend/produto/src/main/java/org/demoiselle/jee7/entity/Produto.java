/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.demoiselle.jee7.entity;

import java.io.Serializable;
import java.math.BigInteger;

/**
 *
 * @author 70744416353
 */
//@Entity
//@Cacheable
//@Table(name = "produto")
//@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "Produto.findAll", query = "SELECT p FROM Produto p"),
//    @NamedQuery(name = "Produto.findById", query = "SELECT p FROM Produto p WHERE p.id = :id"),
//    @NamedQuery(name = "Produto.findByCategoriaId", query = "SELECT p FROM Produto p WHERE p.categoriaId = :categoriaId"),
//    @NamedQuery(name = "Produto.findByDescricao", query = "SELECT p FROM Produto p WHERE p.descricao = :descricao"),
//    @NamedQuery(name = "Produto.findByValor", query = "SELECT p FROM Produto p WHERE p.valor = :valor")})
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Basic(optional = false)
//    @Column(nullable = false)
    private Long id;
//    @Column(name = "categoria_id")
    private BigInteger categoriaId;
//    @Size(max = 45)
//    @Column(length = 45)
    private String descricao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
//    @Column(precision = 12)
    private Float valor;

    public Produto() {
    }

    public Produto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigInteger getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(BigInteger categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
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
        if (!(object instanceof Produto)) {
            return false;
        }
        Produto other = (Produto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.demoiselle.jee7.entity.Produto[ id=" + id + " ]";
    }

}
