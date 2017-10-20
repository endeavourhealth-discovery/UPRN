export class DateHelper {
  public static NOT_KNOWN: Date = null;

  public static parse(dateString: string): Date {
    if (dateString == null || dateString === '')
      return DateHelper.NOT_KNOWN;

    const date: Date = new Date(dateString);

    if (date)
      return date;

    return DateHelper.NOT_KNOWN;
  }
}

