package swing;

import main.Comment;
import main.Post;
import mgr.Manageable;
import mgr.Manager;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Scanner;

public class IndiviBoardPage extends JPanel {
	private static final long serialVersionUID = 1L;
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
	private JLabel commentLabel;
	private ControlPage parent;

	private final MainPage mainPage;

	public IndiviBoardPage(ControlPage parent, int postNum, Manager mgr, String userId, MainPage mainPage) {
		this.parent = parent;
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
		setLayout(null);
		// ================================ 이미지 ================================
		// 게시글 이미지 라벨 출력
		postImageLabel = new JLabel("게시글 이미지");
		postImageLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		postImageLabel.setEnabled(true);
		postImageLabel.setBounds(52, 50, 150, 39);
		add(postImageLabel);

		// 이미지 테두리 패널
//		JPanel imageBorderPane = new JPanel();
//		imageBorderPane.setBounds(52, 101, 339, 292);
//		imageBorderPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
//		add(imageBorderPane);

		// 게시글 이미지 출력
		JPanel imagePanel = new JPanel();
		imagePanel.setBounds(52, 101, 339, 292);
//		imageBorderPane.add(imagePanel);
		String imagePath = "images/post/" + post.getPostNum() + ".png"; // 프로젝트 절대 경로 수정 필요
		ImageIcon icon = new ImageIcon(
				new ImageIcon(imagePath).getImage().getScaledInstance(339, 292, Image.SCALE_DEFAULT));
		JLabel imageLabel = new JLabel(icon);
		imagePanel.add(imageLabel);
		add(imagePanel);
		// ================================ 이미지 ================================

		// ================================ 기타 ================================
		// 기타 정보 테두리 패널
		// 기타 정보 테두리 패널
		JPanel etcBorderPane = new JPanel();
		etcBorderPane.setBounds(52, 434, 339, 179);
		etcBorderPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		add(etcBorderPane);
		etcBorderPane.setLayout(null);

		// 게시글 작성자 라벨 출력
		writerLabel = new JLabel("  작성자 : " + post.getPostWriter());
		writerLabel.setBounds(2, 15, 300, 25);
		writerLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		etcBorderPane.add(writerLabel);

		// 게시글 지역 라벨 출력
		regionLabel = new JLabel("  지역 : " + post.getRegion());
		regionLabel.setBounds(2, 55, 300, 25);
		regionLabel.setEnabled(true);
		regionLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		etcBorderPane.add(regionLabel);

		// 게시글 카테고리 출력
		categoryLabel = new JLabel("  카테고리 : " + post.getPostCategory().get("category"));
		categoryLabel.setBounds(2, 95, 300, 25);
		categoryLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		etcBorderPane.add(categoryLabel);
		
		JLabel rateLabel = new JLabel("  평점 : "+post.getPostRate()+"점");
		rateLabel.setBounds(2, 135, 300, 25);
		rateLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		etcBorderPane.add(rateLabel);
		// ================================ 기타 ================================

		// ================================ 게시글 ================================
		// 게시글 제목 라벨 출력
		postTitlelable = new JLabel("게시글 제목");
		postTitlelable.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		postTitlelable.setEnabled(true);
		postTitlelable.setBounds(432, 50, 122, 39);
		add(postTitlelable);

		// 게시글 제목 테두리 패널
		JPanel postTitleBorderPane = new JPanel();
		postTitleBorderPane.setBounds(432, 101, 253, 50);
		postTitleBorderPane.setLayout(null);
		postTitleBorderPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		add(postTitleBorderPane);

		// 게시글 제목 출력
		postTitleTextField = new JTextField(post.getPostTitle());
		postTitleTextField.setEditable(false);
		postTitleTextField.setEnabled(true);
		postTitleTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		postTitleTextField.setBounds(6, 6, 241, 38);
		postTitleBorderPane.add(postTitleTextField);

		// 게시글 본문 라벨 출력
		postContentLabel = new JLabel("게시글 본문");
		postContentLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		postContentLabel.setEnabled(true);
		postContentLabel.setBounds(432, 163, 122, 39);
		add(postContentLabel);

		// 게시글 본문 테두리 패널
		JPanel postCotentBorderPane = new JPanel();
		postCotentBorderPane.setBounds(432, 214, 253, 399);
		postCotentBorderPane.setLayout(null);
		postCotentBorderPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		add(postCotentBorderPane);

		// 게시글 본문 출력
		postContentTextArea = new JTextArea(post.getPostContent());
		postContentTextArea.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		postContentTextArea.setEditable(false);
		postContentTextArea.setColumns(10);
		postContentTextArea.setBounds(6, 6, 241, 387);
		postContentTextArea.setLineWrap(true);
		postContentTextArea.setMargin(new Insets(10, 10, 10, 10));
		postCotentBorderPane.add(postContentTextArea);
		// ================================ 게시글 ================================

		// ================================ 좋아요/싫어요 ================================
		// 좋아요 버튼
		ImageIcon goodimg = new ImageIcon("./images/like.png");
		Image gimg = goodimg.getImage();
		Image goodimag1 = gimg.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		ImageIcon like = new ImageIcon(goodimag1);

		goodButton = new JButton(like);
		goodButton.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
				String checker = "";
				checker = mgr.controlGoodPointToPost(userId, post.getPostNum());
				if (checker == "add") {
					likeLabel.setText(Integer.toString(post.getGoodPoint().size()));
					JOptionPane addGoodPointJOptionPane = new JOptionPane();
					addGoodPointJOptionPane.showMessageDialog(null, "좋아요를 했습니다.");
				} else if (checker == "delete") {
					likeLabel.setText(Integer.toString(post.getGoodPoint().size()));
					JOptionPane deleteGoodPointJOptionPane = new JOptionPane();
					deleteGoodPointJOptionPane.showMessageDialog(null, "좋아요를 취소하였습니다.");
				} else {
					JOptionPane erreJOptionPane = new JOptionPane();
					erreJOptionPane.showMessageDialog(null, "이미 좋아요한 게시글입니다.");
				}
			}
		});
		goodButton.setBorderPainted(false);
		goodButton.setFocusPainted(false);
		goodButton.setContentAreaFilled(false);
		goodButton.setBounds(430, 649, 83, 50);
		add(goodButton);

		// 좋아요 라벨
		likeLabel = new JLabel(Integer.toString(post.getGoodPoint().size()));
		likeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		likeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		likeLabel.setEnabled(true);
		likeLabel.setBounds(480, 648, 61, 50);
		add(likeLabel);

		// 싫어요 버튼
		ImageIcon badimage = new ImageIcon("./images/bad.png");
		Image bimg = badimage.getImage();
		Image badimage1 = bimg.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		ImageIcon bad = new ImageIcon(badimage1);

		badButton = new JButton(bad);
		badButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String checker = "";
				checker = mgr.controlBadPointToPost(userId, post.getPostNum());
				if (checker == "add") {
					badLabel.setText(Integer.toString(post.getBadPoint().size()));
					JOptionPane addGoodPointJOptionPane = new JOptionPane();
					addGoodPointJOptionPane.showMessageDialog(null, "싫어요를 했습니다.");
				} else if (checker == "delete") {
					badLabel.setText(Integer.toString(post.getBadPoint().size()));
					JOptionPane deleteGoodPointJOptionPane = new JOptionPane();
					deleteGoodPointJOptionPane.showMessageDialog(null, "싫어요를 취소하였습니다.");
				} else {
					JOptionPane erreJOptionPane = new JOptionPane();
					erreJOptionPane.showMessageDialog(null, "이미 싫어요한 게시글입니다.");
				}
			}
		});
		badButton.setBorderPainted(false);
		badButton.setFocusPainted(false);
		badButton.setContentAreaFilled(false);
		badButton.setBounds(580, 649, 83, 50);
		add(badButton);

		// 싫어요 라벨
		badLabel = new JLabel(Integer.toString(post.getBadPoint().size()));
		badLabel.setHorizontalAlignment(SwingConstants.CENTER);
		badLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		badLabel.setEnabled(true);
		badLabel.setBounds(630, 648, 73, 50);
		add(badLabel);
		// ================================ 좋아요/싫어요 ================================

		// ================================ 댓글 ================================
		// 댓글 라벨
		commentLabel = new JLabel("댓글");
		commentLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		commentLabel.setEnabled(true);
		commentLabel.setBounds(722, 50, 122, 39);
		add(commentLabel);

		// 댓글 테두리 패널
		JPanel commentBorderPane = new JPanel();
		commentBorderPane.setBounds(722, 101, 253, 512);
		commentBorderPane.setLayout(null);
		commentBorderPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		add(commentBorderPane);

		// 댓글 리스트
		JList<Comment> commentListPage = new JList<>();
		commentListPage.setFont(new Font("Lucida Grande", Font.BOLD, 12));
		DefaultListModel<Comment> commentListModel = new DefaultListModel<>();
		ArrayList<Comment> comments = mgr.searchComment(post.getPostNum());

		JScrollPane scrollPane = new JScrollPane(commentListPage);
		scrollPane.setBounds(6, 6, 241, 456);
		commentBorderPane.add(scrollPane);

		// 댓글 최신순 정렬
		for (int i = comments.size() - 1; i >= 0; i--) {
			commentListModel.addElement(comments.get(i));
		}

		commentListPage.setModel(commentListModel);

		commentListPage.setCellRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (renderer instanceof JLabel) {
					JLabel commentLabel = (JLabel) renderer;
					commentLabel
							.setText("<html><p style='width:250px; margin:5px'>" + value.toString() + "</p></html>");
					commentLabel.setPreferredSize(null);

					int preferredHeight = calculatePreferredHeight(commentLabel);
					commentLabel.setPreferredSize(new Dimension(320, preferredHeight));
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

		scrollPane.setViewportView(commentListPage);

		// 댓글 작성 텍스트필드
		JEditorPane commentEditor = new JEditorPane();
		commentEditor.setBounds(6, 470, 169, 30);
		add(commentEditor);
		commentBorderPane.add(commentEditor);

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

		commentUploadButton.setBounds(178, 464, 69, 42);
		commentUploadButton.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		commentBorderPane.add(commentUploadButton);

		// 뒤로가기 버튼
		backButton = new JButton("뒤로가기");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("뒤로가기 버튼 클릭");
				parent.showMainPage(mgr, userId);
			}
		});
		backButton.setBounds(52, 649, 117, 50);
		add(backButton);

		// 게시글 삭제
		JButton deleteButton = new JButton("게시글 삭제");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("게시글 삭제 버튼 클릭");
				if (mgr.deletePost(post.getPostNum(), userId) == true) {
					JOptionPane.showMessageDialog(null, "게시글이 삭제되었습니다.");
					parent.showMainPage(mgr, userId);
				} else {
					JOptionPane.showMessageDialog(null, "게시글 작성자가 아닙니다.");
				}
			}
		});
		deleteButton.setBounds(858, 649, 117, 50);
		add(deleteButton); // deletePost

		JPanel background = new ImagePanel();
		background.setBounds(0, 0, 1024, 768);
		add(background);
	}
}
