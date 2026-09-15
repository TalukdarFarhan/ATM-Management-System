import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String pin;
    private int balance;

    public User(String username, String pin) {
        this.username = username;
        this.pin = pin;
        this.balance = 10000; // default starting balance
    }

    public String getUsername() {
        return username;
    }

    public String getPin() {
        return pin;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}