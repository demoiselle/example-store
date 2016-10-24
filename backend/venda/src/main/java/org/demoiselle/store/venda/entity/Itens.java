/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.demoiselle.store.venda.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;


import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author 70744416353
 */
@Entity
@Table(name = "itens")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Itens.findAll", query = "SELECT i FROM Itens i"),
    @NamedQuery(name = "Itens.findById", query = "SELECT i FROM Itens i WHERE i.id = :id"),
    @NamedQuery(name = "Itens.findByVendaId", query = "SELECT i FROM Itens i WHERE i.vendaId = :vendaId"),
    @NamedQuery(name = "Itens.findByProdutoId", query = "SELECT i FROM Itens i WHERE i.produtoId = :produtoId"),
    @NamedQuery(name = "Itens.findByValor", query = "SELECT i FROM Itens i WHERE i.valor = :valor")})

public class Itens implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @Column(name = "venda_id")
    private BigInteger vendaId;
    @Column(name = "produto_id")
    private BigInteger produtoId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 12)
    private BigDecimal valor;
    
    public Itens() {
    }

    public Itens(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigInteger getVendaId() {
        return vendaId;
    }

    public void setVendaId(BigInteger vendaId) {
        this.vendaId = vendaId;
    }

    public BigInteger getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(BigInteger produtoId) {
        this.produtoId = produtoId;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
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
        if (!(object instanceof Itens)) {
            return false;
        }
        Itens other = (Itens) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.demoiselle.jee7.entity.Itens[ id=" + id + " ]";
    }
    
}
