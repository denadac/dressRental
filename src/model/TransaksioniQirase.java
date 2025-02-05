package model;

import java.time.LocalDate;

public class TransaksioniQirase {
    private int transaksioniId;
    private Veshje veshja;
    private Klienti klienti;
    private LocalDate dataFillimit;
    private LocalDate dataMbarimit;

    public TransaksioniQirase(int transaksioniId, Veshje veshja, Klienti klienti, LocalDate dataFillimit, LocalDate dataMbarimit) {
        this.transaksioniId = transaksioniId;
        this.veshja = veshja;
        this.klienti = klienti;
        this.dataFillimit = dataFillimit;
        this.dataMbarimit = dataMbarimit;
    }


    public LocalDate getDataFillimit() {
        return dataFillimit;
    }

    public LocalDate getDataMbarimit() {
        return dataMbarimit;
    }
}
