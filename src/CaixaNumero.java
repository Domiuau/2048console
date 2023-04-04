public class CaixaNumero {

    private int numero = 0;


    public int getNumero() {
        return numero;
    }

    public String getNumeroFormatado() {
        return numero < 10 ? "  " + numero + "  " : numero < 100 ? "  " + numero + " " : numero < 1000 ? " " + numero + " " : numero < 10000 ? " " + numero + "": "" + numero + "";
    }

    public String getCor() {

        for (int i = 1; i < 8; i++) {

            if (getNumero() == Math.pow(2, i)) {
                return "3" + i;
            }
        }

        for (int i = 8; i < 15; i++) {
            if (getNumero() == Math.pow(2, i)) {
                return "9" + (i-7);
            }
        }

        return "00";


    }


    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "\u001B[" + getCor() + "m┌─────┐" +
                "\u001B[" + getCor() + "m│" + getNumeroFormatado() + "│" +
                "\u001B[" + getCor() + "m└─────┘";


    }

    @Override
    public boolean equals(Object obj) {
        CaixaNumero caixaNumero = (CaixaNumero) obj;
        return this.numero == caixaNumero.getNumero() ? true : false;
    }
}
