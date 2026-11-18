

package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class App extends JFrame implements ActionListener {

    private JTextField display;
    private double firstNumber = 0;
    private String operator = "";
    private boolean newNumber = true;

    public App() {

        setTitle("Java Swing Calculator");
        setSize(350, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));

        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 28));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);

        add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(4, 4, 5, 5));

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "C", "0", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 22));
            button.addActionListener(this);
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);
    }

    // Business logic methods (used by JUnit tests)

    public double add(double a, double b) {
        return a + b;
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    public double divide(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        return a / b;
    }

    // Swing button handling

    @Override
    public void actionPerformed(ActionEvent e) {

        String value = e.getActionCommand();

        if (value.matches("[0-9]")) {

            if (newNumber || display.getText().equals("0")) {
                display.setText(value);
                newNumber = false;
            } else {
                display.setText(display.getText() + value);
            }

        } else if (value.equals("C")) {

            display.setText("0");
            firstNumber = 0;
            operator = "";
            newNumber = true;

        } else if (value.equals("=")) {

            if (!operator.isEmpty()) {

                double secondNumber =
                    Double.parseDouble(display.getText());

                try {
                    double result;

                    switch (operator) {
                        case "+":
                            result = add(firstNumber, secondNumber);
                            break;
                        case "-":
                            result = subtract(firstNumber, secondNumber);
                            break;
                        case "*":
                            result = multiply(firstNumber, secondNumber);
                            break;
                        case "/":
                            result = divide(firstNumber, secondNumber);
                            break;
                        default:
                            return;
                    }

                    display.setText(String.valueOf(result));

                } catch (IllegalArgumentException ex) {
                    display.setText(ex.getMessage());
                }

                operator = "";
                newNumber = true;
            }

        } else {

            firstNumber = Double.parseDouble(display.getText());
            operator = value;
            newNumber = true;
        }
    }

    // Application entry point

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            App calculator = new App();
            calculator.setVisible(true);
        });
    }
}

