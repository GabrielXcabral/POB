package daodb4o;

import java.util.List;

import com.db4o.query.Query;

import modelo.IngressoIndividual;

public class DAOIngressoIndividual extends DAO<IngressoIndividual>{
	
	public IngressoIndividual read (Object chave) {
		int codigo = (int) chave;
		Query q = manager.query();
		q.constrain(IngressoIndividual.class);
		q.descend("codigo").constrain(codigo);
		List <IngressoIndividual> resultado = q.execute();
		if(resultado.size()>0) {
			IngressoIndividual i = resultado.get(0);
			return i;
		}
		return null;		
	}
	
	
	public List<IngressoIndividual> ingressosindividualdojogo (int id) {
		Query q;
		q = manager.query();
		q.constrain(IngressoIndividual.class);
		q.descend("jogo").descend("id").constrain(id);
		List<IngressoIndividual> resultados = q.execute();
		if(resultados.size()>0) {
			return resultados;
		}
		return null;	
	}

}
