package swing;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import mgr.Manager;

public class SignInPage extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField passwordTextField;
    private JTextField idTextField;
    private JLabel idLabel;
    private JLabel passwordLabel;
    private JLabel titleLabel;
    private ControlPage parent;
    private String userId= null;

    public SignInPage(ControlPage parent, Manager mgr) {
        this.parent = parent;
        setLayout(null);

        titleLabel = new JLabel("관광명소 리뷰 프로그램 - 임시");
        titleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 35));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(160, 60, 480, 71);
        add(titleLabel);

        idLabel = new JLabel("ID");
        idLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        idLabel.setBounds(178, 172, 222, 37);
        add(idLabel);

        idTextField = new JTextField();
        idTextField.setColumns(10);
        idTextField.setBounds(170, 211, 460, 51);
        add(idTextField);

        passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        passwordLabel.setBounds(178, 291, 222, 37);
        add(passwordLabel);

        passwordTextField = new JTextField();
        passwordTextField.setColumns(10);
        passwordTextField.setBounds(170, 330, 460, 51);
        add(passwordTextField);

        JButton signinButton = new JButton("로그인");
        signinButton.addActionListener(e -> {
        	userId = mgr.login(idTextField.getText(), passwordTextField.getText()); 
        	if( userId != null) {
        		parent.userId = userId;
        		parent.showMainPage(mgr, parent.userId);
        	} else {
        		JOptionPane.showMessageDialog(this, "로그인 실패");
        	}
            // Add your login logic here
        });
        signinButton.setBounds(340, 429, 120, 50);
        add(signinButton);

        JButton signupButton = new JButton("회원가입");
        signupButton.addActionListener(e -> {
            parent.showSignUpPage();
        });
        signupButton.setBounds(635, 474, 120, 50);
        add(signupButton);
    }
    
    public String getUserId() {
		return this.userId;
	}
}
