import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Atm implements ActionListener {
    JFrame frame;
    JPanel loginPanel, menuPanel, withdrawPanel, depositPanel, fastWithdrawPanel, successPanel;
    JTextField pinField, userField, withdrawAmountField, depositAmountField;
    JButton loginBtn, withdrawBtn, depositBtn, fastWithdrawBtn;
    JButton enterWithdrawBtn, enterDepositBtn;
    JButton[] fastWithdrawButtons;
    JButton returnBtn, logoutBtn, showHistoryBtn, clearHistoryBtn, exitBtn;

    JLabel balanceLabel, successMsg;
    User currentUser = null;

    public Atm() {
        frame = new JFrame("ATM");
        frame.setSize(600, 500);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        setupLoginPanel();
        setupMenuPanel();
        setupWithdrawPanel();
        setupDepositPanel();
        setupFastWithdrawPanel();
        setupSuccessPanel();

        frame.add(loginPanel);
        frame.setVisible(true);
    }

    private void setupLoginPanel() {
        loginPanel = new BackgroundPanel();
        loginPanel.setLayout(null);
        loginPanel.setBounds(0, 0, 600, 500);

        JLabel titleLabel = new JLabel("Welcome - User Login");
        titleLabel.setBounds(220, 80, 200, 30);
        loginPanel.add(titleLabel);

        JLabel pinLabel = new JLabel("PIN (numbers only):");
        pinLabel.setBounds(150, 150, 130, 30);
        loginPanel.add(pinLabel);

        pinField = new JTextField();
        pinField.setBounds(300, 150, 120, 30);
        loginPanel.add(pinField);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(150, 200, 130, 30);
        loginPanel.add(userLabel);

        userField = new JTextField();
        userField.setBounds(300, 200, 120, 30);
        loginPanel.add(userField);

        loginBtn = new JButton("Login");
        loginBtn.setBounds(240, 260, 120, 35);
        loginBtn.addActionListener(this);
        loginPanel.add(loginBtn);
    }

    private void setupMenuPanel() {
        menuPanel = new BackgroundPanel();
        menuPanel.setLayout(null);
        menuPanel.setBounds(0, 0, 600, 500);

        balanceLabel = new JLabel();
        balanceLabel.setBounds(180, 80, 300, 30);
        menuPanel.add(balanceLabel);

        withdrawBtn = new JButton("Withdraw");
        withdrawBtn.setBounds(200, 150, 200, 35);
        withdrawBtn.addActionListener(this);
        menuPanel.add(withdrawBtn);

        depositBtn = new JButton("Deposit");
        depositBtn.setBounds(200, 200, 200, 35);
        depositBtn.addActionListener(this);
        menuPanel.add(depositBtn);

        fastWithdrawBtn = new JButton("Fast Withdraw");
        fastWithdrawBtn.setBounds(200, 250, 200, 35);
        fastWithdrawBtn.addActionListener(this);
        menuPanel.add(fastWithdrawBtn);

        exitBtn = new JButton("Exit");
        exitBtn.setBounds(200, 300, 200, 35);
        exitBtn.addActionListener(this);
        menuPanel.add(exitBtn);
    }

    private void setupWithdrawPanel() {
        withdrawPanel = new BackgroundPanel();
        withdrawPanel.setLayout(null);
        withdrawPanel.setBounds(0, 0, 600, 500);

        JLabel amountLabel = new JLabel("Enter Amount (multiple of 500, max 40000):");
        amountLabel.setBounds(120, 150, 350, 30);
        withdrawPanel.add(amountLabel);

        withdrawAmountField = new JTextField();
        withdrawAmountField.setBounds(220, 190, 150, 30);
        withdrawPanel.add(withdrawAmountField);

        enterWithdrawBtn = new JButton("Enter");
        enterWithdrawBtn.setBounds(240, 240, 120, 35);
        enterWithdrawBtn.addActionListener(this);
        withdrawPanel.add(enterWithdrawBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(240, 290, 120, 35);
        backBtn.addActionListener(e -> switchPanel(menuPanel));
        withdrawPanel.add(backBtn);
    }

    private void setupDepositPanel() {
        depositPanel = new BackgroundPanel();
        depositPanel.setLayout(null);
        depositPanel.setBounds(0, 0, 600, 500);

        JLabel amountLabel = new JLabel("Enter Amount (multiple of 500, max 40000):");
        amountLabel.setBounds(120, 150, 350, 30);
        depositPanel.add(amountLabel);

        depositAmountField = new JTextField();
        depositAmountField.setBounds(220, 190, 150, 30);
        depositPanel.add(depositAmountField);

        enterDepositBtn = new JButton("Enter");
        enterDepositBtn.setBounds(240, 240, 120, 35);
        enterDepositBtn.addActionListener(this);
        depositPanel.add(enterDepositBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(240, 290, 120, 35);
        backBtn.addActionListener(e -> switchPanel(menuPanel));
        depositPanel.add(backBtn);
    }

    private void setupFastWithdrawPanel() {
        fastWithdrawPanel = new BackgroundPanel();
        fastWithdrawPanel.setLayout(null);
        fastWithdrawPanel.setBounds(0, 0, 600, 500);

        JLabel label = new JLabel("Choose amount to withdraw:");
        label.setBounds(200, 80, 250, 30);
        fastWithdrawPanel.add(label);

        String[] amounts = {"1000", "5000", "10000", "15000", "20000", "30000", "40000", "50000"};
        fastWithdrawButtons = new JButton[amounts.length];

        int x = 60;
        int y = 140;
        for (int i = 0; i < amounts.length; i++) {
            fastWithdrawButtons[i] = new JButton(amounts[i]);
            fastWithdrawButtons[i].setBounds(x, y, 100, 40);
            fastWithdrawButtons[i].addActionListener(this);
            fastWithdrawPanel.add(fastWithdrawButtons[i]);

            x = x + 120;
            if ((i + 1) % 4 == 0) {
                x = 60;
                y = y + 60;
            }
        }

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(240, 260, 120, 35);
        backBtn.addActionListener(e -> switchPanel(menuPanel));
        fastWithdrawPanel.add(backBtn);
    }

    private void setupSuccessPanel() {
        successPanel = new BackgroundPanel();
        successPanel.setLayout(null);
        successPanel.setBounds(0, 0, 600, 500);

        successMsg = new JLabel("", SwingConstants.CENTER);
        successMsg.setBounds(100, 80, 400, 30);
        successPanel.add(successMsg);

        returnBtn = new JButton("Return to Menu");
        returnBtn.setBounds(200, 150, 200, 35);
        returnBtn.addActionListener(this);
        successPanel.add(returnBtn);

        logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(200, 200, 200, 35);
        logoutBtn.addActionListener(this);
        successPanel.add(logoutBtn);

        showHistoryBtn = new JButton("Show History");
        showHistoryBtn.setBounds(200, 250, 200, 35);
        showHistoryBtn.addActionListener(this);
        successPanel.add(showHistoryBtn);

        clearHistoryBtn = new JButton("Clear History");
        clearHistoryBtn.setBounds(200, 300, 200, 35);
        clearHistoryBtn.addActionListener(this);
        successPanel.add(clearHistoryBtn);

        JButton exitBtn2 = new JButton("Exit");
        exitBtn2.setBounds(200, 350, 200, 35);
        exitBtn2.addActionListener(e -> System.exit(0));
        successPanel.add(exitBtn2);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginBtn) {
            String user = userField.getText().trim();
            String pin = pinField.getText().trim();
            if (user.isEmpty() || pin.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter both username and PIN.");
                return;
            }
            if (!pin.matches("\\d+")) {
                JOptionPane.showMessageDialog(frame, "PIN must be numbers only.");
                return;
            }
            currentUser = loadUser(user, pin);
            updateBalanceLabel();
            switchPanel(menuPanel);
            pinField.setText("");
            userField.setText("");
        } else if (e.getSource() == withdrawBtn) {
            withdrawAmountField.setText("");
            switchPanel(withdrawPanel);
        } else if (e.getSource() == depositBtn) {
            depositAmountField.setText("");
            switchPanel(depositPanel);
        } else if (e.getSource() == fastWithdrawBtn) {
            switchPanel(fastWithdrawPanel);
        } else if (e.getSource() == enterWithdrawBtn) {
            handleWithdraw(withdrawAmountField.getText().trim());
        } else if (e.getSource() == enterDepositBtn) {
            handleDeposit(depositAmountField.getText().trim());
        } else if (e.getSource() == returnBtn) {
            updateBalanceLabel();
            switchPanel(menuPanel);
        } else if (e.getSource() == logoutBtn) {
            saveUser(currentUser);
            currentUser = null;
            switchPanel(loginPanel);
        } else if (e.getSource() == showHistoryBtn) {
            showHistory();
        } else if (e.getSource() == clearHistoryBtn) {
            clearTransactionHistory();
            JOptionPane.showMessageDialog(frame, "All transaction history cleared.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getSource() == exitBtn) {
            saveUser(currentUser);
            System.exit(0);
        }

        // Fast Withdraw Buttons
        if (fastWithdrawButtons != null && currentUser != null) {
            for (JButton btn : fastWithdrawButtons) {
                if (e.getSource() == btn) {
                    handleWithdraw(btn.getText());
                    break;
                }
            }
        }
    }

    private void handleWithdraw(String text) {
        if (currentUser == null) return;
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter an amount.");
            return;
        }
        int amount;
        try {
            amount = Integer.parseInt(text);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid numeric amount.");
            return;
        }
        if (amount % 500 != 0) {
            JOptionPane.showMessageDialog(frame, "Amount must be a multiple of 500.");
        } else if (amount <= 0 || amount > 40000) {
            JOptionPane.showMessageDialog(frame, "Amount must be >0 and <=40000.");
        } else if (currentUser.getBalance() - amount < 0) {
            JOptionPane.showMessageDialog(frame, "Insufficient balance.");
        } else {
            currentUser.setBalance(currentUser.getBalance() - amount);
            updateBalanceLabel();
            writeTransaction("Withdraw", amount);
            successMsg.setText("Withdrawn: $" + amount + " | Balance: $" + currentUser.getBalance());
            switchPanel(successPanel);
        }
    }

    private void handleDeposit(String text) {
        if (currentUser == null) return;
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter an amount.");
            return;
        }
        int amount;
        try {
            amount = Integer.parseInt(text);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid numeric amount.");
            return;
        }
        if (amount % 500 != 0) {
            JOptionPane.showMessageDialog(frame, "Amount must be a multiple of 500.");
        } else if (amount <= 0 || amount > 40000) {
            JOptionPane.showMessageDialog(frame, "Amount must be >0 and <=40000.");
        } else {
            currentUser.setBalance(currentUser.getBalance() + amount);
            updateBalanceLabel();
            writeTransaction("Deposit", amount);
            successMsg.setText("Deposited: $" + amount + " | Balance: $" + currentUser.getBalance());
            switchPanel(successPanel);
        }
    }

    private void showHistory() {
        if (currentUser == null) return;
        File file = new File(currentUser.getUsername() + "_history.txt");
        if (!file.exists()) {
            JOptionPane.showMessageDialog(frame, "No transaction history.", "Transaction History", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error reading history.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (sb.length() == 0) {
            JOptionPane.showMessageDialog(frame, "No transaction history.", "Transaction History", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, sb.toString(), "Transaction History", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void writeTransaction(String type, int amount) {
        if (currentUser == null) return;
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String record = String.format("Time: %s | Type: %s | Amount: $%d | Balance: $%d", time, type, amount, currentUser.getBalance());
        try (FileWriter fw = new FileWriter(currentUser.getUsername() + "_history.txt", true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(record);
            bw.newLine();
        } catch (IOException ex) {
            // Optionally show error or ignore
        }
    }

    private void clearTransactionHistory() {
        if (currentUser == null) return;
        File file = new File(currentUser.getUsername() + "_history.txt");
        try (PrintWriter pw = new PrintWriter(file)) {
            // Overwrite file with nothing
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error clearing history.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateBalanceLabel() {
        if (currentUser != null) {
            balanceLabel.setText("User: " + currentUser.getUsername() + " | Balance: $" + currentUser.getBalance());
        }
    }

    private void switchPanel(JPanel panelToShow) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panelToShow);
        frame.getContentPane().repaint();
        frame.getContentPane().revalidate();
    }

    // User persistence (simple, for demo)
    private void saveUser(User user) {
        if (user == null) return;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(user.getUsername() + ".dat"))) {
            oos.writeObject(user);
        } catch (IOException ex) {
            // Optionally handle error
        }
    }

    private User loadUser(String username, String pin) {
        File file = new File(username + ".dat");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                User user = (User) ois.readObject();
                if (user.getPin().equals(pin)) {
                    return user;
                } else {
                    JOptionPane.showMessageDialog(frame, "Incorrect PIN.");
                    return null;
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error loading user data.");
                return null;
            }

        } 
        else {
            // New user
            User user = new User(username, pin);
            saveUser(user);
            return user;
        }
    }
}