import java.util.*;

class Stock {
    String symbol;
    String name;
    double price;

    Stock(String symbol, String name, double price) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
    }
}

public class StockTradingPlatform {

    static HashMap<String, Stock> market = new HashMap<>();
    static HashMap<String, Integer> portfolio = new HashMap<>();
    static double balance = 100000;

    public static void showMarket() {
        System.out.println("\n===== MARKET DATA =====");
        for (Stock s : market.values()) {
            System.out.println(s.symbol + " - " + s.name + " - ₹" + s.price);
        }
    }

    public static void buyStock(String symbol, int qty) {

        if (!market.containsKey(symbol)) {
            System.out.println("Invalid Stock!");
            return;
        }

        Stock stock = market.get(symbol);

        double cost = stock.price * qty;

        if (cost > balance) {
            System.out.println("Insufficient Balance!");
            return;
        }

        balance -= cost;

        portfolio.put(symbol, portfolio.getOrDefault(symbol, 0) + qty);

        System.out.println("Stock Purchased Successfully!");
    }

    public static void sellStock(String symbol, int qty) {

        if (!portfolio.containsKey(symbol)) {
            System.out.println("You don't own this stock!");
            return;
        }

        int owned = portfolio.get(symbol);

        if (qty > owned) {
            System.out.println("Not enough shares!");
            return;
        }

        Stock stock = market.get(symbol);

        balance += stock.price * qty;

        if (owned == qty)
            portfolio.remove(symbol);
        else
            portfolio.put(symbol, owned - qty);

        System.out.println("Stock Sold Successfully!");
    }

    public static void showPortfolio() {

        System.out.println("\n...... PORTFOLIO ......");

        double total = balance;

        if (portfolio.isEmpty()) {
            System.out.println("No Stocks Purchased.");
        } else {

            for (String symbol : portfolio.keySet()) {

                int qty = portfolio.get(symbol);

                Stock stock = market.get(symbol);

                double value = qty * stock.price;

                total += value;

                System.out.println(symbol +
                        " | Qty : " + qty +
                        " | Price : ₹" + stock.price +
                        " | Value : ₹" + value);
            }
        }

        System.out.println(".........................");
        System.out.println("Cash Balance : ₹" + balance);
        System.out.println("Total Portfolio Value : ₹" + total);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        market.put("TCS", new Stock("TCS", "Tata Consultancy", 3500));
        market.put("INFY", new Stock("INFY", "Infosys", 1600));
        market.put("RELIANCE", new Stock("RELIANCE", "Reliance", 2800));
        market.put("HDFC", new Stock("HDFC", "HDFC Bank", 1700));
        market.put("ITC", new Stock("ITC", "ITC Ltd", 450));

        while (true) {

            System.out.println("\n......... STOCK TRADING PLATFORM .........");
            System.out.println("1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Exit");
            System.out.print("Enter Choice : ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    showMarket();
                    break;

                case 2:
                    System.out.print("Enter Stock Symbol : ");
                    String buy = sc.next().toUpperCase();

                    System.out.print("Enter Quantity : ");
                    int buyQty = sc.nextInt();

                    buyStock(buy, buyQty);
                    break;

                case 3:
                    System.out.print("Enter Stock Symbol : ");
                    String sell = sc.next().toUpperCase();

                    System.out.print("Enter Quantity : ");
                    int sellQty = sc.nextInt();

                    sellStock(sell, sellQty);
                    break;

                case 4:
                    showPortfolio();
                    break;

                case 5:
                    System.out.println("Thank You!");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}