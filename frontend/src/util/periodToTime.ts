//수정 필요!
export function periodToTime(period: number) {
  let m = 0;

  if (period === 0) {
    return "8:00";
  } else if (period >= 19) {
    m = 18 * 60 + 15 + (period - 19) * 45;
  } else {
    m = 9 * 60 + (period - 1) * 30;
  }
  return Math.floor(m / 60) + ":" + String(m % 60).padStart(2, "0");
}
