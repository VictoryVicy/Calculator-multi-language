import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame {
    private JTextField textField;

    public Calculator() {
        super("Simple Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);

        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 20));
        textField.setHorizontalAlignment(JTextField.RIGHT);
        add(textField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 4, 5, 5)); // Menambahkan ruang antar tombol

        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 18));
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String text = source.getText();

            if (text.equals("=")) {
                evaluateExpression();
            } else if (text.equals("C")) {
                clearTextField();
            } else {
                appendToTextField(text);
            }
        }

        private void evaluateExpression() {
            try {
                String expression = textField.getText();
                double result = evalExpression(expression);
                textField.setText(Double.toString(result));
            } catch (Exception ex) {
                textField.setText("Error");
            }
        }

        private double evalExpression(String expression) {
            return (double) new Object() {
                int pos = -1, ch;

                void nextChar() {
                    ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
                }

                boolean isDigitChar() {
                    return (ch >= '0' && ch <= '9');
                }

                double parse() {
                    nextChar();
                    double x = parseExpression();
                    if (pos < expression.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                    return x;
                }

                double parseExpression() {
                    double x = parseTerm();
                    for (; ; ) {
                        if (eat('+')) x += parseTerm();
                        else if (eat('-')) x -= parseTerm();
                        else return x;
                    }
                }

                double parseTerm() {
                    double x = parseFactor();
                    for (; ; ) {
                        if (eat('*')) x *= parseFactor();
                        else if (eat('/')) x /= parseFactor();
                        else return x;
                    }
                }

                double parseFactor() {
                    if (eat('+')) return parseFactor();
                    if (eat('-')) return -parseFactor();

                    double x;
                    int startPos = this.pos;
                    if (eat('(')) {
                        x = parseExpression();
                        eat(')');
                    } else if (isDigitChar() || ch == '.') {
                        while (isDigitChar() || ch == '.') nextChar();
                        x = Double.parseDouble(expression.substring(startPos, this.pos));
                    } else {
                        throw new RuntimeException("Unexpected: " + (char) ch);
                    }

                    return x;
                }

                boolean eat(int charToEat) {
                    while (ch == ' ') nextChar();
                    if (ch == charToEat) {
                        nextChar();
                        return true;
                    }
                    return false;
                }
            }.parse();
        }

        private void clearTextField() {
            textField.setText("");
        }

        private void appendToTextField(String text) {
            textField.setText(textField.getText() + text);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator calculator = new Calculator();
            calculator.setVisible(true);
        });
    }
}
