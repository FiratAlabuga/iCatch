package com.example.icatch;

public class Kart_Islem_Eleman_SP {
    private String Date;
    private String Amount;

    public Kart_Islem_Eleman_SP(String date, String amount) {
        Date = date;
        Amount = amount;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
