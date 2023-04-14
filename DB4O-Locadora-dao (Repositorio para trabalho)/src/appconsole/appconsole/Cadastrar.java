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
		System.out.println("Iniciando Cadastros...");
		
		Fachada.inicializar();
		try {
			/*Fachada.criarTime("Bayer", "al");
			Fachada.criarTime("City", "in");*/
			/*Fachada.criarTime("Flamengo", "br");
			Fachada.criarTime("Corinthians", "br");*/
			/*Fachada.criarTime("Barcelona", "es");
			Fachada.criarTime("Liverpool", "in");*/
			Fachada.criarTime("Arsenal", "in");
		}
		catch(Exception ex) {
			System.out.println("problema ao criar time-->"+ex.getMessage());
		}
		//System.out.println("\n");
		
		try {
			//Fachada.criarJogo("11/04/2023", "Inglaterra", 10000, 20, "Bayer", "City");
			//Fachada.criarJogo("15/04/2023", "maracanã", 10000, 20, "Flamengo", "Corinthians");
			//Fachada.criarJogo("02/01/2022", "camp nou", 10000, 20.0, "Barcelona", "Liverpool");
			//Fachada.criarJogo("01/04/2022", "camp nou", 10000, 20.0, "Barcelona", "Arsenal");*/
			Fachada.criarJogo("02/01/2022", "camp nou", 10000, 20.0, "Chelsea", "Real Madrid");
			Fachada.criarJogo("01/01/2022", "camp nou", 10000, 20.0, "Chelsea", "Flamengo");
			
		
		}
		catch(Exception ex) {
			System.out.println("problema ao criar jogo-->"+ex.getMessage());
		}

		try {
			/*Fachada.criarIngressoIndividual(1);		//id do jogo
			Fachada.criarIngressoIndividual(1);	//id do jogo
			Fachada.criarIngressoIndividual(1);	//id do jogo
			Fachada.criarIngressoIndividual(1);	//id do jogo
			Fachada.criarIngressoIndividual(1);		//id do jogo
			Fachada.criarIngressoIndividual(1);	//id do jogo*/
		}
		catch(Exception ex) {
			System.out.println("problema ao criar ingresso individual-->"+ex.getMessage());
		}
		
		//System.out.println("\n");

		try {
			//Fachada.criarIngressoGrupo(new int[]{1,2} );		//id dos jogos
			/*Fachada.criarIngressoGrupo(new int[]{2,3} );*/		//id dos jogos
			//Fachada.criarIngressoGrupo(new int[]{1,2,3} );		//id dos jogos
		}
		catch(Exception ex) {
			System.out.println("problema ao criar ingresso grupo-->"+ex.getMessage());
		}
		
		//System.out.println("\n");
		
		/*try {
			Fachada.apagarJogo(1);
			System.out.println("Jogo apagado"); 
		}catch (Exception e) {System.out.println("7ok--->"+e.getMessage());}*/	
		
		/*try {
			Fachada.apagarJogo(7);
			
		}catch(Exception ex) {
			System.out.println(ex);
		}*/
		
		/*try {
			Fachada.apagarTime("Bayer");
		}catch(Exception ex) {
			System.out.println(ex);
		}*/
		
		Fachada.finalizar();
		System.out.println("\nfim do programa !");
	}


	public static void main(String[] args) {
		new Cadastrar();
	}
}
