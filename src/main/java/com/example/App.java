
package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {

    JTextField display;
    double firstNumber = 0;
    String operator = "";
    boolean newNumber = true;

    // Connect business logic
    App calculator = new App();

    public Calculator() {

        setTitle("Java Calculator");
        setSize(350, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 28));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);

        add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 5, 5));

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

                double result;

                try {

                    switch (operator) {

                        case "+":
                            result = calculator.add(firstNumber, secondNumber);
                            break;

                        case "-":
                            result = calculator.subtract(firstNumber, secondNumber);
                            break;

                        case "*":
                            result = calculator.multiply(firstNumber, secondNumber);
                            break;

                        case "/":
                            result = calculator.divide(firstNumber, secondNumber);
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

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            Calculator calculator = new Calculator();
            calculator.setVisible(true);
        });
    }
}
