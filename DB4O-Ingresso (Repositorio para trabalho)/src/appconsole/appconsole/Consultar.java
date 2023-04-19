/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

package appconsole;

import modelo.Jogo;
import modelo.Time;

import java.util.List;

import modelo.Ingresso;
import modelo.IngressoGrupo;
import modelo.IngressoIndividual;
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
			
				/*System.out.println("\nlistar todos os jogos do time");
				for(Jogo j : Fachada.jogosdotime("Arl")) {
					System.out.println(j);		
				}*/
			
				/*System.out.println("Todos os jogos de um time: ");
				for(Jogo j : Fachada.jogosdotime("Bayer")) {
					System.out.println(j.getTime1().getNome() + " X " + j.getTime2().getNome());	
				}*/
				
				/*System.out.println("Jogo que tem o codigo do ingresso 'x' ");
				for (Jogo j : Fachada.ingressodojogo(1)){
					System.out.println(j.getTime1().getNome() + " X " + j.getTime2().getNome());
					
				}*/
				
				/*System.out.println("Data dos jogos de um time");
				for (Time t : Fachada.joganadata("11//2023")){
					System.out.println(t.getNome());
					
				}*/
				
				/*System.out.println("ver ingressos individuais de um time");
				for (Time t : Fachada.joganadata("11//2023")){
					System.out.println(t.getNome());
					
				}*/
				
				/*System.out.println("Ingressos individuais do time:");
				for (IngressoIndividual i : Fachada.ingressoindividualdojogo(100)){
					System.out.println(i.getCodigo());
					
				}*/
			
			for(IngressoGrupo i : Fachada.ingressosgrupodojogo(1)) {
				for(Jogo j : i.getJogos()) {
					System.out.println(i.getCodigo() + j.getTime1().getNome() + " x " + j.getTime2().getNome());					
				}
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

