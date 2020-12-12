package com.clemhlrdt.recipeapp.model;


import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@EqualsAndHashCode()
public class RecipeVote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long voteId;

	private int value;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="recipe_id", nullable=false)
	private Recipe recipe;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	public void setRecipe(Recipe recipe){
		this.recipe = recipe;
		recipe.getRecipeVotes().add(this);
	}
}



