import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'titleShortener',
})
export class TitleShortenerPipe implements PipeTransform {
  transform(title: string, length?: number): string {
    if (title.length > 20) {
      title = length ? title.substr(0, length) : title.substr(0, 15);
      title += '...';
    }
    return title;
  }
}
