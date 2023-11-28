package swing;

import main.Comment;
import main.Post;
import mgr.Manageable;
import mgr.Manager;
import table.indiviBoard;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Scanner;

public class indiviBoardPage extends JPanel {
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
    
    private final MainPage mainPage;  
    
    public JPanel getContentPanel() {
        return (JPanel) frame.getContentPane();
    }

    public indiviBoardPage(int postNum, Manager mgr, String userId, MainPage mainPage) {
        this.mainPage = mainPage;
        for (Manageable post : mgr.postList) {
            if (post instanceof Post && ((Post) post).getPostNum() == postNum) {
                Post selectedPost = (Post) post;
                initialize(selectedPost, mgr, userId, mainPage);
                return;
            }
        }
        System.out.println("일치하는 게시글이 없습니다.");
    }

    public void initialize(Post post, Manager mgr, String userId, MainPage mainPage) {
        frame = new JFrame();
        frame.setBounds(100, 100, 1280, 720);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // 게시글 이미지 라벨 출력
        postImageLabel = new JLabel("게시글 이미지");
        postImageLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
        postImageLabel.setEnabled(true);
        postImageLabel.setBounds(46, 42, 172, 35);
        frame.getContentPane().add(postImageLabel);

        // 게시글 이미지 출력
        JPanel imagePanel = new JPanel();
        imagePanel.setBounds(46, 92, 372, 288);
        frame.getContentPane().add(imagePanel);
        String imagePath = "../TeamB_ReviewApp/images/" + post.getPostNum() + ".png"; // 프로젝트 절대 경로 수정 필요
        ImageIcon icon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(640, 360, Image.SCALE_DEFAULT));
        JLabel imageLabel = new JLabel(icon);
        imagePanel.add(imageLabel);

        // 게시글 작성자 라벨 출력
        writerLabel = new JLabel("작성자 : "+post.getPostWriter());
        writerLabel.setFont(new Font("Lucida Grande", Font.BOLD, 25));
        writerLabel.setEnabled(true);
        writerLabel.setBounds(46, 395, 372, 60);
        frame.getContentPane().add(writerLabel);

        // 게시글 지역 라벨 출력
        regionLabel = new JLabel("지역 : "+post.getRegion());
        regionLabel.setEnabled(true);
        regionLabel.setFont(new Font("Lucida Grande", Font.BOLD, 25));
        regionLabel.setBounds(46, 467, 372, 60);
        frame.getContentPane().add(regionLabel);

        // 게시글 카테고리 출력
        categoryLabel = new JLabel("카테고리 : "+post.getPostCategory().get("category"));
        categoryLabel.setFont(new Font("Lucida Grande", Font.BOLD, 25));
        categoryLabel.setEnabled(true);
        categoryLabel.setBounds(46, 539, 372, 60);
        frame.getContentPane().add(categoryLabel);

