public class CaixaNumero {

    //Número que aparecerá em deterimnado espaço na matriz
    private int numero = 0;

    //Se o espaço pode ser somado na rodada ou não
    private boolean somavel = true;


    public int getNumero() {
        return numero;
    }

    /**
     *
     * @return o número armazenado com a quantidade de espaços adequada para encaixar no espaço
     *
     *  retornar assim         e não assim
     *      ┌─────┐              ┌─────┐
     *      │  3  │              │3    │
     *      └─────┘              └─────┘
     *
     */
    public String getNumeroFormatado() {
        return numero < 10 ? "  " + numero + "  " : numero < 100 ? "  " + numero + " " : numero < 1000 ? " " + numero + " " : numero < 10000 ? " " + numero + "": "" + numero + "";
    }

    /**
     *
     * @return retorna uma cor de acordo com o número elevado a 2, que é a base do 2048 2² = 4, 2³ = 8...
     */
    public String getCor() {

        //as dezenas 30 e 90 retornam uma cores diferente quando suas unidades são mudadas

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

    /**
     *
     * @return o objeto ja com seu número dentro, montado e colorido
     */
    @Override
    public String toString() {
        return "\u001B[" + getCor() + "m┌─────┐" +
                "\u001B[" + getCor() + "m│" + getNumeroFormatado() + "│" +
                "\u001B[" + getCor() + "m└─────┘";


    }

    public boolean isSomavel() {
        return somavel;
    }

    public void setSomavel(boolean somavel) {
        this.somavel = somavel;
    }

    @Override
    public boolean equals(Object obj) {
        CaixaNumero caixaNumero = (CaixaNumero) obj;
        return this.numero == caixaNumero.getNumero() ? true : false;
    }
}
