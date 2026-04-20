import java.util.Comparator;

/**
 * Critério B - Volume Total de Itens (crescente).
 * Desempate 1: Data do Pedido.
 * Desempate 2: Código Identificador do pedido.
 */
public class ComparadorCriterioB implements Comparator<Pedido> {

    @Override
    public int compare(Pedido o1, Pedido o2) {
         IOrdenator<Pedido> ordenator;
        if(o1.getTotalItens() == o2.getQuantosProdutos()){
            ordenator.setComparador(new comparadorPorData);
        }else if(o1.getTotalItens()>o2.getTotalItens()){
            return 1;
        }else
         return -1;

        //Sua lógica de comparação aqui
    }
}
