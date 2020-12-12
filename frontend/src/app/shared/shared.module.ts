import { NgModule } from '@angular/core';

import { LoadingSpinnerComponent } from './loading-spinner/loading-spinner.component';
import { PlaceholderDirective } from './placeholder/placeholder.directive';

import { CommonModule } from '@angular/common';
import { RecipeCounterPipe } from '../pipes/recipe-counter.pipe';
import { RecipeServingPipe } from '../pipes/recipe-serving.pipe';
import { LazyImageDirective } from '../directives/lazy-image.directive';
import { DateYearMonthPipe } from '../pipes/date-year-month.pipe';
import { LazyLoadImageModule } from 'ng-lazyload-image';
import { NgxPaginationModule } from 'ngx-pagination';
import { TextareaAutosizeModule } from 'ngx-textarea-autosize';
import { DifficultyPipe } from '../pipes/difficulty.pipe';
import { DateRelativePipe } from '../pipes/date-relative.pipe';
import { TitleShortenerPipe } from '../pipes/title-shortener.pipe';

@NgModule({
  declarations: [
    LoadingSpinnerComponent,
    PlaceholderDirective,
    RecipeCounterPipe,
    RecipeServingPipe,
    LazyImageDirective,
    DateYearMonthPipe,
    DifficultyPipe,
    DateRelativePipe,
    TitleShortenerPipe,
  ],
  imports: [
    CommonModule,
    TextareaAutosizeModule,
    NgxPaginationModule,
    LazyLoadImageModule,
  ],
  exports: [
    LoadingSpinnerComponent,
    PlaceholderDirective,
    CommonModule,
    RecipeCounterPipe,
    RecipeServingPipe,
    DateYearMonthPipe,
    TextareaAutosizeModule,
    NgxPaginationModule,
    LazyLoadImageModule,
    DifficultyPipe,
    DateRelativePipe,
    TitleShortenerPipe,
  ],
  providers: [],
})
export class SharedModule {}
