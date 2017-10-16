import {Pipe, PipeTransform} from '@angular/core';
import * as moment from 'moment';

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
    if (date == null)
        return 'Not Known';

    return moment(date).utc().format('DD-MMM-YYYY');
}

function formatCuiDateTime(date: Date): string {
  if (date == null)
    return 'Not Known';

  return moment(date).utc().format('DD-MMM-YYYY HH:mm:ss');
}
