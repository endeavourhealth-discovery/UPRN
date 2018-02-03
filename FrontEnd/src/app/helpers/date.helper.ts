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

  public static toSqlDateString(date: Date): string {
    const day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate().toString();
    const monthNumber = date.getMonth() + 1;
    const month = monthNumber < 10 ? '0' + monthNumber : monthNumber.toString();
    const year = date.getFullYear();

    return year + '-' + month + '-' + day;
  }
}

