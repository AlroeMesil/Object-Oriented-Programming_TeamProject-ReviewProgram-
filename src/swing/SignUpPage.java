package swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
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
        
        JPanel panel = new JPanel();
        panel.setBounds(150, 150, 724, 468);
        panel.setBackground(new Color(175, 238, 238));
        add(panel);
        panel.setLayout(null);

        idLabel = new JLabel("아이디");
        idLabel.setBounds(388, 103, 222, 37);
        idLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        panel.add(idLabel);

        idTextField = new JTextField();
        idTextField.setBounds(388, 143, 277, 51);
        panel.add(idTextField);
        idTextField.setColumns(10);

        passwordLabel = new JLabel("비밀번호");
        passwordLabel.setBounds(388, 213, 222, 37);
        panel.add(passwordLabel);
        passwordLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));

        passwordTextField = new JTextField();
        passwordTextField.setBounds(388, 260, 277, 51);
        panel.add(passwordTextField);
        passwordTextField.setColumns(10);

        JButton signUpButton = new JButton("회원가입");
        signUpButton.addActionListener(e -> {
        	if(mgr.SignUp(idTextField.getText(), passwordTextField.getText(), nameTextField.getText(), nicknameTextField.getText(), emailTextField.getText()) == true) {
        		JOptionPane.showMessageDialog(this, "회원가입 성공");        		
        	} else {
        		JOptionPane.showMessageDialog(this, "회원가입 실패");
        	}
        });
        signUpButton.setBounds(575, 391, 120, 50);
        panel.add(signUpButton);

        JButton backButton = new JButton("뒤로가기");
        backButton.addActionListener(e -> {
            parent.showSignInPage();
        });
        backButton.setBounds(23, 392, 120, 50);
        panel.add(backButton);

        nameLabel = new JLabel("이름");
        nameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        nameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        nameLabel.setBounds(36, 33, 222, 37);
        panel.add(nameLabel);

        nameTextField = new JTextField();
        nameTextField.setBounds(36, 75, 277, 51);
        nameTextField.setColumns(10);
        panel.add(nameTextField);

        nicknameTextField = new JTextField();
        nicknameTextField.setBounds(36, 197, 277, 51);
        nicknameTextField.setColumns(10);
        panel.add(nicknameTextField);

        emailTextField = new JTextField();
        emailTextField.setBounds(36, 313, 277, 51);
        panel.add(emailTextField);
        emailTextField.setColumns(10);

        nicknameLabel = new JLabel("닉네임");
        nicknameLabel.setBounds(38, 155, 222, 37); 
        panel.add(nicknameLabel);
        nicknameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));

        emailLabel = new JLabel("이메일");
        emailLabel.setBounds(38, 272, 222, 37);
        panel.add(emailLabel);
        emailLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        
        JPanel background = new ImagePanel();
		background.setBounds(0, 0, 1024, 768);
		add(background);
    }
}