import {
  AfterViewInit,
  Component,
  ElementRef,
  Input,
  OnDestroy,
  OnInit,
  ViewChild,
} from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { fromEvent, Subscription } from 'rxjs';
import {
  filter,
  debounceTime,
  distinctUntilChanged,
  tap,
} from 'rxjs/operators';
import { Filter } from 'src/app/models/filter.model';
import { RecipesService } from 'src/app/services/recipes/recipes.service';

@Component({
  selector: 'app-recipe-filter',
  templateUrl: './recipe-filter.component.html',
  styleUrls: ['./recipe-filter.component.scss'],
})
export class RecipeFilterComponent implements OnInit, AfterViewInit, OnDestroy {
  isSortOpen: boolean = false;
  isOrderOpen: boolean = false;
  query: string = null;
  sort: string = null;
  order: string = null;

  @ViewChild('input') input: ElementRef;

  searchValue: string;

  @Input()
  fields: string[];

  @Input()
  page: number;

  subscriptions: Subscription[] = [];

  constructor(
    private recipesService: RecipesService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {}

  ngAfterViewInit() {
    // server-side search
    const inputSub = fromEvent(this.input.nativeElement, 'keyup')
      .pipe(
        filter(Boolean),
        debounceTime(400),
        distinctUntilChanged(),
        tap((text: string) => {
          this.query = this.input.nativeElement.value;
          this.setQuery(this.query);
        })
      )
      .subscribe();

    this.subscriptions.push(inputSub);
  }

  onClick(element) {
    if (element == 'sort') {
      this.isSortOpen = !this.isSortOpen;
      this.isOrderOpen = false;
    } else {
      this.isOrderOpen = !this.isOrderOpen;
      this.isSortOpen = false;
    }
  }

  fetchRecipesWithFilter() {
    this.recipesService.setFilter(
      new Filter(this.sort, this.order, this.query)
    );
    if (this.router.url === '/recipes') {
      this.recipesService.loadAllRecipes(this.page - 1);
    } else {
      this.route.params.subscribe((params: Params) => {
        this.recipesService.loadAllRecipesForUser(this.page - 1, params.id);
      });
    }
  }

  setQuery(query: string) {
    if (this.query != null) {
      this.query = query;
    }
    this.fetchRecipesWithFilter();
  }

  setSort(index: number) {
    this.sort = this.fields[index];
    this.fetchRecipesWithFilter();
    this.isSortOpen = false;
  }

  setOrder(index: number) {
    if (index == 0) {
      this.order = 'asc';
    } else {
      this.order = 'desc';
    }
    this.fetchRecipesWithFilter();
    this.isOrderOpen = false;
  }

  ngOnDestroy() {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }
}
