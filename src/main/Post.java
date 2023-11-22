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
	public int postNum; // 게시글 ID
	public String postWriter; // 작성자
	String postTitle; // 제목
	String region; // 지역
	String postContent; // 본문
	public Map<String, String> postCategory = new HashMap<>(); //카테고리
	public int postRate; // 게시글 평점
	public ArrayList<String> goodPoint = new ArrayList<>(Arrays.asList()); // 게시글 좋아요
	public ArrayList<String> badPoint = new ArrayList<>(Arrays.asList()); // 게시글 싫어요
	BufferedImage postImage;
	
	// 게시글 생성 메소드
	public void createPost(ArrayList<Manageable> mList, String userId) {
		Scanner scan = new Scanner(System.in);
		System.out.println("게시글 제목 / 지역 / 카테고리 / 평점 / 본문 순으로 입력해주세요.");
		postNum = mList.size()+1;
		postTitle = scan.next();
		region = scan.next();
		postCategory.put("category", scan.next());
		postWriter = userId;
		postRate = scan.nextInt();
        postContent = scan.nextLine();
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
	    postTitle = scan.next();
	    region = scan.next();
	    postCategory.put("category", scan.next());
	    postWriter = scan.next();
	    postRate = scan.nextInt();
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
	    File imageFile = new File("../TeamB_ReviewApp/"+postNum+".png");
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
	public void addGoodPoint(String userId) {
		if (!goodPoint.contains(userId)&&!badPoint.contains(userId)) {
	        goodPoint.add(userId);
	        System.out.println("게시글을 좋아요 했습니다.");
	    } else {
	        System.out.println("이미 좋아요한 게시글입니다.");
	    }
	}
	
	// 게시글 좋아요 삭제 메소드
	public void deleteGoodPoint(String userId) {
		if (goodPoint.contains(userId)) {
	        goodPoint.remove(userId);
	        System.out.println("게시글 좋아요를 취소했습니다.");
	    } else {
	        System.out.println("좋아요한 내역이 없습니다.");
	    }
	}
	
	// 게시글 싫어요 추가 메소드
	public void addBadPoint(String userId) {
		if (!goodPoint.contains(userId)&&!badPoint.contains(userId)) {
			badPoint.add(userId);
	        System.out.println("게시글을 싫어요 했습니다.");
	    } else {
	        System.out.println("이미 좋아요한 게시글입니다.");
	    }
	}
	
	// 게시글 싫어요 삭제 메소드
	public void deleteBadPoint(String userId) {
		if (badPoint.contains(userId)) {
			badPoint.remove(userId);
	        System.out.println("게시글 싫어요를 취소했습니다.");
	    } else {
	        System.out.println("싫어요한 내역이 없습니다.");
	    }
	}

	public ArrayList<String> getGoodPoint() {
		// TODO Auto-generated method stub
		return goodPoint;
	}

	public String getRegion() {
		// TODO Auto-generated method stub
		return region;
	}

	
	public Map<String, String> getPostCategory() {
		// TODO Auto-generated method stub
		return postCategory;
	}
	

}
