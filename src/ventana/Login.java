package ventana;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.UsuarioDAO;
import modelo.Usuario;
import sql.Conexion;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Window.Type;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;

public class Login extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtUsuario;
	private JPasswordField passwordField;
	private Principal principal;
	
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			Login dialog = new Login();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	public Login(Principal principal) {
		super(principal,"Iniciar Sesion", true);
		this.principal = principal;
		
		setForeground(new Color(0, 0, 0));
		setFont(new Font("Arial", Font.PLAIN, 20));
		setTitle("Login");
		setBounds(100, 100, 712, 500);
		setResizable(false);

		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 698, 463);
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		txtUsuario = new JTextField();
		txtUsuario.setFont(new Font("Arial", Font.PLAIN, 13));
		txtUsuario.setForeground(new Color(128, 128, 128));
		txtUsuario.setText("Nombre de usuario");
		txtUsuario.addFocusListener(new FocusAdapter() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (txtUsuario.getText().equals("Nombre de usuario")) {
		            txtUsuario.setText("");
		        }
		    }

		    @Override
		    public void focusLost(FocusEvent e) {
		        if (txtUsuario.getText().isEmpty()) {
		            txtUsuario.setText("Nombre de usuario");
		        }
		    }
		});
		txtUsuario.setBounds(427, 286, 230, 32);
		contentPanel.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setToolTipText("\r\n");
		passwordField.setFont(new Font("Arial", Font.PLAIN, 13));
		passwordField.setBounds(427, 342, 230, 32);
		contentPanel.add(passwordField);
		
		JButton btnIniciar_Sesion = new JButton("Iniciar Sesion");
		btnIniciar_Sesion.setBackground(new Color(255, 132, 72));
		btnIniciar_Sesion.setFont(new Font("Arial", Font.PLAIN, 15));
		btnIniciar_Sesion.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        String usuario = txtUsuario.getText().trim();
		        String password = passwordField.getText().trim();

		        if (usuario.isEmpty() || password.isEmpty()) {
		            JOptionPane.showMessageDialog(Login.this, "Por favor, complete todos los campos.");
		            return;
		        }

		        if (usuario.isEmpty() || password.isEmpty()) {
		            JOptionPane.showMessageDialog(Login.this, "Por favor, complete todos los campos.");
		            return;
		        }

		        Usuario user = UsuarioDAO.login(usuario,password);
		        
		        if(user != null) {
		        	JOptionPane.showMessageDialog(Login.this, "Acceso concedido");
		        	principal.habilitarOpciones(true);
		        	dispose(); //cerrar dialogo
		        }else {
		        	JOptionPane.showMessageDialog(Login.this, "Usuario o contraseña incorrectos");
		        }
		    
		    }
		});
		btnIniciar_Sesion.setBounds(427, 398, 230, 32);
		contentPanel.add(btnIniciar_Sesion);
		getRootPane().setDefaultButton(btnIniciar_Sesion);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Login.class.getResource("/img/loginv3.jpg")));
		lblNewLabel_2.setBounds(0, 0, 407, 463);
		contentPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setIcon(new ImageIcon(Login.class.getResource("/img/logo_login2.png")));
		lblNewLabel_3.setBounds(417, 10, 230, 210);
		contentPanel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Inicio de Sesion");
		lblNewLabel_4.setFont(new Font("Arial", Font.BOLD, 30));
		lblNewLabel_4.setBounds(427, 215, 232, 48);
		contentPanel.add(lblNewLabel_4);
		
		setLocationRelativeTo(principal);
	}
	
}
