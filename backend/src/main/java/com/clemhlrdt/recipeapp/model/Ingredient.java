package com.clemhlrdt.recipeapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ingredient")
public class Ingredient {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Column(name="name")
	private String name;

	@NotNull
	@Column(name="amount")
	private String amount;

	public Ingredient(String name, String amount) {
		this.name = name;
		this.amount = amount;
	}
}
