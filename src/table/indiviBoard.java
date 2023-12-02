package table;

import main.Post;
import mgr.Manageable;
import mgr.Manager;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Scanner;

public class indiviBoard {

    private JFrame frame;
    private JTextField postTitleTextField;
    private JTextArea postContentTextArea;
    private JButton backButton;
    private JLabel regionLabel;
    private JLabel categoryLabel;
    private JLabel writerLabel;
    private JLabel likeLabel;
    private JLabel badLabel;
    private JLabel postImageLabel;
    private JLabel postTitlelable;
    private JLabel postContentLabel;
    private JButton goodButton;
    private JButton badButton;
    private  JLabel commentLabel;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Manager mgr = new Manager();
        mgr.readAllPost("postList.txt");
        mgr.readAllUser("userListData.txt");
        EventQueue.invokeLater(() -> {
            try {
                int postNum = scanner.nextInt();
                indiviBoard window = new indiviBoard(mgr.postList, postNum);
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public indiviBoard(ArrayList<Manageable> postList, int postNum) {
        for (Manageable post : postList) {
            if (post instanceof Post && ((Post) post).getPostNum() == postNum) {
                Post selectedPost = (Post) post;
                initialize((Post) post);
                return;
            }
        }
        System.out.println("일치하는 게시글이 없습니다.");
    }

    private void initialize(Post post) {
        frame = new JFrame();
        frame.setBounds(100, 100, 1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // 게시글 이미지 라벨 출력
        postImageLabel = new JLabel("게시글 이미지");
        postImageLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        postImageLabel.setEnabled(true);
        postImageLabel.setBounds(46, 42, 172, 35);
        frame.getContentPane().add(postImageLabel);

        // 게시글 이미지 출력
        JPanel imagePanel = new JPanel();
        imagePanel.setBounds(46, 92, 372, 288);
        frame.getContentPane().add(imagePanel);
        String imagePath = "../ObjectOrientedProgramming-TeamProject/images/" + post.getPostNum() + ".png";
        ImageIcon icon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(640, 360, Image.SCALE_DEFAULT));
        JLabel imageLabel = new JLabel(icon);
        imagePanel.add(imageLabel);

        // 게시글 작성자 라벨 출력
        writerLabel = new JLabel("작성자 : "+post.getPostWriter());
        writerLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
        writerLabel.setEnabled(true);
        writerLabel.setBounds(46, 395, 372, 60);
        frame.getContentPane().add(writerLabel);

        // 게시글 지역 라벨 출력
        regionLabel = new JLabel("지역 : "+post.getRegion());
        regionLabel.setEnabled(true);
        regionLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
        regionLabel.setBounds(46, 467, 372, 60);
        frame.getContentPane().add(regionLabel);

        // 게시글 카테고리 출력
        categoryLabel = new JLabel("카테고리 : "+post.getPostCategory().get("category"));
        categoryLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
        categoryLabel.setEnabled(true);
        categoryLabel.setBounds(46, 539, 372, 60);
        frame.getContentPane().add(categoryLabel);

        // 뒤로가기 버튼
        backButton = new JButton("뒤로가기");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        backButton.setBounds(46, 611, 117, 40);
        frame.getContentPane().add(backButton);

        // 게시글 제목 라벨 출력
        postTitlelable = new JLabel("게시글 제목");
        postTitlelable.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        postTitlelable.setEnabled(true);
        postTitlelable.setBounds(474, 42, 172, 35);
        frame.getContentPane().add(postTitlelable);

        // 게시글 제목 출력
        postTitleTextField = new JTextField(post.getPostTitle());
        postTitleTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
        postTitleTextField.setEditable(false);
        postTitleTextField.setBounds(474, 92, 356, 40);
        frame.getContentPane().add(postTitleTextField);
        postTitleTextField.setColumns(10);

        // 게시글 본문 라벨 출력
        postContentLabel = new JLabel("게시글 본문");
        postContentLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        postContentLabel.setEnabled(true);
        postContentLabel.setBounds(474, 144, 172, 35);
        frame.getContentPane().add(postContentLabel);

        // 게시글 본문 출력
        postContentTextArea = new JTextArea(post.getPostContent());
        postContentTextArea.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
        postContentTextArea.setEditable(false);
        postContentTextArea.setColumns(10);
        postContentTextArea.setBounds(474, 197, 356, 386);
        postContentTextArea.setLineWrap(true);
        postContentTextArea.setMargin(new Insets(10, 10, 10, 10));
        frame.getContentPane().add(postContentTextArea);

        // 좋아요 버튼
        goodButton = new JButton("좋아요");
        goodButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        goodButton.setBounds(475, 611, 71, 40);
        frame.getContentPane().add(goodButton);

        // 좋아요 라벨
        likeLabel = new JLabel(Integer.toString(post.getGoodPoint().size()));
        likeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        likeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        likeLabel.setEnabled(true);
        likeLabel.setBounds(540, 611, 82, 38);
        frame.getContentPane().add(likeLabel);

        // 싫어요 버튼
        badButton = new JButton("싫어요");
        badButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        badButton.setBounds(661, 611, 71, 40);
        frame.getContentPane().add(badButton);

        // 싫어요 라벨
        badLabel = new JLabel(Integer.toString(post.getBadPoint().size()));
        badLabel.setHorizontalAlignment(SwingConstants.CENTER);
        badLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        badLabel.setEnabled(true);
        badLabel.setBounds(722, 611, 82, 38);
        frame.getContentPane().add(badLabel);

        // 댓글 라벨
        commentLabel = new JLabel("댓글");
        commentLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        commentLabel.setEnabled(false);
        commentLabel.setBounds(884, 42, 172, 35);
        frame.getContentPane().add(commentLabel);

        // 댓글 리스트
        JList commentList = new JList();
        commentList.setBounds(884, 92, 334, 491);
        frame.getContentPane().add(commentList);

        // 댓글 작성 텍스트필드
        JEditorPane commentEditor = new JEditorPane();
        commentEditor.setBounds(884, 616, 251, 29);
        frame.getContentPane().add(commentEditor);

        // 업로드 버튼
        JButton commentUploadButton = new JButton("업로드");
        // 기능 추가
        commentUploadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        commentUploadButton.setBounds(1147, 611, 71, 40);
        frame.getContentPane().add(commentUploadButton);
    }
}