        // 뒤로가기 버튼
        backButton = new JButton("뒤로가기");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("뒤로가기 버튼 클릭");
                goBack(mgr, userId, mainPage);
                frame.setVisible(false);
            }
        });
        backButton.setBounds(46, 611, 117, 40);
        frame.getContentPane().add(backButton);

        // 게시글 제목 라벨 출력
        postTitlelable = new JLabel("게시글 제목");
        postTitlelable.setFont(new Font("Lucida Grande", Font.BOLD, 20));
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
        postContentLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
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
            @SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
            	String checker = "";
            	checker = mgr.controlGoodPointToPost(userId, post.getPostNum());
            	if(checker == "add") {
            		likeLabel.setText(Integer.toString(post.getGoodPoint().size()));            		
            		JOptionPane addGoodPointJOptionPane = new JOptionPane();
            		addGoodPointJOptionPane.showMessageDialog(null, "좋아요를 했습니다.");
            	}else if(checker == "delete") {
            		likeLabel.setText(Integer.toString(post.getGoodPoint().size()));
            		JOptionPane deleteGoodPointJOptionPane = new JOptionPane();
            		deleteGoodPointJOptionPane.showMessageDialog(null, "좋아요를 취소하였습니다.");
            	}
            	else {
            		JOptionPane erreJOptionPane = new JOptionPane();
            		erreJOptionPane.showMessageDialog(null, "이미 좋아요한 게시글입니다.");
				}
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
            	String checker = "";
            	checker = mgr.controlBadPointToPost(userId, post.getPostNum());
            	if(checker == "add") {
            		badLabel.setText(Integer.toString(post.getBadPoint().size()));            		
            		JOptionPane addGoodPointJOptionPane = new JOptionPane();
            		addGoodPointJOptionPane.showMessageDialog(null, "싫어요를 했습니다.");
            	}else if(checker == "delete") {
            		badLabel.setText(Integer.toString(post.getBadPoint().size()));
            		JOptionPane deleteGoodPointJOptionPane = new JOptionPane();
            		deleteGoodPointJOptionPane.showMessageDialog(null, "싫어요를 취소하였습니다.");
            	}
            	else {
            		JOptionPane erreJOptionPane = new JOptionPane();
            		erreJOptionPane.showMessageDialog(null, "이미 싫어요한 게시글입니다.");
				}
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
        commentLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
        commentLabel.setEnabled(true);
        commentLabel.setBounds(884, 42, 172, 35);
        frame.getContentPane().add(commentLabel);

        // 댓글 리스트
        JList<Comment> commentListPage = new JList<>();
        commentListPage.setBounds(884, 92, 334, 491);
        commentListPage.setFont(new Font("Lucida Grande", Font.BOLD, 15));
        DefaultListModel<Comment> commentListModel = new DefaultListModel<>();
        ArrayList<Comment> comments = mgr.searchComment(post.getPostNum());

        // 댓글 최신순 정렬
        for (int i = comments.size() - 1; i >= 0; i--) {
            commentListModel.addElement(comments.get(i));
        }

        commentListPage.setModel(commentListModel);

        JScrollPane scrollPane = new JScrollPane(commentListPage);
        scrollPane.setBounds(884, 92, 334, 491);
        frame.getContentPane().add(scrollPane);

        commentListPage.setCellRenderer(new DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (renderer instanceof JLabel) {
                    JLabel commentLabel = (JLabel) renderer;
                    commentLabel.setText("<html><p style='width:250px'>" + value.toString() + "</p></html>");
                    commentLabel.setPreferredSize(null); // Reset preferred size

                    // Calculate preferred height based on the content
                    int preferredHeight = calculatePreferredHeight(commentLabel);
                    commentLabel.setPreferredSize(new Dimension(250, preferredHeight));
                    commentLabel.setHorizontalAlignment(JLabel.LEFT);
                }
                return renderer;
            }

            private int calculatePreferredHeight(JLabel label) {
                FontMetrics metrics = label.getFontMetrics(label.getFont());
                String text = label.getText();
                int width = label.getPreferredSize().width;
                int lineCount = 0;
                for (String line : text.split("\n")) {
                    lineCount += Math.ceil((double) metrics.stringWidth(line) / width);
                }
                int height = metrics.getHeight() * lineCount;
                return height;
            }
        });

        // 댓글 작성 텍스트필드
        JEditorPane commentEditor = new JEditorPane();
        commentEditor.setBounds(884, 616, 251, 29);
        frame.getContentPane().add(commentEditor);

        // 업로드 버튼
        JButton commentUploadButton = new JButton("업로드");
        // 기능 추가
        commentUploadButton.addActionListener(new ActionListener() {
            @SuppressWarnings("static-access")
            public void actionPerformed(ActionEvent e) {
                mgr.controlCommentList(post.getPostNum(), userId, commentEditor.getText());
                JOptionPane addCommentJOptionPane = new JOptionPane();
                addCommentJOptionPane.showMessageDialog(null, "댓글을 업로드했습니다.");

                ArrayList<Comment> comments = mgr.searchComment(post.getPostNum());
                commentListModel.removeAllElements();

                // 댓글 최신순 정렬
                for (int i = comments.size() - 1; i >= 0; i--) {
                    commentListModel.addElement(comments.get(i));
                }
            }
        });

        commentUploadButton.setBounds(1147, 611, 71, 40);
        frame.getContentPane().add(commentUploadButton);

        commentUploadButton.setBounds(1147, 611, 71, 40);
        frame.getContentPane().add(commentUploadButton);
        
        frame.setResizable(false);
    }
    
    private void goBack(Manager mgr, String userId, MainPage MainPage) {
        SwingUtilities.invokeLater(() -> {
            frame.dispose(); // Close the current individual post page
            new MainPage(mgr, userId); // Open a new main page
            mainPage.dispose();
        });
    }


}

