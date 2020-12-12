import { Component, OnDestroy, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { Ingredient } from 'src/app/models/ingredient.model';
import { Recipe } from 'src/app/models/recipe.model';
import { RecipeService } from 'src/app/services/recipe/recipe.service';

@Component({
  selector: 'app-add-recipe',
  templateUrl: './add-recipe.component.html',
  styleUrls: ['./add-recipe.component.scss'],
})
export class AddRecipeComponent implements OnInit, OnDestroy {
  isLoading: boolean = false;
  error: boolean = null;

  private subscriptions: Subscription[] = [];

  isEditMode: boolean = false;
  id: number;
  recipe: FormGroup;
  ingredients: Ingredient[] = [];
  isIngredientValid: boolean = false;

  leftImg = '/assets/img/kitchen.jpg';
  defaultImage = '/assets/img/kitchen-low-res.jpg';

  constructor(
    private router: Router,
    private recipeService: RecipeService,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.isUpdate();
  }

  private initForm() {
    this.isLoading = true;
    this.recipe = this.fb.group({
      name: new FormControl('', Validators.required),
      duration: new FormControl('', Validators.required),
      preparation: new FormControl('', Validators.required),
      imagePath: new FormControl(''),
      difficulty: new FormControl('', Validators.required),
      serving: new FormControl('', Validators.required),
      pricing: new FormControl('', Validators.required),
      ingredient: new FormGroup({
        name: new FormControl(''),
        amount: new FormControl(''),
      }),
    });

    this.isLoading = false;
  }

  onSubmit() {
    if (!this.recipe.valid) {
      return;
    }

    const recipe = this.getRecipeFromForm();

    this.isLoading = true;

    if (!this.isEditMode) {
      const recipeSub = this.recipeService.addRecipe(recipe).subscribe(() => {
        this.toastr.success('Recipe successfully added!');
        this.router.navigate(['/recipes']);
        this.isLoading = false;
      });
      (error) => {
        console.log(`Error ${error}`);
        this.toastr.error('error', 'Could not add a recipe');
      };

      this.subscriptions.push(recipeSub);
    }
  }

  onUpdate() {
    this.route.params.subscribe((params: Params) => {
      let id = +params.id;
      const recipeSub = this.recipeService
        .updateRecipeById(id, this.getRecipeFromForm())
        .subscribe(() => {
          this.toastr.success('Recipe successfully updated!');

          this.router.navigate(['/recipes', id]);
        });

      this.subscriptions.push(recipeSub);
    });
  }

  onAddIngredient() {
    const { name, amount } = this.recipe.value.ingredient;

    const ingredient = new Ingredient(name, amount);
    this.ingredients.push(ingredient);
    this.recipe.controls.ingredient.reset({ name: '', amount: '' });
    this.isIngredientValid = false;
  }

  onDeleteIngredient(name: string) {
    this.ingredients = this.ingredients.filter((x) => x.name != name);
    this.toastr.info('Deleted ' + name);
  }

  private getRecipeFromForm(): Recipe {
    const {
      name,
      duration,
      preparation,
      imagePath,
      difficulty,
      serving,
      pricing,
    } = this.recipe.value;

    const ingredients = this.ingredients;

    return new Recipe(
      name,
      duration,
      preparation,
      difficulty,
      serving,
      pricing,
      imagePath,
      0,
      ingredients
    );
  }

  private isUpdate() {
    if (this.router.url.endsWith('update')) {
      this.isEditMode = true;
      this.fetchRecipeById();
    }
  }

  private fetchRecipeById() {
    this.route.params.subscribe((params: Params) => {
      let id = +params.id;
      this.id = +params.id;
      const recipeSub = this.recipeService.recipe.subscribe((recipeData) => {
        let {
          name,
          duration,
          preparation,
          imagePath,
          difficulty,
          serving,
          pricing,
          ingredients,
        } = recipeData;

        Object.keys(ingredients).map((ing) => {
          this.ingredients.push(ingredients[ing]);
        });

        this.recipe = this.fb.group({
          name: new FormControl(name, Validators.required),
          duration: new FormControl(duration, Validators.required),
          preparation: new FormControl(preparation, Validators.required),
          imagePath: new FormControl(imagePath),
          difficulty: new FormControl(difficulty, Validators.required),
          serving: new FormControl(serving, Validators.required),
          pricing: new FormControl(pricing, Validators.required),
          ingredient: new FormGroup({
            name: new FormControl(''),
            amount: new FormControl(''),
          }),
        });
      });

      this.recipeService.getRecipeById(id);

      this.subscriptions.push(recipeSub);
    });
  }

  get ingredient() {
    return this.recipe.controls.ingredient as FormGroup;
  }

  checkIngredient(ingName, ingAmount) {
    return ingName.length !== 0 && ingAmount.length !== 0
      ? (this.isIngredientValid = true)
      : (this.isIngredientValid = false);
  }

  ngOnDestroy() {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }
}
