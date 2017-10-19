import {Pipe, PipeTransform} from '@angular/core';
import * as moment from 'moment';

export class DateHelper {
  public static NOT_KNOWN: Date = new Date('1000-01-01');

  public static parse(dateString: string): Date {
    if (dateString == null || dateString === '')
      return DateHelper.NOT_KNOWN;

    const date: Date = new Date(dateString);

    if (date)
      return date;

    return DateHelper.NOT_KNOWN;
  }
}

@Pipe({name : 'cuiDate'})
export class CuiDate implements PipeTransform {
  transform(date: Date): string {
    return formatCuiDate(date);
  }
}

@Pipe({name : 'cuiDateTime'})
export class CuiDateTime implements PipeTransform {
  transform(date: Date): string {
    return formatCuiDateTime(date);
  }
}

function formatCuiDate(date: Date): string {
    if (date == null || date === DateHelper.NOT_KNOWN)
        return 'Not Known';

    return moment(date).utc().format('DD-MMM-YYYY');
}

function formatCuiDateTime(date: Date): string {
  if (date == null || date === DateHelper.NOT_KNOWN)
    return 'Not Known';

  return moment(date).utc().format('DD-MMM-YYYY HH:mm:ss');
}
