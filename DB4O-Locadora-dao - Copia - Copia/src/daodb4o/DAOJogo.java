package daodb4o;

import java.util.ArrayList;
import java.util.List;

import com.db4o.query.Query;

import modelo.Jogo;

public class DAOJogo extends DAO<Jogo>{	
	
		public void create(Jogo obj) {
			int id = super.gerarId(); //Gera um id para o Jogo (.gerarId vem da classe DAO)
			obj.setId(id); //Seta o id gerado no jogo criado
			manager.store(obj);
		}

		public Jogo read(Object chave) {
			int id = (int) chave;
			Query q = manager.query();
			q.constrain(Jogo.class);
			q.descend("id").constrain(id);
			List <Jogo> jogoId  = q.execute();
			if(jogoId.size()>0) {
				Jogo j = jogoId.get(0);
				System.out.println(j);
				return j;		
			}			
			
			return null;
		}	

		public ArrayList<Jogo> verificardata (Object chave){
			String data = (String) chave;
			Query q = manager.query();
			q.constrain(Jogo.class);
			q.descend("data").constrain(data);
			List<Jogo> resultado = q.execute();
			ArrayList<Jogo> jogos = new ArrayList<>();
			if(resultado.size()>0){
				jogos.addAll(resultado);
				return jogos;
			}else{
				return null;
			}

			
			
		}
}
