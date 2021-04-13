package com.akkiei.cryptoticker;

public class Trigger {
    String crypto;
    String operator;
    double price;

    Trigger() {
        crypto = "";
        operator = "";
        price = 0;
    }

    void setData(String cryptoName, String operatorText, double price) {
        this.crypto = cryptoName;
        this.operator = operatorText;
        this.price = price;
    }

    String getCryptoName() {

        return this.crypto;
    }

    String getOperator() {

        if (this.operator.equalsIgnoreCase("less than"))
            return "<";
        if (this.operator.equalsIgnoreCase("less than equal to"))
            return "<=";
        if (this.operator.equalsIgnoreCase("greater than"))
            return ">";
        if (this.operator.equalsIgnoreCase("greater than equal to"))
            return ">=";
        else
            return "==";

    }

    double getPrice() {
        return this.price;
    }
}
