import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import net.proteanit.sql.DbUtils;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.toedter.calendar.JDateChooser;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.PopupMenuEvent;
import java.awt.Cursor;

public class Main {

	private JFrame frmProjetCesi;
	private JTextField textField;
	private JTable table;
	private ManagerPersonne bdd;
	private APIReader api;
	private JSpinner textField_1;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmProjetCesi.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}
	
	

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize() {
		frmProjetCesi = new JFrame();
		frmProjetCesi.getContentPane().setBackground(Color.GRAY);
		frmProjetCesi.getContentPane().setForeground(Color.WHITE);
		frmProjetCesi.setResizable(false);
		frmProjetCesi.setTitle("Projet Cesi");
		frmProjetCesi.setBounds(100, 100, 1130, 643);
		frmProjetCesi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmProjetCesi.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 28, 196, 20);
		frmProjetCesi.getContentPane().add(textField);
		textField.setColumns(10);
		
		
		
		
		//connection a la BDD
		bdd = new ManagerPersonne();
		try {
			bdd.connecter();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/////////
		
		
	
		JLabel lblNewLabel = new JLabel("Recherche :");
		lblNewLabel.setBounds(10, 11, 96, 14);
		frmProjetCesi.getContentPane().add(lblNewLabel);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(20, 101, 1094, 502);
		scrollPane_1.setEnabled(false);
		frmProjetCesi.getContentPane().add(scrollPane_1);
		
		table = new JTable();
		table.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		scrollPane_1.setViewportView(table);
		
		
		JDateChooser dateChooser = new JDateChooser();
		scrollPane_1.setViewportView(table);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setBounds(10, 28, 124, 20);
		frmProjetCesi.getContentPane().add(dateChooser);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(10, 59, 314, 31);
		frmProjetCesi.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Type de recherche :");
		lblNewLabel_1.setBounds(6, 7, 125, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Nombre de");
		lblNewLabel_2.setBounds(328, 11, 90, 14);
		frmProjetCesi.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("nouveaux employés :");
		lblNewLabel_3.setBounds(328, 31, 124, 14);
		frmProjetCesi.getContentPane().add(lblNewLabel_3);
		
		
		
		//Quelle type de recherche faire
		JComboBox comboBox = new JComboBox();
		comboBox.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent arg0) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) 
			{
				if (comboBox.getSelectedItem() == "Date") 
				{
					textField.setVisible(false);
					textField.setText(null);
					dateChooser.setVisible(true);
					dateChooser.setEnabled(true);
				}
				else
				{
					textField.setVisible(true);
					dateChooser.setDate(null);
					dateChooser.setVisible(false);
					dateChooser.setEnabled(false);
				}
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Nom", "Prénom", "Date", "Service"}));
		comboBox.setSelectedIndex(0);
		comboBox.setBounds(141, 4, 105, 20);
		panel.add(comboBox);
		
		JButton btnNewButton_1 = new JButton("recherche");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				if (textField.getText().isEmpty() && dateChooser.getDate() == null) 
				{
					actualiseTab();
				}
				else
				{
					if (comboBox.getSelectedItem() == "Nom")
					{
						table.setModel(DbUtils.resultSetToTableModel(bdd.searchNom(textField.getText())));
					}
					
					if (comboBox.getSelectedItem() == "Prénom")
					{
						
						table.setModel(DbUtils.resultSetToTableModel(bdd.searchPrenom(textField.getText())));
					}
					
					if (comboBox.getSelectedItem() == "Service")
					{
						table.setModel(DbUtils.resultSetToTableModel(bdd.searchService(textField.getText())));
					}
					
					if (comboBox.getSelectedItem() == "Date")
					{
						String d = df.format(dateChooser.getDate());
						table.setModel(DbUtils.resultSetToTableModel(bdd.searchDate(d)));
					}
				}
				
				
			}
		});
		btnNewButton_1.setBounds(216, 27, 108, 23);
		frmProjetCesi.getContentPane().add(btnNewButton_1);
		////////////////
		
		////////btn ajout employés
		textField_1 = new JSpinner();
		textField_1.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		textField_1.setBounds(459, 28, 61, 20);
		frmProjetCesi.getContentPane().add(textField_1);
		
		
		JButton btnAPI = new JButton("Nouveau employé random");
		btnAPI.setBounds(334, 59, 186, 31);
		btnAPI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				api = new APIReader();
				actualiseTab();
				///model de la table
				DefaultTableModel modelNewEmployee = new DefaultTableModel(
						new Object[][] {
						},
						new String[] {
								"nom", "prenom", "telephone", "date", "service"
						}
				);
				
				int txtToInt = (int) textField_1.getValue();
				for(int i=0 ; i <= txtToInt-1 ; i++)
				{
					//appel l'API
					api.readAPI();
					//insert in bdd
					bdd.insertPersonnes(api.getLName(), api.getFName(), api.getPhone(), api.getIdService(), api.getDate());
					///update la jtable
					modelNewEmployee.addRow(new Object[]{api.getLName(),api.getFName(),api.getPhone(),api.getDate(),api.getService()});
					
				}
				table.setModel(modelNewEmployee);
			}
		});
		frmProjetCesi.getContentPane().add(btnAPI);
		////////////
		
		//Affichage su tabbleau de données
		actualiseTab(); 

	}
	
	public void actualiseTab()
	{

		try {
			table.setModel(DbUtils.resultSetToTableModel(bdd.lecturePersonnes()));
		} catch (SQLException e1) {
			e1.printStackTrace();		
		}
	}
}

