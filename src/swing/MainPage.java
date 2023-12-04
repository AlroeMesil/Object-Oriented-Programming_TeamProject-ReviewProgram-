package swing;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

import java.awt.CardLayout;
import java.awt.Component;

import main.Comment;
import main.Post;
import mgr.Manageable;
import mgr.Manager;

import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class MainPage extends JPanel {
	private CardLayout cardLayout;
    private JPanel cardPanel;
	private JTextField searchTextField;
	private ControlPage parent;
	private String userId;
	private int postId;
	private final DefaultListModel<Manageable> recentPostsModel;
	private IndiviBoardPage indiviBoardPage;

	public void printPostsByCategoryAndRegionAndKeyword(String category, String region, String keyword, Manager mgr,
			JList<Manageable> recentPostsList) {
		ArrayList<Manageable> filteredPosts = mgr.getPostsByCategoryAndRegion(category, region);

		if (!keyword.isEmpty()) {
			filteredPosts = filterPostsByKeyword(filteredPosts, keyword);
		}

		if (!filteredPosts.isEmpty()) {
			recentPostsModel.clear();

			for (Manageable post : filteredPosts) {
				recentPostsModel.addElement(post);
			}

			recentPostsList.revalidate();
			recentPostsList.repaint();
		} else {
	        JOptionPane.showMessageDialog(MainPage.this, "일치하는 게시글이 없습니다.");
		}
	}

	public void printPostsByRegion(String region, Manager mgr, JList<Manageable> recentPostsList) {
		ArrayList<Manageable> filteredPosts = mgr.getPostsByRegion(region);

		if (!filteredPosts.isEmpty()) {
			recentPostsModel.clear();

			for (Manageable post : filteredPosts) {
				recentPostsModel.addElement(post);
			}

			recentPostsList.revalidate();
			recentPostsList.repaint();
		} else {
            JOptionPane.showMessageDialog(this, "일치하는 게시글이 없습니다.");
		}
	}
	
	public void printPostsByCategory(String category, Manager mgr, JList<Manageable> recentPostsList) {
		ArrayList<Manageable> filteredPosts = mgr.getPostsByCategory(category);

		if (!filteredPosts.isEmpty()) {
			recentPostsModel.clear();

			for (Manageable post : filteredPosts) {
				recentPostsModel.addElement(post);
			}

			recentPostsList.revalidate();
			recentPostsList.repaint();
		} else {
            JOptionPane.showMessageDialog(this, "일치하는 게시글이 없습니다.");
		}
	}
	
	public void printPostsByCategoryAndRegion(String category, String region, Manager mgr,
			JList<Manageable> recentPostsList) {
		ArrayList<Manageable> filteredPosts = mgr.getPostsByCategoryAndRegion(category, region);

		if (!filteredPosts.isEmpty()) {
			recentPostsModel.clear();

			for (Manageable post : filteredPosts) {
				recentPostsModel.addElement(post);
			}

			recentPostsList.revalidate();
			recentPostsList.repaint();
		} else {
            JOptionPane.showMessageDialog(this, "일치하는 게시글이 없습니다.");
		}
	}

	private ArrayList<Manageable> filterPostsByKeyword(ArrayList<Manageable> posts, String keyword) {
		ArrayList<Manageable> filteredPosts = new ArrayList<>();

		for (Manageable post : posts) {
			if (post instanceof Post) {
				Post castedPost = (Post) post;
				if (castedPost.getPostTitle().contains(keyword) || castedPost.getPostContent().contains(keyword)) {
					filteredPosts.add(post);
				}
			}
		}

		return filteredPosts;
	}
	
	public MainPage(ControlPage parent, Manager mgr, String userId) {
		this.parent = parent;
		this.userId = userId;
		recentPostsModel = new DefaultListModel<>();
		JList<Manageable> recentPostsList = new JList<>(recentPostsModel);
        PostListRenderer postListRenderer = new PostListRenderer(mgr, userId, this);
        recentPostsList.setCellRenderer(postListRenderer);
		setLayout(null);

		String postRegion[] = { "전체", "서울", "경기", "부산", "강원", "대구", "충북", "충남", "인천", "전북", "전남", "대전", "광주", "경북", "경남", "제주" };

		String postCategoey[] = { "전체", "관광명소", "맛집", "카페", "연인", "가족여행", "사진", "알뜰여행" };

		JPanel menuPanel = new JPanel();
		menuPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		menuPanel.setBounds(75, 53, 860, 73);
		add(menuPanel);
		menuPanel.setLayout(null);

		JLabel postRegionLabel = new JLabel(" 지역");
		postRegionLabel.setBounds(23, 2, 52, 73);
		menuPanel.add(postRegionLabel);
		postRegionLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));

		JComboBox<String> postRegionComboBox = new JComboBox<>(postRegion);
		JComboBox<String> postCategoryComboBox = new JComboBox<>(postCategoey);
		postCategoryComboBox.addActionListener(e -> {
		    String selectedRegion = (String) postRegionComboBox.getSelectedItem();
		    String selectedCategory = (String) postCategoryComboBox.getSelectedItem();
		    
		    if ("전체".equals(selectedRegion) && "전체".equals(selectedCategory)) {
		        recentPostsModel.clear();
		        for (Manageable post : mgr.postList) {
		            recentPostsModel.addElement(post);
		        }
		    } else {
		        printPostsByCategoryAndRegion(selectedCategory, selectedRegion, mgr, recentPostsList);
		    }
		});
		postRegionComboBox.setBounds(73, 13, 94, 52);
		postRegionComboBox.addActionListener(e -> {
		    String selectedRegion = (String) postRegionComboBox.getSelectedItem();
		    String selectedCategory = (String) postCategoryComboBox.getSelectedItem();
		    
		    if ("전체".equals(selectedRegion) && "전체".equals(selectedCategory)) {
		        recentPostsModel.clear();
		        for (Manageable post : mgr.postList) {
		            recentPostsModel.addElement(post);
		        }
		    } else {
		        printPostsByCategoryAndRegion(selectedCategory, selectedRegion, mgr, recentPostsList);
		    }
		});
		menuPanel.add(postRegionComboBox);
		postRegionComboBox.setFont(new Font("Lucida Grande", Font.PLAIN, 15));

		JLabel postCategoryLabel = new JLabel("카테고리");
		postCategoryLabel.setBounds(168, 2, 102, 73);
		menuPanel.add(postCategoryLabel);
		postCategoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
		postCategoryLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));

		postCategoryComboBox.setBounds(263, 13, 94, 52);
		menuPanel.add(postCategoryComboBox);
		postCategoryComboBox.setFont(new Font("Lucida Grande", Font.PLAIN, 15));

		searchTextField = new JTextField();
		searchTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		searchTextField.setBounds(369, 25, 187, 26);
		menuPanel.add(searchTextField);
		searchTextField.setColumns(10);

		JButton searchButton = new JButton("검색");
		searchButton.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		searchButton.addActionListener(e -> {
			String selectedRegion = (String) postRegionComboBox.getSelectedItem();
		    String selectedCategory = (String) postCategoryComboBox.getSelectedItem();
		    String keyword = searchTextField.getText();
			printPostsByCategoryAndRegionAndKeyword(selectedCategory, selectedRegion, keyword, mgr, recentPostsList);
		});
		searchButton.setBounds(568, 19, 78, 40);
		menuPanel.add(searchButton);

		String sortList[] = {"최신순", "오래된순", "좋아요순"};
		JComboBox<String> sortComboBox = new JComboBox<>(sortList);
		sortComboBox.addActionListener(e -> {
		    String selectedSort = (String) sortComboBox.getSelectedItem();
		    if ("최신순".equals(selectedSort)) {
		        List<Manageable> sortedPosts = new ArrayList<>(mgr.postList);
		        sortedPosts.sort(Comparator.comparingInt(post -> ((Post) post).getPostNum()).reversed());
		        recentPostsModel.clear();
		        for (Manageable post : sortedPosts) {
		            recentPostsModel.addElement(post);
		        }
		    } else if("오래된순".equals(selectedSort)) {
		    	recentPostsModel.clear();
		        for (Manageable post : mgr.postList) {
		            recentPostsModel.addElement(post);
		        }
		    } else if ("좋아요순".equals(selectedSort)) {
		        List<Manageable> sortedPosts = new ArrayList<>(mgr.postList);
		        sortedPosts.sort(Comparator.comparingInt(post -> ((Post) post).getGoodPoint().size()).reversed());
		        recentPostsModel.clear();
		        for (Manageable post : sortedPosts) {
		            recentPostsModel.addElement(post);
		        }
		    } else {
		        recentPostsModel.clear();
		    }
		});
		sortComboBox.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		sortComboBox.setBounds(725, 13, 94, 52);
		menuPanel.add(sortComboBox);

		JLabel sortLabel = new JLabel("정렬");
		sortLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sortLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		sortLabel.setBounds(658, 2, 78, 73);
		menuPanel.add(sortLabel);

		// ==========================================================
		// 게시글 리스트
		recentPostsList.setCellRenderer(postListRenderer);

		recentPostsList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					int index = recentPostsList.locationToIndex(evt.getPoint());
					if (index >= 0) {
						// Double-click detected on a valid index
						Manageable selectedPost = recentPostsModel.getElementAt(index);
						System.out.println("게시글 ID : "+((Post)selectedPost).getPostNum());
						if (selectedPost instanceof Post) {
					        parent.showIndiviBoardPage(((Post) selectedPost).getPostNum(), mgr);
						}
					}
				}
			}
		});

		JScrollPane recentPostsScrollPane = new JScrollPane(recentPostsList);
        List<Manageable> sortedPosts = new ArrayList<>(mgr.postList);
        sortedPosts.sort(Comparator.comparingInt(post -> ((Post) post).getPostNum()).reversed());

        recentPostsModel.clear();
        for (Manageable post : sortedPosts) {
            recentPostsModel.addElement(post);
        }
		recentPostsScrollPane.setBounds(75, 188, 860, 399);
		add(recentPostsScrollPane);

		// ==========================================================

		JButton postWriteButton = new JButton("게시글 작성");
		postWriteButton.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		postWriteButton.addActionListener(e -> {
			parent.showPostWritePage(mgr);
		});
		postWriteButton.setBounds(791, 632, 167, 50);
		add(postWriteButton);

		JButton rankingButton = new JButton("랭킹 페이지");
		rankingButton.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		rankingButton.addActionListener(e -> {
			parent.showRankingPage(mgr);
		});
		rankingButton.setBounds(56, 632, 167, 50);
		add(rankingButton);
		
		JPanel background = new ImagePanel();
		background.setBounds(0, 0, 1024, 768);
		add(background);

	}

	private static class PostListRenderer extends DefaultListCellRenderer {
	    private final Manager mgr;
	    private final String userId;
	    private final MainPage mainPage;

	    public PostListRenderer(Manager mgr, String userId, MainPage mainPage) {
	        this.mgr = mgr;
	        this.userId = userId;
	        this.mainPage = mainPage;
	    }

	    @Override
	    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
	            boolean cellHasFocus) {
	        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

	        if (value instanceof Post) {
	            Post post = (Post) value;
	            ArrayList<Comment> commentLists = mgr.searchComment(post.getPostNum());
	            String title = post.getPostTitle();
	            String region = "#"+post.getRegion();
	            String category = "#"+post.getPostCategory().get("category");
	            String author = "작성자 : " + post.getPostWriter();
	            String likes = "좋아요 : " + post.getGoodPoint().size();
	            String dislikes = "싫어요 : " + post.getBadPoint().size();
	            String postRate = "평점 : " + post.getPostRate();  
	            String comments = "댓글 : " + commentLists.size(); 

	            Font titleFont = getFont().deriveFont(getFont().getSize() * 2f);
	            Font regionFont = getFont().deriveFont(getFont().getSize() * 1.5f);
	            Font categoryFont = getFont().deriveFont(getFont().getSize() * 1.5f);
	            Font authorFont = getFont().deriveFont(getFont().getSize() * 1.5f);
	            Font likesFont = getFont().deriveFont(getFont().getSize() * 1.5f);
	            Font dislikesFont = getFont().deriveFont(getFont().getSize() * 1.5f);
	            Font postRateFont = getFont().deriveFont(getFont().getSize() * 1.5f);
	            Font commentsFont = getFont().deriveFont(getFont().getSize() * 1.5f);

	            String formattedText = "<html><body style='width: 500px;'>" + "<div style='float: left;'>"
	                    + "<b style='font-size:" + titleFont.getSize() + "'>" + title + "</b><br>"
	                    + "<font style='font-size:" + regionFont.getSize() + "; color: green;'>" + region + "</font>&nbsp;&nbsp;&nbsp;"
	                    + "<font style='font-size:" + categoryFont.getSize() + "; color: blue;'>" + category + "</font><br>"
	                    + "<font style='font-size:" + authorFont.getSize() + "'>" + author + "</font>" + "</div>"
	                    + "<div style='float: right; text-align: right;'>" + "<font style='font-size:"
	                    + likesFont.getSize() + "'>" + likes + "</font>&nbsp;&nbsp;&nbsp;<font style='font-size:"
	                    + dislikesFont.getSize() + "'>" + dislikes + "</font>&nbsp;&nbsp;&nbsp;<font style='font-size:"
	                    + postRateFont.getSize() + "'>" + postRate + "</font>&nbsp;&nbsp;&nbsp;<font style='font-size:"
	                    + commentsFont.getSize() + "'>" + comments + "</font></div></body></html>";

	            setText(formattedText);
	            setIcon(new ImageIcon(post.getPostImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
	        }
	        return this;
	    }
	}

}