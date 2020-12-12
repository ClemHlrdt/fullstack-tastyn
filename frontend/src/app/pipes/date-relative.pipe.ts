import { Pipe, PipeTransform } from '@angular/core';

import * as dayjs from 'dayjs';
import * as relativeTime from 'dayjs/plugin/relativeTime';

dayjs.extend(relativeTime);

@Pipe({
  name: 'dateRelative',
})
export class DateRelativePipe implements PipeTransform {
  transform(date: string): string {
    return dayjs().from(dayjs(date), true);
  }
}
