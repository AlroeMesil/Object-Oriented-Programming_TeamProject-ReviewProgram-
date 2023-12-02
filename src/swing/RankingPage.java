package swing;

import main.Post;
import main.Ranking;
import mgr.Manageable;
import mgr.Manager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RankingPage extends JFrame {

    private Manager manager;

    public RankingPage(Manager manager) {
        this.manager = manager;

        setTitle("랭킹 페이지");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 전체 패널
        JPanel mainPanel = new JPanel(new BorderLayout());

        // 지역 랭킹을 표시하는 패널
        JPanel topRegionsPanel = new JPanel(new BorderLayout());

        // "지역랭킹" 글자 레이블
        JLabel regionRankingLabel = new JLabel("[지역랭킹]", SwingConstants.CENTER);
        regionRankingLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        topRegionsPanel.add(regionRankingLabel, BorderLayout.NORTH);

        // 지역 버튼을 나타내는 패널
        JPanel regionButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
        updateTopRegions(regionButtonPanel);
        topRegionsPanel.add(regionButtonPanel, BorderLayout.CENTER);

        // 카테고리 랭킹을 표시하는 패널
        JPanel topCategoriesPanel = new JPanel(new BorderLayout());

        // "카테고리랭킹" 글자 레이블
        JLabel categoryRankingLabel = new JLabel("[카테고리랭킹]", SwingConstants.CENTER);
        categoryRankingLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        topCategoriesPanel.add(categoryRankingLabel, BorderLayout.NORTH);

        // 카테고리 버튼을 나타내는 패널
        JPanel categoryButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        updateTopCategories(categoryButtonPanel);

        // 뒤로가기 버튼을 왼쪽에 추가
        categoryButtonPanel.add(createBackButton());
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

        ArrayList<String> topRegions = ranking.getTopRegions(postList, 5);

        for (int i = 0; i < topRegions.size(); i++) {
            String region = topRegions.get(i);
            JButton regionButton = createImageButton("images/" + region + ".png", region, i + 1);
            topRegionsPanel.add(regionButton);
        }
    }

    private void updateTopCategories(JPanel topCategoriesPanel) {
        ArrayList<Manageable> postList = manager.postList;
        Ranking ranking = new Ranking();

        ArrayList<String> topCategories = ranking.getTopCategories(postList, 5);

        for (int i = 0; i < topCategories.size(); i++) {
            String category = topCategories.get(i);
            JButton categoryButton = createImageButton("images/" + category + ".png", category, i + 1);
            topCategoriesPanel.add(categoryButton);
        }
    }

    private JButton createImageButton(String imagePath, String buttonText, int ranking) {
        JButton button = new JButton();

        // 레이아웃 매니저 설정
        button.setLayout(new BorderLayout());

        // 버튼 아이콘 설정
        ImageIcon icon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(185, 185, Image.SCALE_DEFAULT));
        button.setIcon(icon);

        // 랭킹 레이블 설정
        JLabel rankingLabel = new JLabel("[" + ranking + "위]");
        rankingLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // 랭킹 폰트 크기 조절
        rankingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        button.add(rankingLabel, BorderLayout.NORTH);

        JLabel nameLabel = new JLabel(buttonText);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // 이름 폰트 크기 조절
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        button.add(nameLabel, BorderLayout.SOUTH);

        button.setPreferredSize(new Dimension(200, 240));

        return button;
    }

    private JButton createBackButton() {
        JButton backButton = new JButton("뒤로가기");

        backButton.setLayout(new FlowLayout(FlowLayout.LEFT));
        backButton.setPreferredSize(new Dimension(200, 50));
        backButton.addActionListener(e -> goBack());

        return backButton;
    }

    private void goBack() {
        this.dispose();  // 현재 프레임 닫기
    }

    public static void main(String[] args) {
        Manager manager = new Manager();
        Post post = new Post();
        manager.readAllUser("userListData.txt");
        manager.readAllPost("postList.txt");
        SwingUtilities.invokeLater(() -> new RankingPage(manager));
    }
}
