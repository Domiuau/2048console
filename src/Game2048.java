import java.util.Random;
import java.util.Scanner;

public class Game2048 {

    //Variáveis globais

    //Vetor responsável pela matrix do jogo, todos os valores do game ficarão salvos aqui
    static CaixaNumero[][] matrizDoJogo;
    //Variável responsável por armazenar o lado em que o jogo será "arrastado" após o jogador escolher entra W A S D
    static String lado;

    //Variável responsável por definir a ordem de acordo com a jogada, Ex: caso o usuário jogue "A", a matrix começará a ser arrastada a partir do lado esquerdo
    //e caso o usuário jogue "D", a matrix começará a ser arrastada a partir do lado direito
    static boolean ordem;

    public static void main(String[] args) {   // ┌─│ └┘ ┐
        //Declaração do Scanner para a entrada de dados, no caso W A S D
        Scanner scanner = new Scanner(System.in);
        //Instruções básicas de como jogar
        System.out.println("\u001B[40m  .-----.   .----.      .---.    .-----.   \u001B[m\n" +
                "\u001B[40m / ,-.   \\ /  ..  \\    / .  |   /  .-.  \\  \u001B[m\n" +
                "\u001B[40m '-'  |  |.  /  \\  .  / /|  |  |   \\_.' /  \u001B[m\n" +
                "\u001B[40m    .'  / |  |  '  | / / |  |_  /  .-. '.  \u001B[m\n" +
                "\u001B[40m  .'  /__ '  \\  /  '/  '-'    ||  |   |  | \u001B[m\n" +
                "\u001B[40m |       | \\  `'  / `----|  |-' \\  '-'  /  \u001B[m\n" +
                "\u001B[40m `-------'  `---''       `--'    `----''   \u001B[m");
        System.out.println("Mande W A S D para mover o jogo para cima, para esquerda, para baixo e para a direita respectivamente");
        //Aqui é solicitado o tamanho do jogo, ou tamanho da matrix, que pode ser 1x1, 4x4, 8x9, 20x90, 100x1000...
        System.out.println("Insira o tamanho do jogo (o padrão é 4x4)");
        //Declaração do random usado para determinar onde será colocado um número aleatoriamente em um espaço vazio após uma jogada
        Random random = new Random();
        //O tamanho inserido pelo usuário é é dividido por "x" e inserido em um vetor, então caso o usuário coloca 5x6, será criado um vetor com 2 posições
        //sendo o [0] = 5 e [1] = 6
        String[] tamanhoDoJogo = scanner.next().replace(" ", "").split("x");

        //Aqui a matriz do jogo é criada, onde o maior número é sempre a fileira (x), então 7x5 continua 7x5 e 5x7 vira 7x5
        matrizDoJogo = Integer.parseInt(tamanhoDoJogo[0]) > Integer.parseInt(tamanhoDoJogo[1]) ?
                new CaixaNumero[Integer.parseInt(tamanhoDoJogo[0])][Integer.parseInt(tamanhoDoJogo[1])] :
                new CaixaNumero[Integer.parseInt(tamanhoDoJogo[1])][Integer.parseInt(tamanhoDoJogo[0])];

        //A matrix é completamente preenchida pelo objeto "CaixaNumero", responsável por armazenar o número da posição e gerar seu desenho e sua cor
        for (int i = 0; i < matrizDoJogo.length; i++) {
            for (int j = 0; j < matrizDoJogo[i].length; j++) {

                matrizDoJogo[i][j] = new CaixaNumero();
            }
        }

        //Aqui uma posição da matrix é sorteada para ter o número inicial, que também é gerado aleatoriamente, sendo sorteado um número de 0 a 2
        //caso seja 0 ou 2, o número gerado recebe 2, e caso seja 1, esse número recebe 4, ou seja, 66,6% de change de gerar um 2 e 33% de gerar um 4
        matrizDoJogo[random.nextInt(matrizDoJogo.length)][random.nextInt(matrizDoJogo[0].length)].setNumero(random.nextInt(3) == 1 ? 4 : 2);

        //Variavel responsável por definir se após uma jogada será gerado um número em um espaço vazio aleatorio ou não
        //ela existe por conta de casos mostrados como no exemplo abaixo
        boolean surgirNumero = false;

        //Indicação de que a primeira rodada irá começar
        System.out.println("\u001B[m------------------------------------------------------------------------------");
        System.out.println("Rodada número 1");

        //Impressão manual da matrix do jogo usando o metodo "imprimirJogo()" que será explicado mais tarde
        imprimirJogo();

        //Agora que a matrix já foi gerada e preenchida com objetos CaixaNumero e já foi definido o
        // número inicial, entramos no loop principal do jogo, onde toda a matrix é manipulada
        for (int w = 1; true; ) {

            //Nesse momento é solicitada ao usuário a jogada W - cima A - esquerda S - baixo D - direita
            lado = scanner.next();
            //Essa variável é responsável por mudar a ordem dos loops abaixo, ou definindo por onde a matrix começará a ser arrastada
            ordem = lado.equals("s") || lado.equals("d");


            //for responsável por arrastar os objetos vezes o suficiente para ir de uma ponta a outra caso seja necessário
            //Ternário utilizado para pegar o maior entre a fileira e coluna, inserido e armazenado anteriormente pelo usuário Ex: "3x5", nesse caso é passado o 5
            //então os números são passados para o lado (5 - 1) vezes (4), conforme o exemplo abaixo:


            for (int t = 0; Integer.parseInt(tamanhoDoJogo[0]) > Integer.parseInt(tamanhoDoJogo[1]) ?
                    t < Integer.parseInt(tamanhoDoJogo[0]) - 1 : t < Integer.parseInt(tamanhoDoJogo[1]) - 1; t++) {

                //Dentro da criação dos fors abaixo, é utilizado um operador ternário, para definir se será um for normal ou um for reverso
                //caso seja um for normal, o jogo começará pela esquerda ou por cima
                //caso seja um for reverso, o jogo começará pela direita ou por baixo
                //segue abaixo exemplos do por quê a direção de onde o jogo começa muda

                //Fileira
                for (int i = ordem ? matrizDoJogo.length - 1 : 0; ordem ? i >= 0 : i < matrizDoJogo.length; i = tiraUm(i)) {

                    //Coluna
                    for (int j = ordem ? matrizDoJogo[i].length - 1 : 0; ordem ? j >= 0 : j < matrizDoJogo[i].length; j = tiraUm(j)) {

                        //matrizDoJogo[i][j] = número que será movido
                        //jogada(matrizDoJogo, i, j) = para onde o número será movido
                        //exemplo

                        //Compara se o número que será movido é maior do que 0, caso não for, não acontece nada, não tem o por quê mexer no 0
                        //exemplo


                        if (matrizDoJogo[i][j].getNumero() > 0) {

                            try {

                                //caso o número tenha chegado até aqui, ele é maior do que 0, então pode ser movido
                                //caso o número a ser movido seja diferente do número no lugar de para onde ele irá, ele será movido caso o espaço que ele irá esteja vazio (tenha um número 0)

                                if (jogada(i, j).getNumero() != matrizDoJogo[i][j].getNumero()) {

                                    //Compara se o espaço que o número a ser movido irá contem um 0
                                    //caso tenha, o espaço vazio (espaço com 0) receberá o número a ser movido e o espaço que contém o número que foi movido é esvaziado (recebe 0)
                                    if (jogada(i, j).getNumero() == 0) {
                                        jogada(i, j).setNumero(matrizDoJogo[i][j].getNumero());
                                        matrizDoJogo[i][j].setNumero(0);
                                        //Declara que após o final da rodada, um número pode ser gerado em um espaço vazio aleatório, pois houve movimento na matrix, entçao foi uma jogava válida
                                        surgirNumero = true;
                                    }

                                } else { // <-- agora caso o número a ser movido e o número contido no espaço para onde ele irá não são diferentes, então eles são iguais, e para terem chegado até
                                    //aqui, ambos são maiores do que 0
                                    //o número no espaço para onde o número irá é multiplicado por 2, e o espaço onde o número que está movido está recebe 0

                                    if (jogada(i, j).isSomavel() && matrizDoJogo[i][j].isSomavel()) {
                                        jogada(i, j).setNumero(jogada(i, j).getNumero() * 2);
                                        matrizDoJogo[i][j].setNumero(0);
                                        //o espaço que acabou de receber um número (acabou de se tornar um número maior, dobrando seu tamanho), é definido como "não editavel"
                                        //para evitar o evento relatado no exemplo a baixo
                                        jogada(i, j).setSomavel(false);

                                    }
                                    //Também foi uma jogada válida, então é liberada a criação de um número em um espaço vazio após o termino da rodada
                                    surgirNumero = true;

                                }

                                //está tudo dentro de um bloco try catch para evitar Exceptions, sem ele iria acontecer coisas tipo matrizDoJogo[-1][0], o que iria lançar uma excessão e finalizar o programa

                            } catch (Exception e) {

                            }

                        }

                    }

                }

            }

            //caso tenha acontecido uma jogada válida (algo se moveu na matriz após uma jogada) a variavel surgirNumero será verdadeira
            if (surgirNumero == true) {

                //Após entrar no bloco de execução, ela ja recebe falso, deixando a variável esperando outra jogada válida para que possa receber verdaidero e chegar até aqui novamente
                surgirNumero = false;

                //Aqui começa o processo para definir quais são os lugares válidos para receber um número, no caso são todas aquelas posições que estão vazias (armazenando 0) na matriz

                //Vetor onde onde esses lugares serão armazenados
                CaixaNumero[] matrizDoJogoAjuste = new CaixaNumero[1];
                int k = 0;

                //percorre a coluna
                for (int i = 0; i < matrizDoJogo[0].length; i++) {

                    //percorre a fileira
                    for (int j = 0; j < matrizDoJogo.length; j++) {

                        //caso a posição de [j][i] na matrix do jogo seja = 0, o objeto CaixaNumero presente na posição da matriz é adicionado ao vetor matrizDoJogoAjuste
                        if (matrizDoJogo[j][i].getNumero() == 0) {

                            //um novo vetor é criado com um espaço a mais para receber esse objeto CaixaNumero = 0
                            CaixaNumero[] ajuste = new CaixaNumero[k + 1];

                            //o vetor "ajuste" recebe todos os objetos armazenados no vetor matrizDoJogoAjuste, mas como ele tem um espaõ a mais, o ultimo índice fica vazio
                            for (int l = 0; l < matrizDoJogoAjuste.length; l++) {
                                ajuste[l] = matrizDoJogoAjuste[l];
                            }

                            k++;
                            //o ultimo índice é preenchido com o objeto CaixaNumero presente na posição [j][i] da matriz
                            ajuste[ajuste.length - 1] = matrizDoJogo[j][i];
                            //matrizDoJogoAjuste recebe o vetor "ajuste" que contém todos os objetos presentes no matrizDoJogoAjuste + o objeto que acabou de ser inserido
                            matrizDoJogoAjuste = ajuste;
                        }


                    }


                }

                //Aqui é sorteado um índice com um objeto CaixaNumero presente para ser adicionado um número que tem 66,6% chance de ser 2 e 33,3% de ser 4
                //caso o vetor matrizDoJogoAjuste tenha 6 índices, é sorteado um valor de 0 a 5 para acessar um índice aleatório, e dentro desse índice há um objeto CaixoNúmero = 0 que recebera um número

                try {
                    matrizDoJogoAjuste[random.nextInt(matrizDoJogoAjuste.length)].setNumero(random.nextInt(3) == 1 ? 4 : 2);
                } catch (Exception e) {

                }

            }

            //Indica para o usuário o número da rodada que ele está, sendo "w" esse número
            System.out.println("\u001B[m------------------------------------------------------------------------------");
            System.out.println("Rodada número " + (++w));

            //após toda a manipulação e mudanças feitas na matriz, finalmente o jogo é impresso na tela do usuário
            imprimirJogo();


            //como é o fim da rodada, todas as posições são marcadas como editáveis novamente para o jogo poder rolar normalmente
            for (int i = 0; i < matrizDoJogo.length; i++) {
                for (int j = 0; j < matrizDoJogo[0].length; j++) {
                    matrizDoJogo[i][j].setSomavel(true);
                }
            }
        }
    }

