/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */
package appswing;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.db4o.ObjectContainer;

import daodb4o.Util;
import modelo.Ingresso;
import modelo.IngressoGrupo;
import modelo.IngressoIndividual;
import modelo.Jogo;
import modelo.Time;
import regras_negocio.Fachada;

public class Consulta {
	private JFrame frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton button;
	private JLabel label;
	private JLabel label_4;

	private ObjectContainer manager;
	private JComboBox comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Consulta tela = new Consulta();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Consulta() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Consulta");
		frame.setBounds(100, 100, 729, 385);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				//manager = Util.conectarDb4oLocal();
				Fachada.inicializar();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				Fachada.finalizar();
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 43, 674, 148);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				label_4.setText("selecionado="+ (String) table.getValueAt( table.getSelectedRow(), 0));
			}
		});
		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.PINK);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		label = new JLabel("");		//label de mensagem
		label.setForeground(Color.BLUE);
		label.setBounds(21, 321, 688, 14);
		frame.getContentPane().add(label);

		label_4 = new JLabel("resultados:");
		label_4.setBounds(21, 190, 431, 14);
		frame.getContentPane().add(label_4);

		button = new JButton("Consultar");
		button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = comboBox.getSelectedIndex();
				if(index<0)
					label_4.setText("consulta nao selecionada");
				else
					switch(index) {
					case 0: 
						List<Time> resultado1 = Fachada.nomesTimes();
						nomesTimes(resultado1);
						break;
					case 1: 
						//String modelo = JOptionPane.showInputDialog("digite o modelo");
						List<Jogo> resultado2 = Fachada.jogosSemIngressos();
						timesSemIngressos(resultado2);
						break;
					case 2: 
						String letra = JOptionPane.showInputDialog("Digite a sigla do país do time");
						String origem = letra;
						try {
							List<Time> resultado3 = Fachada.localizarorigem(origem);
							filtarporpais(resultado3);
							break;
							
						}catch(Exception erro) {
							label.setText(erro.getMessage());
			
						}
					case 3:
						String n = JOptionPane.showInputDialog("digite um código de ingresso");
						int numero = Integer.parseInt(n);
						try {
							Ingresso resultado3 = Fachada.localizarIngresso(numero);
							ingressoexpecifico(resultado3);
							break;		
						}catch(Exception erro){
							label.setText(erro.getMessage());
						}
					case 4: 
						String nome = JOptionPane.showInputDialog("Digite o nome do time");
						//String origem = letra;
						try {
							List<Jogo> jogos = Fachada.jogosdotime(nome);
							retornajogosdotime(jogos);
							break;
							
						}catch(Exception erro) {
							label.setText(erro.getMessage());
			
						}
						
						

					}

			}
		});
		button.setBounds(606, 10, 89, 23);
		frame.getContentPane().add(button);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Nomes de times em ordem alfabetica", "Jogos que não possuem ingressos vendidos", "Time que que tem a mesma origem", "Consulta um ingresso especifico dentro do banco", "listar todos os jogos do time"}));
		comboBox.setBounds(21, 10, 513, 22);
		frame.getContentPane().add(comboBox);
	}

	public void nomesTimes(List<Time> lista) {
		try{
			// o model armazena todas as linhas e colunas do table
			DefaultTableModel model = new DefaultTableModel();

			//adicionar colunas no model
			model.addColumn("nome");

			//adicionar linhas no model
			for(Time time : lista) {
				model.addRow(new Object[]{time.getNome()});
			}


			//atualizar model no table (visualizacao)
			table.setModel(model);

			label_4.setText("resultados: "+lista.size()+ " objetos");
		}
		catch(Exception erro){
			label.setText(erro.getMessage());
		}
	}
	public void timesSemIngressos(List<Jogo> lista) {
		try{
			// model armazena todas as linhas e colunas do table
			DefaultTableModel model = new DefaultTableModel();

			//adicionar colunas no model
			model.addColumn("id");
			model.addColumn("data");
			model.addColumn("local");
			model.addColumn("estoque");
			model.addColumn("preco");
			model.addColumn("time1");
			model.addColumn("time2");

			//adicionar linhas no model
			for(Jogo j : lista) {
				model.addRow(new Object[]{j.getId(), j.getData(), j.getLocal(), j.getEstoque(), j.getPreco(), j.getTime1().getNome(), j.getTime2().getNome()});
			}

			//atualizar model no table (visualizacao)
			table.setModel(model);

			label_4.setText("resultados: "+lista.size()+ " objetos");
		}
		catch(Exception erro){
			label.setText(erro.getMessage());
		}
	}
	
	public void filtarporpais(List<Time> lista) {
		try{
			// model armazena todas as linhas e colunas do table
			DefaultTableModel model = new DefaultTableModel();

			//adicionar colunas no model
			model.addColumn("time");
			model.addColumn("origem");

			//adicionar linhas no model
			for(Time t : lista) {
				model.addRow(new Object[]{t.getNome(), t.getOrigem()});
			}

			//atualizar model no table (visualizacao)
			table.setModel(model);

			label_4.setText("resultados: "+lista.size()+ " objetos");
		}
		catch(Exception erro){
			label.setText(erro.getMessage());
		}
	}
	
	public void ingressoexpecifico(Ingresso i) {
		try{
			// model armazena todas as linhas e colunas do table
			DefaultTableModel model = new DefaultTableModel();

			//adicionar colunas no model
			model.addColumn("Ingresso");
			model.addColumn("Jogo");

			//adicionar linhas no model
			if (i instanceof IngressoGrupo grupo) {
				for(Jogo j : grupo.getJogos()) {
					model.addRow(new Object[]{grupo.getCodigo(), j.getTime1().getNome() + " x " + j.getTime2().getNome()});
					//atualizar model no table (visualizacao)
					table.setModel(model);
				}
				}
			else 
				if (i instanceof IngressoIndividual individuo) {
						Jogo j =  individuo.getJogo(); 
						model.addRow(new Object[]{individuo.getCodigo(), j.getTime1().getNome() + " x " + j.getTime2().getNome()});
						//atualizar model no table (visualizacao)
						table.setModel(model);
					}
		}
		catch(Exception erro){
			label.setText(erro.getMessage());
		}
	}
	
	public void retornajogosdotime(List<Jogo> lista) {
		try{
			// model armazena todas as linhas e colunas do table
			DefaultTableModel model = new DefaultTableModel();

			//adicionar colunas no model
			model.addColumn("id");
			model.addColumn("data");
			model.addColumn("local");
			model.addColumn("estoque");
			model.addColumn("time");

			//adicionar linhas no model
			for(Jogo j : lista) {
				model.addRow(new Object[]{j.getId(), j.getData(), j.getLocal(), j.getEstoque(), j.getTime1().getNome() + " x " + j.getTime2().getNome()});
			}

			//atualizar model no table (visualizacao)
			table.setModel(model);

			label_4.setText("resultados: "+lista.size()+ " objetos");
		}
		catch(Exception erro){
			label.setText(erro.getMessage());
		}
	}
	
	

}
