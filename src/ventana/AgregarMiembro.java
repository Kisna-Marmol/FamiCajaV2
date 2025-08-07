package ventana;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import modelo.Miembro;
import dao.MiembroDAO;

import javax.swing.JComboBox;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Date;

public class AgregarMiembro extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtNombre;
	private GestionDeMiembros gestionMiembros;
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			AgregarMiembro dialog = new AgregarMiembro();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	public AgregarMiembro(GestionDeMiembros gestionMiembros) {
		super(gestionMiembros,"Agregar Miembro", true);
		this.gestionMiembros = gestionMiembros;
		setBounds(100, 100, 550, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(255, 255, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre Completo:");
		lblNombre.setFont(new Font("Arial", Font.PLAIN, 18));
		lblNombre.setBounds(37, 49, 160, 18);
		contentPanel.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(220, 48, 252, 25);
		contentPanel.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblFecha = new JLabel("Fecha de Ingreso:");
		lblFecha.setFont(new Font("Arial", Font.PLAIN, 18));
		lblFecha.setBounds(37, 97, 160, 18);
		contentPanel.add(lblFecha);
		
		JDateChooser fecha_ingreso = new JDateChooser();
		fecha_ingreso.setToolTipText("");
		fecha_ingreso.setBounds(220, 90, 115, 25);
		contentPanel.add(fecha_ingreso);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setFont(new Font("Arial", Font.PLAIN, 18));
		lblEstado.setBounds(37, 143, 79, 18);
		contentPanel.add(lblEstado);
		
		JComboBox cbEstado = new JComboBox();
		cbEstado.setFont(new Font("Arial", Font.PLAIN, 12));
		cbEstado.setModel(new DefaultComboBoxModel(new String[] {"Activo", "Inactivo"}));
		cbEstado.setBounds(220, 142, 115, 25);
		contentPanel.add(cbEstado);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = txtNombre.getText().trim();
				Date fecha = fecha_ingreso.getDate();
				String estadoSeleccionado = cbEstado.getSelectedItem().toString();
				boolean estado = estadoSeleccionado.equalsIgnoreCase("Activo");
				
				if(nombre.isEmpty() || fecha == null) {
					JOptionPane.showMessageDialog(null, "Por favor complete todos los datos");
					return;
				}
				
				java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());
				Miembro nuevo = new Miembro(nombre,estado,fechaSQL);
				
				boolean exito = MiembroDAO.insertar(nuevo);
				if(exito) {
					JOptionPane.showMessageDialog(null, "Miembro agregado exitosamente.");
				}else {
					JOptionPane.showMessageDialog(null, "Error al agregar miembro.");
				}
			}
		});
		btnGuardar.setFont(new Font("Arial", Font.PLAIN, 12));
		btnGuardar.setIcon(new ImageIcon(AgregarMiembro.class.getResource("/img/guardar.png")));
		btnGuardar.setBounds(37, 236, 115, 27);
		botonTransparente(btnGuardar);
		contentPanel.add(btnGuardar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setFont(new Font("Arial", Font.PLAIN, 12));
		btnCancelar.setIcon(new ImageIcon(AgregarMiembro.class.getResource("/img/cancelar.png")));
		btnCancelar.setBounds(357, 236, 115, 27);
		botonTransparente(btnCancelar);
		contentPanel.add(btnCancelar);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menuBar.add(Box.createHorizontalGlue());
		
		JLabel lblNewLabel = new JLabel("Agregar Nuevo Miembro");
		lblNewLabel.setIcon(new ImageIcon(AgregarMiembro.class.getResource("/img/mas.png")));
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		menuBar.add(lblNewLabel);
		
		menuBar.add(Box.createHorizontalGlue());
		
		setLocationRelativeTo(null);

	}
	public static void botonTransparente(JButton boton) {
		//boton.setBorderPainted(false); //no pinta el bonde
		boton.setContentAreaFilled(false);
		//boton.setFocusPainted(false); //no pinta el borde cuando enfoca
	}
}
