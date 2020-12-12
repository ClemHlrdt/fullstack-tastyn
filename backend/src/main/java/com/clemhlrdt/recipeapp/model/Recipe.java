package com.clemhlrdt.recipeapp.model;

import com.clemhlrdt.recipeapp.model.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name="recipe", indexes = {
		@Index(name = "recipeindex",  columnList="name", unique = true)
})
public class Recipe extends DateAudit {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Size(min=2)
	@Column(name="name")
	private String name;

	@NotNull
	@Min(1)
	@Column(name="duration")
	private int duration;

	@Lob
	@NotNull
	@Column(name = "preparation", length = 10000, columnDefinition = "text")
	private String preparation;

	@Column(name = "votes")
	private int votes = 0;

	@Column(name = "imagePath")
	private String imagePath;

	@NotNull
	@Min(0)
	@Max(5)
	@Column(name = "difficulty")
	private int difficulty;

	@NotNull
	@Min(0)
	@Column(name = "serving")
	private int serving;

	@NotNull
	@Min(0)
	@Max(5)
	@Column(name = "pricing")
	private int pricing;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Ingredient> ingredients;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	@JsonIgnore
	private User user;

	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
	List<Comment> comments = new ArrayList<>();

	@OneToMany(mappedBy = "recipe", cascade={CascadeType.ALL})
	List<RecipeVote> recipeVotes = new ArrayList<>();

	public void addIngredient(String name, String quantity){
		this.ingredients.add(new Ingredient(name, quantity));
	}

	public void addComment(Comment comment){
		this.comments.add(comment);
		if (comment.getRecipe() != this) {
			comment.setRecipe(this);
		}
	}

	public void addVote(RecipeVote vote){
			this.recipeVotes.add(vote);
			if(vote.getRecipe() != this){
				vote.setRecipe(this);
			}
	}
}
