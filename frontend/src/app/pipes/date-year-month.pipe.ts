import { Pipe, PipeTransform } from '@angular/core';
import * as dayjs from 'dayjs';
import * as customParseFormat from 'dayjs/plugin/customParseFormat';
dayjs.extend(customParseFormat);

@Pipe({
  name: 'dateYearMonth',
})
export class DateYearMonthPipe implements PipeTransform {
  transform(date: string): string {
    return dayjs(date).format('MMMM, YYYY');
  }
}
