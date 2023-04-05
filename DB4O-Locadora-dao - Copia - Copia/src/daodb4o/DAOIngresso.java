package daodb4o;

import java.util.List;

import com.db4o.query.Query;

import modelo.Ingresso;
import modelo.IngressoIndividual;

public class DAOIngresso extends DAO<Ingresso> {

    public Ingresso read(Object chave){
    	int codigo = (int) chave;
		Query q = manager.query();
		q.constrain(Ingresso.class);
		q.descend("codigo").constrain(codigo);
		List <Ingresso> resultado = q.execute();
		if(resultado.size()>0) {
			Ingresso i = resultado.get(0);
			return i;
		}
		return null;		
	}
}