/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

package appconsole;

import regras_negocio.Fachada;

public class Cadastrar {

	public Cadastrar() {
		/*try {
			Fachada.inicializar();
			System.out.println("cadastrando carro...");
			Fachada.cadastrarCarro("AAA1000", "palio");
			Fachada.cadastrarCarro("BBB2000", "onix");
			Fachada.cadastrarCarro("CCC3000", "civic");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			System.out.println("cadastrando cliente...");
			Fachada.cadastrarCliente("joao", "1111");
			Fachada.cadastrarCliente("maria", "2222");
			Fachada.cadastrarCliente("jose", "3333");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
			System.out.println("alugando carro...");
			Fachada.alugarCarro("1111","AAA1000",100.0 , "01/05/2022", "10/05/2022");
			Fachada.alugarCarro("2222","BBB2000",200.0 , "01/05/2022", "10/05/2022");
			Fachada.alugarCarro("2222","CCC3000",300.0 , "01/05/2022", "10/05/2022");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}*/
		
		Fachada.inicializar();
		try {
			/*Fachada.criarTime("Bayer", "al");
			Fachada.criarTime("City", "in");*/
			//Fachada.criarTime("Flamengo", "br");
			//Fachada.criarTime("Fluminense", "br");
			//Fachada.criarTime("Barcelona", "es");
			/*Fachada.criarTime("flamengo", "br");
			Fachada.criarTime("brasil", "br");
			Fachada.criarTime("argentina", "ar");
			Fachada.criarTime("chile", "ch");	
			Fachada.criarTime("bolivia", "bo");*/
	
		}
		catch(Exception ex) {
			System.out.println("problema ao criar time-->"+ex.getMessage());
		}
		
		try {
			/*Fachada.criarJogo("11/04/2023", "Inglaterra", 10000, 20, "Bayer", "City");
			//Fachada.criarJogo("11/04/2023", "maracanã", 10000, 20, "Flamengo", "Fluminense");
			Fachada.criarJogo("01/04/2024", "tokyo", 10000, 20, "City", "Fluminense");

			/*Fachada.criarJogo("02/12/2022", "maracana", 10000, 20.0, "Barcelona", "argentina");
			Fachada.criarJogo("02/12/2022", "maracana", 10000, 20.0, "chile", "bolivia");
			Fachada.criarJogo("04/12/2022", "maracana", 10000, 20.0, "brasil", "chile");
			Fachada.criarJogo("04/12/2022", "maracana", 10000, 20.0, "argentina", "bolivia"); */
		}
		catch(Exception ex) {
			System.out.println("problema ao criar jogo-->"+ex.getMessage());
		}

		try {
			//Fachada.criarIngressoIndividual(1);		//id do jogo
			Fachada.criarIngressoIndividual(2);	//id do jogo
			Fachada.criarIngressoIndividual(2);	//id do jogo
			Fachada.criarIngressoIndividual(2);	//id do jogo
			Fachada.criarIngressoIndividual(3);		//id do jogo
			//Fachada.criarIngressoIndividual(4);		//id do jogo*/
		}
		catch(Exception ex) {
			System.out.println("problema ao criar ingresso individual-->"+ex.getMessage());
		}

		try {
			//Fachada.criarIngressoGrupo(new int[]{1,3} );		//id dos jogos
			Fachada.criarIngressoGrupo(new int[]{2,3} );		//id dos jogos
			//Fachada.criarIngressoGrupo(new int[]{1,2,3} );		//id dos jogos
		}
		catch(Exception ex) {
			System.out.println("problema ao criar ingresso grupo-->"+ex.getMessage());
		}
		
		

		Fachada.finalizar();
		System.out.println("\nfim do programa !");
	}


	public static void main(String[] args) {
		new Cadastrar();
	}
}
