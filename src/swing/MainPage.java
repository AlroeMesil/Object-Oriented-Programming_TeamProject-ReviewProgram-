//package swing;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class MainPage extends JFrame {
//
//    public MainPage() {
//        setTitle("게시판 메인 페이지");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(600, 500);
//
//        JPanel topPanel = new JPanel();
//        topPanel.setLayout(new FlowLayout());
//
//        JLabel regionLabel = new JLabel("지역 선택:");
//        topPanel.add(regionLabel);
//        // 지역 선택
//        String[] regions = {"전체", "서울", "경기", "부산", "인천", "대구", "광주", "대전", "울산"};
//        JComboBox<String> regionComboBox = new JComboBox<>(regions);
//        topPanel.add(regionComboBox);
//
//        JLabel categoryLabel = new JLabel("카테고리 선택:");
//        topPanel.add(categoryLabel);
//        // 카테고리 선택
//        String[] categories = {"전체", "관광 명소", "맛집"};
//        JComboBox<String> categoryComboBox = new JComboBox<>(categories);
//        topPanel.add(categoryComboBox);
//
//        // 중앙 패널
//        JPanel centerPanel = new JPanel();
//        centerPanel.setLayout(new BorderLayout());
//
//        // 게시글 리스트
//        DefaultListModel<String> recentPostsModel = new DefaultListModel<>();
//        JList<String> recentPostsList = new JList<>(recentPostsModel);
//        JScrollPane recentPostsScrollPane = new JScrollPane(recentPostsList);
//        centerPanel.add(recentPostsScrollPane, BorderLayout.CENTER);
//
//        // 게시글 작성 버튼
//        JButton writeButton = new JButton("게시글 작성");
//        writeButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // "게시글 작성" 버튼을 클릭했을 때 수행할 동작 추가
//                JOptionPane.showMessageDialog(MainPage.this, "게시글 작성.");
//            }
//        });
//        centerPanel.add(writeButton, BorderLayout.SOUTH);
//
//        // 전체 프레임에 상단과 중앙 패널 추가
//        add(topPanel, BorderLayout.NORTH);
//        add(centerPanel, BorderLayout.CENTER);
//
//        // 화면 가운데 정렬
//        setLocationRelativeTo(null);
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new MainPage().setVisible(true);
//            }
//        });
//    }
//}
