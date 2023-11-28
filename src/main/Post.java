package main;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;

import mgr.Manageable;

public class Post implements Manageable {
	private int postNum; // 게시글 ID
	private String postWriter; // 작성자
	private String postTitle; // 제목
	private String region; // 지역
	private String postContent; // 본문
	private Map<String, String> postCategory = new HashMap<>(); //카테고리
	private int postRate; // 게시글 평점
	private ArrayList<String> goodPoint = new ArrayList<>(Arrays.asList()); // 게시글 좋아요
	private ArrayList<String> badPoint = new ArrayList<>(Arrays.asList()); // 게시글 싫어요
	BufferedImage postImage;
	
	// 게시글 생성 메소드
	public void createPost(ArrayList<Manageable> mList, String userId) {
		Scanner scan = new Scanner(System.in);
		System.out.println("게시글 생성");
		postNum = mList.size()+1;
		System.out.print("게시글 제목을 입력하세요 : ");
		postTitle = scan.nextLine();

		System.out.print("지역을 입력하세요: ");
		region = scan.nextLine();

		System.out.print("카테고리를 입력하세요: ");
		postCategory.put("category", scan.nextLine());

		System.out.print("평점을 입력하세요: ");
		postRate = scan.nextInt();

		System.out.print("게시글 본문을 입력하세요: ");
        postContent = scan.nextLine();
		postWriter = userId;
	}

	// 게시글 수정 메소드
	public void updatePost() {
		Scanner scanner = new Scanner(System.in);
	    System.out.print("새로운 게시글 제목을 입력하세요: ");
	    this.postTitle = scanner.nextLine();

	    System.out.print("새로운 지역을 입력하세요: ");
	    this.region = scanner.nextLine();

	    System.out.print("새로운 카테고리를 입력하세요: ");
	    this.postCategory.put("category", scanner.nextLine());

	    System.out.print("새로운 본문을 입력하세요: ");
	    this.postContent = scanner.nextLine();
	    
	    System.out.print("새로운 평점을 입력하세요: ");
	    this.postRate = scanner.nextInt();

	    System.out.println("게시글이 수정되었습니다.");
	}

	// 게시글 삭제 메소드
	public void deletePost(ArrayList<Manageable> postList, int postId, String userId) {
	    Iterator<Manageable> iterator = postList.iterator();
	    while (iterator.hasNext()) {
	        Manageable post = iterator.next();
	        if (post instanceof Post && ((Post) post).postNum == postId) {
	            if (userId.equals(((Post) post).postWriter)) {
	                iterator.remove();
	                System.out.println("게시글이 삭제되었습니다.");
	                return;
	            } else {
	                System.out.println("게시글 작성자가 아닙니다.");
	                return;
	            }
	        }
	    }
	    System.out.println("일치하는 게시글이 없습니다.");
	}

	// 게시글 읽기
	@Override
	public void read(Scanner scan) {
		// TODO Auto-generated method stub
		postNum = scan.nextInt();
	    postTitle = scan.nextLine();
	    region = scan.next();
	    postCategory.put("category", scan.next());
	    postWriter = scan.next();
	    postRate = scan.nextInt();
	    scan.nextLine();
	    postContent = scan.nextLine();
	    while (scan.hasNext()) {
	        String goodPointUserId = scan.next();
	        if ("0".equals(goodPointUserId)) {
	            break;
	        } else {
	            goodPoint.add(goodPointUserId);
	        }
	    }
	    while (scan.hasNext()) {
	        String badPointUserId = scan.next();
	        if ("0".equals(badPointUserId)) {
	            break;
	        } else {
	            badPoint.add(badPointUserId);
	        }
	    }
	    File imageFile = new File("../TeamB_ReviewApp/images/"+postNum+".png"); // 프로젝트 절대 경로 수정 필요
	    try {
	    	postImage = ImageIO.read(imageFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 게시글 출력
	@Override
	public void print() {
		// TODO Auto-generated method stub
		System.out.printf("[게시글 ID] %d\n", postNum);
		System.out.printf("[게시글 제목] %s\n", postTitle);
		System.out.printf("[게시글 사진] %s\n", postImage);
		System.out.printf("[작성자] %s\n", postWriter);
		System.out.printf("[지역] %s\n", region);
		System.out.printf("[카테고리] %s\n", postCategory.get("category"));
		System.out.printf("[본문] %s\n", postContent);
		System.out.printf("[좋아요] %d\n", goodPoint.size());
		System.out.printf("[싫어요] %d\n", badPoint.size());
    }
		
	//게시글 검색
	@Override
	public boolean matches(String kwd) {
	    if (postTitle.contains(kwd) || postWriter.contains(kwd) || region.contains(kwd) || postContent.contains(kwd)) {
	        return true;
	    }
	    
	    if (postCategory.get("category").contains(kwd)) {
	        return true;
	    }
	    
	    return false;
	}
	
	// 게시글 좋아요 추가 메소드
	public String controlGoodPoint(String userId) {
		if (!goodPoint.contains(userId)) {
	        goodPoint.add(userId);
	        System.out.println("게시글을 좋아요 했습니다.");
	        return "add";
	    } else if(goodPoint.contains(userId)) {
	    	goodPoint.remove(userId);
	        System.out.println("게시글 좋아요를 취소했습니다.");
	        return "delete";
	    }
		else {
	        System.out.println("이미 좋아요한 게시글입니다.");
	        return "error";
	    }
	}
	
	// 게시글 좋아요 삭제 메소드 -> UI 부분에서 사용 X
	public boolean deleteGoodPoint(String userId) {
		if (goodPoint.contains(userId)) {
	        goodPoint.remove(userId);
	        System.out.println("게시글 좋아요를 취소했습니다.");
	        return true;
	    } else {
	        System.out.println("좋아요한 내역이 없습니다.");
	        return false;
	    }
	}
	
	// 게시글 싫어요 추가 메소드
	public String controlBadPoint(String userId) {
		if (!badPoint.contains(userId)) {
			badPoint.add(userId);
	        System.out.println("게시글을 싫어요 했습니다.");
	        return "add";
	    } else if(badPoint.contains(userId)) {
	    	badPoint.remove(userId);
	        System.out.println("게시글 좋아요를 취소했습니다.");
	        return "delete";
	    }
	    else {
	        System.out.println("이미 좋아요한 게시글입니다.");
	        return "error";
	    }
	}
	
	// 게시글 싫어요 삭제 메소드 -> UI 부분에서 사용 X
	public void deleteBadPoint(String userId) {
		if (badPoint.contains(userId)) {
			badPoint.remove(userId);
	        System.out.println("게시글 싫어요를 취소했습니다.");
	    } else {
	        System.out.println("싫어요한 내역이 없습니다.");
	    }
	}

	public int getPostNum(){
		return this.postNum;
	}

	public String getRegion() {
		// TODO Auto-generated method stub
		return region;
	}

	public String getPostWriter(){
		return this.postWriter;
	}

	public String getPostTitle(){
		return this.postTitle;
	}
	
	public BufferedImage getPostImage() {
	    return this.postImage;
	}


	public String getPostContent(){
		return this.postContent;
	}

	public int getPostRate(){
		return this.postRate;
	}

	public ArrayList<String> getGoodPoint(){
		return this.goodPoint;
	}

	public ArrayList<String> getBadPoint(){
		return this.badPoint;
	}
	
	public Map<String, String> getPostCategory() {
		// TODO Auto-generated method stub
		return this.postCategory;
	}
}
