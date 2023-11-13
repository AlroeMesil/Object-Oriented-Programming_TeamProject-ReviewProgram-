package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import mgr.Manageable;

public class Post implements Manageable {
	public int postNum; // 게시글 ID
	String postTitle; // 제목
	public String postWriter; // 작성자
	String region; // 지역
	Map<String, String> postCategory = new HashMap<>(); //카테고리
	String postContent; // 본문
	
	// 게시글 생성 메소드
	public void createPost(ArrayList<Manageable> mList, User user) {
		Scanner scan = new Scanner(System.in);
		System.out.println("게시글 제목 / 지역 / 카테고리 / 본문 순으로 입력해주세요.");
		postNum = mList.size()+1;
		postTitle = scan.next();
		region = scan.next();
		postCategory.put("category", scan.next());
		postWriter = user.id;
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

	    System.out.println("게시글이 수정되었습니다.");
	}

	// 게시글 삭제 메소드
	public void deletePost(ArrayList<Manageable> postList, int postId) {
	    for (Manageable post : postList) {
	        if (post instanceof Post && ((Post) post).postNum == postId) {
	            postList.remove(post);
	            System.out.println("게시글이 삭제되었습니다.");
	            return;
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
        postContent = scan.nextLine();
	}

	// 게시글 출력
	@Override
	public void print() {
		// TODO Auto-generated method stub
		System.out.printf("[게시글 ID] %d\n", postNum);
		System.out.printf("[게시글 제목] %s\n", postTitle);
		System.out.printf("[작성자] %s\n", postWriter);
		System.out.printf("[지역] %s\n", region);
		System.out.printf("[카테고리] %s\n", postCategory.get("category"));
		System.out.printf("[본문] %s\n", postContent);
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


}
