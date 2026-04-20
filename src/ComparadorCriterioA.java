import java.util.Comparator;

/**
 * Critério A - Valor Final do Pedido (crescente).
 * Desempate 1: Volume Total de Itens (quantProdutos).
 * Desempate 2: Código Identificador do primeiro item do pedido.
 */
public class ComparadorCriterioA implements Comparator<Pedido> {

    @Override
    public int compare(Pedido o1, Pedido o2) {
        if(o1.valorFinal()== o2.valorFinal() && o1.getTotalItens()== o2.getTotalItens()){
            return(o1.getIdPrimeiroProduto()- o2.getIdPrimeiroProduto());
        }else if(o1.valorFinal()== o2.valorFinal()){
            return(o1.getTotalItens()-o2.getTotalItens());
        }else{
        if(o1.valorFinal()>o2.valorFinal())
            return 1;
        else
        return -1;
    }
        //Sua lógica de comparação aqui
    }
}
