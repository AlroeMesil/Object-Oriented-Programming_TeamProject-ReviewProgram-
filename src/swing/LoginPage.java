package swing;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import mgr.Manager;

public class LoginPage {

    JFrame frame;
    private JTextField passwordTextField;
    private JTextField idTextField;
    private JLabel idLabel;
    private JLabel passwordLabel;
    private JLabel titleLabel;

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private SignInPage signInPage;
    private SignUpPage signUpPage;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
    	Manager mgr = new Manager();
        mgr.readAllPost("postList.txt");
        mgr.readAllUser("userListData.txt");
        mgr.readAllComment("commentList.txt");
        EventQueue.invokeLater(() -> {
            try {
                LoginPage window = new LoginPage(mgr);
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     */
    public LoginPage(Manager mgr) {
        initialize(mgr);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize(Manager mgr) {
        frame = new JFrame();
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
//        frame.setVisible(true);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        signInPage = new SignInPage(this, mgr);
        signUpPage = new SignUpPage(this, mgr);

        cardPanel.add(signInPage, "SignIn");
        cardPanel.add(signUpPage, "SignUp");

        frame.getContentPane().add(cardPanel);

        showSignInPage();
    }

    public void showSignInPage() {
        cardLayout.show(cardPanel, "SignIn");
    }

    public void showSignUpPage() {
        cardLayout.show(cardPanel, "SignUp");
    }
}

class SignInPage extends JPanel {

    private JTextField passwordTextField;
    private JTextField idTextField;
    private JLabel idLabel;
    private JLabel passwordLabel;
    private JLabel titleLabel;
    private LoginPage parent;
    private String userId= null;
    
    private void goMainPage(Manager mgr, String userId) {
        SwingUtilities.invokeLater(() -> {
            new MainPage(mgr, userId);
            parent.frame.dispose();
        });
    }
    

    public SignInPage(LoginPage parent, Manager mgr) {
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
        		goMainPage(mgr,userId);
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
}

class SignUpPage extends JPanel {

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
    private LoginPage parent;

    public SignUpPage(LoginPage parent, Manager mgr) {
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
