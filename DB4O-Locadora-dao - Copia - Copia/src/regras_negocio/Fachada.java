package regras_negocio;

import java.util.ArrayList;
import java.util.List;
//import java.util.List;
import java.util.Random;

import daodb4o.DAO;
import daodb4o.DAOIngresso;
import daodb4o.DAOIngressoGrupo;
import daodb4o.DAOIngressoIndividual;
import daodb4o.DAOJogo;
import daodb4o.DAOTime;
import modelo.Ingresso;
//import daodb4o.DAOUsuario;
import modelo.IngressoGrupo;
import modelo.IngressoIndividual;
import modelo.Jogo;
import modelo.Time;
import modelo.Usuario;

public class Fachada {

	private Fachada() {}
	//private static DAOUsuario daousuario = new DAOUsuario(); 
	
	private static DAOTime daotime = new DAOTime(); 
	private static DAOJogo daojogo = new DAOJogo(); 
	private static DAOIngresso daoingresso = new DAOIngresso();
	private static DAOIngressoIndividual daoingressoindividual = new DAOIngressoIndividual(); 
	private static DAOIngressoGrupo daoingressogrupo = new DAOIngressoGrupo();
	public static Usuario logado;

	public static void inicializar(){
		DAO.open();
	}
	public static void finalizar(){
		DAO.close();
	}

	public static List<Ingresso> listarIngressos() {
		DAO.open();
		List<Ingresso> resultado = daoingresso.readAll();
		System.out.println(resultado);
		//retorna todos os ingressos 
		return resultado;
	}

	public static Time 	criarTime(String nome, String origem) throws Exception {
		DAO.begin();
		Time verificartime = daotime.read(nome);
		
		if(verificartime != null)
			throw new Exception("Time: " + nome + " j� foi cadastrado");
		
		//verificar regras de negocio
		//criar o time
		Time time = new Time(nome,origem);
		
		//gravar time no banco
		daotime.create(time);
		DAO.commit();
		return time;
	}
	
	public static Jogo 	criarJogo(String data, String local, int estoque, double preco, String nometime1, String nometime2)  throws Exception {
		DAO.begin();
		
		//localizar time1 e time2
		Time time1 = daotime.read(nometime1);
		Time time2 = daotime.read(nometime2);
		ArrayList<Jogo> verificarjogo = daojogo.verificardata(data);
		
		//verificar regras de negocio
		if(time1 == null || time2 == null) {
			if(time1 == null) {
				throw new Exception(nometime1 + " => N�o Existe");
				}else {
					throw new Exception(nometime2 + " => N�o Existe");
				}			
		}	
		
		//Um jogo n�o pode ter dois times iguais
		if (time1.getNome() == time2.getNome()) {
			throw new Exception ("N�o pode ter dois times iguais");
		}

		if(verificarjogo == null){
			//criar jogo 
			Jogo jogo = new Jogo(data, local, estoque, preco);
		

			//relacionar o jogo com os times e vice-versa 
			jogo.setTime1(time1);
			jogo.setTime2(time2);
			time1.adicionar(jogo);
			time2.adicionar(jogo);
		
		
			//gravar jogo no banco
			daojogo.create(jogo);
			daotime.update(time1);
			daotime.update(time2);
			DAO.commit();
			System.out.print(jogo);
			return jogo;
		}else{
			for(Jogo j : verificarjogo){
				String nome1 = j.getTime1().getNome();
				String nome2 = j.getTime2().getNome();
				
				if(nome1.equals(nometime1) || nome1.equals(nometime2)){
					throw new Exception(nome1 + " Já possui jogo nessa data");
				}
				if(nome2.equals(nometime2) || nome2.equals(nometime1)){
					throw new Exception(nome2 + " Já possui jogo nessa data");
				}
			}
			
			//criar jogo 
			Jogo jogo = new Jogo(data, local, estoque, preco);
			
	
			//relacionar o jogo com os times e vice-versa 
			jogo.setTime1(time1);
			jogo.setTime2(time2);
			time1.adicionar(jogo);
			time2.adicionar(jogo);
			
			
			//gravar jogo no banco
			daojogo.create(jogo);
			daotime.update(time1);
			daotime.update(time2);
			DAO.commit();
			System.out.print(jogo);
			return jogo;

		}
	}

