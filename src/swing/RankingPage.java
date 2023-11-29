package swing;

import javax.swing.JFrame;

import main.Post;
import main.Ranking;
import mgr.Manageable;
import mgr.Manager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class RankingPage extends JFrame {
	private static final long serialVersionUID = 1L;
	private Manager manager;

    public RankingPage(Manager manager) {
        this.manager = manager;

        setTitle("랭킹 페이지");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720); 
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        // JPanel for displaying top 5 posts with titles and images
        JPanel topPostsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));


        for (int i = 0; i < 5; i++) {
            // JPanel for each post (image and title)
            JPanel postPanel = new JPanel(new BorderLayout());
            topPostsPanel.add(postPanel);

            // JLabel for post images
            JLabel postImageLabel = new JLabel();
            postPanel.add(postImageLabel, BorderLayout.CENTER);

            // JLabel for post titles
            JLabel postTitleLabel = new JLabel();
            postPanel.add(postTitleLabel, BorderLayout.SOUTH);
        }

        // JPanel for displaying region and category rankings
        JPanel rankingPanel = new JPanel(new GridLayout(1, 2));

        JPanel regionRankingPanel = new JPanel(new BorderLayout());
        JTextArea regionRankingTextArea = new JTextArea();
        regionRankingTextArea.setEditable(false);
        regionRankingPanel.add(new JScrollPane(regionRankingTextArea), BorderLayout.CENTER);

        // Create button for region rankings
        JButton regionRankingButton = new JButton("지역 랭킹 갱신");
        regionRankingButton.addActionListener(e -> updateRegionRanking(regionRankingTextArea));
        regionRankingPanel.add(regionRankingButton, BorderLayout.SOUTH);
        rankingPanel.add(regionRankingPanel);

        JPanel categoryRankingPanel = new JPanel(new BorderLayout());
        JTextArea categoryRankingTextArea = new JTextArea();
        categoryRankingTextArea.setEditable(false);
        categoryRankingPanel.add(new JScrollPane(categoryRankingTextArea), BorderLayout.CENTER);

        // Create button for category rankings
        JButton categoryRankingButton = new JButton("카테고리 랭킹 갱신");
        categoryRankingButton.addActionListener(e -> updateCategoryRanking(categoryRankingTextArea));
        categoryRankingPanel.add(categoryRankingButton, BorderLayout.SOUTH);
        rankingPanel.add(categoryRankingPanel);

        // JSplitPane to display top posts above and rankings below
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(rankingPanel), new JScrollPane(topPostsPanel));
        splitPane.setDividerLocation(200); // Adjustable value
        add(splitPane);

        // Update rankings immediately when the GUI is launched
        updateRegionRanking(regionRankingTextArea);
        updateCategoryRanking(categoryRankingTextArea);
        updateTopPosts(topPostsPanel);
    }

    private void updateRegionRanking(JTextArea regionRankingTextArea) {
        ArrayList<Manageable> postList = manager.postList;
        Ranking ranking = new Ranking();
        String result = ranking.printPostsByRegionRanking(postList);
        regionRankingTextArea.setText(result);
    }

    private void updateCategoryRanking(JTextArea categoryRankingTextArea) {
        ArrayList<Manageable> postList = manager.postList;
        Ranking ranking = new Ranking();
        String result = ranking.printPostsByCategoryRanking(postList);
        categoryRankingTextArea.setText(result);
    }

    private void updateTopPosts(JPanel topPostsPanel) {
        ArrayList<Manageable> postList = manager.postList;
        Ranking ranking = new Ranking();

        // Sort posts by the number of likes in descending order
        postList.sort(Comparator.comparingInt(o -> ((Post) o).getGoodPoint().size()).reversed());

        // Select the top 5 posts
        ArrayList<Post> top5Posts = new ArrayList<>();
        int count = Math.min(5, postList.size());
        for (int i = 0; i < count; i++) {
            if (postList.get(i) instanceof Post) {
                top5Posts.add((Post) postList.get(i));
            }
        }

        // Display post titles and images using JButtons
        for (int i = 0; i < 5; i++) {
            if (i < top5Posts.size()) {
                Post post = top5Posts.get(i);
                JButton postButton = new JButton();

                // Use absolute path instead of relative path
                String imagePath = "images/" + post.getPostNum() + ".png";
                ImageIcon icon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT));
                postButton.setIcon(icon);

                // Set the layout manager for the JButton
                postButton.setLayout(new BorderLayout());

                // JLabel for post titles
                JLabel postTitleLabel = new JLabel((i + 1) + ". " + post.getPostTitle());
                postButton.add(postTitleLabel, BorderLayout.SOUTH);

                // Set the preferred size of the JButton
                postButton.setPreferredSize(new Dimension(250, 300)); // Adjust the values as needed

                // Add button to the topPostsPanel
                topPostsPanel.add(postButton);
            }
        }
    }
}