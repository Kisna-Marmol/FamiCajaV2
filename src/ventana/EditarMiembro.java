package ventana;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import dao.MiembroDAO;
import modelo.Miembro;

import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import java.awt.Component;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.util.Date;
import java.awt.event.ActionEvent;

public class EditarMiembro extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtNombre;
	private JDateChooser fecha_ingreso;
	private JComboBox cbEstado;
	private int miembroId = -1;
	private GestionDeMiembros gestionMiembros;
	
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			EditarMiembro dialog = new EditarMiembro();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	
	/*public EditarMiembro(Miembro miembro) {
		this(gestionMiembros);//llamada al constructor base
		txtNombre.setText(miembro.getNombre_completo());
		fecha_ingreso.setDate(miembro.getFecha_ingreso());
		cbEstado.setSelectedItem(miembro.isEstado() ? "Activo" : "Inactivo");
		
		//id del miembro a actualizar
		this.miembroId = miembro.getId();
	}*/
	
	public EditarMiembro(Miembro miembro, GestionDeMiembros gestionMiembros) {
		super(gestionMiembros,"Editar Miembro", true);
		this.gestionMiembros = gestionMiembros;
		
		
		//id del miembro a actualizar
		this.miembroId = miembro.getId();
		
		setBounds(100, 100, 550, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre Completo:");
		lblNombre.setFont(new Font("Arial", Font.PLAIN, 18));
		lblNombre.setBounds(56, 42, 151, 18);
		contentPanel.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(224, 41, 252, 25);
		contentPanel.add(txtNombre);
		
		JLabel lblFecha = new JLabel("Fecha de Ingreso:");
		lblFecha.setFont(new Font("Arial", Font.PLAIN, 18));
		lblFecha.setBounds(56, 96, 151, 18);
		contentPanel.add(lblFecha);
		
		fecha_ingreso = new JDateChooser();
		fecha_ingreso.setToolTipText("");
		fecha_ingreso.setBounds(224, 89, 115, 25);
		contentPanel.add(fecha_ingreso);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setFont(new Font("Arial", Font.PLAIN, 18));
		lblEstado.setBounds(56, 146, 73, 18);
		contentPanel.add(lblEstado);
		
		cbEstado = new JComboBox();
		cbEstado.setModel(new DefaultComboBoxModel(new String[] {"Activo", "Inactivo"}));
		cbEstado.setFont(new Font("Arial", Font.PLAIN, 12));
		cbEstado.setBounds(224, 145, 115, 25);
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
				Miembro editado = new Miembro(miembroId,nombre,estado,fechaSQL);
				
				boolean exito = MiembroDAO.actualizar(editado);
				if(exito) {
					JOptionPane.showMessageDialog(null, "Miembro agregado exitosamente.");
				}else {
					JOptionPane.showMessageDialog(null, "Error al agregar miembro.");
				}
			}
		});
		btnGuardar.setIcon(new ImageIcon(EditarMiembro.class.getResource("/img/guardar.png")));
		btnGuardar.setFont(new Font("Arial", Font.PLAIN, 12));
		btnGuardar.setContentAreaFilled(false);
		btnGuardar.setBounds(56, 241, 115, 27);
		botonTransparente(btnGuardar);
		contentPanel.add(btnGuardar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancelar.setIcon(new ImageIcon(EditarMiembro.class.getResource("/img/cancelar.png")));
		btnCancelar.setFont(new Font("Arial", Font.PLAIN, 12));
		btnCancelar.setBounds(332, 241, 115, 27);
		botonTransparente(btnCancelar);
		contentPanel.add(btnCancelar);
		
		JMenuBar menuBar = new JMenuBar();
		getContentPane().add(menuBar, BorderLayout.NORTH);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		menuBar.add(horizontalGlue);
		
		JLabel lblEditarMiembro = new JLabel("Editar Miembro");
		lblEditarMiembro.setIcon(new ImageIcon(EditarMiembro.class.getResource("/img/editar_icono.png")));
		lblEditarMiembro.setFont(new Font("Arial", Font.PLAIN, 18));
		menuBar.add(lblEditarMiembro);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		menuBar.add(horizontalGlue_1);
		
		
		
		txtNombre.setText(miembro.getNombre_completo());
		fecha_ingreso.setDate(miembro.getFecha_ingreso());
		cbEstado.setSelectedItem(miembro.isEstado() ? "Activo" : "Inactivo");
		
		
		
		setLocationRelativeTo(null);
	}
	
	public static void botonTransparente(JButton boton) {
		//boton.setBorderPainted(false); //no pinta el bonde
		boton.setContentAreaFilled(false);
		//boton.setFocusPainted(false); //no pinta el borde cuando enfoca
	}
}
