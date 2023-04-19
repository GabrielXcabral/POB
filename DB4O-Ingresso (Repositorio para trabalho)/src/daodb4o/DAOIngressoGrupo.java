package daodb4o;

import java.util.List;

import com.db4o.query.Query;

import modelo.IngressoGrupo;

public class DAOIngressoGrupo extends DAO<IngressoGrupo>{
	
	public IngressoGrupo read (Object chave){
		return null;
	}
	
	public List<IngressoGrupo> ingressosgrupodojogo (int id) {
		Query q;
		q = manager.query();
		q.constrain(IngressoGrupo.class);
		q.descend("jogos").descend("id").constrain(id);
		List<IngressoGrupo> resultados = q.execute();
		if(resultados.size()>0) {
			return resultados;
		}
		return null;
		
		
		
	}

}
