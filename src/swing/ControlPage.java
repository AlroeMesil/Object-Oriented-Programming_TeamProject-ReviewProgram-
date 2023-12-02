package swing;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import mgr.Manager;

public class ControlPage {

    JFrame frame;

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private SignInPage signInPage;
    private SignUpPage signUpPage;
    private MainPage mainPage;
    private PostWritePage postWritePage;
    private IndiviBoardPage indiviBoardPage;
    public String userId;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
    	Manager mgr = new Manager();
        mgr.readAllPost("postList.txt");
        mgr.readAllUser("userListData.txt");
        mgr.readAllComment("commentList.txt");
        EventQueue.invokeLater(() -> {
            try {
            	ControlPage window = new ControlPage(mgr);
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     */
    public ControlPage(Manager mgr) {
        initialize(mgr);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize(Manager mgr) {
        frame = new JFrame();
        frame.setBounds(100, 100, 1024, 768);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
//        frame.setVisible(true);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        signInPage = new SignInPage(this, mgr);
        signUpPage = new SignUpPage(this, mgr);
        

        cardPanel.add(signInPage, "SignIn");
        cardPanel.add(signUpPage, "SignUp");
        

        frame.getContentPane().add(cardPanel);

        showSignInPage();
    }

    public void showSignInPage() {
        cardLayout.show(cardPanel, "SignIn");
    }

    public void showSignUpPage() {
        cardLayout.show(cardPanel, "SignUp");
    }
    
    public void showMainPage(Manager mgr, String userId) {
    	mainPage = new MainPage(this, mgr, userId);
    	cardPanel.add(mainPage, "Main");
        cardLayout.show(cardPanel, "Main");
        System.out.println("로그인 유저 : "+this.userId);
    }
    
    public void showPostWritePage(Manager mgr) {
    	postWritePage = new PostWritePage(this, mgr, userId);
    	cardPanel.add(postWritePage, "PostWrite");
        cardLayout.show(cardPanel, "PostWrite");
    }

	public void showIndiviBoardPage(int postId, Manager mgr) {
		indiviBoardPage = new IndiviBoardPage(this, postId, mgr, userId, mainPage);
		cardPanel.add(indiviBoardPage, "IndiviBoard");
		cardLayout.show(cardPanel, "IndiviBoard");
	}

}
