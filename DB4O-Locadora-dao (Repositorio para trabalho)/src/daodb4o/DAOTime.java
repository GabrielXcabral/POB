package daodb4o;

import java.util.List;

import com.db4o.query.Query;

import modelo.Time;

public class DAOTime extends DAO<Time>{
	
	public Time read(Object chave) {
		String nome =  (String) chave;
		Query q = manager.query();
		q.constrain(Time.class);
		q.descend("nome").constrain(nome);
		List<Time> time = q.execute();
		if(time.size() > 0) {
			Time t = time.get(0);
			//System.out.println(t);
			return t;
		}else {
			return null;
		}	
	}
	
	public List<Time> times(){
		Query q;
		q = manager.query();
		q.constrain(Time.class);
		q.descend("nome").orderAscending();
		List<Time> resultado = q.execute();
		return resultado;
	}
	
	public List<Time> consultarorigens (String origem){
		Query q;
		q = manager.query();
		q.constrain(Time.class);
		q.descend("origem").constrain(origem);
		List<Time> resultado = q.execute();
		return resultado;
	}
	
	public List<Time> time (String time){
		Query q;
		q = manager.query();
		q.constrain(Time.class);
		q.descend("nome").constrain(time);
		List<Time> resultado = q.execute();
		if(resultado.size() > 0) {
			return resultado;
		}else {
			return null;
		}
	}
	
	
	
	
	
	
}