    public static IngressoIndividual criarIngressoIndividual(int id) throws Exception{		
		DAO.begin();
		ArrayList <Integer> codigosinseridos = new ArrayList<>();
		//verificar regras de negocio
		List<Ingresso> ingressos = listarIngressos();

		//System.out.println(ingressos);

		Jogo jogo = daojogo.read(id);
		if(jogo == null)
			throw new Exception ("ID: " + id + " => Invalido" + "\n" +
		"JOGO NÃO EXISTE...");
		
		/*for(Ingresso i : ingressos){
			codigosinseridos.add(i.getCodigo());
		}*/
		
		
		Ingresso ing = daoingresso.read(id);
		
		//gerar codigo aleatório
		//verificar unicididade do codigo no sistema; 
		int codigo;
		do {
			codigo = new Random().nextInt(10);
			
		} while (codigosinseridos.contains(codigo));
		
		codigosinseridos.add(codigo);
		
		//criar o ingresso individual 
		IngressoIndividual ingresso = new IngressoIndividual(codigo);

		//relacionar este ingresso com o jogo e vice-versa
		ingresso.setJogo(jogo);
		jogo.adicionar(ingresso);
		jogo.setEstoque(jogo.getEstoque()-1);

		//gravar ingresso no banco
		daoingressoindividual.create(ingresso);
		System.out.println(ingresso.toString());
		daojogo.update(jogo);
		DAO.commit();
		return ingresso;
	}

	public static IngressoGrupo	criarIngressoGrupo(int[] ids) throws Exception{
		DAO.begin();
		ArrayList <Integer> codigosinseridos = new ArrayList<>();
		//verificar regras de negocio
		/*List<Ingresso> ingressos = listarIngressos();
		for(Ingresso i : ingressos){
			codigosinseridos.add(i.getCodigo());
		}*/
		
		ArrayList<Jogo> jogos = new ArrayList<>();
		
		//verificar regras de negocio
		int[] idsJogos = ids;
		for(int i=0; i<idsJogos.length; i++) {
			int id = idsJogos[i];
			Jogo id_ = daojogo.read(id);
			if(id_ == null) {
				jogos.clear();
				throw new Exception ("ID: " + id  + " n�o existe...");	
			}
			jogos.add(id_);					
		}
		
		//gerar codigo aleat�rio 
		int codigo;
		Ingresso ing = null;
		do {
			codigo = new Random().nextInt(1000000);
			ing = daoingresso.read(codigo);
			//verificar unicididade no sistema 
		}while(ing != null);
		
		//codigosinseridos.add(codigo);
		
		
		//criar o ingresso grupo 
		IngressoGrupo ingresso = new IngressoGrupo(codigo);
		
		
		
		
		//relacionar este ingresso com os jogos indicados e vice-versa
		
		for (Jogo j: jogos) {
			j.adicionar(ingresso);
			j.setEstoque(j.getEstoque()-1);
			ingresso.adicionar(j);
			daojogo.update(j);
			daoingressogrupo.update(ingresso);
			
		}
		
		//gravar ingresso no banco
		daoingressogrupo.create(ingresso);
		DAO.commit();
		return ingresso;
	}
	
	public static void	apagarIngresso(int codigo) throws Exception {
		DAO.begin();
		//o codigo refere-se a ingresso individual ou grupo
		//verificar regras de negocio
		//remover o relacionamento entre o ingresso e o(s) jogo(s) ligados a ele

		
		Ingresso ingresso = daoingresso.read(codigo);
		if (ingresso instanceof IngressoGrupo grupo) {
			ArrayList<Jogo> jogos = grupo.getJogos();
			for (Jogo j : jogos) {
				j.remover(grupo);
				j.setEstoque(j.getEstoque()+1);
			}
		}
		else 
			if (ingresso instanceof IngressoIndividual individuo) {
				Jogo jogo = individuo.getJogo();
				jogo.remover(individuo);
				jogo.setEstoque(jogo.getEstoque()+1);
			}

		//apagar ingresso no banco

		DAO.commit();
	}
	
	public static void	apagarTime(String nome) throws Exception {
		DAO.begin();
		//verificar regras de negocio
		//Um time não poderá ser excluído se possuir jogos
		
		//apagar time no banco
		Time time = daotime.read(nome);
		if(time !=null)
		{
			daotime.delete(time);
		};
		
		
		DAO.commit();
	}

	public static void 	apagarJogo(int id) throws Exception{
		DAO.begin();
		//verificar regras de negocio
		//Um jogo não poderá ser excluído se possuir ingressos

		//apagar jogo no banco
		Jogo jogo = daojogo.read(id);
		if(jogo != null) {
			daojogo.delete(jogo);
		};
		DAO.commit();
	}

	

}
