package org.demoiselle.jee7.example.store.sale.security;

import java.io.Serializable;
import java.util.Objects;

public class Credentials implements Serializable {

	private static final long serialVersionUID = 1L;
	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int hashCode() {
		int hash = 3;
		hash = 53 * hash + Objects.hashCode(this.username);
		return hash;
	}

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
		final Credentials other = (Credentials) obj;
		if (!Objects.equals(this.username, other.username)) {
			return false;
		}
		return true;
	}

	public String toString() {
		return "Credentials{" + "username=" + username + ", password=" + password + '}';
	}
}