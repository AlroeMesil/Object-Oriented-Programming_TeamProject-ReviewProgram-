package swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import mgr.Manager;
import swing.MainPage;


public class PostWritePage extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	public PostWritePage(Manager mgr, String userId) {
		setSize(1280,720);
		setTitle("게시글 작성");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        
        Font font = new Font(null, Font.PLAIN|Font.BOLD, 20);//글씨 크기 변경
        setLayout(new BorderLayout());
    	
        JPanel button = new JPanel(new BorderLayout());//카테고리+지역
        JPanel panel2 = new JPanel(new BorderLayout());//button+file
        JPanel north = new JPanel(new BorderLayout());//title+panel2
        
        //1. 게시글 제목
        JPanel title = new JPanel();
        JTextField titleField = new JTextField("제목",60);
        titleField.setPreferredSize(new Dimension(100,80));
        titleField.setFont(font);
        title.add(titleField);
        north.add(title,BorderLayout.NORTH);
        
       
        //2-1. 지역
        JRadioButton rb[] = new JRadioButton[13];
        String region_name[] = {"서울", "경기", "부산", "대구", "충북",
        		"충남","인천","전북","전남","대전","광주","경북","경남"}; 
        
        JPanel region = new JPanel();
        JLabel regionlabel = new JLabel("지역");
        
        regionlabel.setFont(font);
        region.add(regionlabel);


        ButtonGroup regiongroup = new ButtonGroup();
        
        for(int i=0; i<rb.length; i++){
            rb[i] = new JRadioButton(region_name[i]);
            regiongroup.add(rb[i]);//그룹추가
            region.add(rb[i]);//패널추가
            rb[i].addActionListener(this);
        }
        
       
         
        region.setBorder(new EmptyBorder(0, 50, 0, 20));
        region.setPreferredSize(new Dimension(550,70));
        
        
        //2-2. 카테고리
        JRadioButton cb[] = new JRadioButton[6];
        String category_name[] = {"관광명소", "맛집", "사진", 
        		"카페", "가족여행","연인"};
        
        JPanel category = new JPanel();
        JLabel calabel = new JLabel("카테고리");
        

        calabel.setFont(font);
        category.add(calabel);
        
        ButtonGroup categorygroup = new ButtonGroup();
        for(int i=0; i<cb.length; i++){
        	cb[i] = new JRadioButton(category_name[i]);
            categorygroup.add(cb[i]);
            category.add(cb[i]);
            cb[i].addActionListener(this);
        }
        
        category.setBorder(new EmptyBorder(0, 50, 0, 0));
        category.setPreferredSize(new Dimension(400,60));
        
        ////button패널에 region,category추가 
        button.add(region,BorderLayout.NORTH);
        button.add(category,BorderLayout.SOUTH);
        button.setBorder(new EmptyBorder(0, 100, 0, 20));

        
        //3.첨부파일
        JPanel file = new JPanel();
        JButton fileb = new JButton("파일 첨부");
        fileb.setPreferredSize(new Dimension(200,80));
        file.setBorder(new EmptyBorder(15, 0, 0, 300));
        ///버튼기능
        fileb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// 파일 선택 다이얼로그 열기
		        JFileChooser fileChooser = new JFileChooser();
		        int result = fileChooser.showOpenDialog(null);

		        if (result == JFileChooser.APPROVE_OPTION) {
		            // 사용자가 파일을 선택한 경우
		            File selectedFile = fileChooser.getSelectedFile();
		            String fileName = selectedFile.getName();

		            // 선택한 파일의 이름을 저장
		            JOptionPane.showMessageDialog(null, "선택한 파일: " + fileName);
		        }
				
			}
		});
        file.add(fileb);
        
        
        ////panel2에 button,file추가 
        panel2.add(button,BorderLayout.WEST);
        panel2.add(file,BorderLayout.EAST);
        /////north패널에 panel2추가 
        north.add(panel2,BorderLayout.SOUTH);
        add(north,BorderLayout.NORTH);
        
        //4.본문
        JPanel text = new JPanel();
        JTextField textField = new JTextField("본문내용",80);
        textField.setPreferredSize(new Dimension(150,400));
        text.add(textField);
        text.setBorder(new EmptyBorder(0, 0, 300, 20));
        add(text,BorderLayout.CENTER);
        
       //5. 뒤로가기, 업로드
        JPanel south = new JPanel(new BorderLayout());
        JPanel panel = new JPanel();
        JButton back = new JButton("뒤로가기");
        JButton upload = new JButton("업로드");
        
        upload.setPreferredSize(new Dimension(150,50));
        back.setPreferredSize(new Dimension(150,50));
        south.setBorder(new EmptyBorder(5, 150, 5, 100));
        
        south.add(back,BorderLayout.WEST);
        south.add(upload,BorderLayout.EAST);
        
        add(south,BorderLayout.SOUTH);
        
        ////뒤로가기 기능
        back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				goMainPage(mgr, userId);
			}
		});  
        
        ////업로드기능
        upload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                	
                	String titleText = titleField.getText();
                    String regionText = getSelectedButtonText(regiongroup);
                    String categoryText = getSelectedButtonText(categorygroup);
                    String bodyText = textField.getText();

                    //선택된 값이 없는 경우 예외 처리
                    if (titleText.equals("제목") || regionText == null || categoryText == null || bodyText.equals("본문내용")) {
                        JOptionPane.showMessageDialog(null, "입력이 완료되지 않았습니다.");
                        return;
                    }

                    //파일에 저장
                    FileWriter fw = new FileWriter("postList.txt", true);
                    BufferedWriter bf = new BufferedWriter(fw);

                    bf.write(titleText + "\n");
                    bf.write(regionText + "\n");
                    bf.write(categoryText + "\n");
                    bf.write(bodyText + "\n\n");

                    bf.close();

                    // 저장 성공 시
                    JOptionPane.showMessageDialog(null, "저장 성공");

               
                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(null, "실패");
                }
            }
        });
	}
	
	private String getSelectedButtonText(ButtonGroup buttonGroup) {
	    for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
	        AbstractButton button = buttons.nextElement();

	        if (button.isSelected()) {
	            return button.getText();
	        }
	    }
	    return null;
	}
	
	private void goMainPage(Manager mgr, String userId) {
        SwingUtilities.invokeLater(() -> {
        	new MainPage(mgr, userId); 
            dispose(); 
        });
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


}