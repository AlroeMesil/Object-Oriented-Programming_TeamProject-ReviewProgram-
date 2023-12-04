package swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.JPasswordField;

import mgr.Manager;

public class SignInPage extends JPanel {
	private static final long serialVersionUID = 1L;
	// 비밀번호 입력창 가려지도록 수정
	private JPasswordField passwordField;
	private JTextField idTextField;
	private JLabel idLabel;
	private JLabel passwordLabel;
	private JLabel titleLabel;
	private ControlPage parent;
	private String userId = null;

	public SignInPage(ControlPage parent, Manager mgr) {
		this.parent = parent;
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel.setBackground(new Color(175, 238, 238));
		panel.setBounds(482, 130, 424, 468);
		add(panel);
		panel.setLayout(null);

		JPanel nameImagePanel = new SignInImagePanel();
		nameImagePanel.setBounds(62, 142, 306, 71);
		nameImagePanel.setBackground(new Color(0, 0, 0, 0));
		add(nameImagePanel);

//		titleLabel = new JLabel("트래블로그");
//		titleLabel.setForeground(SystemColor.activeCaption);
//		titleLabel.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 50));
//		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
//		titleLabel.setBounds(62, 142, 306, 71);
//		add(titleLabel);

		idLabel = new JLabel("아이디");
		idLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		idLabel.setBounds(67, 85, 120, 37);
		panel.add(idLabel);

		idTextField = new JTextField();
		idTextField.setColumns(10);
		idTextField.setBounds(67, 127, 290, 51);
		panel.add(idTextField);

		passwordLabel = new JLabel("비밀번호");
		passwordLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		passwordLabel.setBounds(67, 197, 120, 37);
		panel.add(passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		passwordField.setBounds(67, 239, 290, 51);
		panel.add(passwordField);

		JButton signinButton = new JButton("로그인");
		signinButton.addActionListener(e -> {
			userId = mgr.login(idTextField.getText(), new String(passwordField.getPassword()));
			if (userId != null) {
				parent.userId = userId;
				parent.showMainPage(mgr, parent.userId);
			} else {
				JOptionPane.showMessageDialog(this, "로그인 실패");
			}
		});
		signinButton.setBounds(150, 327, 124, 50);
		panel.add(signinButton);

		JButton signupButton = new JButton("회원가입");
		signupButton.addActionListener(e -> {
			parent.showSignUpPage();
		});
		signupButton.setBounds(284, 396, 120, 50);
		panel.add(signupButton);

		JPanel background = new ImagePanel("SignIn");
		background.setBounds(0, 0, 1024, 768);
		add(background);
	}

	class SignInImagePanel extends JPanel {
		private Image image;

		public SignInImagePanel() {
			this.image = new ImageIcon("images/title.png").getImage();
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			g.drawImage(image, 0, 0, 306, 71, null);
		}
	}

	public String getUserId() {
		return this.userId;
	}
}
