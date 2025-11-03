import java.time.LocalDateTime;
import java.util.Date;

public class Expense {

  private int id;
  private LocalDateTime date;
  private double value;
  private String merchant;

  public Expense(int id, LocalDateTime date, double value, String merchant) {
    this.id = id;
    this.date = date;
    this.value = value;
    this.merchant = merchant;
  }

  @Override
  public String toString() {
    return String.format("Expense [id=%d, date=%s, value=%f, merchant=%s]", id, date, value,
        merchant);
  }

  public String toCSV() {
    return String.format("%d,%s,%f,%s", id, date, value, merchant);
  }
}
