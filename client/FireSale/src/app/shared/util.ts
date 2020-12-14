export class Util {

  public static pad(value: number, size: number){
    let s = value.toString();
    while (s.length < (size || 2)) {s = "0" + s;}
    return s;
  }

  public static formatDate(value: { date: number; }): string {
    const hours = Math.floor((value.date / (1000 * 60 * 60)));
    const minutes = Math.floor((value.date % (1000 * 60 * 60)) / (1000 * 60));
    const seconds = Math.floor((value.date % (1000 * 60)) / 1000);

    return `${Util.pad(hours,2)}:${Util.pad(minutes,2)}:${Util.pad(seconds,2)}`;
  }
}
