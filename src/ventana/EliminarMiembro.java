package ventana;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modelo.Miembro;
import dao.MiembroDAO;

import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import java.awt.Component;
import javax.swing.Box;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EliminarMiembro extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtNombre;
	private Miembro miembroSeleccionado;
	private int miembroId;
	private static GestionDeMiembros gestionMiembros;
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			EliminarMiembro dialog = new EliminarMiembro();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 * @wbp.parser.constructor
	 */
	
	public EliminarMiembro(Miembro miembro, GestionDeMiembros gestionMiembros) {
		this(gestionMiembros);
		this.miembroSeleccionado = miembro;
		txtNombre.setText(miembro.getNombre_completo());
		
		this.miembroId = miembro.getId();
	}
	
	public EliminarMiembro(GestionDeMiembros gestionMiembros) {
		super(gestionMiembros,"Eliminar Miembro", true);
		this.gestionMiembros = gestionMiembros;
		setBounds(100, 100, 550, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(255, 255, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNombre_titulo = new JLabel("Nombre Completo:");
		lblNombre_titulo.setFont(new Font("Arial", Font.PLAIN, 18));
		lblNombre_titulo.setBounds(26, 122, 151, 18);
		contentPanel.add(lblNombre_titulo);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int respuesta = JOptionPane.showConfirmDialog(null, "¿Estas seguro que deseas eliminar este miembro?",
						"Confirmacion de eliminacion", JOptionPane.YES_NO_OPTION);
				
				if(respuesta == JOptionPane.YES_OPTION) {
					boolean exito = MiembroDAO.eliminar(miembroId);
					if(exito) {
						JOptionPane.showMessageDialog(null, "Miembro eliminado exitosamente.");
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Error al eliminar miembro.");
					}
				}
			}
		});
		btnConfirmar.setIcon(new ImageIcon(EliminarMiembro.class.getResource("/img/confirmacion.png")));
		btnConfirmar.setFont(new Font("Arial", Font.PLAIN, 12));
		btnConfirmar.setContentAreaFilled(false);
		btnConfirmar.setBounds(26, 238, 133, 27);
		botonTransparente(btnConfirmar);
		contentPanel.add(btnConfirmar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setIcon(new ImageIcon(EliminarMiembro.class.getResource("/img/cancelar.png")));
		btnCancelar.setFont(new Font("Arial", Font.PLAIN, 12));
		btnCancelar.setBounds(346, 238, 115, 27);
		botonTransparente(btnCancelar);
		contentPanel.add(btnCancelar);
		
		JLabel lblInformacion = new JLabel("Seguro que deseas eliminar:");
		lblInformacion.setFont(new Font("Arial", Font.PLAIN, 18));
		lblInformacion.setBounds(26, 25, 230, 18);
		contentPanel.add(lblInformacion);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(209, 121, 252, 25);
		txtNombre.setEditable(false);
		contentPanel.add(txtNombre);
		txtNombre.setColumns(10);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		menuBar.add(horizontalGlue);
		
		JLabel lblEliminarMiembro = new JLabel("Eliminar Miembro");
		lblEliminarMiembro.setIcon(new ImageIcon(EliminarMiembro.class.getResource("/img/eliminar_icono.png")));
		lblEliminarMiembro.setFont(new Font("Arial", Font.PLAIN, 18));
		menuBar.add(lblEliminarMiembro);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		menuBar.add(horizontalGlue_1);
		
		setLocationRelativeTo(null);
	}
	
	public static void botonTransparente(JButton boton) {
		//boton.setBorderPainted(false); //no pinta el bonde
		boton.setContentAreaFilled(false);
		//boton.setFocusPainted(false); //no pinta el borde cuando enfoca
	}
	
}
