import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class App {
    static final int[] tamanhosTesteGrande =  { 31_250_000, 62_500_000, 125_000_000, 250_000_000, 500_000_000 };
    static final int[] tamanhosTesteMedio =   {     12_500,     25_000,      50_000,     100_000,     200_000 };
    static final int[] tamanhosTestePequeno = {          3,          6,          12,          24,          48 };
    static Random aleatorio = new Random();
    static long operacoes;
    static double nanoToMilli = 1.0/1_000_000;
    

    /**
     * Gerador de vetores aleatórios de tamanho pré-definido. 
     * @param tamanho Tamanho do vetor a ser criado.
     * @return Vetor com dados aleatórios, com valores entre 1 e (tamanho/2), desordenado.
     */
    static int[] gerarVetor(int tamanho){
        int[] vetor = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = aleatorio.nextInt(1, tamanho/2);
        }
        return vetor;        
    }

    /**
     * Gerador de vetores de objetos do tipo Integer aleatórios de tamanho pré-definido. 
     * @param tamanho Tamanho do vetor a ser criado.
     * @return Vetor de Objetos Integer com dados aleatórios, com valores entre 1 e (tamanho/2), desordenado.
     */
    static Integer[] gerarVetorObjetos(int tamanho) {
        Integer[] vetor = new Integer[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = aleatorio.nextInt(1, 10 * tamanho);
        }
        return vetor;
    }


    public static void main(String[] args) {

        System.out.println("Escolha qual metodo de Ordenação vc quer:");
                System.out.println("1-BubbleSort");
                System.out.println("2-InsertSort");
                System.out.println("3-SelectionSort");
                System.out.println("4-MergeSort");
                Scanner leitor = new Scanner(System.in);
                int x = leitor.nextInt();

         switch (x) {
            case 1:


        //Bolha

        int tam = 20;
        Integer[] vetor = gerarVetorObjetos(tam);

        BubbleSort<Integer> bolha = new BubbleSort<>();

        Integer[] vetorOrdenadoBolha = bolha.ordenar(vetor);

        System.out.println("\nVetor ordenado método Bolha:");
        System.out.println("Comparações: " + bolha.getComparacoes());
        System.out.println("Movimentações: " + bolha.getMovimentacoes());
        System.out.println("Tempo de ordenação (ms): " + bolha.getTempoOrdenacao());
        break;

        case 2:
        
         //Insert

        Integer[] vetorInsert = gerarVetorObjetos(tamanhosTesteMedio);
        InsertionSort<Integer> insert = new InsertionSort<>();
        Integer[] vetorOrdenadoinsert = insert.ordenar(vetorInsert);

        System.out.println("\nVetor ordenado método Insertion:");
        System.out.println("Comparações: " + insert.getComparacoes());
        System.out.println("Movimentações: " + insert.getMovimentacoes());
        System.out.println("Tempo de ordenação (ms): " + insert.getTempoOrdenacao());
        break;

        case 3:

        /*
        --Selection--
         */

        Integer[] vetorSelection = gerarVetorObjetos(tamanhosTestePequeno);
        SelectionSort<Integer> selection = new SelectionSort<>();
        Integer[] vetorOrdenadoselection = insert.ordenar(vetorSelection);

        System.out.println("\nVetor ordenado método Selection:");
        System.out.println("Comparações: " + selection.getComparacoes());
        System.out.println("Movimentações: " + selection.getMovimentacoes());
        System.out.println("Tempo de ordenação (ms): " + selection.getTempoOrdenacao());
        break;
        // Merge
        case 4:
        Integer[] vetorMerge = gerarVetorObjetos(tamanhosTestePequeno);
        MergeSortSort<Integer> Merge = new MergeSort<>();
        Integer[] vetorOrdenadoselection = merge.ordenar(vetorMerge);

        System.out.println("\nVetor ordenado método Merge:");
        System.out.println("Comparações: " + merge.getComparacoes());
        System.out.println("Movimentações: " + merge.getMovimentacoes());
        System.out.println("Tempo de ordenação (ms): " + merge.getTempoOrdenacao());
         break;
    
    }

        //Bolha


        

        /* TO DO
        *Fazer a implementacao do restante do main para a ordenacao 
        *  com os algoritmos InsertionSort e SelectionSort
        */

        
        


        // Merge

        Integer[] vetorMerge = gerarVetorObjetos(tamanhosTestePequeno);
        MergeSortSort<Integer> Merge = new MergeSort<>();
        Integer[] vetorOrdenadoselection = merge.ordenar(vetorMerge);

        System.out.println("\nVetor ordenado método Merge:");
        System.out.println("Comparações: " + merge.getComparacoes());
        System.out.println("Movimentações: " + merge.getMovimentacoes());
        System.out.println("Tempo de ordenação (ms): " + merge.getTempoOrdenacao());
    }
}
