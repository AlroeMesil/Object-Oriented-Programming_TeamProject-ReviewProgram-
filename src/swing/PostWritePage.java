package swing;


import java.awt.Color;
import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSlider;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import mgr.Manager;

import javax.swing.SwingConstants;


public class PostWritePage extends JPanel {
	private static final long serialVersionUID = 1L;
	private ControlPage parent;
	private JTextField postTitleTextField;
	private String selectedImagePath;
	private JFileChooser fileChooser;

	public PostWritePage(ControlPage parent, Manager mgr, String userId) {
		this.parent = parent;
        setLayout(null);
		
		JLabel postTitleLabel = new JLabel(" 게시글 제목");
		postTitleLabel.setFont(new Font("Lucida Grande", Font.BOLD, 25));
		postTitleLabel.setBounds(75, 42, 428, 45);
		postTitleLabel.setForeground(Color.WHITE);
		add(postTitleLabel);
		
		postTitleTextField = new JTextField();
		postTitleTextField.setToolTipText("");
		postTitleTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		postTitleTextField.setBounds(75, 96, 860, 52);
		add(postTitleTextField);
		postTitleTextField.setColumns(10);
		
		JButton backButton = new JButton("뒤로가기");
		backButton.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		backButton.addActionListener(e -> {
            parent.showMainPage(mgr, userId);
        });
		backButton.setBounds(56, 632, 117, 50);
		add(backButton);
		
		JEditorPane postContentPane = new JEditorPane();
		postContentPane.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		postContentPane.setBounds(75, 316, 860, 292);
		add(postContentPane);
		
		JLabel postContentLabel = new JLabel(" 게시글 본문");
		postContentLabel.setFont(new Font("Lucida Grande", Font.BOLD, 25));
		postContentLabel.setBounds(75, 259, 414, 45);
		postContentLabel.setForeground(Color.WHITE);
		add(postContentLabel);
		
		JLabel postRegionLabel = new JLabel(" 지역");
		postRegionLabel.setFont(new Font("Lucida Grande", Font.BOLD, 25));
		postRegionLabel.setBounds(75, 168, 80, 73);
		postRegionLabel.setForeground(Color.WHITE);
		add(postRegionLabel);
		
		JLabel postCategoryLabel = new JLabel("카테고리");
		postCategoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
		postCategoryLabel.setFont(new Font("Lucida Grande", Font.BOLD, 25));
		postCategoryLabel.setBounds(275, 168, 120, 73);
		postCategoryLabel.setForeground(Color.WHITE);
		add(postCategoryLabel);
		
		String postRegion[] = {"선택","경기","부산","대구","충북","충남","인천","전북","전남","대전","광주","경북","경남","제주"};
		JComboBox<String> postRegionComboBox = new JComboBox<>(postRegion);
		postRegionComboBox.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		postRegionComboBox.setBounds(150, 179, 94, 52);
		add(postRegionComboBox);
		
		String postCategoey[] = {"선택","관광명소","맛집","카페","연인","가족여행","사진"};
		JComboBox<String> postCategoryComboBox = new JComboBox<>(postCategoey);
		postCategoryComboBox.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		postCategoryComboBox.setBounds(395, 179, 94, 52);
		add(postCategoryComboBox);
		
		JLabel postRateLabel = new JLabel("평점");
		postRateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		postRateLabel.setFont(new Font("Lucida Grande", Font.BOLD, 25));
		postRateLabel.setBounds(524, 169, 54, 73);
		postRateLabel.setForeground(Color.WHITE);
		add(postRateLabel);
		
		JSlider slider = new JSlider();
		slider.setSnapToTicks(true);
		slider.setMinimum(1);
		slider.setValue(3);
		slider.setMaximum(5);
		slider.setBounds(590, 169, 150, 73);
		slider.setBackground(new Color(0, 0, 0, 0));
		add(slider);
		
		fileChooser = new JFileChooser();
	    fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));
		JButton addImageButton = new JButton("첨부파일");
		addImageButton.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		addImageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        int result = fileChooser.showOpenDialog(null);
		        if (result == JFileChooser.APPROVE_OPTION) {
		            selectedImagePath = fileChooser.getSelectedFile().getAbsolutePath();
		            System.out.print(selectedImagePath);
		        }
		    }
		});
		addImageButton.setBounds(793, 169, 142, 73);
		add(addImageButton);
		
		JButton uploadButton = new JButton("업로드");
		uploadButton.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		uploadButton.addActionListener(e -> {
			if (postTitleTextField.getText().equals("")) {
	        	JOptionPane.showMessageDialog(this, "제목을 입력해주세요");
	        } else if("선택".equals(postRegionComboBox.getSelectedItem())) {
	        	JOptionPane.showMessageDialog(this, "지역을 선택해주세요");
	        } else if("선택".equals(postCategoryComboBox.getSelectedItem())) {
	        	JOptionPane.showMessageDialog(this, "카테고리를 선택해주세요");
	        } else if("".equals(postContentPane.getText())) {
	        	JOptionPane.showMessageDialog(this, "게시글 내용을 입력해주세요");
	        } else if(selectedImagePath == null) {
	        	JOptionPane.showMessageDialog(this, "이미지를 추가해주세요");
	        } else {
	        	mgr.addPostList(userId, postTitleTextField.getText(), postRegionComboBox.getSelectedItem().toString(),
	                    postCategoryComboBox.getSelectedItem().toString(), slider.getValue(), postContentPane.getText(), selectedImagePath);
	        	mgr.printAllPost();
	        	JOptionPane.showMessageDialog(this, "게시글이 업로드되었습니다.");
	        	parent.showMainPage(mgr, userId);
	        }
        });
		uploadButton.setBounds(851, 632, 117, 50);
		add(uploadButton);
		
		JPanel background = new ImagePanel();
		background.setBounds(0, 0, 1024, 768);
		add(background);
		
	}
}