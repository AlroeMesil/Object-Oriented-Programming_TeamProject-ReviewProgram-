package swing;

import main.Post;
import main.Ranking;
import mgr.Manageable;
import mgr.Manager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class RankingPage extends JFrame {

    private Manager manager;

    public RankingPage(Manager manager) {
        this.manager = manager;

        setTitle("랭킹 페이지");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 전체 패널
        JPanel mainPanel = new JPanel(new BorderLayout());

        // 지역 랭킹을 표시하는 패널
        JPanel topRegionsPanel = new JPanel(new BorderLayout());

        // 1행: "지역랭킹" 글자를 나타내는 레이블
        JLabel regionRankingLabel = new JLabel("[지역랭킹]", SwingConstants.CENTER);
        regionRankingLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        topRegionsPanel.add(regionRankingLabel, BorderLayout.NORTH);

        // 2행: 지역 버튼을 나타내는 패널
        JPanel regionButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
        updateTopRegions(regionButtonPanel);
        topRegionsPanel.add(regionButtonPanel, BorderLayout.CENTER);

        // 카테고리 랭킹을 표시하는 패널
        JPanel topCategoriesPanel = new JPanel(new BorderLayout());

        // 1행: "카테고리랭킹" 글자를 나타내는 레이블
        JLabel categoryRankingLabel = new JLabel("[카테고리랭킹]", SwingConstants.CENTER);
        categoryRankingLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        topCategoriesPanel.add(categoryRankingLabel, BorderLayout.NORTH);

        // 2행: 카테고리 버튼을 나타내는 패널
        JPanel categoryButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
        updateTopCategories(categoryButtonPanel);
        topCategoriesPanel.add(categoryButtonPanel, BorderLayout.CENTER);

        // 하단에 상위 게시물 및 랭킹을 표시하는 JSplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topRegionsPanel, topCategoriesPanel);
        splitPane.setDividerLocation(360); // 조절 가능한 값
        mainPanel.add(splitPane, BorderLayout.CENTER);

        add(mainPanel);

        setLocationRelativeTo(null); // 화면 중앙에 프레임 배치
        setVisible(true);
    }

    private void updateTopRegions(JPanel topRegionsPanel) {
        ArrayList<Manageable> postList = manager.postList;
        Ranking ranking = new Ranking();

        // Get the list of top regions
        ArrayList<String> topRegions = ranking.getTopRegions(postList, 5);

        // Update the top regions buttons
        for (int i = 0; i < topRegions.size(); i++) {
            String region = topRegions.get(i);
            JButton regionButton = createImageButton("images/" + region + ".png", region, i + 1);
            topRegionsPanel.add(regionButton);
        }
    }
    private void updateTopCategories(JPanel topCategoriesPanel) {
        ArrayList<Manageable> postList = manager.postList;
        Ranking ranking = new Ranking();

        // Get the list of top categories
        ArrayList<String> topCategories = ranking.getTopCategories(postList, 5);

        // Update the top categories buttons
        for (int i = 0; i < topCategories.size(); i++) {
            String category = topCategories.get(i);
            JButton categoryButton = createImageButton("images/" + category + ".png", category, i + 1);
            topCategoriesPanel.add(categoryButton);
        }
    }

    private JButton createImageButton(String imagePath, String buttonText, int ranking) {
        JButton button = new JButton();

        // Set layout manager
        button.setLayout(new BorderLayout());

        // Set button icon
        ImageIcon icon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(210, 210, Image.SCALE_DEFAULT));
        button.setIcon(icon);

        // Set ranking label
        JLabel rankingLabel = new JLabel("[" + ranking + "위]");
        rankingLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // 순위 폰트 크기 조절
        rankingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        button.add(rankingLabel, BorderLayout.NORTH);

        // Set button text
        JLabel nameLabel = new JLabel(buttonText);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 20)); // 이름 폰트 크기 조절
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        button.add(nameLabel, BorderLayout.SOUTH);

        // Set preferred size
        button.setPreferredSize(new Dimension(220, 270));

        button.addActionListener(e -> {
            // 버튼 클릭 시 검색 결과 페이지로 이동
           // goToSearchResults(buttonText, ranking, MainPage, mgr);
        });
        return button;
    }
    // RankingPage 클래스의 goToSearchResults 메서드 수정
    private void goToSearchResults(String buttonText, int ranking, MainPage mainPage, Manager mgr) {
        // 선택된 지역 또는 카테고리에 따라 검색 결과 페이지로 이동
    }
    public static void main(String[] args) {
        Manager manager = new Manager();
        Post post = new Post();
        manager.readAllUser("userListData.txt");
        manager.readAllPost("postList.txt");
        SwingUtilities.invokeLater(() -> new RankingPage(manager));
    }
}
