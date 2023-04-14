/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

package appconsole;


import regras_negocio.Fachada;

public class Apagar {

	public Apagar() {
		Fachada.inicializar();
		/*try {
			
			Fachada.apagarIngresso(6);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			
		}
		*/
		try {
		Fachada.apagarTime("Flamengo");
	}catch(Exception ex) {
		System.out.println(ex);
	}
		
		try {
			Fachada.apagarJogo(2);
			
		}catch(Exception ex) {
			System.out.println(ex);
		}

		Fachada.finalizar();
		System.out.println("\nfim do programa !");
	}

	public static void main(String[] args) {
		new Apagar();
	}
}
