package com.clemhlrdt.recipeapp.model;

import com.clemhlrdt.recipeapp.model.audit.DateAudit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name="profile", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"username"}),
		@UniqueConstraint(columnNames = {"email"})
})
public class User extends DateAudit {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Size(min= 3,max=15)
	@Column(name = "username")
	@NotBlank(message = "Username can't be blank")
	private String username;

	@Column(name = "email")
	@NotBlank(message = "Email can't be blank")
	@Size(max = 40)
	@Email
	private String email;
 
	@Column(name = "password")
	@NotBlank
	@Size(max = 100)
	private String password;

	@Column(name = "description", length = 10000, columnDefinition = "text")
	private String description;

	@Column(name = "avatar")
	private String avatar;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	List<Recipe> recipes;

	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
	@JoinTable(name = "user_roles",
	joinColumns = @JoinColumn(name = "user_id"),
	inverseJoinColumns = @JoinColumn(name="role_id"))
	private Set<Role> roles = new HashSet<>();

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}
}

