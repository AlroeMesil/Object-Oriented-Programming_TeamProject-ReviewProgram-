package swing;
import javax.swing.*;

import main.Post;
import mgr.Manageable;
import mgr.Manager;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class MainPage extends JFrame {
	private final DefaultListModel<Manageable> recentPostsModel;
	public String userId;
	
	public void showMainPage(Manager mgr, String userId) {
	    recentPostsModel.clear();
	    for (Manageable post : mgr.postList) {
	        recentPostsModel.addElement(post);
	    }
	    revalidate();
	    repaint();
	}

	// Combined search method
	public void printPostsByCategoryAndRegionAndKeyword(String category, String region, String keyword, Manager mgr, JList<Manageable> recentPostsList) {
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
	
	 // Combined search method
    public void printPostsByCategoryAndRegion(String category, String region, Manager mgr, JList<Manageable> recentPostsList) {
        ArrayList<Manageable> filteredPosts = mgr.getPostsByCategoryAndRegion(category, region);

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


    public MainPage(Manager mgr, String userId) {
    	this.userId = userId;
        setTitle("관광 명소 리뷰 프로그램");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720); 

        // 중앙 패널
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        // 게시글 리스트
        recentPostsModel = new DefaultListModel<>();
        JList<Manageable> recentPostsList = new JList<>(recentPostsModel);
        PostListRenderer postListRenderer = new PostListRenderer(mgr, userId, this);
        recentPostsList.setCellRenderer(postListRenderer);
        
        recentPostsList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int index = recentPostsList.locationToIndex(evt.getPoint());
                    if (index >= 0) {
                        // Double-click detected on a valid index
                        Manageable selectedPost = recentPostsModel.getElementAt(index);
                        if (selectedPost instanceof Post) {
                            Post post = (Post) selectedPost;
                            postListRenderer.openIndividualPostPage(post.getPostNum());
                        }
                    }
                }
            }
        });
        JScrollPane recentPostsScrollPane = new JScrollPane(recentPostsList);
        ArrayList<Manageable> posts = mgr.postList;
        for (Manageable post : posts) {
            recentPostsModel.addElement(post);
        }
        centerPanel.add(recentPostsScrollPane, BorderLayout.CENTER);

        // 게시글 작성 버튼
        JButton writeButton = new JButton("게시글 작성");
        writeButton.addActionListener(e -> {
            // "게시글 작성" 버튼을 클릭했을 때 수행할 동작 추가
            JOptionPane.showMessageDialog(MainPage.this, "게시글 작성.");
        });
        centerPanel.add(writeButton, BorderLayout.SOUTH);
        
        JPanel  topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        JLabel regionLabel = new JLabel("지역 선택:");
        topPanel.add(regionLabel);
        
        // 지역 선택 String category, String region, Manager mgr, JList<Manageable> recentPostsList
        String[] regions = {"전체", "서울", "경기", "부산", "인천", "대구", "광주", "대전", "울산", "제주"};
        String[] categories = {"전체", "관광명소", "맛집"};
        JComboBox<String> regionComboBox = new JComboBox<>(regions);
        JComboBox<String> categoryComboBox = new JComboBox<>(categories);
        regionComboBox.addActionListener(e -> {
            String selectedRegion = (String) regionComboBox.getSelectedItem();
            String selectedCategory = (String) categoryComboBox.getSelectedItem();

            if ("전체".equals(selectedRegion)) {
                recentPostsModel.clear();
                for (Manageable post : mgr.postList) {
                    recentPostsModel.addElement(post);
                }
            } else {
            	printPostsByCategoryAndRegion(selectedCategory, selectedRegion, mgr, recentPostsList);
            }
        });

        topPanel.add(regionComboBox);
        
        // 카테고리 선택
        categoryComboBox.addActionListener(e -> {
            String selectedRegion = (String) regionComboBox.getSelectedItem();
            String selectedCategory = (String) categoryComboBox.getSelectedItem();

            if ("전체".equals(selectedCategory)) {
                recentPostsModel.clear();
                for (Manageable post : mgr.postList) {
                    recentPostsModel.addElement(post);
                }
            } else {
            	printPostsByCategoryAndRegion(selectedCategory, selectedRegion, mgr, recentPostsList);
            }
        });


        topPanel.add(categoryComboBox);
        
        // 검색창
        JTextField searchField = new JTextField(15);
        topPanel.add(searchField);

        // 검색 버튼
        JButton searchButton = new JButton("검색");
        searchButton.addActionListener(e -> {
            String searchText = searchField.getText();
            String selectedRegion = (String) regionComboBox.getSelectedItem();
            String selectedCategory = (String) categoryComboBox.getSelectedItem();

            if ("전체".equals(selectedRegion) && "전체".equals(selectedCategory)) {
                // If both region and category are "전체", search all posts without filtering
                recentPostsModel.clear();
                for (Manageable post : mgr.postList) {
                    recentPostsModel.addElement(post);
                }
            } else {
                // Use the combined search method
            	printPostsByCategoryAndRegionAndKeyword(selectedCategory, selectedRegion, searchText, mgr, recentPostsList);
            }
        });

        topPanel.add(searchButton);

        
        // 랭킹 페이지 이동 버튼
        JButton rankingButton = new JButton("랭킹 페이지");
        rankingButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(MainPage.this, "Searching for: ");
        });
        topPanel.add(rankingButton);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

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
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value instanceof Post) {
                Post post = (Post) value;
                String title = post.getPostTitle();
                String author = "작성자: " + post.getPostWriter();
                String likes = "좋아요: " + post.getGoodPoint().size();
                String dislikes = "싫어요: " + post.getBadPoint().size();

                Font titleFont = getFont().deriveFont(getFont().getSize() * 2.5f);
                Font authorFont = getFont().deriveFont(getFont().getSize() * 1.5f);
                Font likesFont = getFont().deriveFont(getFont().getSize() * 1.5f);
                Font dislikesFont = getFont().deriveFont(getFont().getSize() * 1.5f);

                String formattedText = "<html><body style='width: 800px;'>" +
                        "<div style='float: left;'>" +
                        "<b style='font-size:" + titleFont.getSize() + "'>" + title + "</b><br>" +
                        "<font style='font-size:" + authorFont.getSize() + "'>" + author + "</font>" +
                        "</div>" +
                        "<div style='float: right; text-align: right;'>" +
                        "<font style='font-size:" + likesFont.getSize() + "'>" + likes +
                        "</font> <font style='font-size:" + dislikesFont.getSize() + "'>" +
                        dislikes + "</font></div></body></html>";

                setText(formattedText);
                setIcon(new ImageIcon(post.getPostImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT)));

            }
            return this;
        }

    	// MainPage.java 내의 PostListRenderer 클래스 내의 openIndividualPostPage 메서드 수정
    	private void openIndividualPostPage(int postId) {
    	    EventQueue.invokeLater(() -> {
    	        try {
    	            indiviBoardPage individualPostPage = new indiviBoardPage(postId, mgr, userId, this.mainPage);
    	            this.mainPage.setContentPane(individualPostPage.getContentPanel());
    	            this.mainPage.revalidate();
    	            this.mainPage.repaint();
    	        } catch (Exception e) {
    	            e.printStackTrace();
    	        }
    	    });
    	}
    }

    
    public static void main(String[] args) {
        Manager mgr = new Manager();
        mgr.readAllPost("postList.txt");
        mgr.readAllUser("userListData.txt");
        mgr.readAllComment("commentList.txt");
        String userId = "aaa";
        
        SwingUtilities.invokeLater(() -> {
            MainPage mainPage = new MainPage(mgr, userId);
            mainPage.setVisible(true);
        });
    }
 }