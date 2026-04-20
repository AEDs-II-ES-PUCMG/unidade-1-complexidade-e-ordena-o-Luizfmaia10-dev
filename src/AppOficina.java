
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * MIT License
 *
 * Copyright(c) 2022-25 João Caram <caram@pucminas.br>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

public class AppOficina {

    static final int MAX_PEDIDOS = 100;
    static Produto[] produtos;
    static int quantProdutos = 0;
    static String nomeArquivoDados = "produtos.txt";
    static Produto[] produtosPorId;
    static Produto[] produtosPorDescricao;
    static IOrdenador<Produto> ordenador;

    // #region utilidades
    static Scanner teclado;

    

    static <T extends Number> T lerNumero(String mensagem, Class<T> classe) {
        System.out.print(mensagem + ": ");
        T valor;
        try {
            valor = classe.getConstructor(String.class).newInstance(teclado.nextLine());
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            return null;
        }
        return valor;
    }

    static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void pausa() {
        System.out.println("Tecle Enter para continuar.");
        teclado.nextLine();
    }

    static void cabecalho() {
        limparTela();
        System.out.println("XULAMBS COMÉRCIO DE COISINHAS v0.2\n================");
    }
    

    static int exibirMenuPrincipal() {
        cabecalho();
        System.out.println("1 - Procurar produto");
        System.out.println("2 - Filtrar produtos por preço máximo");
        System.out.println("3 - Ordenar produtos");
        System.out.println("4 - Embaralhar produtos");
        System.out.println("5 - Listar produtos");
        System.out.println("0 - Finalizar");
       
        return lerNumero("Digite sua opção", Integer.class);
    }

    static int exibirMenuOrdenadores() {
        cabecalho();
        System.out.println("1 - Bolha");
        System.out.println("2 - Inserção");
        System.out.println("3 - Seleção");
        System.out.println("4 - Mergesort");
        System.out.println("0 - Finalizar");
       
        return lerNumero("Digite sua opção", Integer.class);
    }

    static int exibirMenuComparadores() {
        cabecalho();
        System.out.println("1 - Padrão");
        System.out.println("2 - Por código");
        
        return lerNumero("Digite sua opção", Integer.class);
    }

    // #endregion
    static Produto[] carregarProdutos(String nomeArquivo){
        Scanner dados;
        Produto[] dadosCarregados;
        try{
            dados = new Scanner(new File(nomeArquivo));
            int tamanho = Integer.parseInt(dados.nextLine());
            
            dadosCarregados = new Produto[tamanho];
            while (dados.hasNextLine()) {
                Produto novoProduto = Produto.criarDoTexto(dados.nextLine());
                dadosCarregados[quantProdutos] = novoProduto;
                quantProdutos++;
            }
            dados.close();
        }catch (FileNotFoundException fex){
            System.out.println("Arquivo não encontrado. Produtos não carregados");
            dadosCarregados = null;
        }
        return dadosCarregados;

        //aaa
    }


    static Produto localizarProduto() {
        cabecalho();
        System.out.println("Localizar por:");
        System.out.println("1 - Código (ID)");
        System.out.println("2 - Descrição");
        int tipo = Integer.parseInt(teclado.nextLine());

        if (tipo == 1) {
            System.out.print("Digite o ID: ");
            int id = Integer.parseInt(teclado.nextLine());
            // Criamos um "produto dummy" apenas com o ID para comparar
            Produto dummy = new ProdutoNaoPerecivel("Busca", 1, 1) {
                @Override public int getId() { return id; }
                @Override public int hashCode() { return id; }
            };

            int pos = pesquisaBinaria(produtosPorId, dummy, new ComparadorPorCodigo());
            return (pos != -1) ? produtosPorId[pos] : null;

        } else if (tipo == 2) {
            System.out.print("Digite a Descrição: ");
            String desc = teclado.nextLine();
            Produto dummy = new ProdutoNaoPerecivel(desc, 1, 1);

            int pos = pesquisaBinaria(produtosPorDescricao, dummy,
                    (p1, p2) -> p1.getDescricao().compareToIgnoreCase(p2.getDescricao()));
            return (pos != -1) ? produtosPorDescricao[pos] : null;
        }

        return null;
    }

    private static void mostrarProduto(Produto produto) {
        cabecalho();
        String mensagem = "Dados inválidos";
        
        if(produto!=null){
            mensagem = String.format("Dados do produto:\n%s", produto);            
        }
        
        System.out.println(mensagem);
    }

