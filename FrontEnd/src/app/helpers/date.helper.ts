export class DateHelper {
  public static NOT_KNOWN: Date = null;

  public static parse(dateString: string): Date {
    if (dateString == null || dateString === '')
      return DateHelper.NOT_KNOWN;

      const date: Date = new Date(dateString);

      if (isNaN(date.getTime()))
        return DateHelper.NOT_KNOWN;

      return date;
  }
}

