package com.clemhlrdt.recipeapp.model;

import com.clemhlrdt.recipeapp.model.audit.DateAudit;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode()
@Entity
@Table(name = "comment")
public class Comment extends DateAudit {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Column(name="comment", length = 10000, columnDefinition = "text")
	private String comment;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@ManyToOne
	@JoinColumn(name="recipe_id", nullable=false)
	private Recipe recipe;

	public void setRecipe(Recipe recipe){
		this.recipe = recipe;
		if (!recipe.getComments().contains(this)) {
			recipe.getComments().add(this);
		}
	}
}
