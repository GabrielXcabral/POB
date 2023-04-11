/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Persistencia de objetos
 * Prof. Fausto Maranhão Ayres
 **********************************/

package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Ingresso;
import modelo.IngressoGrupo;
import modelo.IngressoIndividual;
import modelo.Jogo;
import modelo.Time;
import regras_negocio.Fachada;
import javax.swing.JTextField;

public class TelaTime_ {
	private JFrame frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JLabel label;
	private JLabel label_6;
	private JLabel label_2;
	private JButton button;
	private JButton button_1;
	private JLabel label_3;
	private JTextField textField;
	private JTextField textField_1;
	private JButton button_2;



	/**
	 * Launch the application.
	 */
	//	public static void main(String[] args) {
	//		EventQueue.invokeLater(new Runnable() {
	//			public void run() {
	//				try {
	//					TelaJogo window = new TelaJogo();
	//					window.frame.setVisible(true);
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				}
	//			}
	//		});
	//	}

	/**
	 * Create the application.
	 */
	public TelaTime_() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Ingressos");
		frame.setBounds(100, 100, 729, 385);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				Fachada.inicializar();
				listagem();
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
		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.WHITE);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		label = new JLabel("");
		label.setForeground(Color.BLUE);
		label.setBounds(21, 321, 688, 14);
		frame.getContentPane().add(label);

		label_6 = new JLabel("selecione");
		label_6.setBounds(21, 190, 431, 14);
		frame.getContentPane().add(label_6);

		label_2 = new JLabel("jogos:");
		label_2.setHorizontalAlignment(SwingConstants.LEFT);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_2.setBounds(467, 261, 146, 14);
		frame.getContentPane().add(label_2);
		
		textField = new JTextField();
		textField.setBounds(78, 215, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(78, 259, 86, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Time:");
		lblNewLabel.setBounds(31, 218, 46, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Origem:");
		lblNewLabel_1.setBounds(31, 262, 46, 14);
		frame.getContentPane().add(lblNewLabel_1);

		button_1 = new JButton("Criar Time");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					if(		textField_1.getText().isEmpty() ||
							textField.getText().isEmpty() )
					{
						label.setText("campo vazio");
						return;
					}
				
					String time = textField.getText();
					String origem = textField_1.getText();
					
					Fachada.inicializar();
					Time t = Fachada.criarTime(time, origem);
					
					label_3.setText("Time:" + t.getNome());
					label_2.setText("Origem:" + t.getOrigem());
					
					label.setText("jogo criado: "+ "Time: " + time + " - " + "Origem: " + origem);
					listagem();
				}catch(Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_1.setBounds(289, 202, 201, 23);
		frame.getContentPane().add(button_1);
		
		

		button = new JButton("Listar");
		button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		button.setBounds(267, 9, 89, 23);
		frame.getContentPane().add(button);

		label_3 = new JLabel("codigo:");
		label_3.setHorizontalAlignment(SwingConstants.LEFT);
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_3.setBounds(284, 261, 146, 14);
		frame.getContentPane().add(label_3);
		
		button_2 = new JButton("Apagar Time");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if (table.getSelectedRow() >= 0){
						String nome = (String) table.getValueAt( table.getSelectedRow(), 0);
						Fachada.apagarTime(nome);
						label.setText("Apagou Time " +nome);
						listagem();
					}
					else
						label.setText("Time não foi selecionado!!!");
				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_2.setBounds(428, 9, 145, 23);
		frame.getContentPane().add(button_2);
		
	}

	public void listagem() {
		try{
			List<Time> lista = Fachada.listarTimes();

			//model contem todas as linhas e colunas da tabela
			DefaultTableModel model = new DefaultTableModel();

			//colunas
			model.addColumn("Time");
			model.addColumn("Origem");
			
			//linhas
			for(Time time : lista) {
				model.addRow(new Object[]{time.getNome()+"", time.getOrigem()});
			}
			
			table.setModel(model);
			label_6.setText("resultados: "+lista.size()+ " - times cadastrados!!!");
		}
		catch(Exception erro){
			label.setText(erro.getMessage());
		}
	}
}
