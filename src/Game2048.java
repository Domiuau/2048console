import java.util.Random;
import java.util.Scanner;

public class Game2048 {

    static CaixaNumero[][] matrizDoJogo;
    static String lado;
    static boolean ordem;

    public static void main(String[] args) {   // ┌─│ └┘ ┐
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insira o tamanho do jogo (o padrão é 4x4)");
        Random random = new Random();
        String[] tamanhoDoJogo = scanner.next().replace(" ", "").split("x");


        matrizDoJogo = Integer.parseInt(tamanhoDoJogo[0]) > Integer.parseInt(tamanhoDoJogo[1]) ?
                new CaixaNumero[Integer.parseInt(tamanhoDoJogo[0])][Integer.parseInt(tamanhoDoJogo[1])] :
                new CaixaNumero[Integer.parseInt(tamanhoDoJogo[1])][Integer.parseInt(tamanhoDoJogo[0])];

        for (int i = 0; i < matrizDoJogo.length; i++) {
            for (int j = 0; j < matrizDoJogo[i].length; j++) {

                matrizDoJogo[i][j] = new CaixaNumero();
            }
        }


        matrizDoJogo[random.nextInt(matrizDoJogo.length)][random.nextInt(matrizDoJogo[0].length)].setNumero(random.nextInt(3) == 1 ? 4 : 2);


        boolean surgirNumero = false;
        boolean somar = false;

        imprimirJogo();


        while (true) {
            lado = scanner.next();
            ordem = lado.equals("s") || lado.equals("d");

            for (int t = 0; Integer.parseInt(tamanhoDoJogo[0]) > Integer.parseInt(tamanhoDoJogo[1]) ?
                    t < Integer.parseInt(tamanhoDoJogo[0]) - 1 : t < Integer.parseInt(tamanhoDoJogo[1]) - 1; t++) {

                for (int i = ordem ? matrizDoJogo.length - 1 : 0; ordem ? i >= 0 : i < matrizDoJogo.length; i = tiraUm(i)) {

                    for (int j = ordem ? matrizDoJogo[i].length - 1 : 0; ordem ? j >= 0 : j < matrizDoJogo[i].length; j = tiraUm(j)) {
                        if (matrizDoJogo[i][j].getNumero() > 0) {

                            try {

                                if (jogada(matrizDoJogo, i, j).getNumero() != matrizDoJogo[i][j].getNumero()) {

                                    if (jogada(matrizDoJogo, i, j).getNumero() == 0) {
                                        jogada(matrizDoJogo, i, j).setNumero(matrizDoJogo[i][j].getNumero());
                                        surgirNumero = true;
                                    }

                                    System.out.println("ooooeeeoo");
                                } else {
                                    jogada(matrizDoJogo, i, j).setNumero(jogada(matrizDoJogo, i, j).getNumero() * 2);
                                    //System.out.println(jogada(matrizDoJogo, i, j).getNumero() != matrizDoJogo[i][j].getNumero());
                                    matrizDoJogo[i][j].setNumero(0);
                                    surgirNumero = true;
                                }

                                if (jogada(matrizDoJogo, i, j).getNumero() == matrizDoJogo[i][j].getNumero()) {

                                    matrizDoJogo[i][j].setNumero(0);

                                }


                            } catch (Exception e) {

                            }

                        }


                    }


                }

//                try {
//                    Thread.sleep(200);
//                } catch (Exception e){
//
//
//                }
//
//                imprimirJogo();


            }

            if (surgirNumero == true) {

                surgirNumero = false;

                CaixaNumero[] matrizDoJogoAjuste = new CaixaNumero[1];
                int k = 0;
                for (int i = 0; i < matrizDoJogo[0].length; i++) {


                    for (int j = 0; j < matrizDoJogo.length; j++) {

                        if (matrizDoJogo[j][i].getNumero() == 0) {

                            CaixaNumero[] ajuste = new CaixaNumero[k + 1];

                            for (int l = 0; l < matrizDoJogoAjuste.length; l++) {
                                ajuste[l] = matrizDoJogoAjuste[l];
                            }


                            k++;
                            ajuste[ajuste.length - 1] = matrizDoJogo[j][i];
                            matrizDoJogoAjuste = ajuste;
                        }


                    }


                }


                try {
                    matrizDoJogoAjuste[random.nextInt(matrizDoJogoAjuste.length - 1)].setNumero(random.nextInt(3) == 1 ? 4 : 2);
                } catch (Exception e) {
                    matrizDoJogoAjuste[0].setNumero(random.nextInt(3) == 1 ? 4 : 2);
                }


                for (CaixaNumero a :
                        matrizDoJogoAjuste) {
                    System.out.println(a.getNumero());
                }
            }

            imprimirJogo();


            System.out.println("--------");
        }


    }


//    static CaixaNumero anterior(CaixaNumero[][] matrizDoJogo, int i, int j){
//        Scanner scanner = new Scanner(System.in);
//        switch (scanner.next()){
//
//            case "w": return matrizDoJogo[i+1][j];
//            case "s": return matrizDoJogo[i-1][j];
//            case "a": return matrizDoJogo[i][j+1];
//            case "d": return matrizDoJogo[i][j-1];
//
//            default: return null;
//        }


