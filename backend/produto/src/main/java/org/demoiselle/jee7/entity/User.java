package org.demoiselle.jee7.entity;

import java.io.Serializable;
import java.security.Principal;
import java.util.Objects;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 70744416353
 */
//@Entity
//@Cacheable
//@XmlRootElement
//@Table(name = "usuario")
public class User implements Serializable {

    private static final long serialVersionUID = 5625711959333905292L;

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @NotNull
    private String name;

    private String perfil;

    private String email;

    private String cpf;

    private String fone;

    private String senha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", perfil=" + perfil + ", email=" + email + ", cpf=" + cpf + ", fone=" + fone + ", senha=" + senha + '}';
    }

}
