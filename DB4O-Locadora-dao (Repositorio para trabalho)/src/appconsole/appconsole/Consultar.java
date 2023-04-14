/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

package appconsole;

import modelo.Jogo;
import modelo.Time;
import modelo.Ingresso;

import regras_negocio.Fachada;

public class Consultar {

	public Consultar() {
		try {
			Fachada.inicializar();

			/*System.out.println("consultas... \n");
			System.out.println("\nNomes de times em ordem alfabetica");
			for(Time t : Fachada.nomesTimes())
				System.out.println(t.getNome());


			System.out.println("\nJogos que n�o possuem ingressos vendidos");
			for(Jogo j : Fachada.jogosSemIngressos())
				System.out.println(j);*/
			
			/*System.out.println("\nTime que tem a mesma origem");
			for(Time t : Fachada.localizarorigem("a")) {
				System.out.print("Time: " + t.getNome() + 
						" - " + " Origem: " + t.getOrigem()+"\n");
			}*/


			/*System.out.println("\nConsulta um ingresso especifico dentro do banco");
			Ingresso i = Fachada.localizarIngresso(10);
				System.out.println(i);*/
			
				System.out.println("\nlistar todos os jogos do time");
				for(Jogo j : Fachada.jogosdotime("Arl")) {
					System.out.println(j);		
				}
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Fachada.finalizar();
		System.out.println("\nfim do programa !");
	}

	public static void main(String[] args) {
		new Consultar();
	}
}