    //   }

    static CaixaNumero anterior(CaixaNumero[][] matrizDoJogo, int i, int j) {

        switch (lado) {

            case "w":
                return matrizDoJogo[i][j + 1];
            case "s":
                return matrizDoJogo[i][j - 1];
            case "a":
                return matrizDoJogo[i + 1][j];
            case "d":
                return matrizDoJogo[i - 1][j];

            default:
                return null;
        }
    }


    static CaixaNumero jogada(CaixaNumero[][] matrizDoJogo, int i, int j) {

        switch (lado) {

            case "w":
                return matrizDoJogo[i][j - 1];
            case "s":
                return matrizDoJogo[i][j + 1];
            case "a":
                return matrizDoJogo[i - 1][j];
            case "d":
                return matrizDoJogo[i + 1][j];

            default:
                return null;
        }


    }

    static CaixaNumero jogadaPedir(CaixaNumero[][] matrizDoJogo, int i, int j) {

        switch (lado) {

            case "w":
                return matrizDoJogo[i][j - 1];
            case "s":
                return matrizDoJogo[i][j + 1];
            case "a":
                return matrizDoJogo[i - 1][j];
            case "d":
                return matrizDoJogo[i + 1][j];

            default:
                return null;
        }
    }

    static void imprimirJogo() {


        String a = "";
        int inicioSubString = 0;
        int finalSubString = 12;

        for (int i = 0; i < matrizDoJogo[0].length; i++) {

            for (int j = 0; j < 3; j++) {

                for (int k = 0; k < matrizDoJogo.length; k++) {


                    a += matrizDoJogo[k][i].toString().substring(inicioSubString, finalSubString) + "  ";

                }

                inicioSubString += 12;
                finalSubString += 12;

                System.out.println(a);
                a = "";

            }

            inicioSubString = 0;
            finalSubString = 12;


        }


//        String a = "";
//        String b = "";
//        String c = "";
//
//        for (int i = 0; i < matrizDoJogo.length; i++) {
//
//            for (int j = 0; j < matrizDoJogo[i].length; j++) {
//
//                a += matrizDoJogo[i][j].toString().substring(0, 12) + "  ";
//                b += matrizDoJogo[i][j].toString().substring(12, 24) + "  ";
//                c += matrizDoJogo[i][j].toString().substring(24, 36) + "  ";
//
//
//            }
//
//
//            System.out.println(a);
//            a = "";
//            System.out.println(b);
//            b = "";
//            System.out.println(c);
//            c = "";
//
//
//        }
    }

    static int tiraUm(int i) {
        return ordem ? i - 1 : i + 1;

    }
}
