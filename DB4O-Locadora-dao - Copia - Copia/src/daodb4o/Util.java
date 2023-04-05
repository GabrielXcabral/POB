/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

package daodb4o;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
/*import com.db4o.cs.Db4oClientServer;
import com.db4o.cs.config.ClientConfiguration;

import modelo.Aluguel;*/
/*import modelo.Carro;
import modelo.Cliente;*/
import modelo.IngressoGrupo;
import modelo.IngressoIndividual;
import modelo.Jogo;
import modelo.Time;

public class Util {
	private static ObjectContainer manager=null;
	private static int contador;	//contador de pedidos de conexao
	
	public static ObjectContainer conectarDb4oLocal(){
		contador++;
		if (manager != null)
			return manager;		//ja tem uma conexao
		
		//---------------------------------------------------------------
		//configurar, criar e conectar banco local (na pasta do projeto
		//---------------------------------------------------------------
		
		EmbeddedConfiguration config =  Db4oEmbedded.newConfiguration(); 
		config.common().messageLevel(0);  // mensagens na tela 0(desliga),1,2,3...
		
		// habilitar cascata na altera��o, remo��o e leitura
		/*config.common().objectClass(Carro.class).cascadeOnDelete(false);;
		config.common().objectClass(Carro.class).cascadeOnUpdate(true);;
		config.common().objectClass(Carro.class).cascadeOnActivate(true);
		config.common().objectClass(Cliente.class).cascadeOnDelete(false);;
		config.common().objectClass(Cliente.class).cascadeOnUpdate(true);;
		config.common().objectClass(Cliente.class).cascadeOnActivate(true);
		config.common().objectClass(Aluguel.class).cascadeOnDelete(false);;
		config.common().objectClass(Aluguel.class).cascadeOnUpdate(true);;
		config.common().objectClass(Aluguel.class).cascadeOnActivate(true);*/

		config.common().objectClass(Jogo.class).cascadeOnDelete(true);;
		config.common().objectClass(Jogo.class).cascadeOnUpdate(true);;
		config.common().objectClass(Jogo.class).cascadeOnActivate(true);
		config.common().objectClass(Time.class).cascadeOnDelete(true);;
		config.common().objectClass(Time.class).cascadeOnUpdate(true);;
		config.common().objectClass(Time.class).cascadeOnActivate(true);
		config.common().objectClass(IngressoIndividual.class).cascadeOnDelete(true);;
		config.common().objectClass(IngressoIndividual.class).cascadeOnUpdate(true);;
		config.common().objectClass(IngressoIndividual.class).cascadeOnActivate(true);
		config.common().objectClass(IngressoGrupo.class).cascadeOnDelete(true);;
		config.common().objectClass(IngressoGrupo.class).cascadeOnUpdate(true);;
		config.common().objectClass(IngressoGrupo.class).cascadeOnActivate(true);
		
		// criar indices (opcional) sobre campos de busca
		/*config.common().objectClass(Carro.class).objectField("placa").indexed(true);
		config.common().objectClass(Cliente.class).objectField("cpf").indexed(true);
		
		// nivel de profundidade do grafo para leitura e atualiza��o
		config.common().objectClass(Carro.class).updateDepth(5);
		config.common().objectClass(Carro.class).minimumActivationDepth(5);
		config.common().objectClass(Cliente.class).updateDepth(5);
		config.common().objectClass(Cliente.class).minimumActivationDepth(5);*/
		
		//conexao local
		manager = Db4oEmbedded.openFile(config, "banco.db4o");
		return manager;
	}
	
	/*public static ObjectContainer conectarDb4oRemoto(){		
		//---------------------------------------
		//configurar e conectar/criar banco remoto
		//---------------------------------------
		ClientConfiguration config = Db4oClientServer.newClientConfiguration( ) ;
		config.common().messageLevel(0);   //0,1,2,3,4

		// habilitar cascata na altera��o, remo��o e leitura
		config.common().objectClass(Carro.class).cascadeOnDelete(false);;
		config.common().objectClass(Carro.class).cascadeOnUpdate(true);;
		config.common().objectClass(Carro.class).cascadeOnActivate(true);
		config.common().objectClass(Cliente.class).cascadeOnDelete(false);;
		config.common().objectClass(Cliente.class).cascadeOnUpdate(true);;
		config.common().objectClass(Cliente.class).cascadeOnActivate(true);
		config.common().objectClass(Aluguel.class).cascadeOnDelete(false);;
		config.common().objectClass(Aluguel.class).cascadeOnUpdate(true);;
		config.common().objectClass(Aluguel.class).cascadeOnActivate(true);
		
		// criar indices (opcional) sobre campos de busca
		config.common().objectClass(Carro.class).objectField("placa").indexed(true);
		config.common().objectClass(Cliente.class).objectField("cpf").indexed(true);
		
		// nivel de profundidade do grafo para leitura e atualiza��o
		config.common().objectClass(Carro.class).updateDepth(5);
		config.common().objectClass(Carro.class).minimumActivationDepth(5);
		config.common().objectClass(Cliente.class).updateDepth(5);
		config.common().objectClass(Cliente.class).minimumActivationDepth(5);

		//Conex�o remota 
		//****************
		String ipservidor="";
		//ipservidor = "10.0.4.147";		// computador do lab3 (professor)
		ipservidor = "54.163.92.174";		// AWS
		manager = Db4oClientServer.openClient(config, ipservidor, 34000,"usuario1","senha1");
		return manager;
	}*/
	
	public static void desconectar() {
		if(manager!=null && contador>0) {
			manager.close();
			manager=null;
		}
	}
}