    /**
     * @param i posição da fileira da matriz (x do número a ser movido)
     * @param j posição da coluna da matriz matriz (y do número a ser movido)
     * @return posição do número a esquerda, direita, cima ou baixo na matriz em relação ao número que será movido ou mantido no mesmo lugar
     */

    static CaixaNumero jogada(int i, int j) {

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

            //caso o método receba i = 2 e j = 1, então o número a ser movido é o número 4 entre parênteses
            //caso o usuário insira "w", o metodo retornará [i][j-1], ou seja i = 2 e j = 0, onde o 2 entre chaves está presente
            //caso o usuário insira "d", o metodo retornará [i + 1][j], ou seja i = 3 e j = 1, onde o 8 entre colchetes está presente
            //o que representa o posição que o número a ser movido tentará se deslocar


//                      ┌─────┐  ┌─────┐  ┌─────┐  ┌─────┐
//                      │  2  │  │  8  │  │ {2} │  │  4  │
//                      └─────┘  └─────┘  └─────┘  └─────┘
//                      ┌─────┐  ┌─────┐  ┌─────┐  ┌─────┐
//                      │  8  │  │  2  │  │ (4) │  │ [8] │
//                      └─────┘  └─────┘  └─────┘  └─────┘
//                      ┌─────┐  ┌─────┐  ┌─────┐  ┌─────┐
//                      │  16 │  │  8  │  │  4  │  │  2  │
//                      └─────┘  └─────┘  └─────┘  └─────┘
//                      ┌─────┐  ┌─────┐  ┌─────┐  ┌─────┐
//                      │  32 │  │  16 │  │  8  │  │  4  │
//                      └─────┘  └─────┘  └─────┘  └─────┘


        }
    }

    /**
     * imprimi na tela a matriz do jogo
     */

    static void imprimirJogo() {

        //como no console não é possivel voltar para a linha de cima, é preciso imprimir linha por linha Ex:

        //para imprimir os seguintes CaixaNumeros     ┌─────┐  ┌─────┐  ┌─────┐  ┌─────┐
        //                                            │  2  │  │  8  │  │  16 │  │  4  │
        //                                            └─────┘  └─────┘  └─────┘  └─────┘


        //é preciso primeiro pegar a parte de cima de cada um e concatenar com a parte de cima do CaixoNumero posterior

        // ┌─────┐ + ┌─────┐ + ┌─────┐ + ┌─────┐ <- primeira impressão

        // │  2  │ + │  8  │ + │  16 │ + │  4  │ <- segunda impressão

        // └─────┘ + └─────┘ + └─────┘ + └─────┘ <- terceira impressão

        //nessa String "a" ficarão armazenadas as partes de cada CaixaNúmero
        String a = "";
        //variaveis respossáveis por separar os CaixaNumeros
        int inicioSubString = 0;
        int finalSubString = 12;


        //   sem cor  │   com cor
        //   ┌─────┐  "\u001B[33m┌─────┐"         cada objeto CaixaNumero possui 36 caracteres, com 3 linhas de 12 caracteres cada
        //   │  16 │  "\u001B[33m│  16 │"         o que é contado como caractere: "\u001B" - é contado como 1 só "[33m┌─────┐" 11 caracteres 11 + 1 = 12
        //   └─────┘  "\u001B[33m└─────┘"

        //como temos que pegar a parte de cima de cada CaixaNumero e transformar tudo em uma única linha, começamos pegando linha por linha
        for (int i = 0; i < matrizDoJogo[0].length; i++) {

            //linha 0:
//                      ┌─────┐  ┌─────┐  ┌─────┐  ┌─────┐
//                      │  2  │  │  8  │  │  2  │  │  4  │
//                      └─────┘  └─────┘  └─────┘  └─────┘

            for (int j = 0; j < 3; j++) {


                //agora pegamos as 3 colunas de cada CaixaNumero, cada CaixaNumero, não a matriz do game
                for (int k = 0; k < matrizDoJogo.length; k++) {

                    // agora pegamos do caractere 0 ao caractere 12 "\u001B[33m┌─────┐" e concatenamos com um espaço para separar e o caractere 0 a 12 do CaixaNumero posterior
                    a += matrizDoJogo[k][i].toString().substring(inicioSubString, finalSubString) + "  ";

                }

                //após pegar do caractere 0 ao 12, é preciso pegar do 12 ao 24, depois do 24 ao 36, então a cada vez que o loop é repetido, é adicionado 12 ao começo e ao final da subString
                inicioSubString += 12;
                finalSubString += 12;
                //impressão do caractere 0 ao 12, depois 12 ao 24 na linha de baixo e 24 ao 36 na linha de baixo
                System.out.println(a);
                //a cada loop a variável "a" é limpa para poder armazenar a nova linha de colunas de CaixaNumeros
                a = "";

            }

            //Após acabar de imprimir uma linha da matrizDojogo, o processo se repete para imprimir as linhas seguintes

            inicioSubString = 0;
            finalSubString = 12;

        }

    }


    /**
     * @param i variavel
     * @return variavel + 1 ou variavel - 1, resposável inverter um for
     */
    static int tiraUm(int i) {
        return ordem ? i - 1 : i + 1;

    }
}
