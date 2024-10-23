import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class CurrencyConverter extends JFrame implements ActionListener {
    private JComboBox<String> fromCurrencyComboBox;
    private JComboBox<String> toCurrencyComboBox;
    private JTextField amountTextField;
    private JLabel resultLabel;

    // Using a graph data structure to store exchange rates
    private Map<String, Map<String, Double>> exchangeRatesGraph;

    public CurrencyConverter() {
        setTitle("Currency Converter");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Initialize exchange rates graph
        exchangeRatesGraph = new HashMap<>();
        initializeExchangeRates();

        String[] currencies = {"USD", "EUR", "GBP", "INR"}; // Add more currencies here

        fromCurrencyComboBox = new JComboBox<>(currencies);
        toCurrencyComboBox = new JComboBox<>(currencies);
        amountTextField = new JTextField(10);
        JButton convertButton = new JButton("Convert");
        convertButton.addActionListener(this);

        resultLabel = new JLabel();

        add(new JLabel("Amount:"));
        add(amountTextField);
        add(fromCurrencyComboBox);
        add(new JLabel("to"));
        add(toCurrencyComboBox);
        add(convertButton);
        add(resultLabel);

        setVisible(true);
    }

    // Initialize exchange rates in the graph
    private void initializeExchangeRates() {
        addExchangeRate("USD", "USD", 1.0);
        addExchangeRate("USD", "EUR", 0.85);
        addExchangeRate("USD", "GBP", 0.72);
        addExchangeRate("USD", "INR", 74.5);

        addExchangeRate("EUR", "USD", 1.18);
        addExchangeRate("EUR", "EUR", 1.0);
        addExchangeRate("EUR", "GBP", 0.85);
        addExchangeRate("EUR", "INR", 88.56);

        addExchangeRate("GBP", "USD", 1.39);
        addExchangeRate("GBP", "EUR", 1.18);
        addExchangeRate("GBP", "GBP", 1.0);
        addExchangeRate("GBP", "INR", 104.11);

        addExchangeRate("INR", "USD", 0.013);
        addExchangeRate("INR", "EUR", 0.011);
        addExchangeRate("INR", "GBP", 0.0096);
        addExchangeRate("INR", "INR", 1.0);
    }

    // Add exchange rate to the graph
    private void addExchangeRate(String fromCurrency, String toCurrency, double rate) {
        exchangeRatesGraph.computeIfAbsent(fromCurrency, k -> new HashMap<>()).put(toCurrency, rate);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            double amount = Double.parseDouble(amountTextField.getText());
            String fromCurrency = (String) fromCurrencyComboBox.getSelectedItem();
            String toCurrency = (String) toCurrencyComboBox.getSelectedItem();

            double convertedAmount = convertCurrency(amount, fromCurrency, toCurrency);
            resultLabel.setText("Converted amount: " + convertedAmount + " " + toCurrency);
        } catch (NumberFormatException ex) {
            resultLabel.setText("Invalid input. Please enter a valid amount.");
        }
    }

    // Convert currency using graph-based approach
    private double convertCurrency(double amount, String fromCurrency, String toCurrency) {
        Map<String, Double> ratesFrom = exchangeRatesGraph.get(fromCurrency);
        if (ratesFrom != null) {
            Double toRate = ratesFrom.get(toCurrency);
            if (toRate != null) {
                return amount * toRate;
            }
        }
        throw new IllegalArgumentException("Conversion rate not available for selected currencies.");
    }

    public static void main(String[] args) {
        new CurrencyConverter();
    }
}