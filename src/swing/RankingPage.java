package swing;

import main.Post;
import main.Ranking;
import mgr.Manageable;
import mgr.Manager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RankingPage extends JPanel {

	private ControlPage parnent;
    private Manager manager;

    public RankingPage(ControlPage parent, Manager manager, String userId) {
    	this.parnent = parent;
        this.manager = manager;
        
//        JPanel background = new ImagePanel();
//		background.setBounds(0, 0, 1024, 768);
//		add(background);
        
        // "지역랭킹" 글자 레이블
        JLabel regionRankingLabel = new JLabel("[지역 랭킹]", SwingConstants.CENTER);
        regionRankingLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
        add(regionRankingLabel, BorderLayout.NORTH);

        // 지역 버튼을 나타내는 패널
        JPanel regionButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
        updateTopRegions(regionButtonPanel);
        add(regionButtonPanel, BorderLayout.CENTER);

        // "카테고리랭킹" 글자 레이블
        JLabel categoryRankingLabel = new JLabel("[카테고리 랭킹]", SwingConstants.CENTER);
        categoryRankingLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
        add(categoryRankingLabel, BorderLayout.NORTH);

        // 카테고리 버튼을 나타내는 패널
        JPanel categoryButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        updateTopCategories(categoryButtonPanel);

        // 뒤로가기 버튼을 왼쪽에 추가
        add(categoryButtonPanel, BorderLayout.CENTER);
        
        JButton backButton = new JButton("뒤로가기");
        backButton.setBounds(56, 632, 167, 50);
        backButton.setLayout(null);
        backButton.addActionListener(e->{
        	parent.showMainPage(manager,userId);
        });
        add(backButton);
    }

    private JButton createImageButton(String imagePath, String buttonText, int ranking, int count) {
        JButton button = new JButton();

        // 레이아웃 매니저 설정
        button.setLayout(new BorderLayout());

        // 버튼 아이콘 설정
        ImageIcon icon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(185, 185, Image.SCALE_DEFAULT));
        button.setIcon(icon);

        // 랭킹 레이블 설정
        JLabel rankingLabel = new JLabel("[" + ranking + "위]");
        rankingLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16)); // 랭킹 폰트 크기 조절
        rankingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        button.add(rankingLabel, BorderLayout.NORTH);

        // 이름 레이블 설정
        JLabel nameLabel = new JLabel(buttonText + " : " + count + "개");
        nameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16)); // 이름 폰트 크기 조절
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        button.add(nameLabel, BorderLayout.SOUTH);

        button.setPreferredSize(new Dimension(200, 240));

        return button;
    }

    private void updateTopRegions(JPanel topRegionsPanel) {
        ArrayList<Manageable> postList = manager.postList;
        Ranking ranking = new Ranking();

        ArrayList<String> topRegions = ranking.getTopRegions(postList, 5);

        for (int i = 0; i < topRegions.size(); i++) {
            String region = topRegions.get(i);
            int count = getCountForRegion(region, postList);
            JButton regionButton = createImageButton("images/region/" + region + ".png", region, i + 1, count);
            regionButton.setBackground(Color.WHITE);
            topRegionsPanel.add(regionButton);
        }
    }

    private int getCountForRegion(String region, ArrayList<Manageable> postList) {
        int count = 0;

        for (Manageable post : postList) {
            if (post instanceof Post && ((Post) post).getRegion().equalsIgnoreCase(region)) {
                count++;
            }
        }

        return count;
    }


    private void updateTopCategories(JPanel topCategoriesPanel) {
        ArrayList<Manageable> postList = manager.postList;
        Ranking ranking = new Ranking();

        ArrayList<String> topCategories = ranking.getTopCategories(postList, 5);

        for (int i = 0; i < topCategories.size(); i++) {
            String category = topCategories.get(i);
            int count = getCountForCategory(category, postList);
            JButton categoryButton = createImageButton("images/category/" + category + ".png", category, i + 1, count);
            categoryButton.setBackground(Color.WHITE);
            topCategoriesPanel.add(categoryButton);
        }
    }

    private int getCountForCategory(String category, ArrayList<Manageable> postList) {
        int count = 0;

        for (Manageable post : postList) {
            if (post instanceof Post && ((Post) post).getPostCategory().get("category").equalsIgnoreCase(category)) {
                count++;
            }
        }

        return count;
    }



    private JButton createBackButton() {
        JButton backButton = new JButton("뒤로가기");

        backButton.setLayout(new FlowLayout(FlowLayout.LEFT));
        backButton.setPreferredSize(new Dimension(200, 50));
//        backButton.addActionListener();

        return backButton;
    }
}