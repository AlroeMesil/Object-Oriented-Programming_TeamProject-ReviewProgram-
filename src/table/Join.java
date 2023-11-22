package table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Join extends JFrame {
    public Join() {
        // JFrame 설정
        setSize(500, 500);
        setTitle("회원가입");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        JPanel jp = new JPanel();

        //입력 분류
        JLabel idLabel = new JLabel("아이디");
        JLabel pwLabel = new JLabel("비밀번호");
        JLabel nameLabel = new JLabel("이름");
        JLabel nickNameLabel = new JLabel("닉네임");
        JLabel emailLabel = new JLabel("이메일");

        add(idLabel);
        add(pwLabel);
        add(nameLabel);
        add(nickNameLabel);
        add(emailLabel);

        //입력 분류 위치 설정
        idLabel.setBounds(50, 50, 40, 40);
        pwLabel.setBounds(50, 90, 80, 40);
        nameLabel.setBounds(50, 130, 40, 40);
        nickNameLabel.setBounds(50, 170, 40, 40);
        emailLabel.setBounds(50, 210, 40, 40);

        //입력부
        JTextField idField = new JTextField("", 20);
        JPasswordField pwField = new JPasswordField("", 20);
        JTextField nameField = new JTextField("", 20);
        JTextField nickNameField = new JTextField("", 20);
        JTextField emailField = new JTextField("", 20);

        add(idField);
        add(pwField);
        add(nameField);
        add(nickNameField);
        add(emailField);

        //입력부 위치 설정
        idField.setBounds(150, 50, 200, 30);
        pwField.setBounds(150, 90, 200, 30);
        nameField.setBounds(150, 130, 200, 30);
        nickNameField.setBounds(150, 170, 200, 30);
        emailField.setBounds(150, 210, 200, 30);

        //저장, 취소 버튼
        JButton store = new JButton("저장");
        JButton cancel = new JButton("취소");

        add(store);
        add(cancel);

        //버튼 위치
        store.setBounds(125, 330, 80, 30);
        cancel.setBounds(240, 330, 80, 30);

        //저장 버튼 클릭 시 파일에 저장
        store.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileWriter fw = new FileWriter("userListData.txt", true);
                    BufferedWriter bf = new BufferedWriter(fw);

                    bf.write(idField.getText() + " ");
                    bf.write(String.valueOf(pwField.getPassword()) + " ");
                    bf.write(nameField.getText() + " ");
                    bf.write(nickNameField.getText() + " ");
                    bf.write(emailField.getText() + "\n");
                    bf.close();

                    // 저장 성공 시
                    JOptionPane.showMessageDialog(null, "저장 성공");

                    idField.setText("");
                    pwField.setText("");
                    nameField.setText("");
                    nickNameField.setText("");
                    emailField.setText("");
                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(null, "실패");
                }
            }
        });
    }

    public static void main(String[] args) {
        Join join = new Join();
    }
}