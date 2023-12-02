package swing;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mgr.Manager;

public class SignUpPage extends JPanel {

    private JTextField passwordTextField;
    private JTextField idTextField;
    private JLabel idLabel;
    private JLabel passwordLabel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JTextField nicknameTextField;
    private JTextField emailTextField;
    private JLabel nicknameLabel;
    private JLabel emailLabel;
    private ControlPage parent;

    public SignUpPage(ControlPage parent, Manager mgr) {
        this.parent = parent;
        setLayout(null);

        idLabel = new JLabel("ID");
        idLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        idLabel.setBounds(444, 173, 222, 37);
        add(idLabel);

        idTextField = new JTextField();
        idTextField.setColumns(10);
        idTextField.setBounds(440, 210, 277, 51);
        add(idTextField);

        passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        passwordLabel.setBounds(444, 300, 222, 37);
        add(passwordLabel);

        passwordTextField = new JTextField();
        passwordTextField.setColumns(10);
        passwordTextField.setBounds(440, 337, 277, 51);
        add(passwordTextField);

        JButton signUpButton = new JButton("회원가입");
        signUpButton.addActionListener(e -> {
        	if(mgr.SignUp(idTextField.getText(), passwordTextField.getText(), nameTextField.getText(), nicknameTextField.getText(), emailTextField.getText()) == true) {
        		JOptionPane.showMessageDialog(this, "회원가입 성공");        		
        	} else {
        		JOptionPane.showMessageDialog(this, "회원가입 실패");
        	}
        });
        signUpButton.setBounds(640, 488, 120, 50);
        add(signUpButton);

        JButton backButton = new JButton("뒤로가기");
        backButton.addActionListener(e -> {
            parent.showSignInPage();
        });
        backButton.setBounds(37, 488, 120, 50);
        add(backButton);

        nameLabel = new JLabel("이름");
        nameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        nameLabel.setBounds(58, 100, 222, 37);
        add(nameLabel);

        nameTextField = new JTextField();
        nameTextField.setColumns(10);
        nameTextField.setBounds(52, 140, 277, 51);
        add(nameTextField);

        nicknameTextField = new JTextField();
        nicknameTextField.setColumns(10);
        nicknameTextField.setBounds(52, 251, 277, 51);
        add(nicknameTextField);

        emailTextField = new JTextField();
        emailTextField.setColumns(10);
        emailTextField.setBounds(52, 367, 277, 51);
        add(emailTextField);

        nicknameLabel = new JLabel("닉네임");
        nicknameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        nicknameLabel.setBounds(58, 215, 222, 37);
        add(nicknameLabel);

        emailLabel = new JLabel("이메일");
        emailLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        emailLabel.setBounds(58, 330, 222, 37);
        add(emailLabel);
    }
}