    private static void filtrarPorPrecoMaximo(){
        cabecalho();
        System.out.println("Filtrando por valor máximo:");
        double valor = lerNumero("valor", Double.class);
        StringBuilder relatorio = new StringBuilder();
        for (int i = 0; i < quantProdutos; i++) {
            if(produtos[i].valorDeVenda() < valor)
            relatorio.append(produtos[i]+"\n");
        }
        System.out.println(relatorio.toString());
    }

    static void ordenarProdutos() {
        cabecalho();
        IOrdenador<Produto> ordenador = null; // Criamos a variável local

        int opcao = exibirMenuOrdenadores();
        switch (opcao) {
            case 1 -> ordenador = new Bubblesort<>();
            case 2 -> ordenador = new SelectionSort<>(); // Segui a ordem comum, ajuste se necessário
            case 3 -> ordenador = new InsertSort<>();
            case 4 -> ordenador = new Mergesort<>();
            case 0 -> { return; } // Caso queira sair do menu
        }

        if (ordenador != null) {
            // 1. Executa a ordenação e guarda o resultado em uma cópia
            Produto[] copiaOrdenada = ordenador.ordenar(produtos);

            // 2. Exibe os relatórios de desempenho (os gets que estão no seu IOrdenador)
            System.out.println("\n--- Relatório de Ordenação ---");
            System.out.println("Tempo: " + ordenador.getTempoOrdenacao() + "ms");
            System.out.println("Comparações: " + ordenador.getComparacoes());
            System.out.println("Movimentações: " + ordenador.getMovimentacoes());

            // 3. Pergunta se o usuário quer que essa ordem vire a ordem oficial
            verificarSubstituicao(produtos, copiaOrdenada);
        }
    }
    static void embaralharProdutos(){
        Collections.shuffle(Arrays.asList(produtos));
    }

    static void verificarSubstituicao(Produto[] dadosOriginais, Produto[] copiaDados){
        cabecalho();
        System.out.print("Deseja sobrescrever os dados originais pelos ordenados (S/N)?");
        String resposta = teclado.nextLine().toUpperCase();
        if(resposta.equals("S"))
            dadosOriginais = Arrays.copyOf(copiaDados, copiaDados.length);
    }

    static void listarProdutos(){
        cabecalho();
        for (int i = 0; i < quantProdutos; i++) {
            System.out.println(produtos[i]);
        }
    }

    public static void main(String[] args) {
        teclado = new Scanner(System.in);
        
        produtos = carregarProdutos(nomeArquivoDados);
        embaralharProdutos();

        int opcao = -1;
        
        do {
            opcao = exibirMenuPrincipal();
            switch (opcao) {
                case 1 -> mostrarProduto(localizarProduto());
                case 2 -> filtrarPorPrecoMaximo();
                case 3 -> ordenarProdutos();
                case 4 -> embaralharProdutos();
                case 5 -> listarProdutos();
                case 0 -> System.out.println("FLW VLW OBG VLT SMP.");
            }
            pausa();
        }while (opcao != 0);
        teclado.close();
    }
    public static void prepararBuscas() {
        // Cópia para ordenar por ID (usando a ordem natural/Comparable do Produto)
        produtosPorId = Arrays.copyOf(produtos, quantProdutos);
        new Mergesort<Produto>().ordenar(produtosPorId);

        // Cópia para ordenar por Descrição
        produtosPorDescricao = Arrays.copyOf(produtos, quantProdutos);
        new Mergesort<Produto>().ordenar(produtosPorDescricao,
                (p1, p2) -> p1.getDescricao().compareToIgnoreCase(p2.getDescricao()));
    }
    /**
     * Pesquisa binária genérica para encontrar um produto em um vetor ordenado.
     */
    static <T> int pesquisaBinaria(T[] vetor, T chave, java.util.Comparator<T> comparador) {
        int baixo = 0;
        int alto = vetor.length - 1;

        while (baixo <= alto) {
            int meio = (baixo + alto) / 2;
            int comp = comparador.compare(vetor[meio], chave);

            if (comp < 0) baixo = meio + 1;
            else if (comp > 0) alto = meio - 1;
            else return meio; // Encontrou
        }
        return -1; // Não encontrado
    }
}
