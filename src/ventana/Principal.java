package ventana;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.Color;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Principal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelContenedor;
	private CardLayout cardLayout;
	//botones menu
	private JButton btnMiembros;
	private JButton btnAportes;
	private JButton btnPrestamos;
	private JButton btnReportes;
	private JButton btnPagoPrestamos;
	private JButton btnRegistroIntereses;
	private JButton btnPagoIntereses;
	private JButton btnEstadoCuenta;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1080, 800);
		
		setResizable(false);

		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Sistema");
		mnNewMenu.setFont(new Font("Arial", Font.PLAIN, 15));
		mnNewMenu.setIcon(new ImageIcon(Principal.class.getResource("/img/seguridad-informatica.png")));
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Login");
		mntmNewMenuItem_1.setFont(new Font("Arial", Font.PLAIN, 18));
		mntmNewMenuItem_1.setIcon(new ImageIcon(Principal.class.getResource("/img/cuenta.png")));
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Login dialog = new Login(Principal.this);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Salir");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0); // Cierra la aplicación
			}
		});
		mntmNewMenuItem.setFont(new Font("Arial", Font.PLAIN, 18));
		mntmNewMenuItem.setIcon(new ImageIcon(Principal.class.getResource("/img/cerrar-sesion.png")));
		mnNewMenu.add(mntmNewMenuItem);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		cardLayout = new CardLayout();
		panelContenedor = new JPanel();
		panelContenedor.setBackground(new Color(255, 255, 255));
		contentPane.add(panelContenedor, BorderLayout.CENTER);
		panelContenedor.setLayout(cardLayout);
		
		
		JPanel panelInicio = new JPanel();
		panelInicio.setLayout(null);
		panelInicio.setBackground(Color.WHITE);
		panelContenedor.add(panelInicio, "name_268370416371400");
		
		JLabel lblBienvenido = new JLabel("Bienvenido");
		lblBienvenido.setFont(new Font("Arial", Font.BOLD, 35));
		lblBienvenido.setBounds(446, 33, 190, 56);
		panelInicio.add(lblBienvenido);
		
		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(new ImageIcon(Principal.class.getResource("/img/logo_sin_fondo8.png")));
		lblFondo.setHorizontalAlignment(SwingConstants.CENTER);
		lblFondo.setBounds(0, 0, 1056, 731);
		panelInicio.add(lblFondo);
		
		btnMiembros = new JButton("Miembros");
		btnMiembros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					GestionDeMiembros dialog = new GestionDeMiembros(Principal.this);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnMiembros.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnMiembros.setIcon(new ImageIcon(Principal.class.getResource("/img/miembros2.png")));
		btnMiembros.setHorizontalTextPosition(SwingConstants.CENTER);
		btnMiembros.setFont(new Font("Arial", Font.PLAIN, 15));
		btnMiembros.setContentAreaFilled(false);
		btnMiembros.setBounds(182, 118, 129, 136);
		botonTransparente(btnMiembros);
		panelInicio.add(btnMiembros);
		
		btnAportes = new JButton("Aportes");
		btnAportes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					GestionDeAportes dialog = new GestionDeAportes(Principal.this);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnAportes.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnAportes.setIcon(new ImageIcon(Principal.class.getResource("/img/aportes.png")));
		btnAportes.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAportes.setFont(new Font("Arial", Font.PLAIN, 15));
		btnAportes.setBounds(796, 118, 129, 136);
		botonTransparente(btnAportes);
		panelInicio.add(btnAportes);
		
		btnPrestamos = new JButton("Prestamos");
		btnPrestamos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					SolicitudPrestamos dialog = new SolicitudPrestamos(Principal.this);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnPrestamos.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnPrestamos.setIcon(new ImageIcon(Principal.class.getResource("/img/prestamos.png")));
		btnPrestamos.setHorizontalTextPosition(SwingConstants.CENTER);
		btnPrestamos.setFont(new Font("Arial", Font.PLAIN, 15));
		btnPrestamos.setBounds(182, 335, 129, 136);
		botonTransparente(btnPrestamos);
		panelInicio.add(btnPrestamos);
		
		btnReportes = new JButton("Reportes");
		btnReportes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ReporteGeneral dialog = new ReporteGeneral(Principal.this);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnReportes.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnReportes.setIcon(new ImageIcon(Principal.class.getResource("/img/reportes.png")));
		btnReportes.setHorizontalTextPosition(SwingConstants.CENTER);
		btnReportes.setFont(new Font("Arial", Font.PLAIN, 15));
		btnReportes.setBounds(796, 335, 129, 136);
		botonTransparente(btnReportes);
		panelInicio.add(btnReportes);
		
		JButton btnMasOpciones = new JButton("Mas Opciones");
		btnMasOpciones.setIcon(new ImageIcon(Principal.class.getResource("/img/mas_opciones3.png")));
		btnMasOpciones.setFont(new Font("Arial", Font.PLAIN, 15));
		btnMasOpciones.setBounds(424, 597, 212, 55);
		btnMasOpciones.addActionListener(e -> cardLayout.show(panelContenedor, "opciones"));
		botonTransparente(btnMasOpciones);
		panelInicio.add(btnMasOpciones);
		
		JPanel panelMasOpciones = new JPanel();
		panelMasOpciones.setLayout(null);
		panelMasOpciones.setBackground(Color.WHITE);
		panelContenedor.add(panelMasOpciones, "name_268370422184400");
		
		JLabel lblNewLabel = new JLabel("Bienvenido");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 35));
		lblNewLabel.setBounds(446, 33, 190, 56);
		panelMasOpciones.add(lblNewLabel);
		
		JLabel lblNewLabel_2_1 = new JLabel("");
		lblNewLabel_2_1.setIcon(new ImageIcon(Principal.class.getResource("/img/logo_sin_fondo8.png")));
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_1.setBounds(0, 0, 1056, 731);
		panelMasOpciones.add(lblNewLabel_2_1);
		
		btnPagoPrestamos = new JButton("Pago de Prestamos");
		btnPagoPrestamos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					PagoPrestamos dialog = new PagoPrestamos(Principal.this);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnPagoPrestamos.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnPagoPrestamos.setIcon(new ImageIcon(Principal.class.getResource("/img/pago_prestamos.png")));
		btnPagoPrestamos.setHorizontalTextPosition(SwingConstants.CENTER);
		btnPagoPrestamos.setFont(new Font("Arial", Font.PLAIN, 15));
		btnPagoPrestamos.setContentAreaFilled(false);
		btnPagoPrestamos.setBounds(103, 118, 179, 136);
		botonTransparente(btnPagoPrestamos);
		panelMasOpciones.add(btnPagoPrestamos);
		
		btnRegistroIntereses = new JButton("Registro de intereses");
		btnRegistroIntereses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					RegistroIntereses dialog = new RegistroIntereses(Principal.this);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnRegistroIntereses.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnRegistroIntereses.setIcon(new ImageIcon(Principal.class.getResource("/img/registro_de_intereses.png")));
		btnRegistroIntereses.setHorizontalTextPosition(SwingConstants.CENTER);
		btnRegistroIntereses.setFont(new Font("Arial", Font.PLAIN, 15));
		btnRegistroIntereses.setBounds(769, 118, 179, 136);
		botonTransparente(btnRegistroIntereses);
		panelMasOpciones.add(btnRegistroIntereses);
		
		btnPagoIntereses = new JButton("Pago de Intereses");
		btnPagoIntereses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					PagoIntereses dialog = new PagoIntereses(Principal.this);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnPagoIntereses.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnPagoIntereses.setIcon(new ImageIcon(Principal.class.getResource("/img/pago_intereses.png")));
		btnPagoIntereses.setHorizontalTextPosition(SwingConstants.CENTER);
		btnPagoIntereses.setFont(new Font("Arial", Font.PLAIN, 15));
		btnPagoIntereses.setBounds(103, 335, 179, 136);
		botonTransparente(btnPagoIntereses);
		panelMasOpciones.add(btnPagoIntereses);
		
		btnEstadoCuenta = new JButton("Estado de cuenta");
		btnEstadoCuenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					EstadoCuenta dialog = new EstadoCuenta(Principal.this);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnEstadoCuenta.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnEstadoCuenta.setIcon(new ImageIcon(Principal.class.getResource("/img/estado_de_cuenta.png")));
		btnEstadoCuenta.setHorizontalTextPosition(SwingConstants.CENTER);
		btnEstadoCuenta.setFont(new Font("Arial", Font.PLAIN, 15));
		btnEstadoCuenta.setBounds(769, 335, 179, 136);
		botonTransparente(btnEstadoCuenta);
		panelMasOpciones.add(btnEstadoCuenta);
		
		JButton btnVolver = new JButton("Volver al menu");
		btnVolver.setIcon(new ImageIcon(Principal.class.getResource("/img/volver.png")));
		btnVolver.setFont(new Font("Arial", Font.PLAIN, 15));
		btnVolver.setBounds(424, 597, 212, 55);
		btnVolver.addActionListener(e -> cardLayout.show(panelContenedor, "inicio"));
		botonTransparente(btnVolver);
		panelMasOpciones.add(btnVolver);
		
		
		panelContenedor.add(panelInicio, "inicio");
		panelContenedor.add(panelMasOpciones, "opciones");

		habilitarOpciones(false);
		
		setLocationRelativeTo(null);
	}
	
	public static void botonTransparente(JButton boton) {
		//boton.setBorderPainted(false); //no pinta el bonde
		boton.setContentAreaFilled(false);
		//boton.setFocusPainted(false); //no pinta el borde cuando enfoca
	}
	
	public void habilitarOpciones(boolean estado) {
		btnMiembros.setEnabled(estado);
	    btnAportes.setEnabled(estado);
	    btnPrestamos.setEnabled(estado);
	    btnReportes.setEnabled(estado);
	    btnPagoPrestamos.setEnabled(estado);
	    btnRegistroIntereses.setEnabled(estado);
	    btnPagoIntereses.setEnabled(estado);
	    btnEstadoCuenta.setEnabled(estado);
	}
}
