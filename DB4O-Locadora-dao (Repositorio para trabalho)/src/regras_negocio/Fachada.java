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
import daodb4o.DAOUsuario;
import modelo.Ingresso;
//import daodb4o.DAOUsuario;
import modelo.IngressoGrupo;
import modelo.IngressoIndividual;
import modelo.Jogo;
import modelo.Time;
import modelo.Usuario;

public class Fachada {

	private Fachada() {}
	private static DAOUsuario daousuario = new DAOUsuario(); 

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
		//System.out.println(resultado);
		//retorna todos os ingressos 
		return resultado;
	}

	public static List<Jogo> listarJogos() {
		//retorna todos os jogos
		DAO.open();
		List<Jogo> resultado = daojogo.readAll();
		//System.out.println(resultado);
		//retorna todos os ingressos 
		return resultado;
	}

	public static List<Time> listarTimes() {
		//retorna todos os jogos
		DAO.open();
		List<Time> resultado = daotime.readAll();
		//System.out.println(resultado);
		//retorna todos os ingressos 
		return resultado;
	}

	public static List<Jogo> listarJogos(String data) throws Exception {
		//retorna todos os jogos
		DAO.open();
		List<Jogo> resultado = daojogo.verificardata(data);
		if(resultado == null) {
			throw new Exception("N�o existe jogo nesta data: "+data);
		}
		//System.out.println(resultado);
		//retorna todos os ingressos 
		return resultado;
	}

	public static Jogo	localizarJogo(int id) throws Exception {
		//retorna o jogo com o id fornecido
		DAO.open();
		Jogo j = daojogo.read(id);
		if(j == null) {
			throw new Exception("Jogo com id: " + id + " n�o existe!!!");
		}

		return j;
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
			//System.out.print(jogo);
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
			//System.out.print(jogo);
			return jogo;

		}
	}

	public static IngressoIndividual criarIngressoIndividual(int id) throws Exception{		
		DAO.begin();
		//ArrayList <Integer> codigosinseridos = new ArrayList<>();
		//verificar regras de negocio
		/*List<Ingresso> ingressos = listarIngressos();
		for(Ingresso i : ingressos){
			codigosinseridos.add(i.getCodigo());
		}*/

		//System.out.println(ingressos);

		Jogo jogo = daojogo.read(id);
		if(jogo == null)
			throw new Exception ("ID: " + id + " => Invalido" + "\n" +
					"JOGO NÃO EXISTE...");


		//gerar codigo aleatório
		//verificar unicididade do codigo no sistema; 

		int codigo;
		Ingresso ing;
		do {
			codigo = new Random().nextInt(10);
			ing = daoingresso.read(codigo);

		} while ((ing != null));
		//codigosinseridos.contains(codigo)
		//codigosinseridos.add(codigo);

		//criar o ingresso individual 
		IngressoIndividual ingresso = new IngressoIndividual(codigo);

		//relacionar este ingresso com o jogo e vice-versa
		ingresso.setJogo(jogo);
		jogo.adicionar(ingresso);
		jogo.setEstoque(jogo.getEstoque()-1);

		//gravar ingresso no banco
		daojogo.update(jogo);
		daoingressoindividual.create(ingresso);
		DAO.commit();
		return ingresso;
	}

	public static IngressoGrupo	criarIngressoGrupo(int[] ids) throws Exception{
		DAO.begin();
		// ArrayList <Integer> codigosinseridos = new ArrayList<>();
		// //verificar regras de negocio
		// List<Ingresso> ingressos = listarIngressos();
		// for(Ingresso i : ingressos){
		// 	codigosinseridos.add(i.getCodigo());
		// }

		ArrayList<Jogo> jogos = new ArrayList<>();

		//verificar regras de negocio -> Verificar se o Jogo Existe
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
		Ingresso ing;
		do {
			codigo = new Random().nextInt(10000);
			ing = daoingresso.read(codigo);
		} while ((ing != null));


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

	public static void 	apagarJogo(int id) throws Exception{
		DAO.begin();
		//verificar regras de negocio
		Jogo jogo = daojogo.read(id);
		if(jogo == null) {
			throw new Exception ("N�o existe nenhum jogo com este id: " + id);
		}

		if(jogo.getIngressos().size() != 0) {
			throw new Exception ("Jogo n�o pode ser excluido! "
					+ "(JOGO POSSUI INGRESSOS VENDIDOS)");
		}

		Time t1 = jogo.getTime1();
		t1.remover(jogo);
		Time t2 = jogo.getTime2();
		t2.remover(jogo);

		//apagar jogo no banco
		daojogo.delete(jogo);
		daotime.update(t1);
		daotime.update(t2);
		DAO.commit();
	}

	public static void	apagarIngresso(int codigo) throws Exception {
		DAO.begin();
		Ingresso ingresso = daoingresso.read(codigo);


		if(ingresso == null) {
			throw new Exception("Codigo: " + codigo+ " de ingresso n�o existe no banco");
		}



		if (ingresso instanceof IngressoGrupo grupo) {
			ArrayList<Jogo> jogos = grupo.getJogos();
			for (Jogo j : jogos) {
				j.remover(ingresso);
				j.setEstoque(j.getEstoque()+1);
				//Atualizar ingresso e jogo no banco
				//daoingressogrupo.delete(grupo);
				daojogo.update(j);

			}
			daoingressogrupo.delete(grupo);
		}
		else 
			if (ingresso instanceof IngressoIndividual individuo) {
				Jogo jogo = individuo.getJogo();
				jogo.remover(ingresso);
				jogo.setEstoque(jogo.getEstoque()+1);
				System.out.println(jogo);
				//Atualizar ingresso e jogo no banco
				daoingressoindividual.delete(individuo);
				daojogo.update(jogo);

			}

		DAO.commit();
	}

	public static void	apagarTime(String nome) throws Exception {
		DAO.begin();
		//verificar regras de negocio
		Time t = daotime.read(nome);
		if(t == null) {
			throw new Exception ("Time: " + nome + " n�o foi criado!!!");
		}

		//Um time n�o poder� ser exclu�do se possuir jogos
		if(t.getjogos().size() != 0) {
			throw new Exception ("Time: " + nome + " n�o pode ser excluido, pois possui jogos");
		}

		//apagar time no banco
		daotime.delete(t);
		DAO.commit();
	}

	public static Usuario criarUsuario(String email, String senha) throws Exception{
		DAO.begin(); 
		Usuario usu = daousuario.read(email);
		if (usu!=null)
			throw new Exception("Usuario ja cadastrado:" + email);
		usu = new Usuario(email, senha);

		daousuario.create(usu);
		DAO.commit();
		return usu;
	}
	public static Usuario validarUsuario(String email, String senha) {
		DAO.begin();
		Usuario usu = daousuario.read(email);
		if (usu==null)
			return null;

		if (! usu.getSenha().equals(senha))
			return null;

		DAO.commit();
		return usu;
	}


	//------------------------------------------------
	// 5 Consultas:
	//Nomes de times em ordem alfabetica
	public static List<Time> nomesTimes () {
		//retorna todos os jogos
		DAO.open();
		List<Time> resultado = daotime.times();
		//System.out.println(resultado);
		//retorna todos os ingressos 
		return resultado;
	}

	//Jogos que n�o possuem ingressos
	public static List<Jogo> jogosSemIngressos () {
		//retorna todos os jogos
		DAO.open();
		List<Jogo> resultado = daojogo.verificaringressos();
		//System.out.println(resultado);
		//retorna todos os ingressos 
		return resultado;
	}

	//consultar um ingresso
	public static Ingresso localizarIngresso(int codigo) throws Exception {
		//retorna o ingresso com o c�digo fornecido
		DAO.open();
		Ingresso i = daoingresso.read(codigo);
		if(i == null) {
			throw new Exception ("Id: "+ codigo  + " n�o existe!!!");
		}
		return i;
	}

	//filtrar times por origem
	public static List<Time> localizarorigem(String origem) throws Exception {
		//retorna o ingresso com o c�digo fornecido
		DAO.open();
		List<Time> i = daotime.consultarorigens(origem);
		if(i.size() == 0) {
			throw new Exception ("Origem: " + "'" + origem + "'" + " n�o existe no banco!!!");
		}
		return i;
	}

	//listar todos os jogos do time:
	public static List<Jogo> jogosdotime (String nome) throws Exception{
		DAO.open();
		List<Time> time = daotime.time(nome);

		if(time == null) {
			throw new Exception("nome n existe");
		}

		Time T = time.get(0);
		if(T.getjogos().size() == 0) {
			throw new Exception ("Time: " + nome + " n�o tem jogos cadastrados");
		}

		List<Jogo> j = null;
		for(Time t : time) {
			j = t.getjogos();	
		}

		return j;
	}







}
