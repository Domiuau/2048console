import java.util.Random;
import java.util.Scanner;

public class Game2048 {

    //Variáveis globais

    //Vetor responsável pela matriz do jogo, todos os valores do game ficarão salvos aqui
    static CaixaNumero[][] matrizDoJogo;
    //Variável responsável por armazenar o lado em que o jogo será "arrastado" após o jogador escolher entre W A S D
    static String lado;

    //Variável responsável por definir a ordem de acordo com a jogada, Ex: caso o usuário jogue "A",
    //a matriz começará a ser arrastada a partir do lado esquerdo
    //e caso o usuário jogue "D", a matriz começará a ser arrastada a partir do lado direito
    static boolean ordem;

    public static void main(String[] args) {   // ┌─│ └┘ ┐
        //Declaração do Scanner para a entrada de dados, no caso W A S D
        Scanner scanner = new Scanner(System.in);
        //Instruções básicas de como jogar
        System.out.println("  .-----.   .----.      .---.    .-----.   \n" +
                " / ,-.   \\ /  ..  \\    / .  |   /  .-.  \\  \n" +
                " '-'  |  |.  /  \\  .  / /|  |  |   \\_.' /  \n" +
                "    .'  / |  |  '  | / / |  |_  /  .-. '.  \n" +
                "  .'  /__ '  \\  /  '/  '-'    ||  |   |  | \n" +
                " |       | \\  `'  / `----|  |-' \\  '-'  /  \n" +
                " `-------'  `---''       `--'    `----''   ");
        System.out.println("Mande W A S D para mover o jogo para cima, para esquerda, para baixo e para a direita respectivamente");
        //Aqui é solicitado o tamanho do jogo, ou tamanho da matriz, que pode ser 1x1, 4x4, 8x9, 90x20, 100x1000...
        System.out.println("Insira o tamanho do jogo (o padrão é 4x4)");
        //Declaração do random usado para determinar onde será colocado um número aleatoriamente em um espaço vazio após uma jogada
        Random random = new Random();
        //O tamanho inserido pelo usuário entra como String, depois é dividida por "x" e seu valor é inserido em um vetor, então caso
        // o usuário coloque 5x6, será criado um vetor com 2 posições, sendo o [0] = 5 e [1] = 6
        String[] tamanhoDoJogo = scanner.next().replace(" ", "").split("x");

        //Aqui a matriz do jogo é criada, onde o maior número é sempre a fileira (x), então 7x5 continua 7x5 e 5x7 vira 7x5
        matrizDoJogo = Integer.parseInt(tamanhoDoJogo[0]) > Integer.parseInt(tamanhoDoJogo[1]) ?
                new CaixaNumero[Integer.parseInt(tamanhoDoJogo[0])][Integer.parseInt(tamanhoDoJogo[1])] :
                new CaixaNumero[Integer.parseInt(tamanhoDoJogo[1])][Integer.parseInt(tamanhoDoJogo[0])];

        //A matriz é completamente preenchida pelo objeto "CaixaNumero", responsável por armazenar o número da posição e gerar seu desenho e sua cor
        for (int i = 0; i < matrizDoJogo.length; i++) {
            for (int j = 0; j < matrizDoJogo[i].length; j++) {

                matrizDoJogo[i][j] = new CaixaNumero();
            }
        }

        //Aqui uma posição da matriz é sorteada para receber o número inicial, que também é gerado aleatoriamente, sendo sorteado um número de 0 a 2
        //caso seja 0 ou 2, o número gerado recebe 2, e caso seja 1, esse número recebe 4, ou seja, 66,6% de chance de gerar um 2 e 33% de gerar um 4
        matrizDoJogo[random.nextInt(matrizDoJogo.length)][random.nextInt(matrizDoJogo[0].length)].setNumero(random.nextInt(3) == 1 ? 4 : 2);

        //Variável responsável por definir se após uma jogada será gerado um número em um espaço vazio aleatório ou não
        boolean surgirNumero = false;
        //ela existe por conta de casos mostrados como no exemplo abaixo:

        //Sem isso, quando o usuário jogasse "A" nessa situação, nenhum número seria movido e apareceria um número em uma posição aleatória, o que não é o correto de acontecer, pois não foi uma jogada válida


        //Indicação de que a primeira rodada irá começar
        System.out.println("\u001B[m------------------------------------------------------------------------------");
        System.out.println("Rodada número 1");

        //Impressão da matriz do jogo usando o método "imprimirJogo()" que será explicado mais tarde
        imprimirJogo();

        //Agora que a matriz já foi gerada e preenchida com objetos CaixaNumero e já foi definido o
        //número inicial, entramos no loop principal do jogo, onde toda a matriz é manipulada
        for (int w = 1; true; ) {

            //Nesse momento é solicitada ao usuário a jogada W - cima A - esquerda S - baixo D - direita
            lado = scanner.next();
            //Essa variável é responsável por mudar a ordem dos loops abaixo, ou definindo por onde a matriz começará a ser arrastada
            ordem = lado.equals("s") || lado.equals("d");

            //for responsável por arrastar os objetos vezes o suficiente para ir de uma ponta a outra caso seja necessário
            //Ternário utilizado para pegar o maior entre a fileira e coluna, inserido e armazenado anteriormente pelo usuário Ex: "3x5", nesse caso é passado o 5
            //então os números são passados para o lado (5 - 1) vezes (4), conforme o exemplo abaixo:

            for (int t = 0; Integer.parseInt(tamanhoDoJogo[0]) > Integer.parseInt(tamanhoDoJogo[1]) ?
                    t < Integer.parseInt(tamanhoDoJogo[0]) - 1 : t < Integer.parseInt(tamanhoDoJogo[1]) - 1; t++) {

                //Dentro da criação dos fors abaixo, é utilizado um operador ternário, para definir se será um for normal ou um for reverso
                //caso seja um for normal, o jogo começará pela esquerda ou por cima
                //caso seja um for reverso, o jogo começará pela direita ou por baixo
                //segue abaixo exemplos do porquê a direção de onde o jogo começa muda


                //Imagine essa situação e o usuário jogue "D"


                //Isso aconteceria caso o for não fosse invertido


                //Quando o correto é acontecer isso

                //Fileira
                for (int i = ordem ? matrizDoJogo.length - 1 : 0; ordem ? i >= 0 : i < matrizDoJogo.length; i = tiraUm(i)) {

                    //Coluna
                    for (int j = ordem ? matrizDoJogo[i].length - 1 : 0; ordem ? j >= 0 : j < matrizDoJogo[i].length; j = tiraUm(j)) {

                        //matrizDoJogo[i][j] = número que será movido
                        //jogada(i, j) = para onde o número será movido, Exemplo:


                        //128 é o número a ser movido
                        //caso a jogada seja "A", o 4 é o número que o 128 irá ser comparado, e caso seja "D", o 0 ao lado será o número a ser comparado ao 128
                        //nesse caso, a comparação de 128 com o 4, são ambos maiores do que 0 e diferentes, então nada acontecerá
                        //agora a comparação com o 0, como ambos são diferentes e possui um 0, eles trocarão de valor

                        //Compara se o número que será movido é maior do que 0, caso não for, não acontece nada, o 0 não precisa mudar de posição
                        if (matrizDoJogo[i][j].getNumero() > 0) {

                            try {

                                //Caso o número tenha chegado até aqui, ele é maior do que 0, então pode ser movido
                                //e caso esse número seja diferente do número no lugar de para onde ele irá, ele será movido caso sua futura posição esteja vazia (tenha um número 0)
                                if (jogada(i, j).getNumero() != matrizDoJogo[i][j].getNumero()) {

                                    //Compara se o espaço que o número a ser movido irá contém um 0
                                    //caso tenha, o espaço vazio (espaço com 0) receberá o número a ser movido e o espaço que contém o número que foi movido é esvaziado (recebe 0)
                                    if (jogada(i, j).getNumero() == 0) {
                                        jogada(i, j).setNumero(matrizDoJogo[i][j].getNumero());
                                        matrizDoJogo[i][j].setNumero(0);
                                        //Declara que após o final da rodada, um número pode ser gerado em um espaço vazio aleatório, pois houve movimento na matriz, logo foi uma jogava válida
                                        surgirNumero = true;
                                    }
                                } else { // <-- agora caso o número a ser movido e o número contido no espaço para onde ele irá não são diferentes, então eles são iguais, e para terem chegado até
                                    //aqui, ambos são maiores do que 0
                                    //o número no espaço para onde o número irá é multiplicado por 2, e o espaço onde o número que está movido está recebe 0

                                    if (jogada(i, j).isSomavel() && matrizDoJogo[i][j].isSomavel()) {
                                        jogada(i, j).setNumero(jogada(i, j).getNumero() * 2);
                                        matrizDoJogo[i][j].setNumero(0);
                                        //o espaço que acabou de receber um número (acabou de se tornar um número maior, dobrando seu tamanho), é definido como "não editavel"
                                        //para evitar o evento relatado no exemplo abaixo

                                        //Imagine a seguinte situação

                                        //Ao usuário jogar "D" aqui, isso acontecerá

                                        //O correto é acontecer isso, pois o mesmo espaço não pode somar 2 vezes na mesma jogada

                                        jogada(i, j).setSomavel(false);

                                    }
                                    //Também foi uma jogada válida, então é liberada a criação de um número em um espaço vazio após o termino da rodada
                                    surgirNumero = true;

                                }

                                //está tudo dentro de um bloco try catch para evitar Exceptions, sem ele iria acontecer coisas tipo matrizDoJogo[-1][0], o que iria lançar uma exceção e finalizar o programa

                            } catch (Exception e) {

                            }

                        }

                    }

                }

            }

            //caso tenha acontecido uma jogada válida (algo se moveu na matriz após uma jogada) a variável surgirNumero será verdadeira
            if (surgirNumero == true) {

                //Após entrar no bloco de execução, ela já recebe falso, deixando a variável esperando outra jogada válida para que possa receber verdadeiro e chegar até aqui novamente
                surgirNumero = false;

                //Aqui começa o processo para definir quais são os lugares válidos para receber um número, no caso são todas aquelas posições que estão vazias (armazenando 0) na matriz

                //Vetor onde esses lugares serão armazenados
                CaixaNumero[] matrizDoJogoAjuste = new CaixaNumero[1];
                int k = 0;

                //percorre a coluna
                for (int i = 0; i < matrizDoJogo[0].length; i++) {
                    //percorre a fileira
                    for (int j = 0; j < matrizDoJogo.length; j++) {

                        //caso a posição de [j][i] na matriz do jogo seja = 0, o objeto CaixaNumero presente na posição da matriz é adicionado ao vetor matrizDoJogoAjuste
                        if (matrizDoJogo[j][i].getNumero() == 0) {
                            //um novo vetor é criado com um espaço a mais para receber esse objeto CaixaNumero = 0
                            CaixaNumero[] ajuste = new CaixaNumero[k + 1];

                            //o vetor "ajuste" recebe todos os objetos armazenados no vetor matrizDoJogoAjuste, mas como ele tem um espaço a mais, o último índice fica vazio
                            for (int l = 0; l < matrizDoJogoAjuste.length; l++) {
                                ajuste[l] = matrizDoJogoAjuste[l];
                            }

                            k++;
                            //o último índice é preenchido com o objeto CaixaNumero presente na posição [j][i] da matriz
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

            //Indica para o usuário o número da rodada que ele está sendo "w" esse número
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
     * @param i posição da fileira na matriz (x do número a ser movido)
     * @param j posição da coluna na matriz (y do número a ser movido)
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
            //caso o usuário insira "w", o método retornará [i][j-1], ou seja i = 2 e j = 0, onde o 2 entre chaves está presente
            //caso o usuário insira "d", o método retornará [i + 1][j], ou seja i = 3 e j = 1, onde o 8 entre colchetes está presente
            //o que representa a posição que o número a ser movido tentará se deslocar


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

        //como no console não é possível voltar para a linha de cima, é preciso imprimir linha por linha, Exemplo:

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
        //   ┌─────┐  "\u001B[33m┌─────┐"
        //   │  16 │  "\u001B[33m│  16 │"
        //   └─────┘  "\u001B[33m└─────┘"

        //Cada objeto CaixaNumero possui 36 caracteres, com 3 linhas de 12 caracteres cada
        //o que é contado como caractere: "\u001B" - é contado como 1 só "[33m┌─────┐" 11 caracteres 11 + 1 = 12

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
     * @param i variável
     * @return variável + 1 ou variável - 1, responsável inverter um for
     */
    static int tiraUm(int i) {
        return ordem ? i - 1 : i + 1;

    }
}
