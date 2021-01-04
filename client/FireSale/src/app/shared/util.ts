export class Util {

  public static pad(value: number, size: number): string {
    let s = value.toString();
    while (s.length < (size || 2)) { s = '0' + s; }
    return s;
  }

  public static formatDate(value: { date: number; }): string {
    const seconds = Math.floor((value.date / 1000) % 60);
    const minutes = Math.floor((value.date / 1000 / 60) % 60);
    const hours = Math.floor((value.date / (1000 * 60 * 60)) % 24);
    const days = Math.floor(value.date / (1000 * 60 * 60 * 24));

    let returnValue = `${Util.pad(hours, 2)}:${Util.pad(minutes, 2)}:${Util.pad(seconds, 2)}`;
    if (days > 0) {
      returnValue = `${Util.pad(days, 1)}d ${returnValue}`;
    }

    return returnValue;
  }
}
